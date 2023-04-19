package com.stannard.liam.user;

class UserNotFoundException extends RuntimeException
{
    UserNotFoundException(Long id)
    {
        super("\nCould not find user with id -  [" + id + "]");
    }
}