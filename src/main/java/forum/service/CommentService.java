package forum.service;

import forum.exception.CustomException;
import forum.model.BadWordsEntity;
import forum.model.CommentEntity;
import forum.repositories.BadWordsRepository;
import forum.repositories.CommentRepository;
import forum.utils.DateUtils;
import forum.utils.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    public CommentEntity findCommentById(long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CustomException("Comment not found"));
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
    public ResponseEntity<String> addComment(String commentText, long postId) {
        Optional<String> word = badWordsRepository.findByWord(commentText);
        if (word.isPresent()) {
            commentText = "Нельзя писать такое слово";
        }
        var comment = CommentEntity.builder()
                .commentText(commentText)
                .localDate(DateUtils.getDate())
                .countLikes(0)
                .user(userService.getCurrentUserByPrincipal())
                .post(postService.findById(postId))
                .build();
        saveComment(comment);
        return ResponseEntity.ok("Comment was added");
    }

    /**
     * Добавление лойка под комментарием.
     *
     * @param commentId
     */
    public ResponseEntity<String> addLikeToComment(long commentId) {
        var comment = findCommentById(commentId);
        comment.setCountLikes(comment.getCountLikes() + 1);
        saveComment(comment);
        return ResponseEntity.ok("Like was added");
    }

    /**
     * Обновление комментария. Проверка на собственника и на разрешенные роли юзера для обновления
     *
     * @param commentId
     * @param commentText
     * @return
     */

    public ResponseEntity<String> updateComment(long commentId, String commentText) {
        var currentUser = userService.getCurrentUserByPrincipal();
        var owner = findUserLoginByCommentId(commentId);
        if (currentUser == null) {
            throw new CustomException("Unauthorized user");
        }
        var userAuthorities = UtilService.getUserAuthorities();
        if (currentUser.getLogin().equals(owner) || UtilService.hasAdminOrModeratorRole(userAuthorities)) {
            var comment = findCommentById(commentId);
            comment.setCommentText(commentText);
            saveComment(comment);
            return ResponseEntity.ok( String.format("%s update successfully comment %d", currentUser.getLogin(), commentId));
        }
        throw new CustomException("You have no roots for this");
    }

    /**
     * Удаление комментария. Проверяет, принадлежит ли комментарий собственнику или юзер является модером или админом
     *
     * @param commentId
     * @return
     */

    public ResponseEntity<String> deleteComment(long commentId) {
        var currentUser = userService.getCurrentUserByPrincipal();
        var owner = findUserLoginByCommentId(commentId);
        if (currentUser == null) {
            throw new CustomException("Unauthorized user");
        }
        var userAuthorities = UtilService.getUserAuthorities();
        if (currentUser.getLogin().equals(owner) || UtilService.hasAdminOrModeratorRole(userAuthorities)) {
            deleteCommentById(commentId);
            return ResponseEntity.ok(String.format("Comment %d was deleted", commentId));
        }
        throw new CustomException("You have no roots for this");
    }
}
