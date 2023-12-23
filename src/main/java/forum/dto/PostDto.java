package forum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class PostDto {
    private Long id;
    private String postName;
    private String postContent;
    private LocalDate localDate;
    private Long userId;
}
