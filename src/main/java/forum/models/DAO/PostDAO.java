package forum.models.DAO;

import forum.models.Post;
import forum.models.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostDAO {
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
