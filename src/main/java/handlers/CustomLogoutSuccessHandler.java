package handlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomLogoutSuccessHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication){
            if (authentication != null && authentication.getDetails() != null) {
                try {
                    httpServletRequest.getSession().invalidate();
                    System.out.println("User Successfully Logout");
                    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                    httpServletResponse.sendRedirect("/login");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }




