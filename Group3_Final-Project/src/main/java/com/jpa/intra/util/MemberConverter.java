package com.jpa.intra.util;

import com.jpa.intra.domain.Member;
import com.jpa.intra.repository.A_Member_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter implements Converter<String, Member> {

    @Autowired
    private A_Member_Repository aMemberRepository;

    @Override
    public Member convert(String source) {
        Long memNum = Long.parseLong(source);
        System.out.println("맴버 컨버터 태스트 : "+aMemberRepository.findById(memNum));
        return aMemberRepository.findById(memNum);
    }
}
