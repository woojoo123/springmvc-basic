package com.example.repo;

import com.example.domain.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class PostStore {
    private static final List<Post> items = Collections.synchronizedList(new ArrayList<>());
    private static final AtomicLong seq = new AtomicLong(0);

    public static List<Post> findAll() {
        synchronized (items) {
            return new ArrayList<>(items); // 복사본 반환
        }
    }

    public static Post add(String title, String content) {
        long id = seq.incrementAndGet();
        Post p = new Post(id, title, content);
        items.add(p);
        return p;
    }
}
