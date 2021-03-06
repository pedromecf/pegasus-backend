package br.com.lacostech.pegasusbackend.services;

import br.com.lacostech.pegasusbackend.model.entities.User;
import br.com.lacostech.pegasusbackend.repositories.UserRepository;
import br.com.lacostech.pegasusbackend.services.exceptions.ForbiddenException;
import br.com.lacostech.pegasusbackend.services.exceptions.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {

    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getAuthenticatedUser() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByEmail(username);
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid user");
        }
    }

    public void validateSelfOrAdmin(final Long userId) {
        User user = getAuthenticatedUser();
        if (!user.getId().equals(userId) && !user.hasRole(ADMIN_ROLE)) {
            throw new ForbiddenException("Access denied");
        }
    }

}
