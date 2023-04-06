package com.jpa.intra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

@Component
@RequiredArgsConstructor
public class Init {
    private final initService init;

    @PostConstruct
    public void initAction(){
        init.testinit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    public class initService{
        private final EntityManager em;
        public void testinit(){

            System.out.println("================");
            System.out.println(this.getClass());
        }
    }

}
