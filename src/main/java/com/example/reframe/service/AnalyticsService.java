package com.example.reframe.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.dto.GenderRatioResponseDTO;
import com.example.reframe.entity.account.AccountStatus;
import com.example.reframe.entity.account.AccountType;
import com.example.reframe.entity.auth.Gender;
import com.example.reframe.repository.GenderCountView;
import com.example.reframe.repository.auth.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

	@Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public GenderRatioResponseDTO getProductJoinerGenderRatio() {
        // 1) DB에서 성별별 사용자 수 조회 (상품계좌=PRODUCT & 상태=ACTIVE 보유자만)
        List<GenderCountView> rows =
        		userRepository.countUsersByGenderForActiveProductHolders(
                AccountType.PRODUCT, AccountStatus.ACTIVE);

        // 2) 결과를 (Gender -> count) 맵으로 정리
        Map<Gender, Long> map = new EnumMap<>(Gender.class);
        long unknown = 0L; // gender == null 카운트용

        for (GenderCountView row : rows) {
            if (row.getGender() == null) {
                unknown += row.getCount();
            } else {
                map.put(row.getGender(), row.getCount());
            }
        }

        long male = map.getOrDefault(Gender.M, 0L);   // 남성
        long female = map.getOrDefault(Gender.F, 0L); // 여성
        long total = male + female + unknown;         // 전체(중복 없는 사용자 수)

        // 3) 퍼센트 계산 (소수점 1자리)
        List<String> labels = new ArrayList<>();
        List<Long> data = new ArrayList<>();
        List<Double> percentages = new ArrayList<>();

        add(labels, data, percentages, "남성", male, total);
        add(labels, data, percentages, "여성", female, total);
        add(labels, data, percentages, "미상", unknown, total);

        return new GenderRatioResponseDTO(labels, data, percentages, total);
    }

    // 라벨/건수/비율을 한 번에 리스트에 추가하는 유틸
    private void add(List<String> labels, List<Long> data, List<Double> percentages,
                     String label, long count, long total) {
        labels.add(label);
        data.add(count);
        percentages.add(toPercent(count, total));
    }

    // (count / total) * 100, 소수점 1자리 반올림
    private double toPercent(long count, long total) {
        if (total == 0) return 0.0;
        return BigDecimal.valueOf(count * 100.0 / total)
                         .setScale(1, RoundingMode.HALF_UP)
                         .doubleValue();
    }
}