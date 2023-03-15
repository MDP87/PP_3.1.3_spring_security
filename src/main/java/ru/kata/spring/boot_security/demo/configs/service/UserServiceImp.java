package ru.kata.spring.boot_security.demo.configs.service;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.configs.repository.UserRepository;
import ru.kata.spring.boot_security.demo.configs.model.User;

@Service
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }
  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }
  @Override
  public User findById(long id) {
    return userRepository.findById(id).orElse(null);
  }
  @Transactional
  @Override
  public void save(User user) {
    Optional<User> userFromDb = userRepository.findUserByUsername(user.getUsername());
    if (!userFromDb.isEmpty())
        throw new UsernameNotFoundException("User already exist");
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);

  }

  @Transactional
  @Override
  public void updateUser(long id, User userToUpdate) {
    userToUpdate.setId(id);
    userToUpdate.setPassword(passwordEncoder.encode(userToUpdate.getPassword()));
    userRepository.save(userToUpdate);
  }

  @Transactional
  @Override
  public void delete(long id) {
    userRepository.deleteById(id);
  }
    }