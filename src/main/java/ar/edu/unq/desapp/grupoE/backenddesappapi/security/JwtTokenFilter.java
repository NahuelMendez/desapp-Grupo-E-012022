package ar.edu.unq.desapp.grupoE.backenddesappapi.security;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoE.backenddesappapi.persistence.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JWTProvider jwtTokenUtil;
    private final UserRepository userRepository;

    public JwtTokenFilter(JWTProvider jwtTokenUtil,
                          UserRepository userRepo) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header.isEmpty() /*|| !header.startsWith("Bearer ")*/) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        /*final String token = header.split(" ")[1].trim();*/
        if (!jwtTokenUtil.validateToken(header)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get user identity and set it on the spring security context
        User userDetails = userRepository
                .findByEmail(jwtTokenUtil.getUserEmail(header))
                .orElse(null);

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ?
                        Collections.emptyList() : userDetails.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
