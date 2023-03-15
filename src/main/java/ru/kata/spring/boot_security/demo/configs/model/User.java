package ru.kata.spring.boot_security.demo.configs.model;

import java.util.Collection;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User {
  @Id
  @Column(name = "user_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column (name = "username", nullable = false, unique = true)
  @NotEmpty(message = "Enter correct username")
  @Size(min = 3, message = "Username should contain at least 3 characters")
  private String username;
  @NotEmpty(message = "Enter correct last name")
  @Size(min = 2, max = 20, message = "Last Name should be from 2 to 20 symbols")
  @Column(name = "lastName", nullable = false)
  private String lastName;

  @NotEmpty(message = "Enter correct email")
  @Email(message = "Enter correct email")
  @Column(name = "email", nullable = false)
  private String email;
  @NotEmpty(message = "Enter correct password")
//  @Size(min = 1, max = 20, message = "Password must be from 1 to 20 symbols")
  @Column (name = "password", nullable = false)
  private String password;

  @Transient
  private Role role;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @Column (name = "role")
  @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name = "user_user_id"),
                inverseJoinColumns = @JoinColumn(name = "roles_id"))
  private Set<Role> roles;

  public User() {}

  public User( String email, String username) {
    this.email = email;
    this.username = username;
  }

  public User(String username, String email, Long id, String password, Collection<? extends GrantedAuthority> authoritiesRoles) {
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getRoles();
  }

  // For user's interface
//  @Override
//  public String getPassword() {
//    return password;
//  }
//  @Override
//  public String getUsername() {
//    return username;
//  }
//
//  @Override
//  public boolean isAccountNonExpired() {
//    return true;
//  }
//
//  @Override
//  public boolean isAccountNonLocked() {
//    return true;
//  }
//
//  @Override
//  public boolean isCredentialsNonExpired() {
//    return true;
//  }
//
//  @Override
//  public boolean isEnabled() {
//    return true;
//  }

//   For User
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  @Override
  public String toString() {
    return String.format("Id = %s \n First name = %s \n" +
        " Last name = %s \n Email = %s \n", getId(), getUsername(), getEmail());
  }
}



