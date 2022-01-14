package com.nyj.diet.service;

import com.fasterxml.classmate.MemberResolver;
import com.nyj.diet.config.Role;
import com.nyj.diet.dao.MemberRepository;
import com.nyj.diet.domain.Member;
import com.nyj.diet.dto.MemberSaveForm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByLoginId(username).get();
    }

    /**
     * 회원 중복 체크
     * @param loginId
     * @param nickname
     * @param email
     */
    public void isDuplicateMember(String loginId, String nickname, String email){

        if(memberRepository.existByLoginId(loginId)){
            throw new IllegalStateException("이미 존재하는 아이디 입니다");
        }else if(memberRepository.existByNickname(nickname)){
            throw new IllegalStateException("이미 존재하는 아이디 입니다");
        }else if(memberRepository.existByEmail(email)){
            throw new IllegalStateException("이미 존재하는 이메일 입니다.");
        }
    }

    /**
     * 회원가입
     * @param memberSaveForm
     */

    @Transactional
    public void save(MemberSaveForm memberSaveForm) throws IllegalStateException{

        isDuplicateMember(
                memberSaveForm.getLoginId(),
                memberSaveForm.getNickname(),
                memberSaveForm.getEmail()
        );

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Member member = Member.createMember(
                memberSaveForm.getLoginId(),
                bCryptPasswordEncoder.encode(memberSaveForm.getLoginPw()),
                memberSaveForm.getName(),
                memberSaveForm.getNickname(),
                memberSaveForm.getEmail(),
                Role.MEMBER
        );

        memberRepository.save(member);
    }

}
