package managing.tool.config;

import managing.tool.authentication.filters.JwtRequestFilter;
import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.service.impl.UserDetailsServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WithMockCustomUserSecurityContextFactory
    	implements WithSecurityContextFactory<WithMockCustomUser>
    {

        private final UserDetailsServiceImpl securityUserDetailsService;
        private final JwtRequestFilter jwtRequestFilter;

        public WithMockCustomUserSecurityContextFactory(UserDetailsServiceImpl securityUserDetailsService, JwtRequestFilter jwtRequestFilter) {
            this.securityUserDetailsService = securityUserDetailsService;
            this.jwtRequestFilter = jwtRequestFilter;
        }

        @Override
        public SecurityContext createSecurityContext(WithMockCustomUser customUser) {

            UserDetails principal = securityUserDetailsService.loadUserByUsername(customUser.companyNum());

            Authentication auth =
                    new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            return context;
        }
}
