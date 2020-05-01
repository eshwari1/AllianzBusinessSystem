package com.allianz.utils.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.allianz.utils.auth.model.AuthToken;
import com.allianz.utils.auth.model.JwtUser;
import com.allianz.utils.auth.service.JwtUtil;

@RestController
@RequestMapping("/token")
@CrossOrigin
public class TokenController {

  @Autowired
  private JwtUtil jwtGenerator;

  @GetMapping("/generate")
  public ResponseEntity<String> generate(@RequestHeader(value = "x-auth-user") String userName,
      @RequestHeader(value = "x-auth-role") String role) {
    JwtUser user = new JwtUser();
    user.setSubject(userName);
    user.setRole(role);
    return ResponseEntity.ok(jwtGenerator.generate(user));
  }

  @PostMapping("/validate")
  public ResponseEntity<JwtUser> validate(@RequestBody final AuthToken token) {
    return ResponseEntity.ok(jwtGenerator.validate(token.getToken()));
  }
}
