package com.api.Timesheets.enums;

public enum Role {
  ROLE_USER("User"),
  ROLE_ADMIN("Admin");

  private String roleName;

  public String getRoleName() {
    return roleName;
  }

  private Role(String roleName) {
    this.roleName = roleName;
  }

  public static Role get(String roleName) {
    return Role.valueOf(roleName);
  }
}
