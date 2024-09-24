package com.stannard.liam.auth;

import com.stannard.liam.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

  private String username;
  private Role role;
  private String email;
  private String password;
}
