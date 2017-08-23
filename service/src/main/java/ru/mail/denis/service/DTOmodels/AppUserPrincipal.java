package ru.mail.denis.service.DTOmodels;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.mail.denis.models.Role;
import ru.mail.denis.models.Status;
import ru.mail.denis.models.User;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by user on 15.08.2017.
 */
public class AppUserPrincipal implements UserDetails {

    private User user;
    private Collection<SimpleGrantedAuthority> grantedAuthorities;

    public AppUserPrincipal(User user) {
        this.user = user;
        grantedAuthorities = Collections.singletonList(
                new SimpleGrantedAuthority(user.getUserRole().name())
        );
    }

    public Integer getUserId() {
        return user.getUserId();
    }

    public Role getRole() {
        return user.getUserRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserEmail();
    }

    public Status getStatus(){
        return user.getUserStatus();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
