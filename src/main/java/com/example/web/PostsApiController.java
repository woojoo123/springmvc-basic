package com.example.web;

import com.example.api.BadRequestException;
import com.example.domain.Post;
import com.example.repo.PostStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostsApiController {

    // GET /posts -> JSON 목록
    @GetMapping
    public List<Post> list() {
        return PostStore.findAll(); // List<Post>를 그대로 반환
    }

    // POST /posts -> JSON 생성
    @PostMapping
    public ResponseEntity<Post> create(@RequestBody CreatePostRequest body) {
        if (body == null ||
            body.title == null || body.title.trim().isEmpty() ||
            body.content == null || body.content.trim().isEmpty()) {
            throw new BadRequestException("VALIDATION_ERROR", "title과 content는 필수입니다.");
        }

        Post created = PostStore.add(body.title.trim(), body.content.trim());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // JSON 요청 바디 매핑용 DTO (내부 클래스)
    public static class CreatePostRequest {
        public String title;
        public String content;
    }
}
