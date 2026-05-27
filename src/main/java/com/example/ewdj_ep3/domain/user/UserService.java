package com.example.ewdj_ep3.domain.user;

import com.example.ewdj_ep3.domain.role.Role;
import com.example.ewdj_ep3.dto.request.InputLoginDTO;
import com.example.ewdj_ep3.dto.request.InputRegistrationDTO;
import com.example.ewdj_ep3.mapper.UserMapper;
import com.example.ewdj_ep3.persistence.RoleRepository;
import com.example.ewdj_ep3.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void saveUser(InputRegistrationDTO userInputDTO) {
        try {
            User newUser = UserMapper.toEntity(userInputDTO);

            Role role = roleRepository.getById(2L);
            newUser.setRoles(Set.of(role));

            userRepository.save(newUser);
            log.info("Registered new user");
        } catch (Exception e) {
            log.info("Failed to register new user");
        }
    }

    //TODO exception handling
    public User findByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}
