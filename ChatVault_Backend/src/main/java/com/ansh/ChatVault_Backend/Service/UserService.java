package com.ansh.ChatVault_Backend.Service;

import com.ansh.ChatVault_Backend.Mappers.UserMapper;
import com.ansh.ChatVault_Backend.Repositories.UserRepository;
import com.ansh.ChatVault_Backend.Responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> finAllUsersExceptSelf(Authentication connectedUser) {
        return userRepository.findAllUsersExceptSelf(connectedUser.getName())
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }
}
