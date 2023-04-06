package com.jpa.intra;

import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public class Init {

    @PostConstruct
    public void initAction(){
        testinit();
    }


    public void testinit(){
        System.out.println("이닛작동.");
    }

}
