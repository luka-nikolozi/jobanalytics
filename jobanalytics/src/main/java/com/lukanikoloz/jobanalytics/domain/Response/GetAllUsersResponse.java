package com.lukanikoloz.jobanalytics.domain.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllUsersResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public static GetAllUsersResponse fromEntity(com.lukanikoloz.jobanalytics.domain.Entity.User user) {
        return new GetAllUsersResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}
