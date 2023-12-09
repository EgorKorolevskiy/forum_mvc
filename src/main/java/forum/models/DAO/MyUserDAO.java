package forum.models.DAO;

import forum.models.MyUser;
import forum.models.repositories.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDAO {
    private final MyUserRepository myUserRepository;
    public void addUser(MyUser user) {
         myUserRepository.save(user);
    }
    public MyUser findById(long id) {
        return myUserRepository.findById(id);
    }
    public Optional<MyUser> findByLogin(String login) {
        return myUserRepository.findByLogin(login);
    }
    public void updateUser(MyUser myUser) {
        myUserRepository.saveAndFlush(myUser);
    }
    public void deleteUserById(long id) {
        myUserRepository.deleteById(id);
    }

    public String registrationUser(String login, String password) {
        Optional<MyUser> existLogin = findByLogin(login);
        if (existLogin.isEmpty()) {
            addUser(MyUser.builder().login(login).password(password).build());
            return "created";
        }
        return "already exists";
    }
}
