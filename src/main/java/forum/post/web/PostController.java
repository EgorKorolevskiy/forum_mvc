package forum.post.web;

import org.springframework.web.bind.annotation.RestController;
/**
 * Для постов такой маппинг:
 * GET -- /posts - получаем все посты
 * POST -- /posts - создание нового поста
 * GET -- /posts/new - html форма для поста
 * GET -- /posts/id/edit - html форма редактирования поста
 * GET -- /posts/id - получаем один пост
 * PATCH -- /posts/id - обновляем пост
 * DELETE - /posts/id - удаление поста
 */
@RestController
public class PostController {
}
