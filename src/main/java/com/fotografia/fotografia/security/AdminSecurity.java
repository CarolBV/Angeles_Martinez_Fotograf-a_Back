package com.fotografia.fotografia.security;

import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fotografia.fotografia.models.Admin;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AdminSecurity implements UserDetails {

    private Admin admin;



    @Override
    public String getPassword() {
        
        return admin.getPassword();
    }

    @Override
    public String getUsername() {

        return admin.getUsername();
    }

    @Override
    public java.util.Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + admin.getRole())); // ROLE_ADMIN
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

    // Nuevo método para obtener el objeto Admin
    public Admin getAdmin() {
        return this.admin;
    }
  
}
