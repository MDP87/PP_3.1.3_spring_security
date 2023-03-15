package ru.kata.spring.boot_security.demo.configs.service;

import java.util.List;
import ru.kata.spring.boot_security.demo.configs.model.User;

public interface UserService {
   List<User> findAll();
   User findById(long id);
   void save(User user);
   void updateUser(long id, User userToUpdate);
   void delete(long id);

}
