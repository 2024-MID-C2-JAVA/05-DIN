package com.bank.management.usecase;

import com.bank.management.User;
import com.bank.management.exception.CustomerAlreadyExistsException;
import com.bank.management.gateway.UserRepository;

import java.util.Optional;

public class CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User apply(User user) {
        Optional<User> userFound = userRepository.findByUsername(user.getUsername());
        if (userFound.isPresent()) {
            throw new CustomerAlreadyExistsException(user.getUsername());
        }
        User userCreated = new User(user.getUsername(), user.getPassword());
        return userRepository.saveUser(userCreated);
    }
}
