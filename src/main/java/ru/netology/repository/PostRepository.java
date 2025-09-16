package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PostRepository {
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong postIdCounter = new AtomicLong(0);

    public List<Post> all() {
        return posts.values().stream()
                .filter(post -> !post.isRemoved())
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<Post> getById(long id) {
        Post post = posts.get(id);
        return Optional.ofNullable(post)
                .filter(p -> !p.isRemoved());
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            final long newId = postIdCounter.incrementAndGet();
            post.setId(newId);
            post.setRemoved(false);
            posts.put(newId, post);
        } else {
            posts.put(post.getId(), post);
        }
        return post;
    }

    public void removeById(long id) {
        Optional.ofNullable(posts.get(id))
                .ifPresent(post -> post.setRemoved(true));
    }
}