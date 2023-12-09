package forum.post.service;

import forum.post.model.Post;
import forum.post.model.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public void addPost(Post post) {
        postRepository.save(post);
    }
    public Post findById(long id) {
        return postRepository.findById(id);
    }

    public void updatePost(Post post) {
        postRepository.saveAndFlush(post);
    }

    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }
}
