package com.allianz.utils.auth.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtUser {
  private long id;
  private String subject;
  private String role;

}
