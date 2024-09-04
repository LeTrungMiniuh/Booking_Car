package com.example.car.confuration;

import org.springframework.stereotype.Component;  
import org.springframework.core.annotation.Order;  
import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import java.io.IOException;  

import java.util.HashMap;  
import java.util.Map;  
import org.springframework.core.Ordered;  
/*
@Component  
@Order(Ordered.HIGHEST_PRECEDENCE)  
*/
public class SimpleCorsFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}  
/*
    @Override  
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)   
            throws  ServletException, IOException {  
        HttpServletResponse response = (HttpServletResponse) res;  
        HttpServletRequest request = (HttpServletRequest) req;  
        
        Map<String, String> map = new HashMap<>();  
       
        String originHeader = request.getHeader("origin");  
        response.setHeader("Access-Control-Allow-Origin", originHeader);  
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");  
        response.setHeader("Access-Control-Max-Age", "3600");  
        response.setHeader("Access-Control-Allow-Headers", "*");  
        
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {  
            response.setStatus(HttpServletResponse.SC_OK);  
        } else {  
            chain.doFilter(req, res);  
        }  
    }  
    
    @Override  
    public void init(FilterConfig filterConfig) {  
        // Initialization code if needed  
    }  

    @Override  
    public void destroy() {  
        // Cleanup code if needed  
    }  
	*/

}
