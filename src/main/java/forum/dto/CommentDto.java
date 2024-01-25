package forum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String commentText;
    private LocalDate commentDate;
    private int countLikes;
    private Long userId;
}
