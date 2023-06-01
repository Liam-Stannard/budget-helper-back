package com.stannard.liam.user;

public record UserDTO(
        Long id,
        String username,
        Role role,
        String email
) {

}
