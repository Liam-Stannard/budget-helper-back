package com.stannard.liam.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService
{

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserDTOMapper userDTOMapper;

    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper)
    {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }

    public List<UserDTO> getUsers()
    {
        return userRepository.findAll()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id)
    {
        return userRepository.findById(id)
                .map(userDTOMapper);
    }

    public Optional<User> getUserByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    public void addNewUser(User user)
    {
       Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
       if(userOptional.isPresent())
       {
           throw new IllegalStateException("Email already in use - " + user.getEmail());
       }

       userRepository.save(user);
    }

    public void updateUser(Long id, User userUpdate)
    {
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

    public void deleteUser(Long id)
    {
        if(!userRepository.existsById(id))
        {
            throw new IllegalStateException("User doesn't exist with id - " + id);
        }

        userRepository.deleteById(id);
    }
}
