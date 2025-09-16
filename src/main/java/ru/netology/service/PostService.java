package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(
                () -> new NotFoundException("Post with ID " + id + " not found or is removed.")
        );
    }

    public Post save(Post post) {
        if (post.getId() != 0) {
            Post existingPost = repository.getById(post.getId())
                    .orElseThrow(() -> new NotFoundException("Post with ID " + post.getId() + " not found or is removed for update."));
            existingPost.setContent(post.getContent());
            return repository.save(existingPost);
        }

        return repository.save(post);
    }

    public void removeById(long id) {
        Post postToRemove = repository.getById(id).orElseThrow(
                () -> new NotFoundException("Post with ID " + id + " not found or already removed for deletion.")
        );
        repository.removeById(id);
    }
}