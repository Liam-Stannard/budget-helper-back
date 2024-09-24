package com.stannard.liam.user;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  @Autowired
  private final UserRepository userRepository;
  @Autowired
  private final UserService userService;

  UserController(UserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @GetMapping("/")
  public List<UserDTO> getUsers() {
    return userService.getUsers();
  }

  @PostMapping("/")
  public void createUser(@RequestBody User user) {
    userService.addNewUser(user);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<UserDTO>> getUser(@PathVariable Long id) {
    Optional<UserDTO> userDTO = userService.getUserById(id);

    return ResponseEntity.ok(userDTO);


  }

  @PutMapping("/{id}")
  public void updateUser(@RequestBody User user, @PathVariable Long id) {
    userService.updateUser(id, user);
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);

  }
}