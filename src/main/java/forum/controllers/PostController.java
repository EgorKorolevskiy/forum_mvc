package forum.controllers;

import forum.dto.PostDto;
import forum.mapper.PostMapper;
import forum.model.UserEntity;
import forum.service.PostService;
import forum.service.UserService;
import forum.utils.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Для постов такой маппинг:
 * GET -- /post/{login} - Возвращает посты юзера по логину
 * GET -- /post/all - Возвращаем все посты
 * GET -- /post/all/post_comments - Возвращаем все посты и комментарии под постом
 * GET -- /post/date/{date} - Возвращаем все посты по дате
 * POST -- /post/add - Добавляет пост в БД
 * PUT -- /post/update/{postId} - Обновление поста юзера через id поста в БД.
 * DEL -- /post/delete/{postId} - Удаление поста через id поста в БД
 * <p>
 * (тустовые старые варианты)
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

    /**
     * Возвращает посты юзера по логину
     * @return
     */
    @GetMapping("/posts")
    public List<PostDto> showPostsByLogin() {
        UserEntity currUser = userService.getCurrentUserByPrincipal();
        return postService.findPostsByUserLogin(currUser.getLogin()).stream().map(PostMapper::mapToDto).toList();
    }

    /**
     * Возвращаем все посты
     *
     * @return
     */
    @GetMapping("/all")
    public List<PostDto> showAllPosts() {
        return postService.findAll().stream().map(PostMapper::mapToDto).toList();
    }

    /**
     * Возвращает все посты и комментарии под постом
     *
     * @return
     */
    @GetMapping("/all/post_comments")
    public List<PostDto> showAllPostsWithComments() {
        return postService.findAllPostsWithComments().stream().map(PostMapper::mapToDto).toList();
    }


    /**
     * Возвращаем все посты по дате
     *
     * @param date
     * @return
     */
    @GetMapping("/date/{date}")
    public List<PostDto> showPostByDate(@PathVariable("date") LocalDate date) {
        return postService.findByDate(date).stream().map(PostMapper::mapToDto).toList();
    }

    /**
     * Добавляет пост в БД
     *
     * @param postDto принимаем DTO, затем формируем пост в сервисе. Из дто дергаем нужные поля
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<String> addNewPost(@RequestBody PostDto postDto) {
        return postService.createPost(postDto.getPostName(), postDto.getPostContent());
    }

    /**
     * Обновление поста юзера через id поста в БД.
     *
     * @param postId
     * @param postDto
     * @return
     */
    @PutMapping("/update/{postId}")
    public ResponseEntity<String> updatePostById(@PathVariable Long postId, @RequestBody PostDto postDto) {
        return postService.updatePost(postId, postDto.getPostName(), postDto.getPostContent());
    }

    /**
     * Удаление поста через id поста в БД
     *
     * @param postId
     * @return
     */
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable Long postId) {
        return postService.deletePostById(postId);
    }
}
