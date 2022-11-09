package com.springcode.blog.dao;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {

    private String usernameOrEmail;
    private String password;
}
