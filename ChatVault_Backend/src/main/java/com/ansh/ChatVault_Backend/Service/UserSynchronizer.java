package com.ansh.ChatVault_Backend.Service;

import com.ansh.ChatVault_Backend.Mappers.UserMapper;
import com.ansh.ChatVault_Backend.Model.User;
import com.ansh.ChatVault_Backend.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void synchronizeWithIdp(Jwt token) {
        log.info("Synchronizing user with idp");
        Map<String, Object> claims = token.getClaims();
        
        // Check if sub claim exists (this is the user ID from Keycloak)
        if (claims.containsKey("sub")) {
            String userId = claims.get("sub").toString();
            log.info("Synchronizing user with ID {}", userId);
            
            // Create user from token attributes
            User user = userMapper.fromTokenAttributes(claims);
            
            // Try to find existing user by ID first
            Optional<User> existingUserById = userRepository.findById(userId);
            
            // If user exists by ID, update their data
            if (existingUserById.isPresent()) {
                User existingUser = existingUserById.get();
                user.setId(existingUser.getId());
                user.setCreatedDate(existingUser.getCreatedDate());
                log.info("Updating existing user with ID {}", userId);
            } 
            // If user doesn't exist by ID but has email, try to find by email
            else if (claims.containsKey("email")) {
                String userEmail = claims.get("email").toString();
                Optional<User> existingUserByEmail = userRepository.findByEmail(userEmail);
                
                if (existingUserByEmail.isPresent()) {
                    User existingUser = existingUserByEmail.get();
                    // Update ID to match Keycloak ID
                    user.setId(userId);
                    user.setCreatedDate(existingUser.getCreatedDate());
                    log.info("Updating existing user with email {} to have ID {}", userEmail, userId);
                }
            }
            
            // Save the user regardless
            userRepository.save(user);
            log.info("User synchronization completed for ID {}", userId);
        } else {
            log.warn("Cannot synchronize user: missing 'sub' claim in token");
        }
    }

    private Optional<String> getUserEmail(Jwt token) {
        Map<String, Object> attributes = token.getClaims();
        if (attributes.containsKey("email")) {
            return Optional.of(attributes.get("email").toString());
        }
        return Optional.empty();
    }
}
