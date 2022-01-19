package com.nyj.diet.service;

import com.nyj.diet.config.Role;
import com.nyj.diet.dao.MemberRepository;
import com.nyj.diet.domain.Member;
import com.nyj.diet.dto.member.MemberModifyForm;
import com.nyj.diet.dto.member.MemberSaveForm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

        if(memberRepository.existsByLoginId(loginId)){
            throw new IllegalStateException("이미 존재하는 아이디 입니다");
        }else if(memberRepository.existsByNickname(nickname)){
            throw new IllegalStateException("이미 존재하는 별명 입니다");
        }else if(memberRepository.existsByEmail(email)){
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

    public Member findByLoginId(String loginId) throws IllegalStateException{
        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);

        // orElseThrow - 존재하지 않은 회원일때 대처하기 위해
        memberOptional.orElseThrow(
                () -> new IllegalStateException("존재하지 않는 회원입니다.")
        );

        return memberOptional.get();
    }
    
    //회원정보수정
    //변경이니 transactional
    @Transactional
    public Long modifyMember(MemberModifyForm memberModifyForm, String loginId){
        
        Member findMember = findByLoginId(loginId);
        
        // 암호화
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        
        findMember.modifyMember(
                bCryptPasswordEncoder.encode(memberModifyForm.getLoginPw()),
                memberModifyForm.getNickname(),
                memberModifyForm.getEmail()
        );
        
        return findMember.getId();

    }

}
