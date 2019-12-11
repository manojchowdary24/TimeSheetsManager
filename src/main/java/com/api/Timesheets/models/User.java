package com.api.Timesheets.models;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

  @Column(name =  "password_expiration_date")
  private Boolean passwordExpirationDate;

  @Column(name = "permissions_set")
  private String permissionsSet;

  @Column(name = "create_date")
  private Date createDate;

  @Column(name = "created_by")
  private Date createBy;

  @Column(name = "update_date")
  private Date updateDate;

  @Column(name = "update_by")
  private Date updateBy;

  @Column
  private String password;

  @Override
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
