package com.example.reframe.api.qna;

import com.example.reframe.auth.CurrentUser;
import com.example.reframe.entity.Qna;
import com.example.reframe.entity.auth.User;
import com.example.reframe.repository.QnaRepository;
import com.example.reframe.repository.auth.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/mobile/qna")
@RequiredArgsConstructor
public class QnaApiController {

    private final QnaRepository qnaRepository;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;

    @GetMapping
    public ResponseEntity<List<QnaApiResponse>> myQnaList() {
        Long uid = requireUser();
        var list = qnaRepository.findByUserId(uid)
                .stream()
                .map(QnaApiResponse::from)
                .toList();
        return ResponseEntity.ok(list);
    }


    @GetMapping("/{qnaId}")
    public ResponseEntity<QnaApiResponse> detail(@PathVariable("qnaId") Integer qnaId) {
        Long uid = requireUser();
        var qna = qnaRepository.findByQnaIdAndUserId(qnaId, uid)
                .orElseThrow(() -> new IllegalArgumentException("Q&A not found"));
        return ResponseEntity.ok(QnaApiResponse.from(qna));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<QnaApiResponse> create(@Valid @RequestBody QnaApiRequest req) {
        Long uid = requireUser();
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        Qna q = Qna.builder()
                .user(user)
                .category(req.getCategory())
                .title(req.getTitle())
                .content(req.getContent())
                .answer(null)
                .status("대기중")
                .build();

        qnaRepository.save(q);
        var body = QnaApiResponse.from(q);
        return ResponseEntity.created(URI.create("/mobile/qna/" + q.getQnaId())).body(body);
    }

    @PutMapping("/{qnaId}")
    @Transactional
    public ResponseEntity<QnaApiResponse> updateMyQuestion(@PathVariable("qnaId") Integer qnaId,
                                                           @Valid @RequestBody QnaApiRequest req) {
        Long uid = requireUser();
        Qna q = qnaRepository.findByQnaIdAndUserId(qnaId, uid)
                .orElseThrow(() -> new IllegalArgumentException("Q&A not found"));

        // 답변이 달리면 수정 잠금 예시(원하지 않으면 이 조건 제거)
        if (q.getAnswer() != null && !q.getAnswer().isBlank()) {
            throw new IllegalStateException("답변 완료된 항목은 수정할 수 없습니다.");
        }

        q.setCategory(req.getCategory());
        q.setTitle(req.getTitle());
        q.setContent(req.getContent());
        q.setStatus("대기중");
        return ResponseEntity.ok(QnaApiResponse.from(q));
    }

    @DeleteMapping("/{qnaId}")
    @Transactional
    public ResponseEntity<Void> deleteMyQuestion(@PathVariable("qnaId") Integer qnaId) {
        Long uid = requireUser();
        Qna q = qnaRepository.findByQnaIdAndUserId(qnaId, uid)
                .orElseThrow(() -> new IllegalArgumentException("Q&A not found"));
        qnaRepository.delete(q);
        return ResponseEntity.noContent().build();
    }

    private Long requireUser() {
        Long id = currentUser.id();
        if (id == null) throw new IllegalStateException("Unauthorized");
        return id;
    }
}
