package com.stannard.liam.config;

import com.stannard.liam.auth.AuthenticationService;
import com.stannard.liam.auth.RegisterRequest;
import com.stannard.liam.user.Role;
import com.stannard.liam.user.User;
import com.stannard.liam.user.UserRepository;
import com.stannard.liam.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

    @Component
    public class DevStartup  implements CommandLineRunner {
        @Autowired
        private final AuthenticationService authenticationService;


        public DevStartup( AuthenticationService authenticationService)
        {
            this.authenticationService = authenticationService;
        }

        @Override
        public void run(String... args) throws Exception
        {
                RegisterRequest registerRequest = new RegisterRequest("test1", Role.ROLE_USER, "test1@tmail.com", "password1");
                System.out.println("ACCESS TOKEN  : " + authenticationService.register(registerRequest).getAccessToken());

        }

    }
