package com.example.ewdj_ep3.domain.user;

import com.example.ewdj_ep3.domain.role.Role;
import com.example.ewdj_ep3.dto.request.UserInputDTO;
import com.example.ewdj_ep3.mapper.UserMapper;
import com.example.ewdj_ep3.persistence.RoleRepository;
import com.example.ewdj_ep3.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public String saveUser(UserInputDTO userInputDTO) {
        try {
            User newUser = UserMapper.toEntity(userInputDTO);

            Role role = roleRepository.getById(2L);
            newUser.setRoles(Set.of(role));

            userRepository.save(newUser);
            log.info("Registered new user");
            return "Success";
        } catch (Exception e) {
            return "Failed to save";
        }
    }

}
