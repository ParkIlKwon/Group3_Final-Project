package com.jpa.intra.group3_finalproject;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Group3FinalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(Group3FinalProjectApplication.class, args);
    }

    @Bean
    Hibernate5Module hibernate5Module(){
        Hibernate5Module hiber = new Hibernate5Module();
        hiber.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING ,true);
        return  hiber;
    }

}
