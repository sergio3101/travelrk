package ru.flystar.travelrk.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Created by sergey on 03.11.2017.
 */
@Log
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    handle(request, response, authentication);
    clearAuthenticationAttributes(request);
  }

  protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    String targetUrl = determineTargetUrl(authentication);
    if (response.isCommitted()) {
      log.info("Response has already been committed. Unable to redirect to " + targetUrl);
      return;
    }

    redirectStrategy.sendRedirect(request, response, targetUrl);
  }

  protected String determineTargetUrl(Authentication authentication) {
    boolean isUser = false;
    boolean isAdmin = false;
    boolean isManager = false;
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    for (GrantedAuthority grantedAuthority : authorities) {
      if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
        isUser = true;
        break;
      } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
        isAdmin = true;
        break;
      } else if (grantedAuthority.getAuthority().equals("ROLE_MANAGER")) {
        isManager = true;
        break;
      }
    }

    if (isUser) {
      return "/admin/exclusivetour";
    } else if (isAdmin) {
      return "/admin/youtubegallery";
    } else if (isManager) {
      return "/admin/rentatour";
    } else {
      throw new IllegalStateException();
    }
  }

  protected void clearAuthenticationAttributes(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {
      return;
    }
    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
  }

  public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
    this.redirectStrategy = redirectStrategy;
  }

  protected RedirectStrategy getRedirectStrategy() {
    return redirectStrategy;
  }
}