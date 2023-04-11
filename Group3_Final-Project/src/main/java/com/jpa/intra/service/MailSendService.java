package com.jpa.intra.service;

import com.jpa.intra.domain.Mail;
import com.jpa.intra.repository.Mail_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MailSendService {
    private final Mail_Repository mailRepository;

    @Transactional
    public void sendMail(Mail mail){
        mailRepository.sendMail(mail); 
        //리포지에서 따로 persist 해주고 여기서 DB에 쏴주는 역활
    }

}
