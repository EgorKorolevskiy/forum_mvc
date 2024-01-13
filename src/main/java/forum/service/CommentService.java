package forum.service;

import forum.model.BadWordsEntity;
import forum.model.CommentEntity;
import forum.repositories.BadWordsRepository;
import forum.repositories.CommentRepository;
import forum.utils.DateUtils;
import forum.utils.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserService userService;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final BadWordsRepository badWordsRepository;

    public void saveComment(CommentEntity commentEntity) {
        commentRepository.save(commentEntity);
    }

    public Optional<CommentEntity> findCommentById(long id) {
        return commentRepository.findById(id);
    }

    public Optional<CommentEntity> findById(long id) {
        return commentRepository.findById(id);
    }

    private void deleteCommentById(long id) {
        commentRepository.deleteById(id);
    }

    public String findUserLoginByCommentId(long commentId) {
        return commentRepository.findUserLoginByCommentId(commentId);
    }

    /**
     * Добавляется новый комментарий только у авторизованного юзера. У коммента есть привязка к посту и к юзеру
     *
     * @param commentText
     * @param postId
     */
    public void addComment(String commentText, long postId) {
        var badWordsList = badWordsRepository.findByWord(commentText);
        if (!badWordsList.isEmpty()) {
            commentText = "Нельзя писать такое слово";
        }
        var comment = CommentEntity.builder()
                .commentText(commentText)
                .localDate(DateUtils.getDate())
                .countLikes(0)
                .user(userService.getCurrentUserByPrincipal())
                .post(postService.findById(postId).orElseThrow(() -> new RuntimeException("Пост не найден")))
                .build();
        saveComment(comment);
    }

    /**
     * Добавление лойка под комментарием.
     *
     * @param commentId
     */
    public void addLikeToComment(long commentId) {
        var comment = findCommentById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setCountLikes(comment.getCountLikes() + 1);
        saveComment(comment);
    }

    /**
     * Обновление комментария. Проверка на собственника и на разрешенные роли юзера для обновления
     *
     * @param commentId
     * @param commentText
     * @return
     */

    public String updateComment(long commentId, String commentText) {
        var currentUser = userService.getCurrentUserByPrincipal();
        var owner = findUserLoginByCommentId(commentId);
        if (currentUser == null) {
            return "Unauthorized user";
        }
        var userAuthorities = userService.getUserAuthorities();
        if (currentUser.getLogin().equals(owner) || UtilService.hasAdminOrModeratorRole(userAuthorities)) {
            var comment = findCommentById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
            comment.setCommentText(commentText);
            saveComment(comment);
            return String.format("%s update successfully comment %d", currentUser.getLogin(), commentId);
        }
        return "You have no roots for this";
    }

    /**
     * Удаление комментария. Проверяет, принадлежит ли комментарий собственнику или юзер является модером или админом
     *
     * @param commentId
     * @return
     */

    public String deleteComment(long commentId) {
        var currentUser = userService.getCurrentUserByPrincipal();
        var owner = findUserLoginByCommentId(commentId);
        if (currentUser == null) {
            return "Unauthorized user";
        }
        var userAuthorities = userService.getUserAuthorities();
        if (currentUser.getLogin().equals(owner) || UtilService.hasAdminOrModeratorRole(userAuthorities)) {
            deleteCommentById(commentId);
            return String.format("Comment %d was deleted", commentId);
        }
        return "You have no roots for this";
    }
}
