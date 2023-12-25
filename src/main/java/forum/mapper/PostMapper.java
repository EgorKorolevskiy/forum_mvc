package forum.mapper;

import forum.dto.PostDto;
import forum.model.PostEntity;

public class PostMapper {
    public static PostDto mapToDto(PostEntity postEntity){
        return PostDto.builder()
                .id(postEntity.getId())
                .postName(postEntity.getPostName())
                .postContent(postEntity.getPostContent())
                .localDate(postEntity.getLocalDate())
                .userId(postEntity.getUser().getId())
                .build();
    }
}
