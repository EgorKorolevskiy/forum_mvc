package forum.models;

import forum.user.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String role;

    @ManyToOne
    // указываем явно по какому столбцу будет идти связка
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
