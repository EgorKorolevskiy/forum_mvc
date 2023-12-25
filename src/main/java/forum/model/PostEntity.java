package forum.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "post_entity")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column(name = "post_name", nullable = false, unique = true)
    private String postName;

    @NotNull
    @Column(name = "post_content", nullable = false)
    private String postContent;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDate localDate;

    // пишем mappedBy = "post" т.к. в клссе CommentEntity у нас есть поле private PostEntity post;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
