package forum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PostDto {
    private Long id;
    private String postName;
    private String postContent;
    private LocalDate localDate;
    private Long userId;
    private List<CommentDto> comments;
}
