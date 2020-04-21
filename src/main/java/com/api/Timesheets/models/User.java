package com.api.Timesheets.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;
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
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "user_name")
  private String username;
  @Column
  private String email;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column
  private Boolean active;

  @Column(name = "change_password_required")
  private Boolean changePasswordRequired;

  @Column(name =  "reset_pw_token_exp_date")
  @JsonIgnore
  private LocalDate tokenExpDate;

  @Column(name = "reset_password_token")
  @JsonIgnore
  private String resetPasswordToken;

  @Column(name = "permissions_set")
  @JsonIgnore
  private String permissionsSet;

  @Column(name = "create_date")
  private Date createDate;

  @Column(name = "created_by")
  private String createBy;

  @Column(name = "update_date")
  private Date updateDate;

  @Column(name = "update_by")
  private String updateBy;

  @Column
  @JsonIgnore
  private String password;

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
    return this.changePasswordRequired;
  }

  @Override
  public boolean isEnabled() {
    return this.active;
  }
}
