package forum.controllers;

import org.springframework.web.bind.annotation.RestController;
/**
 * Для постов такой маппинг:
 * GET -- /posts/comments - получаем все комментарии под постами
 * POST -- /posts/id/comments - создание нового комментария
 * GET -- /posts/comments/new - html форма для комментария
 * GET -- /posts/id/comments/id/edit - html форма редактирования комментария
 * GET -- /posts/id/comments/id - получаем комментарий под постом
 * PATCH -- /posts/id/comments/id - обновляем комментарий
 * DELETE - /posts/id/comments/id - удаляем комментарий
 */
@RestController
public class CommentController {
}
