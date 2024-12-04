package vn.tuanjava.service;

import org.springframework.stereotype.Service;
import vn.tuanjava.exception.ResourceNotFoundException;
import vn.tuanjava.model.Token;
import vn.tuanjava.repository.TokenRepository;

import java.util.Optional;

@Service
public record TokenService(TokenRepository tokenRepository) {

    public int save(Token token) {
        Optional<Token> optional = tokenRepository.findByUsername(token.getUsername());
        if (optional.isEmpty()) {
            tokenRepository.save(token);
            return token.getId();
        } else {
            Token t = optional.get();
            t.setAccessToken(token.getAccessToken());
            t.setRefreshToken(token.getRefreshToken());
            tokenRepository.save(t);
            return t.getId();
        }
    }

    public String delete(Token token) {
        tokenRepository.delete(token);
        return "Deleted!";
    }

    public Token getByUsername(String username) {
        return tokenRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Token not exists"));
    }
}
