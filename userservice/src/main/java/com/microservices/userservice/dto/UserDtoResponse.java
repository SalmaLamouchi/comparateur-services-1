
package com.microservices.userservice.dto;

import lombok.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoResponse {
    private Long id;
    private String username;
    private String email;
    private String image;
    private Set<String> roles;
    private boolean isActive;
}
