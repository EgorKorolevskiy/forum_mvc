package forum.mapper;

import forum.dto.CommentDto;
import forum.model.CommentEntity;

public class CommentMapper {
    public static CommentDto mapToDto(CommentEntity commentEntity) {
        return CommentDto.builder()
                .id(commentEntity.getId())
                .commentText(commentEntity.getCommentText())
                .commentDate(commentEntity.getLocalDate())
                .countLikes(commentEntity.getCountLikes())
                .userId(commentEntity.getUser().getId())
                .build();
    }
}
