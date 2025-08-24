package com.demo.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticatedUser {
    private String userId;
    private String email;
    private String role;
    private String jwtToken;
}
