// com/example/reframe/service/FortuneService.java
package com.example.reframe.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.reframe.dto.fortune.FortuneRequest;
import com.example.reframe.dto.fortune.FortuneResponse;
import com.example.reframe.dto.fortune.ProductBrief;
import com.example.reframe.entity.DepositProduct;
import com.example.reframe.entity.FortuneKeyword;
import com.example.reframe.entity.KeywordRecommendation;
import com.example.reframe.repository.KeywordRecommendationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FortuneService {

    private final OpenAIService openAIService;
    private final KeywordRecommendationRepository keywordRepo;
    private final ObjectMapper om = new ObjectMapper();

    public FortuneResponse generateFortune(FortuneRequest req) {
        // 허용 키워드 라벨 목록을 enum에서 직접 가져옴
        final String allowed = String.join(" / ", FortuneKeyword.allowedLabels());

        String birth = req.getBirthDate();
        String dateIso = req.getDate() != null ? req.getDate().toLocalDate().toString() : "2025-08-20";

        String system = """
        너는 은행 앱의 '오늘의 운세' 생성기야.
        반드시 JSON만 출력해. 다른 텍스트, 설명, 코드블록, 접두/접미 문장 금지.
        출력 스키마:
        {
          "fortune": "<한 문장, 반드시 '~하기 좋은 하루' 형식으로 끝>",
          "keyword": "<하나의 단어>"
        }
        keyword 는 다음 중 반드시 하나: %s
        """.formatted(allowed);

        String user = String.format(
            "나는 %s에 태어났어. 이걸 기준으로 %s 운세를 '~하기 좋은 하루' 형식의 한 문장으로 말해줘. 그리고 운세의 요약을 위 목록 중 한 단어(keyword)로 골라줘. 응답은 JSON만.",
            birth, dateIso
        );

        String raw = openAIService.askChatGPT2WithSystem(system, user);

        Map<String, Object> map;
        try {
            map = om.readValue(raw, Map.class);
        } catch (Exception e) {
            map = Map.of("fortune", "계획을 정리하기 좋은 하루예요.", "keyword", "안정");
        }

        String fortune = String.valueOf(map.getOrDefault("fortune", "습관을 다듬기 좋은 하루예요."));
        String keywordLabel = String.valueOf(map.getOrDefault("keyword", "안정")).trim();

        // 한국어 라벨 -> enum 상수로 안전 변환
        FortuneKeyword keyword = FortuneKeyword.fromLabel(keywordLabel).orElse(FortuneKeyword.ANJEONG);

        // 키워드 매핑 조회
        KeywordRecommendation kr = keywordRepo.findByKeyword(keyword)
            .orElseThrow(() -> new IllegalStateException("키워드 매핑이 없습니다: " + keyword));

        DepositProduct p1 = kr.getProduct1();
        DepositProduct p2 = kr.getProduct2();

        List<ProductBrief> briefs = List.of(
            ProductBrief.builder()
                .productId(p1.getProductId())
                .name(p1.getName())
                .category(p1.getCategory())
                .summary(StringUtils.hasText(p1.getSummary()) ? p1.getSummary() : "")
                .build(),
            ProductBrief.builder()
                .productId(p2.getProductId())
                .name(p2.getName())
                .category(p2.getCategory())
                .summary(StringUtils.hasText(p2.getSummary()) ? p2.getSummary() : "")
                .build()
        );

        // 응답의 keyword는 한국어 라벨로 반환
        return FortuneResponse.builder()
                .fortune(fortune)
                .keyword(keyword.label())
                .products(briefs)
                .build();
    }
}
