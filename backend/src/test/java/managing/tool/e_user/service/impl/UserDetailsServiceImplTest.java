package managing.tool.e_user.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.model.dto.UserDetailsDto;
import managing.tool.e_user.repository.UserRepository;
import managing.tool.e_user.service.RoleService;
import managing.tool.e_user.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {
    private UserDetailsServiceImpl testService;

    @Mock
    UserService mockedUserService;

    @BeforeEach
    public void setUp(){
        testService = new UserDetailsServiceImpl(mockedUserService);
    }

    @Test
    public void noSuchUser(){
        Assertions.assertThrows(
                UsernameNotFoundException.class, () -> {
                    testService.loadUserByUsername("throw_error_user");
                });
    }

    @Test
    void returnExistingUser(){
        UserDetailsDto existingUser = new UserDetailsDto();
        Set<RoleEntity> roles = new HashSet<>();

        RoleEntity role = new RoleEntity();
        RoleEntity authority = new RoleEntity();
        role.setName(RoleEnum.valueOf("ADMIN"));
        authority.setName(RoleEnum.valueOf("ENGINEER"));

        roles.add(role);
        roles.add(authority);

        existingUser.setCompanyNum("N20202")
                    .setPassword("202")
                    .setRoles(roles);

        Mockito.when(mockedUserService.findUserDetails("N20202"))
                .thenReturn(java.util.Optional.of(existingUser));

        UserDetails userDetails = testService.loadUserByUsername("N20202");

        Assertions.assertEquals(testService.loadUserByUsername("N20202"), userDetails);
        Assertions.assertEquals(existingUser.getCompanyNum(), userDetails.getUsername());
        Assertions.assertEquals(2, userDetails.getAuthorities().size());

        List<String> authorities = userDetails.
                getAuthorities().
                stream().
                map(GrantedAuthority::getAuthority).
                collect(Collectors.toList());

        Assertions.assertTrue(authorities.contains("ROLE_ADMIN"));
        Assertions.assertTrue(authorities.contains("ROLE_ENGINEER"));


    }
}
