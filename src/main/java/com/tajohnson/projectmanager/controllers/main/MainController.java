package com.tajohnson.projectmanager.controllers.main;

import com.tajohnson.projectmanager.models.user.LoginUser;
import com.tajohnson.projectmanager.models.user.User;
import com.tajohnson.projectmanager.services.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
  @Autowired
  private UserService userService;

  // *** LOGIN AND REGISTRATION ROUTE ***
  @GetMapping("/")
  public String index(HttpSession session, Model model) {
    if (session.getAttribute("userId") != null) {
      return "redirect:/dashboard";
    }

    model.addAttribute("newUser", new User());
    model.addAttribute("newLogin", new LoginUser());

    return "main/loginReg.jsp";
  }

  // *** USER REGISTRATION ***
  @PostMapping("/users/register")
  public String registerNewUser(
    @Valid @ModelAttribute("newUser") User newUser,
    BindingResult result,
    Model model,
    HttpSession session
  ) {
    User user = userService.register(newUser, result);

    if (result.hasErrors()) {
      model.addAttribute("newLogin", new LoginUser());

      return "main/loginReg.jsp";
    }

    session.setAttribute("userId", user.getId());

    return "redirect:/dashboard";
  }

  // *** USER LOGIN ***
  @PostMapping("/users/login")
  public String loginUser(
    @Valid @ModelAttribute("newLogin") LoginUser newLogin,
    BindingResult result,
    Model model,
    HttpSession session
  ) {
    User user = userService.login(newLogin, result);

    if (result.hasErrors()) {
      model.addAttribute("newUser", new User());

      return "main/loginReg.jsp";
    }

    session.setAttribute("userId", user.getId());

    return "redirect:/dashboard";
  }

  // *** USER LOGOUT ***
  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/";
  }
}