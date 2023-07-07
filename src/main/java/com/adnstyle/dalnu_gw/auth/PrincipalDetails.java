package com.adnstyle.dalnu_gw.auth;

import com.adnstyle.dalnu_gw.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails , Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Member member;
    
    public PrincipalDetails(Member member){
        this.member = member;
    }
    
    /**
     * 유저의 권한을 리턴한는곳
     */
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        member.getRoleList().forEach(r ->{
//            authorities.add(() -> {
//            return  r;
//            });
//        });
//        return authorities;
//    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    
    public Member getMember(){
        return this.member;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
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
