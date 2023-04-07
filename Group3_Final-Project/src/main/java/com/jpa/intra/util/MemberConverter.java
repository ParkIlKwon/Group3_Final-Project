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

    // Member타입의 객체를 String으로 받아오기 때문에 다시 Member타입으로 변환해주다.
    @Override
    public Member convert(String source) {
        Long memNum = Long.parseLong(source);
        System.out.println("맴버 컨버터 태스트 : "+aMemberRepository.findById(memNum));
        return aMemberRepository.findById(memNum);
    }
}
