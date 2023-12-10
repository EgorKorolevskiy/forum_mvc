package forum.user.service;

import forum.user.model.UserEntity;
import forum.user.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void addUser(UserEntity user) {
         userRepository.save(user);
    }
    public UserEntity findById(long id) {
        return userRepository.findById(id);
    }
    public Optional<UserEntity> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
    public void updateUser(UserEntity userEntity) {
        userRepository.saveAndFlush(userEntity);
    }
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public String registrationUser(String login, String password) {
        Optional<UserEntity> existLogin = findByLogin(login);
        if (existLogin.isEmpty()) {
            addUser(UserEntity.builder().login(login).password(password).build());
            return "created";
        }
        return "already exists";
    }
}
