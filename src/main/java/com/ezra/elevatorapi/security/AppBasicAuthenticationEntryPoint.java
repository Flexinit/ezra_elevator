package com.ezra.elevatorapi.security;

import com.ezra.elevatorapi.DbLogger.IPGeolocationService;
import com.ezra.elevatorapi.utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AppBasicAuthenticationEntryPoint
    extends BasicAuthenticationEntryPoint {

  @Autowired
  private  IPGeolocationService ipGeolocationService;



  @Override
  public void commence(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException authEx) throws IOException {

    APIUtils.USER_LOCATION = ipGeolocationService.getUserLocation(request.getRemoteAddr());

    response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    PrintWriter writer = response.getWriter();
    writer.println("HTTP Status 401 - " + authEx.getMessage());
  }

  @Override
  public void afterPropertiesSet() {
    setRealmName("ezra");
    super.afterPropertiesSet();
  }
}