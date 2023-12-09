package forum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "post_name")
    private String postName;
    @Column(name = "post_content")
    private String postContent;

    //Каскадное удаление (cascade = CascadeType.ALL) означает, что при удалении пользователя,
    // связанные с ним посты и комментарии также будут удалены.
    // optional = false означает что переменная или параметр с именем optional должна быть обязательно указана или задана.

    @Column(name = "creation_date")
    private LocalDate localDate;
    //    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
//    private List<Comment> comments;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;
    @ManyToOne
    @JoinColumn(name = "my_user_id")
    private MyUser author;
}
