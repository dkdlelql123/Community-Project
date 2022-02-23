package com.nyj.diet.service;

import com.nyj.diet.config.Role;
import com.nyj.diet.dao.MemberRepository;
import com.nyj.diet.domain.Article;
import com.nyj.diet.domain.Member;
import com.nyj.diet.dto.article.ArticleDTO;
import com.nyj.diet.dto.member.MemberModifyForm;
import com.nyj.diet.dto.member.MemberSaveForm;
import com.nyj.diet.dto.member.MyPageDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final ArticleService articleService;

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



    public Member findById(Long id) throws NoSuchElementException{
        Optional<Member> mOptional = memberRepository.findById(id);

        mOptional.orElseThrow(
                () -> new NoSuchElementException("존재하지 않은 회원입니다.")
        );

        return mOptional.get();
    }


    public Member findByLoginId(String loginId) throws IllegalStateException{

        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);

        memberOptional.orElseThrow(
                () -> new IllegalStateException("존재하지 않는 회원입니다")
        );

        return memberOptional.get();
    }



    public MyPageDTO getMyPageDTO(String loginId) {

        Member findMember = findByLoginId(loginId);

        List<ArticleDTO> articleDTOList = new ArrayList<>();
        List<Article> findArticles =  findMember.getArticles();

        for(Article article : findArticles){
//            ArticleDTO articleDTO = new ArticleDTO(article);
            ArticleDTO articleDTO = articleService.getArticle(article.getId());
            articleDTOList.add(articleDTO);
        }

        return new MyPageDTO(findMember, articleDTOList);
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

    public boolean isDupleLoginId(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    public boolean isDupleNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public boolean isDupleEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public void changeTempPw(String pw, Member member){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        member.changePW(bCryptPasswordEncoder.encode(pw));
    }
}
