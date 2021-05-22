package com.curesio.ehealth.services.impl;

import com.curesio.ehealth.models.UserPrincipal;
import com.curesio.ehealth.models.entities.User;
import com.curesio.ehealth.repositories.UserRepository;
import com.curesio.ehealth.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.curesio.ehealth.constants.UserImplementationConstants.NO_USER_FOUND_WITH_USERNAME;

@Service
@Transactional
@Qualifier("userService")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return new UserPrincipal(user.get());
        } else {
            LOGGER.error(NO_USER_FOUND_WITH_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_WITH_USERNAME + username);
        }
    }
}
