package com.nyj.diet.service;

import com.nyj.diet.dao.MemberRepository;
import com.nyj.diet.domain.Article;
import com.nyj.diet.domain.Member;
import com.nyj.diet.dto.article.ArticleListDTO;
import com.nyj.diet.dto.member.MemberDTO;
import com.nyj.diet.dto.member.MemberDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdmMemberService {

    private final MemberRepository memberRepository;

    public List<MemberDTO> getMemberList (){

        List<MemberDTO> memberDTOList = new ArrayList<>();
        List<Member> memberList = memberRepository.findAll();

        for(Member member  : memberList){
            MemberDTO memberDTO = new MemberDTO(member);
            memberDTOList.add(memberDTO);
        }

        return memberDTOList;
    }

    public Member getMember(Long memberId) throws  IllegalStateException{
        Optional<Member> memberOptional = memberRepository.findById(memberId);

        memberOptional.orElseThrow(
                () -> new IllegalStateException("존재하지 않는 회원입니다.")
        );

        return memberOptional.get();

    }

    public MemberDetailDTO getMemberDetail(Long memberId){
        Member findMember = getMember(memberId);

        List<ArticleListDTO> articleDTOList = new ArrayList<>();

        List<Article> articles = findMember.getArticles();

        for(Article article : articles){
            ArticleListDTO articleListDTO = new ArticleListDTO(article);
            articleDTOList.add(articleListDTO);
        }

        return new MemberDetailDTO(findMember, articleDTOList);

    }


    @Transactional
    public void banMember(Long memberId){
        Member findMember = getMember(memberId);

        // 회원 데이터 삭제
        memberRepository.delete(findMember);
    }

}
