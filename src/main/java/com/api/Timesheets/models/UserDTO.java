package com.api.Timesheets.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

  private String userName;

  private String email;

  private String firstName;

  private String lastName;

  private Set<String> roles;

  private Boolean active;

  @JsonIgnore
  private String password;
}
