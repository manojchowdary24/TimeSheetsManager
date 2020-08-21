package com.api.Timesheets.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Builder
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "user_name")
  private String username;

  @Column private String email;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column private Boolean active = true;

  @Column(name = "change_password_required")
  private Boolean changePasswordRequired = false;

  @Column(name = "reset_pw_token_exp_date")
  @JsonIgnore
  private Date tokenExpDate;

  @Column(name = "reset_password_token")
  @JsonIgnore
  private String resetPasswordToken;

  @Column(name = "permissions_set")
  @JsonIgnore
  private String permissionsSet = "ROLE_USER";

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "create_date", insertable = false)
  private Date createDate;

  @Column(name = "created_by")
  private String createBy;

  @Column(name = "update_date")
  private Date updateDate;

  @Column(name = "update_by")
  private String updateBy;

  @Column @JsonIgnore private String password;

  public static User fromUserDTO(UserDTO userDTO) {
    return User.builder()
        .firstName(userDTO.getFirstName())
        .lastName(userDTO.getLastName())
        .username(userDTO.getUsername())
        .changePasswordRequired(userDTO.getChangePasswordRequired())
        .email(userDTO.getEmail())
        .permissionsSet(userDTO.getPermissionsSet())
        .resetPasswordToken(userDTO.getResetPasswordToken())
        .tokenExpDate(userDTO.getTokenExpDate())
        .build();
  }

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Arrays.asList(new SimpleGrantedAuthority(this.permissionsSet));
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !this.changePasswordRequired;
  }

  @Override
  public boolean isEnabled() {
    return this.active;
  }
}
