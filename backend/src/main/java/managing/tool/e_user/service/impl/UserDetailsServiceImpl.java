package managing.tool.e_user.service.impl;

import managing.tool.e_user.model.dto.UserDetailsDto;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String companyNum) {
        UserDetailsDto userDetailsDto = this.userService.findUserDetails(companyNum);

        return mapToUserDetails(userDetailsDto);
    }

    private UserDetails mapToUserDetails(UserDetailsDto userDetailsDto) {
        List<SimpleGrantedAuthority> authorities = userDetailsDto
                .getRoles()
                .stream()
                .map(ur -> new SimpleGrantedAuthority("ROLE_" + ur.getName()))
                .collect(Collectors.toList());

        return new User(
                userDetailsDto.getCompanyNum(),
                userDetailsDto.getPassword(),
                authorities
        );
    }
}
