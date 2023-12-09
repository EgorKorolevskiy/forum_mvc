package forum.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "my_user")
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String login;
    private String password;
    //Аннотация @ElementCollection с параметром targetClass указывает, что поле roles представляет коллекцию элементов.
    //fetch = FetchType.EAGER - это параметр, указывающий, каким образом должна быть загружена коллекция roles
    // при загрузке сущности MyUser. Значение EAGER означает, что коллекция будет немедленно загружена вместе
    // с основной сущностью MyUser. В противоположность этому, значение LAZY означает,
    // что коллекция будет загружена только при обращении к ней.
//    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    // @CollectionTable определяет имя таблицы, в которой будут храниться роли пользователя, и связывает ее с основной
    // таблицей users через внешний ключ name = "user_id".
//    @CollectionTable(name = "my_user_roles", joinColumns = @JoinColumn(name = "my_user_id"))
    // Аннотация @Enumerated указывает, что значения ролей будут храниться как строки в базе данных.
//    @Enumerated(EnumType.STRING)
//    private List<Role> roles;

    // у текущего entity есть связь с несколькими другими entity, поэтому мы должны указать имя поля в связанной сущности
    // с которым будем устанавливать связь. JPA автоматом возьмет поле myUser из сущности Role и прибавит к нему суффикс _id
    // но имя внешнего ключа будет в итоге my_user_id
    @OneToMany(mappedBy = "myUser")
    private List<Role> roles;
}
