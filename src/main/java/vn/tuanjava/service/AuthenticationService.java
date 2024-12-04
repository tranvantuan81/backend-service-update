package vn.tuanjava.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.tuanjava.dto.request.SignInRequest;
import vn.tuanjava.dto.response.TokenResponse;
import vn.tuanjava.exception.InvalidDataException;
import vn.tuanjava.model.Token;
import vn.tuanjava.model.User;
import vn.tuanjava.repository.UserRepository;

import java.util.Optional;

import static vn.tuanjava.util.TokenType.ACCESS_TOKEN;
import static vn.tuanjava.util.TokenType.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenService tokenService;

    public TokenResponse authenticate(SignInRequest signInRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

        var user = userRepository.findByUsername(signInRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("username or password incorrect"));

        String accessToken = jwtService.generateToken(user);

        String refreshToken = jwtService.generateRefreshToken(user);

        // save token db
        tokenService.save(Token.builder().username(user.getUsername()).accessToken(accessToken).refreshToken(refreshToken).build());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .build();
    }

    public TokenResponse refresh(HttpServletRequest request) {
        // validate
        String refreshToken = request.getHeader("x-token");
        if (StringUtils.isBlank(refreshToken)) {
            throw new InvalidDataException("Token must be not blank");
        }

        // extract user from token
        final String username = jwtService.extractUsername(refreshToken, REFRESH_TOKEN);
        System.out.println("Username " + username);
        // check it into db
        Optional<User> user = userRepository.findByUsername(username);
        System.out.println("userId "+ user.get().getId());

        if (!jwtService.isValid(refreshToken, REFRESH_TOKEN, user.get())) {
            throw new InvalidDataException("Token is invalid");
        }
        String accessToken = jwtService.generateToken(user.get());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.get().getId())
                .build();
    }

    public String logout(HttpServletRequest request) {
        String refreshToken = request.getHeader("x-token");
        if (StringUtils.isBlank(refreshToken)) {
            throw new InvalidDataException("Token must be not blank");
        }
        final String username = jwtService.extractUsername(refreshToken, ACCESS_TOKEN);

        // check token in db
        Token currentToken = tokenService.getByUsername(username);

        // delete token permanent
        tokenService.delete(currentToken);
        return "Deleted!";
    }
}
