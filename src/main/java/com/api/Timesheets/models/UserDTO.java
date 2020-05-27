package com.api.Timesheets.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private Boolean changePasswordRequired;

    private Date tokenExpDate;

    private String resetPasswordToken;

    private String permissionsSet;

}
