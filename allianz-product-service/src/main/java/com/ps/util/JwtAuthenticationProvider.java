package com.ps.util;

import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import com.ps.model.AuthToken;
import com.ps.model.JwtUser;
import com.ps.model.JwtUserDetails;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  private RestTemplate restTemplate;

  private String jwtValidateUrl = "https://allianz-auth-service.cfapps.io/token/validate";

  public JwtAuthenticationProvider() {
    super();
    this.restTemplate = new RestTemplate();
  }


  @Override
  protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authToken)
      throws AuthenticationException {

    try {
      AuthToken token = ((AuthToken) authToken);
      HttpEntity<AuthToken> entity = new HttpEntity<AuthToken>(token);
      ResponseEntity<JwtUser> response =
          restTemplate.exchange(jwtValidateUrl, HttpMethod.POST, entity, JwtUser.class);

      JwtUser jwtUser = response.getBody();

      List<GrantedAuthority> grantedAuthorities =
          AuthorityUtils.commaSeparatedStringToAuthorityList(jwtUser.getRole());

      return new JwtUserDetails(jwtUser.getSubject(), jwtUser.getId(), token.getToken(),
          grantedAuthorities);
    } catch (HttpStatusCodeException ex) {
      throw new RuntimeException("Invalid Token.");
    }
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return (AuthToken.class.isAssignableFrom(aClass));
  }

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
      throws AuthenticationException {
    // NO need to implement
  }
}
