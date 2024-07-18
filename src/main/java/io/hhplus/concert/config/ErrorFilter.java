package io.hhplus.concert.config;

import io.hhplus.concert.common.exception.CustomException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;

public class ErrorFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            // Request 로그 기록
            logger.info("Request URL: {}", httpRequest.getRequestURL());
            logger.info("Request Method: {}", httpRequest.getMethod());
            logger.info("Request Headers: {}", getHeadersInfo(httpRequest));

            chain.doFilter(request, response);

        } catch (CustomException e) {
            // CustomException 처리
            logger.info("CustomException caught: {}", e.getErrorCode().getMessage(), e);
            httpResponse.setStatus(e.getErrorCode().getHttpStatus().value());
            httpResponse.getWriter().write(e.getErrorCode().getMessage());
        } catch (Exception ex) {
            // 기타 예외 처리
            logger.error("Unhandled exception caught", ex);
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpResponse.getWriter().write("Internal server error");
        }

        // Response 로그 기록
        logger.info("Response Status: {}", httpResponse.getStatus());
    }

    private String getHeadersInfo(HttpServletRequest request) {
        StringBuilder headers = new StringBuilder();
        for (String headerName : Collections.list(request.getHeaderNames())) {
            headers.append(headerName).append(": ").append(request.getHeader(headerName)).append("\n");
        }
        return headers.toString();
    }

}
