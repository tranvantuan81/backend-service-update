package vn.tuanjava.service;

import org.springframework.security.core.userdetails.UserDetails;
import vn.tuanjava.util.TokenType;

public interface JwtService {

     String generateToken(UserDetails user);

     String generateRefreshToken(UserDetails user);

     String extractUsername(String token, TokenType type);

     boolean isValid(String token, TokenType type, UserDetails user);
}