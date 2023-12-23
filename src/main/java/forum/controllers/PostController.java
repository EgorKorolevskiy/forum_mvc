package forum.controllers;

import forum.model.PostEntity;
import forum.model.UserEntity;
import forum.service.PostService;
import forum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @PostMapping("/")
    public String addNewPost(@RequestParam String postName, @RequestParam String postContent) {
        postService.createPost(postName, postContent);
        return "Post added successful";
    }

//    @GetMapping("/{login}")
//    public List<PostEntity> showPostsByLogin(@PathVariable String login) {
//        return postService.findPostsByUserLogin(login);
//    }
}
