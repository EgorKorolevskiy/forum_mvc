package forum.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "role_entity")
@EqualsAndHashCode(of = {"name"})
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users = new HashSet<>();

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "role_id", referencedColumnName="id", nullable = false, updatable = false),
//            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName="id", nullable = false, updatable = false))
//    private Set<UserEntity> users = new HashSet<>();
}
