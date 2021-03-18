package managing.tool.authentication;

import managing.tool.authentication.models.AuthenticationRequest;
import managing.tool.authentication.models.AuthenticationResponse;
import managing.tool.e_user.service.impl.UserDetailsServiceImpl;
import managing.tool.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static managing.tool.constants.GlobalConstants.FRONTEND_URL;

@RestController
@CrossOrigin(FRONTEND_URL)
class AuthenticateController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsServiceimpl;

    @Autowired
    AuthenticateController(AuthenticationManager authenticationManager, JwtUtil jwtTokenUtil, UserDetailsServiceImpl userDetailsServiceimpl) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsServiceimpl = userDetailsServiceimpl;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        }
        catch (BadCredentialsException e) {
            //TODO Handle Exeption
            throw new Exception("Incorrect username or password", e);
//            return ResponseEntity.noContent().build();
        }

        final UserDetails userDetails = userDetailsServiceimpl
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}