package forum.controllers;

import forum.dto.PostDto;
import forum.mapper.PostMapper;
import forum.service.PostService;
import forum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Для постов такой маппинг:
 * GET -- /post/get/{login} - Возвращает посты юзера по логину
 * GET -- /post/get/all/posts - Возвращаем все посты
 * GET -- /post/get/all - Возвращаем все посты и комментарии под постом
 * GET -- /post/get/date/{date} - Возвращаем все посты по дате
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
     *
     * @param login
     * @return
     */
    @GetMapping("/get/{login}")
    public List<PostDto> showPostsByLogin(@PathVariable String login) {
        return postService.findPostsByUserLogin(login).stream().map(PostMapper::mapToDto).toList();
    }

    /**
     * Возвращаем все посты
     *
     * @return
     */
    @GetMapping("/get/all/posts")
    public List<PostDto> showAllPosts() {
        return postService.findAll().stream().map(PostMapper::mapToDto).toList();
    }

    /**
     * Возвращает все посты и комментарии под постом
     *
     * @return
     */
    @GetMapping("/get/all")
    public List<PostDto> showAllPostsWithComments() {
        return postService.findAllPostsWithComments();
    }


    /**
     * Возвращаем все посты по дате
     *
     * @param date
     * @return
     */
    @GetMapping("/get/date/{date}")
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
    public String addNewPost(@RequestBody PostDto postDto) {
        postService.createPost(postDto.getPostName(), postDto.getPostContent());
        return "Post added successful";
    }

    /**
     * Обновление поста юзера через id поста в БД.
     *
     * @param postId
     * @param postDto
     * @return
     */
    @PutMapping("/update/{postId}")
    public String updatePostById(@PathVariable Long postId, @RequestBody PostDto postDto) {
        return postService.updatePost(postId, postDto.getPostName(), postDto.getPostContent());
    }

    /**
     * Удаление поста через id поста в БД
     *
     * @param postId
     * @return
     */
    @DeleteMapping("/delete/{postId}")
    public String deletePostById(@PathVariable Long postId) {
        return postService.deletePostById(postId);
    }
}
