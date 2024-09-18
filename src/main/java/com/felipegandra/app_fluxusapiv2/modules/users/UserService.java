package com.felipegandra.app_fluxusapiv2.modules.users;

import com.felipegandra.app_fluxusapiv2.config.TokenService;
import com.felipegandra.app_fluxusapiv2.exceptions.DatabaseOperationException;
import com.felipegandra.app_fluxusapiv2.exceptions.NoRecordsFoundException;
import com.felipegandra.app_fluxusapiv2.exceptions.UserNotFoundException;
import com.felipegandra.app_fluxusapiv2.modules.users.dtos.*;
import com.felipegandra.app_fluxusapiv2.modules.users.enums.UserRole;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public UserService(UserRepository repository, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.repository = repository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }


    public UserTokenReponse getUserToken(UserLoginRequest request){
        try{
            var usernamePassword = new UsernamePasswordAuthenticationToken(
                    request.email(),
                    request.password()
            );

            var auth = authenticationManager.authenticate(usernamePassword);
            var user = (User)auth.getPrincipal();
            var token = tokenService.generateToken(user);

            return new UserTokenReponse(token);

        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }

    }

    public UserResponse findByEmail(String email) {
        var user = repository.getByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        return new UserResponse(user);
    }

    public UserResponse findByProfessionalId(Long professionalId) {
        var user = repository.findByProfessionalId(professionalId).orElseThrow(() -> new UserNotFoundException(professionalId));
        return new UserResponse(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email);
    }

    public UserResponse save(UserCreateRequest request) {
        try {
            var user = new User(
                    request.email(),
                    new BCryptPasswordEncoder().encode(request.password())
            );

            var savedUser = repository.save(user);
            return new UserResponse(savedUser);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public UserResponse updateInfo(UserUpdateInfoRequest request) {
        var user = repository.findById(request.id()).orElseThrow(() -> new UserNotFoundException(request.id()));

        try{
            user.setEmail(request.email());
            user.setPassword(new BCryptPasswordEncoder().encode(request.password()));

            var savedUser = repository.save(user);
            return new UserResponse(savedUser);
        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public UserResponse updateConfig(UserUpdateConfigRequest request) {
        var user = repository.findById(request.id()).orElseThrow(() -> new UserNotFoundException(request.id()));

        try{
            user.setProfessionalId(request.professionalId());
            user.setLegalResponsible(request.legalResponsible());
            user.setTechnicianResponsible(request.technicianResponsible());

            var savedUser = repository.save(user);
            return new UserResponse(savedUser);
        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public UserResponse activate(Long id) {
        var user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        try {
            user.setActive(true);
            var updatedUser = repository.save(user);
            return new UserResponse(updatedUser);
        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public UserResponse deactivate(Long id) {
        var user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        try {
            user.setActive(false);
            var updatedUser = repository.save(user);
            return new UserResponse(updatedUser);
        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public UserResponse findById(Long id) {
        var user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return new UserResponse(user);
    }

    public UserResponse upgradePermission(Long id) {
        var user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        try {
            user.setRole(UserRole.ADMIN);
            var updatedUser = repository.save(user);
            return new UserResponse(updatedUser);
        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public UserResponse downgradePermission(Long id) {
        var user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        try {
            user.setRole(UserRole.USER);
            var updatedUser = repository.save(user);
            return new UserResponse(updatedUser);
        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public Page<UserResponse> getAll(Pageable page) {
        var users = repository.findAll(page);
        if (users.isEmpty()) { throw new NoRecordsFoundException(); }

        return users.map(user -> new UserResponse(user));
    }

}
