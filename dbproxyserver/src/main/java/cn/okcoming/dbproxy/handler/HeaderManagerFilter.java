package cn.okcoming.dbproxy.handler;

import cn.okcoming.dbproxy.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@WebFilter
public class HeaderManagerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {//初始化过滤器
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            if(httpRequest.getHeader(Constants.X_UUID) != null){
                httpResponse.addHeader(Constants.X_UUID,httpRequest.getHeader(Constants.X_UUID));
            }
        }
        filterChain.doFilter(request, response);
    }
 
    @Override
    public void destroy() {
    }
 
}