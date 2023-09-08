package com.selflearning.blogging.bloggingapplicationapi.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest
{
    private String username;
    private String password;
}
