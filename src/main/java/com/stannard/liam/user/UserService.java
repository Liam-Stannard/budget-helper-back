package com.stannard.liam.user;

import com.stannard.liam.exception.ApiRequestException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private final UserRepository userRepository;

  @Autowired
  private final UserDTOMapper userDTOMapper;

  public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper) {
    this.userRepository = userRepository;
    this.userDTOMapper = userDTOMapper;
  }

  public List<UserDTO> getUsers() {
    return userRepository.findAll()
        .stream()
        .map(userDTOMapper)
        .collect(Collectors.toList());
  }

  public Optional<UserDTO> getUserById(Long id) throws ApiRequestException {
    Optional<UserDTO> userDTO = userRepository.findById(id)
        .map(userDTOMapper);

    if (userDTO.isPresent()) {
      return userDTO;
    } else {
      throw new ApiRequestException("User not found", HttpStatus.NOT_FOUND,
          ZonedDateTime.now(ZoneId.of("Z")));
    }

  }

  public Optional<User> getUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public void addNewUser(User user) {
    Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
    if (userOptional.isPresent()) {
      throw new IllegalStateException("Email already in use - " + user.getEmail());
    }

    userRepository.save(user);
  }

  public void updateUser(Long id, User userUpdate) {
    User userToUpdate = userRepository.findById(id)
        .map(user -> {
          user.setUsername(userUpdate.getUsername());
          user.setRole(userUpdate.getRole());
          user.setEmail(userUpdate.getEmail());
          return userRepository.save(user);
        }) //
        .orElseGet(() -> {
          userUpdate.setId(id);
          return userRepository.save(userUpdate);
        });
  }

  public void deleteUser(Long id) {
    if (!userRepository.existsById(id)) {
      throw new IllegalStateException("User doesn't exist with id - " + id);
    }

    userRepository.deleteById(id);
  }
}
