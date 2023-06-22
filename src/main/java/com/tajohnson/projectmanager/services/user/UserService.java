package com.tajohnson.projectmanager.services.user;

import com.tajohnson.projectmanager.models.user.LoginUser;
import com.tajohnson.projectmanager.models.user.User;
import com.tajohnson.projectmanager.repositories.user.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public User login(LoginUser newLogin, BindingResult result) {
    if (result.hasErrors()) {
      return null;
    }

    Optional<User> user = userRepository.findByEmail(newLogin.getLogEmail());

    if (user.isEmpty()) {
      result.rejectValue(
        "logEmail",
        "EMAIL-NOT-PRESENT",
        "User not found. Check the e-mail and try again or register a new user"
      );
      return null;
    }

    User foundUser = user.get();

    if (!BCrypt.checkpw(newLogin.getLogPassword(), foundUser.getPassword())) {
      result.rejectValue(
        "logPassword",
        "INVALID-LOGIN-PW",
        "Incorrect password. Please try again"
      );
      return null;
    }

    return foundUser;
  }

  public User register(User newUser, BindingResult result) {
    if (result.hasErrors()) {
      return null;
    }

    if (!newUser.getPassword().equals(newUser.getConfirmPassword())) {
      result.rejectValue(
        "confirmPassword",
        "PW-MISMATCH",
        "Passwords must match"
      );
      return null;
    }

    if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
      result.rejectValue(
        "email",
        "EMAIL-PRESENT",
        "There is already an account with this email"
      );
      return null;
    }

    String hashedPassword = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
    newUser.setPassword(hashedPassword);

    return userRepository.save(newUser);
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User getUserById(Long id) {
    Optional<User> optional = userRepository.findById(id);

    return optional.orElse(null);
  }

  public boolean isValidId(Long id) {
    return getUserById(id) != null;
  }

  public User updateUser(User user) {
    return userRepository.save(user);
  }
}