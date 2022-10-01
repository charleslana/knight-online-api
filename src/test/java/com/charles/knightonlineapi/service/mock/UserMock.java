package com.charles.knightonlineapi.service.mock;

import com.charles.knightonlineapi.enums.RoleEnum;
import com.charles.knightonlineapi.model.dto.UserDTO;
import com.charles.knightonlineapi.model.entity.UserEntity;
import com.charles.knightonlineapi.model.mapper.UserMapper;
import com.charles.knightonlineapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserMock {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    public void createUser(UserDTO dto) {
        UserEntity entity = mapper.toEntity(dto);
        entity.setPassword(encoder.encode(dto.getPassword()));
        repository.save(entity);
    }

    public void createUserWithRole(UserDTO dto, RoleEnum role) {
        UserEntity entity = mapper.toEntity(dto);
        entity.setRole(role);
        entity.setPassword(encoder.encode(dto.getPassword()));
        repository.save(entity);
    }

    public UserDTO getUserMock() {
        UserDTO dto = new UserDTO();
        dto.setEmail("user@user.com");
        dto.setPassword("123456");
        return dto;
    }

    public UserDTO getUserMock(String email, String password) {
        UserDTO dto = new UserDTO();
        dto.setEmail(email);
        dto.setPassword(password);
        return dto;
    }

    public void deleteAllUser() {
        repository.deleteAll();
    }

    public Long getUser() {
        Optional<UserEntity> first = repository.findAll().stream().findFirst();
        if (first.isEmpty()) {
            return 0L;
        }
        return first.get().getId();
    }
}
