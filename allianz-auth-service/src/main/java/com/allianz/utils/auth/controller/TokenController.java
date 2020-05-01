package com.allianz.utils.auth.controller;

import com.allianz.utils.auth.model.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.allianz.utils.auth.model.JwtUser;
import com.allianz.utils.auth.security.JwtUtil;

@RestController
@RequestMapping("/token")
@CrossOrigin
public class TokenController {

    @Autowired
    private JwtUtil jwtGenerator;

    @PostMapping("/generate")
    public ResponseEntity<String> generate(@RequestBody final JwtUser jwtUser) {
        return ResponseEntity.ok(jwtGenerator.generate(jwtUser));
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestBody final AuthToken token) {
        return ResponseEntity.ok(jwtGenerator.validate(token.getToken()) != null);
    }
}
