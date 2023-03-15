package ru.kata.spring.boot_security.demo.configs.service;

import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.configs.model.User;
import ru.kata.spring.boot_security.demo.configs.repository.UserRepository;
import ru.kata.spring.boot_security.demo.configs.security.UserDetailsImp;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
  private final UserRepository userRepository;
  @Autowired
  public UserDetailsServiceImp(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> userOptional = userRepository.findUserByUsername(username);
    if (userOptional.isEmpty())
      throw new UsernameNotFoundException("!!! User not found !!!");
    return new UserDetailsImp(userOptional.get());
//    User user = (User) userOptional.get();
//    return new org.springframework.security.core.userdetails.User( user.getEmail(),
//        user.getPassword(), getAuthorities(user));
  }

  private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
    String[] userRoles = user.getRoles().stream().map((role) -> role.getName())
        .toArray(String[]::new);
    Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
    return authorities;
  }
}
