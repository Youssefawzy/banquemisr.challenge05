package com.banquemisr.challenge05.security.Jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    // This method is triggered when a user tries to access
    // a secured resource without proper authentication
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         // information why the auth faild
                         AuthenticationException authException)
            throws IOException, ServletException {

        //set response header
        //response will be in json
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // set code 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // store the response as key value pair
        final Map<String, Object> body = new HashMap<>();

        body.put("status",HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        //convert the body map to JSON object
        final ObjectMapper mapper = new ObjectMapper();
        // modify the http response rather than return something
        mapper.writeValue(response.getOutputStream(),body);
    }
}
