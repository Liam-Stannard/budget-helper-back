package com.stannard.liam.auth;

public class AuthenticationResponse
{
    private String access;
    private String refresh;

    public AuthenticationResponse(String accessToken, String refresh)
    {
        this.access = accessToken;
        this.refresh = refresh;
    }

    public String getAccess()
    {
        return access;
    }

    public void setRefresh(String accessToken)
    {
        this.access = accessToken;
    }

    public void setAccess(String access)
    {
        this.access = access;
    }

    public String getRefresh()
    {
        return refresh;
    }
}
