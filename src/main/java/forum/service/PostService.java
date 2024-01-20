package forum.service;

import forum.dto.PostDto;
import forum.exception.CustomException;
import forum.mapper.CommentMapper;
import forum.mapper.PostMapper;
import forum.model.CommentEntity;
import forum.model.PostEntity;
import forum.repositories.PostRepository;
import forum.utils.DateUtils;
import forum.utils.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserService userService;
    private final PostRepository postRepository;

    public void addPost(PostEntity postEntity) {
        postRepository.save(postEntity);
    }

    public Optional<PostEntity> findById(long id) {
        return postRepository.findById(id);
    }

    public String findUserLoginByPostId(long postId) {
        return postRepository.findUserLoginByPostId(postId);
    }

    public List<PostEntity> findAll() {
        return postRepository.findAll();
    }

    public List<PostEntity> findByDate(LocalDate date) {
        return postRepository.findByCreationDate(date);
    }


    public List<PostEntity> findPostsByUserLogin(String login) {
        var userEntity = userService.findByLogin(login).orElseThrow(() -> new CustomException("User not found"));
        return postRepository.findPostByUserLogin(userEntity.getId());
    }

    /**
     * Находим все посты и комментарии под постами
     *
     * @return
     */
    public List<PostEntity> findAllPostsWithComments() {
       return findAll().stream()
                .peek(post -> {
                    List<CommentEntity> comments = post.getComments();
                    post.setComments(comments);
                })
                .toList();
    }

    public void deleteById(long id) {
        postRepository.deleteById(id);
    }

    /**
     * Обновление поста. Только собственник поста либо любой модер, админ
     *
     * @param postId
     * @param postName
     * @param postContent
     * @return
     */
    public ResponseEntity<String> updatePost(Long postId, String postName, String postContent) {
        // получаем авторизованного юзера
        var currentUser = userService.getCurrentUserByPrincipal();
        // получаем логин собственника поста
        final var owner = findUserLoginByPostId(postId);
        // проверка на нул
        if (currentUser == null) {
            throw new CustomException("Unauthorized user");
        }
        // проверка на собственника поста и роли через Spring Security
        // Получение всех ролей пользователя
        var userAuthorities = UtilService.getUserAuthorities();
        if (currentUser.getLogin().equals(owner) || UtilService.hasAdminOrModeratorRole(userAuthorities)) {
            // Находим пост по id
            var post = findById(postId).orElseThrow(() -> new CustomException("Post not found"));
            post.setPostName(postName);
            post.setPostContent(postContent);
            addPost(post);
            return ResponseEntity.ok(String.format("%s update successfully post %d", currentUser.getLogin(), postId));
        }
        throw new CustomException("You have no roots for this");
    }

    /**
     * Удаление поста по id в бд. Только собственник поста либо любой модер, админ
     *
     * @param postId
     * @return
     */
    public ResponseEntity<String> deletePostById(long postId) {
        // получаем авторизованного юзера
        var currentUser = userService.getCurrentUserByPrincipal();
        // получаем логин собственника поста
        var owner = findUserLoginByPostId(postId);
        // проверка на нул
        if (currentUser == null) {
            throw new CustomException("Unauthorized user");
        }
        // Получение всех ролей пользователя
        var userAuthorities = UtilService.getUserAuthorities();
        if (currentUser.getLogin().equals(owner) || UtilService.hasAdminOrModeratorRole(userAuthorities)) {
            deleteById(postId);
            return ResponseEntity.ok(String.format("Post %d was deleted", postId));
        }
        throw new CustomException("You have no roots for this");
    }

    /**
     * Создание поста юзера
     *
     * @param postName
     * @param postContent
     */
    public ResponseEntity<String> createPost(String postName, String postContent) {
        var post = PostEntity.builder()
                .postName(postName)
                .postContent(postContent)
                .localDate(DateUtils.getDate())
                .comments(new ArrayList<>())
                .user(userService.getCurrentUserByPrincipal())
                .build();
        addPost(post);
        return ResponseEntity.ok("Post was added");
    }
}