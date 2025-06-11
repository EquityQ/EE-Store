package org.example.javawebv2.com.v2.api;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter("/api/*")
public class apiFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化过滤器，如果有需要的话
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        // 实现跨域
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type,token,code"); // 添加 Content-Type
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Api-Version", "V2.0.0");

        // 处理 OPTIONS 请求
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        try {
            filterChain.doFilter(servletRequest, httpResponse);
        } catch (Exception e) {
            // 异常处理
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpResponse.getWriter().write("Internal Server Error");
            e.printStackTrace(); // 打印堆栈跟踪，便于调试
        }
    }

    @Override
    public void destroy() {
        // 销毁过滤器，如果有需要的话
    }
}
