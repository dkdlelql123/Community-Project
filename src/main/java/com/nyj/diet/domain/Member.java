package com.nyj.diet.domain;

import com.nyj.diet.config.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor

public class Member implements UserDetails {

    private String loginId;
    private String loginPw;

    //그 다음 Member로 받을 나머지 값들을 넣어준다.
    private String name;
    private String nickname;
    private String email;

    //DB의 PK에 연결할 id를 만들어준다,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role authority;

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    public static Member createMember(String loginId, String loginPw, String name, String nickname, String email, Role authority) {

        Member member = new Member();

        member.loginId = loginId;
        member.loginPw = loginPw;

        member.name = name;
        member.nickname = nickname;
        member.email = email;

        member.authority = authority;

        return member;
    }


    @Override
//    public Collection getAuthorities() {
//
//        List authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(this.authority.getValue()));
//
//        return authorities;
//    }

    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.authority.getValue()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return loginPw;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
