package com.api.Timesheets.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "user_name"),
    @UniqueConstraint(columnNames = "email")
})
@Builder
public class User {

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

  @Column private Boolean active;

  @Column(name = "change_password_required")
  private Boolean changePasswordRequired;

  @Column(name = "reset_pw_token_exp_date")
  @JsonIgnore
  private Date tokenExpDate;

  @Column(name = "reset_password_token")
  @JsonIgnore
  private String resetPasswordToken;

//  @Column(name = "permissions_set")
//  @JsonIgnore
//  private String permissionsSet = "ROLE_USER";

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

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(	name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public static User fromUserDTO(UserDTO userDTO) {
    return User.builder()
        .firstName(userDTO.getFirstName())
        .lastName(userDTO.getLastName())
        .username(userDTO.getUserName())
        .email(userDTO.getEmail())
        .changePasswordRequired(true)
        .build();
  }

}
