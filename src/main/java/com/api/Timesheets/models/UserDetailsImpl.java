package com.api.Timesheets.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsImpl implements UserDetails {

  private static final long serialVersionUID = 1L;

  private Integer id;

  private String username;
  private String firstName;
  private String lastName;

  private String email;

  private Boolean active;

  @JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public static UserDetailsImpl build(User user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

    return UserDetailsImpl.builder()
        .id(user.getId())
        .email(user.getEmail())
        .username(user.getUsername())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .password(user.getPassword())
        .authorities(authorities)
        .active(user.getActive())
        .build();
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
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
    return true;
  }

  @Override
  public boolean isEnabled() {
    return active;
  }
}
