package mx.edu.utex.APREHO.security.service;

import mx.edu.utex.APREHO.model.user.User;
import mx.edu.utex.APREHO.security.model.UserDetailsImpl;
import mx.edu.utex.APREHO.services.ServicesUser.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService service;

    public UserDetailsServiceImpl(UserService service) {
        this.service = service;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email ) throws UsernameNotFoundException {
        Optional<User> foundUser = service.findUserByUsername( email);
        if (foundUser.isPresent())
            return UserDetailsImpl.build(foundUser.get());
        throw new UsernameNotFoundException("UserNotFound");
    }
}
