package com.lcsserver.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcsserver.dto.LcsErrorResponse;
import com.lcsserver.dto.ValueDto;
import com.lcsserver.exception.LcsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.lcsserver.util.ErrorMessages.REQUEST_BODY_EMPTY_MESSAGE;

@WebFilter("/*")
@Component
public class RequestBodyFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Autowired
    public RequestBodyFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CachedHttpServletRequest cachedHttpServletRequest = new CachedHttpServletRequest(request);
        String requestBody = new String(cachedHttpServletRequest.getInputStream().readAllBytes()).trim();

        if(requestBody.length() == 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(objectMapper.writeValueAsString(new LcsErrorResponse(REQUEST_BODY_EMPTY_MESSAGE)));
            return;
        }

        filterChain.doFilter(cachedHttpServletRequest, response);
    }
}
