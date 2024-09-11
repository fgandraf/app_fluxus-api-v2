package com.felipegandra.app_fluxusapiv2.modules.users;

import com.felipegandra.app_fluxusapiv2.exceptions.NotFoundException;
import com.felipegandra.app_fluxusapiv2.modules.users.dtos.UserOutput;
import com.felipegandra.app_fluxusapiv2.modules.users.enums.UserRole;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }




    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email);
    }

    public User findByEmail(String email) {
        var userDetail = repository.findByEmail(email);
        if (userDetail != null) {
            var user = new User();
            BeanUtils.copyProperties(userDetail, user);
            return user;
        } else {
            throw new NotFoundException("User", email);
        }
    }





    public User findByProfessionalId(Long professionalId) {
        var userDetail = repository.findByProfessionalId(professionalId);
        if (userDetail != null) {
            var user = new User();
            BeanUtils.copyProperties(userDetail, user);
            return user;
        } else {
            throw new NotFoundException("User", professionalId);
        }
    }

    public UserOutput save(User user) {
        return new UserOutput(repository.save(user));
    }

    public UserOutput update(User user) {
        var passwd = user.getPassword();
        user.setPassword(new BCryptPasswordEncoder().encode(passwd));
        return new UserOutput(repository.save(user));
    }











    public User activate(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            user.get().setActive(true);
            return repository.save(user.get());
        } else {
            throw new NotFoundException("User", id);
        }
    }

    public User deactivate(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            user.get().setActive(false);
            return repository.save(user.get());
        } else {
            throw new NotFoundException("User", id);
        }
    }

    public User findById(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException("User", id);
        }
    }



    public User upgradePermission(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            user.get().setRole(UserRole.ADMIN);
            return repository.save(user.get());
        } else {
            throw new NotFoundException("User", id);
        }
    }

    public User downgradePermission(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            user.get().setRole(UserRole.USER);
            return repository.save(user.get());
        } else {
            throw new NotFoundException("User", id);
        }
    }

    public Page<UserOutput> getAll(Pageable page) {

        return repository
                .findAll(page)
                .map(UserOutput::new);
    }

}
