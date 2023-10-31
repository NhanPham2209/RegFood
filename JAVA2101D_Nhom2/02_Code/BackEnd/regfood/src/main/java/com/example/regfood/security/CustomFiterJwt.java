package com.example.regfood.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.regfood.utils.JwtUtilsHelper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class CustomFiterJwt extends OncePerRequestFilter {
	@Autowired
    JwtUtilsHelper jwtUtilsHelper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
         response.setHeader("Access-Control-Allow-Origin", "*");
         response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
//         response.setHeader("Access-Control-Allow-Headers", "Authorization,content-type,xsrf-token");
//         response.addHeader("Access-Control-Expose-Headers", "xsrf-token");

        String token = getTokenFromHeader(request);
        if(token!=null){
            if(jwtUtilsHelper.verifyToken(token)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("", "", new ArrayList<>());
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);

        // if("OPTIONS".equals(request.getMethod())){
        //     response.setStatus(HttpServletResponse.SC_OK);
        // }else{
        //     filterChain.doFilter(request, response);        

        // }
    }
    private String getTokenFromHeader(HttpServletRequest request){
        String token = null;
        String header = request.getHeader("Authorization");
        if(StringUtils.hasText(header) && header.startsWith("Bearer")){
         token = header.substring(7);
        }
        return token;
    }

}
