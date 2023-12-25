package forum.service;

import forum.model.CommentEntity;
import forum.model.PostEntity;
import forum.model.UserEntity;
import forum.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserService userService;
    private final LocalDate localDate = LocalDate.now();
    private final PostRepository postRepository;
    public void addPost(PostEntity postEntity) {
        postRepository.save(postEntity);
    }
    public Optional<PostEntity> findById(long id) {
        return postRepository.findById(id);
    }

    public List<PostEntity> findPostsByUserLogin(String login) {
        UserEntity userEntity = userService.findByLogin(login).orElseThrow(()->new RuntimeException("User not found"));
        return postRepository.findPostByUserLogin(userEntity.getId());
    }

    public void updatePost(PostEntity postEntity) {
        postRepository.saveAndFlush(postEntity);
    }

    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }

    public void createPost(String postName, String postContent) {
        PostEntity.builder()
                .postName(postName)
                .postContent(postContent)
                .localDate(localDate)
                .comments(new ArrayList<CommentEntity>())
                .user(userService.getCurrentUser()).build();
    }
}
