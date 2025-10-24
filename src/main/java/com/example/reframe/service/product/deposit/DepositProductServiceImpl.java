package com.example.reframe.service.product.deposit;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;                      // ✅ 추가
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;    // ✅ (기존 사용 중이면 유지)
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reframe.dto.product.deposit.DepositProductDTO;
import com.example.reframe.dto.product.deposit.ProductInputFormatDTO;
import com.example.reframe.entity.product.deposit.DepositProduct;
import com.example.reframe.entity.product.deposit.ProductInputFormat;
import com.example.reframe.entity.product.deposit.ProductViewLog;
import com.example.reframe.repository.deposit.ProductInputFormatRepository;
import com.example.reframe.repository.deposit.ProductViewLogRepository;    // ✅ 추가
import com.example.reframe.repository.product.deposit.DepositProductRepository;
import com.example.reframe.service.document.DocumentService;
import com.example.reframe.util.DocumentMapper;
import com.example.reframe.util.MarkdownUtil;
import com.example.reframe.util.ProductInputFormatMapper;
import com.example.reframe.util.SetProductContent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepositProductServiceImpl implements DepositProductService {

    private final DepositProductRepository depositProductRepository;
    private final ProductInputFormatRepository productInputFormatRepository;
    private final ProductViewLogRepository productViewLogRepository;   // ✅ 추가

    private final DocumentService documentService;
    private final EntityManager em;

    private SetProductContent setProductContent = new SetProductContent();
    private DocumentMapper documentMapper = new DocumentMapper();
    private ProductInputFormatMapper productInputFormatMapper = new ProductInputFormatMapper();

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    @Override
    public List<DepositProductDTO> getAllProducts(String status, String category) {
        List<DepositProduct> products;

        if (status != null && category != null) {
            products = depositProductRepository.findAll().stream()
                    .filter(p -> p.getStatus().equals(status) && p.getCategory().equals(category))
                    .collect(Collectors.toList());
        } else if (status != null) {
            products = depositProductRepository.findByStatus(status);
        } else if (category != null) {
            products = depositProductRepository.findByCategory(category);
        } else {
            products = depositProductRepository.findAll();
        }

        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<DepositProductDTO> searchProducts(String keyword) {
        List<DepositProduct> products = depositProductRepository.findByNameContaining(keyword);
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Long saveProduct(DepositProductDTO dto) {
        DepositProduct product = convertToEntity(dto);
        depositProductRepository.save(product);
        return product.getProductId();
    }

    @Override
    public Long updateProduct(Long productId, DepositProductDTO dto) {
        DepositProduct product = depositProductRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + productId));

        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setPurpose(dto.getPurpose());
        product.setSummary(dto.getSummary());
        product.setDetail(dto.getDetail());
        product.setModalDetail(dto.getModalDetail()); // ✅ modalDetail 업데이트
        product.setMaxRate(dto.getMaxRate());
        product.setMinRate(dto.getMinRate());
        product.setPeriod(dto.getPeriod());
        product.setStatus(dto.getStatus());

        depositProductRepository.save(product);
        return product.getProductId();
    }

    @Override
    public void deleteProduct(Long productId) {
        depositProductRepository.deleteById(productId);
    }

    @Override
    public List<DepositProductDTO> getProductsByPurpose(String purpose) {
        List<DepositProduct> products = depositProductRepository.findByPurposeAndStatus(purpose, "S");
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<DepositProductDTO> getRecommendedProducts() {
        List<DepositProduct> products = depositProductRepository.findByStatus("S");
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // ✅ 📌 추가: 페이지네이션 + 정렬 + 검색 통합 메서드
    @Override
    public Page<DepositProductDTO> getPagedProducts(String status, String category, String keyword, String sort, int page) {
        Pageable pageable;

        if ("rate".equals(sort)) {
            pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "maxRate"));
        } else { // 기본 추천순
            pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "viewCount"));
        }

        Page<DepositProduct> entityPage;

        if (keyword != null && !keyword.isBlank()) {
            entityPage = depositProductRepository.findByStatusAndNameContainingOrStatusAndSummaryContaining(
                    status, keyword, status, keyword, pageable
            );
        } else if (category != null && !category.isBlank()) {
            entityPage = depositProductRepository.findByStatusAndCategory(status, category, pageable);
        } else {
            entityPage = depositProductRepository.findByStatus(status, pageable);
        }

        return entityPage.map(this::convertToDTO);
    }

    // ===== DTO 변환 유틸 =====
    private DepositProductDTO convertToDTO(DepositProduct p) {
        return DepositProductDTO.builder()
                .productId(p.getProductId())
                .name(p.getName())
                .category(p.getCategory())
                .purpose(p.getPurpose())
                .summary(p.getSummary())
                .detail(p.getDetail())
                .modalDetail(p.getModalDetail()) // ✅ modalDetail DTO에 포함
                .maxRate(p.getMaxRate())
                .minRate(p.getMinRate())
                .period(p.getPeriod())
                .status(p.getStatus())
                .createdAt(formatDate(p.getCreatedAt()))
                .viewCount(p.getViewCount())
                .build();
    }

    private DepositProduct convertToEntity(DepositProductDTO dto) {
        DepositProduct p = new DepositProduct();
        p.setName(dto.getName());
        p.setCategory(dto.getCategory());
        p.setPurpose(dto.getPurpose());
        p.setSummary(dto.getSummary());
        p.setDetail(dto.getDetail());
        p.setModalDetail(dto.getModalDetail()); // ✅ modalDetail 엔티티에 설정
        p.setMaxRate(dto.getMaxRate());
        p.setMinRate(dto.getMinRate());
        p.setPeriod(dto.getPeriod());
        p.setStatus(dto.getStatus() != null ? dto.getStatus() : "S");
        p.setCreatedAt(new Date());
        p.setViewCount(0L);
        return p;
    }

    @Override
    @Transactional
    public DepositProductDTO getProductDetail(Long productId) throws JsonMappingException, JsonProcessingException {
        DepositProduct product = depositProductRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));

        String detail = setProductContent.setDepositDetail(product.getDetail()); // Json → HTML
        String modalDetail = MarkdownUtil.toHtml(product.getModalDetail());      // MarkDown → HTML
        String modalRate = MarkdownUtil.toHtml(product.getModalRate());          // MarkDown → HTML

        List<String> termImages = new ArrayList<>();
        List<String> manualImages = new ArrayList<>();

        if (product.getTerm() != null) {
            termImages = documentService.getImages(product.getTerm().getDocumentId());   // 약관 이미지 조회
        }
        if (product.getManual() != null) {
            manualImages = documentService.getImages(product.getManual().getDocumentId());// 상품설명서 이미지 조회
        }

        // ✅ 조회수 증가 (null-safe)
        Long vc = product.getViewCount() == null ? 0L : product.getViewCount();
        product.setViewCount(vc + 1);
        depositProductRepository.save(product);

        // ✅ 조회 로그 적재
        logProductView(product, /*userId*/ null);

        // Entity → DTO 변환
        return DepositProductDTO.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .summary(product.getSummary())
                .detail(product.getDetail())
                .modalDetail(modalDetail)
                .modalRate(modalRate)
                .detail(detail)
                .maxRate(product.getMaxRate())
                .minRate(product.getMinRate())
                .period(product.getPeriod())
                .viewCount(product.getViewCount())
                .imageUrl(product.getImageUrl())
                .termImages(termImages)
                .manualImages(manualImages)
                .build();
    }

    @Override
    @Transactional
    public DepositProductDTO getProductDetail2(Long productId) throws JsonMappingException, JsonProcessingException {
        DepositProduct product = depositProductRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));

        String modalDetail = MarkdownUtil.toHtml(product.getModalDetail()); // MarkDown → HTML
        String modalRate = MarkdownUtil.toHtml(product.getModalRate());     // MarkDown → HTML

        List<String> termImages = new ArrayList<>();
        List<String> manualImages = new ArrayList<>();

        if (product.getTerm() != null) {
            termImages = documentService.getImages(product.getTerm().getDocumentId());
        }
        if (product.getManual() != null) {
            manualImages = documentService.getImages(product.getManual().getDocumentId());
        }

        // ✅ 조회수 증가 (null-safe)
        Long vc = product.getViewCount() == null ? 0L : product.getViewCount();
        product.setViewCount(vc + 1);
        depositProductRepository.save(product);

        // ✅ 조회 로그 적재
        logProductView(product, /*userId*/ null);

        // Entity → DTO 변환
        return DepositProductDTO.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .summary(product.getSummary())
                .category(product.getCategory())
                .detail(product.getDetail())
                .modalDetail(modalDetail)
                .modalRate(modalRate)
                .detail(product.getDetail())
                .maxRate(product.getMaxRate())
                .minRate(product.getMinRate())
                .period(product.getPeriod())
                .viewCount(product.getViewCount())
                .imageUrl(product.getImageUrl())
                .termImages(termImages)
                .manualImages(manualImages)
                .term(documentMapper.toDTO(product.getTerm()))
                .manual(documentMapper.toDTO(product.getManual()))
                .paymentCycle(product.getPaymentCycle())
                .minPeriodMonths(product.getMinPeriodMonths())
                .maxPeriodMonths(product.getMaxPeriodMonths())
                .termList(product.getTermList())
                .termMode(product.getTermMode())
                .build();
    }

    @Override
    public List<DepositProductDTO> getProductsByCategory(String category) {
        List<DepositProduct> products = depositProductRepository.findByCategoryAndStatus(category, "S");
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<DepositProductDTO> getThemeRecommended(String theme) {
        List<String> purposes = new ArrayList<>();

        switch (theme) {
            case "직장인" -> purposes = List.of("청년자립자금", "사업자전용", "단기자금운용", "월급통장", "기업자금운용");
            case "주부" -> purposes = List.of("육아자금", "출산자금", "자녀교육자금", "가계우대");
            case "학생" -> purposes = List.of("청소년자금관리", "학생우대", "공동상품/학생우대");
        }

        List<DepositProduct> result = depositProductRepository
                .findByPurposeInAndStatus(purposes, "S");

        return result.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<DepositProductDTO> getTopFiveByViewCount() {
        List<DepositProduct> depositList = depositProductRepository.findTopFiveByViewCount();
        return depositList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public List<DepositProductDTO> searchByKeywords(String keywords) {
        String[] words = keywords.split(" ");
        StringBuilder sql = new StringBuilder("SELECT * FROM deposit_product WHERE 1=1 ");

        for (int i = 0; i < words.length; i++) {
            sql.append("AND (name LIKE :word").append(i)
               .append(" OR summary LIKE :word").append(i)
               .append(" OR detail LIKE :word").append(i).append(") ");
        }

        Query query = em.createNativeQuery(sql.toString(), DepositProduct.class);
        for (int i = 0; i < words.length; i++) {
            query.setParameter("word" + i, "%" + words[i] + "%");
        }

        @SuppressWarnings("unchecked")
        List<DepositProduct> result = query.getResultList();

        return result.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<String> findSuggestions(String keyword) {
        List<String> allNames = depositProductRepository.findAllNames();
        String lowerKeyword = keyword.toLowerCase();

        return allNames.stream()
                .filter(name -> name.toLowerCase().contains(lowerKeyword))
                .limit(10)
                .toList();
    }

    @Override
    public ProductInputFormatDTO getProductInputFormat(Long productId) {
        Optional<ProductInputFormat> optional = productInputFormatRepository.findById(productId);

        if (optional.isEmpty())
            return null;

        ProductInputFormat inputFormat = optional.get();

        return productInputFormatMapper.toDTO(inputFormat);
    }

    /* ====================== 내부 유틸 ====================== */

    /** 조회 로그 적재 (필요시 userId 주입) */
    private void logProductView(DepositProduct product, Long userId) {
        ProductViewLog log = new ProductViewLog();
        log.setProduct(product);                 // FK 연관
        log.setViewedAt(LocalDateTime.now());    // 필요하면 Asia/Seoul 기준으로 조정
        log.setUserId(userId);                   // 로그인 사용자 ID가 있으면 전달
        productViewLogRepository.save(log);
    }
}
