package ru.kata.spring.boot_security.demo.configs.controller;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.configs.security.UserDetailsImp;
import ru.kata.spring.boot_security.demo.configs.service.UserDetailsServiceImp;

@Controller
@RequestMapping("/")
public class UserController {
  private final UserDetailsServiceImp userDetailsServiceImp;
  @Autowired
  public UserController(UserDetailsServiceImp userDetailsServiceImp) {
    this.userDetailsServiceImp = userDetailsServiceImp;
  }
  @GetMapping(value = "/")
  public String printWelcome(ModelMap model) {
    List<String> messages = new ArrayList<>();
    messages.add("Hello!");
    model.addAttribute("messages", messages);
    return "index";
  }
  @GetMapping("/user")
  public String showOneUser(Model model, Principal principal ) {
    UserDetailsImp userDetailsImp = (UserDetailsImp) userDetailsServiceImp.loadUserByUsername(principal.getName());
    model.addAttribute("user", userDetailsImp.getUser());
    return "user";
  }



}
