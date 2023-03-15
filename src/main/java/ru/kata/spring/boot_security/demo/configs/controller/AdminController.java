package ru.kata.spring.boot_security.demo.configs.controller;


import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.configs.model.Role;
import ru.kata.spring.boot_security.demo.configs.model.User;
import ru.kata.spring.boot_security.demo.configs.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.configs.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {
  private final UserService userService;
  private final RoleRepository roleRepository;
  @Autowired
  public AdminController(UserService userService, RoleRepository roleRepository) {
    this.userService = userService;
    this.roleRepository = roleRepository;

  }
  @GetMapping
  public String showAllUsers(Model model,  Principal principal) {
    List<User> list = userService.findAll();
    model.addAttribute("users", list);
    return "users";
  }
  @GetMapping("/update/{id}")
  public String editUser(@PathVariable("id") long id, Model model) {
    model.addAttribute("user", userService.findById(id));
    List<Role> roles = roleRepository.findAll();
    model.addAttribute("roles", roles);
    return "update";
  }
  @PatchMapping("/user/{id}")
  public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") long id ) {
    if (bindingResult.hasErrors())
      return "update";
    userService.updateUser(id, user);
    return "redirect:/admin";
  }
  @DeleteMapping("/delete/{id}")
  public String deleteUser(@PathVariable("id") long id) {
    userService.delete(id);
    return "redirect:/admin";
  }

  @PostMapping("/register")
  public String registerPost(@ModelAttribute("user") User user) {
    userService.save(user);
    return "loginp";
  }
  @GetMapping("/register")
  public String registerGet(Model model) {
    model.addAttribute("user", new User());
    List<Role> roles = roleRepository.findAll();
    model.addAttribute("roles", roles);
    return "register";
  }



}
