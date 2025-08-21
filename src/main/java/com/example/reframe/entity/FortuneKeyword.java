// com/example/reframe/entity/FortuneKeyword.java
package com.example.reframe.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum FortuneKeyword {
    MIRAE("미래"),
    NAEIL("내일"),
    KKUJUNHAM("꾸준함"),
    NORYEOK("노력"),
    JAYU("자유"),
    SEONTAEK("선택"),
    YOONYEONHAM("유연함"),
    ANJEONG("안정"),
    DEUNDEUN("든든"),
    SARANG("사랑"),
    HAEINGBOK("행복"),
    HUEGMANG("희망"),
    DOJEON("도전"),
    SEONGCHI("성취"),
    TEUKBYEOL("특별"),
    GIHOE("기회"),
    GEONGANG("건강"),
    HWARYEOK("활력"),
    NANUM("나눔"),
    HAMKKE("함께"),
    SEUNGNI("승리"),
    YEOLJEONG("열정");

    private final String label; // 한국어 표시용

    FortuneKeyword(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public static Optional<FortuneKeyword> fromLabel(String label) {
        if (label == null) return Optional.empty();
        String t = label.trim();
        return Arrays.stream(values())
                .filter(k -> k.label.equals(t))
                .findFirst();
    }

    public static List<String> allowedLabels() {
        return Arrays.stream(values()).map(FortuneKeyword::label).toList();
    }
}
