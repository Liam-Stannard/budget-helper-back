package com.stannard.liam.auth;

public class AuthenticationResponse
{
    private String accessToken;
    private String refreshToken;

    public AuthenticationResponse(String accessToken, String refreshToken )
    {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setRefreshToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }
}
