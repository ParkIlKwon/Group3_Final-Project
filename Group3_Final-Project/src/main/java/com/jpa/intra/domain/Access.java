package com.jpa.intra.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Access { // 로그인시 , 멤버의 정보를 불러오기 보다는 . 사용권한을
    //줌으로서 세션에 불필요한 회원 정보가 들어가는것을 막기위해.
    //하지만 회원정보를 써야 할 경우를 위해 공통코드는 따로 만들 예정 .

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //즉,id 값을 null로 하면 DB가 알아서 AUTO_INCREMENT 해준다
    Long id ; //pk



}
