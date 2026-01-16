package com.github.diszexuf.core.web.dto;

import com.github.diszexuf.core.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    private String username;

    private String password;

    private RoleType role;

}
