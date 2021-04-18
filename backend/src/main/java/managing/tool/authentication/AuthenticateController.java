package managing.tool.authentication;

import lombok.AllArgsConstructor;
import managing.tool.authentication.models.AuthenticationRequest;
import managing.tool.authentication.models.AuthenticationResponse;
import managing.tool.e_user.service.impl.UserDetailsServiceImpl;
import managing.tool.exception.NotFoundInDb;
import managing.tool.authentication.util.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static managing.tool.constants.GlobalConstants.FRONTEND_URL;

@RestController
@CrossOrigin(FRONTEND_URL)
@AllArgsConstructor
class AuthenticateController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsServiceimpl;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        }
        catch (BadCredentialsException e) {

            throw new NotFoundInDb("Incorrect username or password", "login");
//            return ResponseEntity.noContent().build();
        }

        final UserDetails userDetails = userDetailsServiceimpl
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}