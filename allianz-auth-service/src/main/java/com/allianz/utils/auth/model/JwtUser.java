package com.allianz.utils.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtUser {
	@JsonIgnore
	private long id;
	private String subject;
	private String role;
}
