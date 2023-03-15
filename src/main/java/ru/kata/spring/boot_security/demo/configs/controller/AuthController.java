package ru.kata.spring.boot_security.demo.configs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class AuthController {
  @GetMapping("/index")
  public String login() {
    return "index";
  }
  @GetMapping
  public String  loginForm(Model model) {
    return "loginp";
  }
  @GetMapping("/logout")
  public String logOutGet() {
    return "loginp";
  }
  @PostMapping("/logout")
  public String logOutPost() {
    return "loginp";
  }

}
