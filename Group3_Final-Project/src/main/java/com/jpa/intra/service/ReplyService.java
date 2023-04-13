package com.jpa.intra.service;

import com.jpa.intra.domain.Reply;
import com.jpa.intra.repository.Reply_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class ReplyService {
    private final Reply_Repository replyRepository;

    @Transactional
    public Long createNewReply(Reply reply) {
        replyRepository.createNewReply(reply);
        return reply.getId();
    }

    public List<Reply> findReply() {return replyRepository.findAllReply();}

    public List<Reply> findReplyByBoardId(Long boardId) {return replyRepository.findReplyByBoardId(boardId);}
}
