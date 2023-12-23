package forum.controllers;

import forum.dto.PostDto;
import forum.mapper.PostMapper;
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
//TODO во всех RequestMapping заменить не единственное число
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    //TODO не оставлять пустые эндпоинты
    @PostMapping("/add")
    public String addNewPost(@RequestParam String postName, @RequestParam String postContent) {
        postService.createPost(postName, postContent);
        return "Post added successful";
    }

    @PostMapping("/add/json")
    public String addNewPost(@RequestBody PostDto postDto) {
        postService.createPost(postDto.getPostName(), postDto.getPostContent());
        return "Post added successful";
    }

    //TODO возвращать объекты в виде DTO
    @GetMapping("/{login}")
    public List<PostDto> showPostsByLogin(@PathVariable String login) {
        return postService.findPostsByUserLogin(login).stream().map(PostMapper::mapToDto).toList();
    }
}
