package forum.controllers;

import forum.dto.CommentDto;
import forum.exception.CustomException;
import forum.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Для постов такой маппинг:
 * POST -- /comment/add/{postId} - Добавление нового комментария под постом
 * PUT -- /comment/add/like/{commentId} - Добавление лайка к комментарию
 * PUT -- /comment/update/{commentId} - Обновление комментария. Обновлять может либо автор, либо юзеры с определенным ролями (ADMIN, MODER)
 * DEL -- /comment/delete/{commentId} - Удаление комментария. Удалять может либо автор, либо юзеры с определенным ролями (ADMIN, MODER)
 * <p>
 * (Тестовые варианты другие)
 * POST -- /add/{postId} - Добавление нового комментария под постом
 * POST -- /posts/id/comments - создание нового комментария
 * GET -- /posts/comments/new - html форма для комментария
 * GET -- /posts/id/comments/id/edit - html форма редактирования комментария
 * GET -- /posts/id/comments/id - получаем комментарий под постом
 * PATCH -- /posts/id/comments/id - обновляем комментарий
 * DELETE - /posts/id/comments/id - удаляем комментарий
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    /**
     * Добавление нового комментария под постом
     *
     * @param commentDto
     * @param postId
     * @return
     */
    @PostMapping("/add/{postId}")
    public ResponseEntity<String> addNewComment(@RequestBody CommentDto commentDto, @PathVariable Long postId) {
        return commentService.addComment(commentDto.getCommentText(), postId);
    }

    /**
     * Добавление лайка к комментарию
     *
     * @param commentId
     * @return
     */
    @PutMapping("/add/like/{commentId}")
    public ResponseEntity<String> addLikeToComment(@PathVariable Long commentId) {
       return commentService.addLikeToComment(commentId);
    }

    /**
     * Обновление комментария. Обновлять может либо автор, либо юзеры с определенным ролями (ADMIN, MODER)
     *
     * @param commentId
     * @param commentDto
     * @return
     */
    @PutMapping("/update/{commentId}")
    public ResponseEntity<String> updateCommentById(@PathVariable Long commentId, @RequestBody CommentDto commentDto) {
        return commentService.updateComment(commentId, commentDto.getCommentText());
    }

    /**
     * Удаление комментария. Удалять может либо автор, либо юзеры с определенным ролями (ADMIN, MODER)
     *
     * @param commentId
     * @return
     */
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }
}
