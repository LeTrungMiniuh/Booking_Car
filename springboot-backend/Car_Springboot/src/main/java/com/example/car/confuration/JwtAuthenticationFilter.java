package com.example.car.confuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.car.Util.JWTUtil;
import com.example.car.service.jwt.UserService;



import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {
	
	 private final JWTUtil jwtUtil;
	    private final UserService userService;

	    @Autowired
	    public JwtAuthenticationFilter(JWTUtil jwtUtil, UserService userService) {
	        this.jwtUtil = jwtUtil;
	        this.userService = userService;
	    }

	    @Override
	    protected void doFilterInternal(@NonNull HttpServletRequest request,
	                                    @NonNull HttpServletResponse response,
	                                    @NonNull FilterChain filterChain)
	            throws ServletException, java.io.IOException {

	        // Lấy giá trị từ header Authorization
	        final String authHeader = request.getHeader("Authorization");
	        final String jwt;
	        final String userEmail;

	        // Kiểm tra header Authorization có chứa Bearer token hay không
	        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith( authHeader,"Bearer ")) {
	            filterChain.doFilter(request, response);
	            return;
	        }

	        // Trích xuất JWT từ header
	        jwt = authHeader.substring(7);

	        // Trích xuất email từ JWT
	        userEmail = jwtUtil.extractUserName(jwt);

	        // Nếu email có trong JWT và chưa có xác thực trong SecurityContext
	        if (StringUtils.isNotBlank(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {

	            // Tải thông tin người dùng từ cơ sở dữ liệu
	            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

	            // Kiểm tra tính hợp lệ của JWT
	            if (jwtUtil.isTokenValid(jwt, userDetails)) {

	                // Tạo đối tượng UsernamePasswordAuthenticationToken
	                UsernamePasswordAuthenticationToken authToken = 
	                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

	                // Thiết lập thông tin xác thực vào SecurityContext
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
	        }

	        // Tiếp tục chuỗi lọc
	        filterChain.doFilter(request, response);
	    }
	
	

}
