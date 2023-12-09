package forum.models;

import forum.post.model.Post;
import forum.user.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text")
    private String text;

    @Column(name = "creation_date")
    private LocalDate localDate;

    @Column(name = "count_likes")
    private int countLikes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity owner;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
