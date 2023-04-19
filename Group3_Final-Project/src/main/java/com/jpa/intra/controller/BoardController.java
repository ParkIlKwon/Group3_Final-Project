package com.jpa.intra.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jpa.intra.domain.Mail;
import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.Reply;
import com.jpa.intra.domain.Team;
import com.jpa.intra.domain.board.BoardApproval;
import com.jpa.intra.domain.board.BoardFree;
import com.jpa.intra.domain.board.BoardNotice;
import com.jpa.intra.domain.board.BoardTask;
import com.jpa.intra.query.*;
import com.jpa.intra.repository.Board_Repository;
import com.jpa.intra.repository.Member_Repository;
import com.jpa.intra.service.BoardService;
import com.jpa.intra.service.MailSendService;
import com.jpa.intra.service.MemberService;
import com.jpa.intra.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final Board_Repository bBoardRepository;
    private final MailSendService mailSendService;
    private final ReplyService replyService;
    private final Member_Repository member_repository;

    // 현재 날짜와 시간 정보를 LocalDateTime을 통해 가져오고 Formatter를 이용하여 필요한 형식으로 치환하다.
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
    String formattedDate = now.format(formatter);

    // 자유게시판(공지)으로 이동
    @GetMapping("/board/newfreeboard")
    public String callBoardFreeWriteForm(Model model) {
        model.addAttribute("boardFreeDTO", new BoardFreeDTO());
        return "board/boardFreeWriteForm";
    }

    // 자유게시판(공지) 작성
    @PostMapping("/board/newfreeboard")
    public String writeNewBoardFree(BoardFreeDTO boardFreeDTO, BindingResult rst) {

        if(rst.hasErrors()) {return "board/boardFreeWriteForm";}

        BoardFree boardFree=new BoardFree();    // boardFree객체
        boardFree.setBoardTitle(boardFreeDTO.getBoardTitle());  //제목
        boardFree.setBoardContent(boardFreeDTO.getBoardContent());  //내용
        boardFree.setCreateDate(formattedDate);    //작성일
        boardFree.setUpdateDate(null);    //수정일
        boardFree.setViewCount(0);      //조회수

        boardService.createBoardFree(boardFree);

        // 글 작성이 완료되면 경로이동
        return "redirect:/";
    }

    // 업무게시판작성 폼을 불러오다
    @GetMapping("/board/newtaskboard")
    public String callBoardTaskWriteForm(Model model) {
        model.addAttribute("boardTaskDTO", new BoardTaskDTO());
        return "project/boardTaskWriteForm";
    }

    // 업무게시판 작성
    private BoardTask createNewBoardTask(HttpSession session, BoardTaskDTO boardTaskDTO) {
        Member memberObject=(Member)session.getAttribute("user");

        BoardTask boardTask=new BoardTask();    //boardTask객체
        boardTask.setBoardTitle(boardTaskDTO.getBoardTitle());  //제목
        boardTask.setBoardContent(boardTaskDTO.getBoardContent());  //내용
        boardTask.setCreateDate(formattedDate); //작성일
        boardTask.setUpdateDate(null);  //수정일
        boardTask.setBoardWriter(memberObject.getMem_name());
        boardTask.setBoardWriterObject(memberObject);
        boardTask.setResponsibleMemNum(boardTaskDTO.getResponsibleMemNum());    //담당자번호
        boardTask.setStartDate(boardTaskDTO.getStartDate());    //업무시작 날짜
        boardTask.setEndDate(boardTaskDTO.getEndDate());    //업무 종료 날짜
        boardTask.setTeamNum(boardTaskDTO.getTeamNum());    //담당자의 팀번호
        boardTask.setProgress("TO_DO");  //진행상황("TO_DO","IN_PROGRESS","DONE") : 최초 저장은 무조건 "할 일"이기 때문에 "TO_DO"고정

        return boardTask;
    }

    @PostMapping("/board/newtaskboard")
    @ResponseBody
    public String writeNewBoardTask(HttpSession session, @RequestParam("boardTitle") String boardTitle, @RequestParam("boardContent") String boardContent, @RequestParam("responsibleMemNum") String responsibleMemNum, @RequestParam("teamNum") String teamNum, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        Member memberObject=(Member)session.getAttribute("user");
        Member responsibleMember=member_repository.findById(Long.parseLong(responsibleMemNum));
        Team referenceTeam=bBoardRepository.findById(Long.parseLong(teamNum));

        System.out.println(responsibleMember.getMem_name());
        System.out.println(referenceTeam.getTeam_name());

        BoardTaskDTO boardTaskDTO=new BoardTaskDTO();

        boardTaskDTO.setBoardTitle(boardTitle);
        boardTaskDTO.setBoardContent(boardContent);
        boardTaskDTO.setResponsibleMemNum(responsibleMember);
        boardTaskDTO.setTeamNum(referenceTeam);
        boardTaskDTO.setStartDate(startDate);
        boardTaskDTO.setEndDate(endDate);

        BoardTask boardTask=createNewBoardTask(session, boardTaskDTO);
        boardService.createBoardTask(boardTask);

        return "success";
    }


    // 업무게시판 삭제
    @DeleteMapping("/board/deleteboardtask")
    public ResponseEntity<Void> deleteBoardTask(@RequestBody Map<String, Object> reqData) {
        Long boardId = Long.parseLong(reqData.get("boardId").toString());
        boardService.deleteBoardTaskById(boardId);

        return ResponseEntity.noContent().build();
    }

    // 업무 진행상태 변경
    @PostMapping("/board/changetaskprogress")
    public ResponseEntity<Void> changeTaskProgress(@RequestParam Long boardId, @RequestParam String boardProgress) {
        boardService.changeTaskProgress(boardId, boardProgress);

        return ResponseEntity.noContent().build();
    }

    // 결재게시판작성폼 호출
    @GetMapping("/board/newapprovalvacationboard")
    public String callBoardApprovalWriteForm1(HttpSession session, Model model) {
        Member curMember=(Member)session.getAttribute("user");
        List<Member> mlist = member_repository.getAllMemberList();
        Member hrGuy = pickOneHrGuy(mlist);
        model.addAttribute("boardApprovalDTO", new BoardApprovalDTO());
        model.addAttribute("boardApprovalInfoDTO", new BoardApprovalInfoDTO());
        model.addAttribute("curMember",curMember);
        model.addAttribute("hrGuy", hrGuy);
        return "approval/appvacation";
    }

    @GetMapping("/board/newapprovalovertimeboard")
    public String callBoardApprovalWriteForm2(HttpSession session, Model model) {
        Member curMember=(Member)session.getAttribute("user");
        List<Member> mlist = member_repository.getAllMemberList();
        Member hrGuy = pickOneHrGuy(mlist);
        model.addAttribute("boardApprovalDTO", new BoardApprovalDTO());
        model.addAttribute("boardApprovalInfoDTO", new BoardApprovalInfoDTO());
        model.addAttribute("curMember",curMember);
        model.addAttribute("hrGuy", hrGuy);
        return "approval/appovertime";
    }

    @GetMapping("/board/newapprovalwocboard")
    public String callBoardApprovalWriteForm3(HttpSession session, Model model) {
        Member curMember=(Member)session.getAttribute("user");
        List<Member> mlist = member_repository.getAllMemberList();
        Member hrGuy = pickOneHrGuy(mlist);
        model.addAttribute("boardApprovalDTO", new BoardApprovalDTO());
        model.addAttribute("boardApprovalInfoDTO", new BoardApprovalInfoDTO());
        model.addAttribute("curMember",curMember);
        model.addAttribute("hrGuy", hrGuy);
        return "approval/appwoc";
    }

    // 공통된 결재게시판 작성로직
    private BoardApproval createNewBoardApproval(HttpSession session, Long memberId, BoardApprovalDTO boardApprovalDTO, BoardApprovalInfoDTO boardApprovalInfoDTO, String boardTitle, String approvalType) {
        Member memberObject=(Member)session.getAttribute("user");

        System.out.println("유저라는 세션에 들어와있는 맴버객채의 계정아이디를 확인하다 : "+memberObject.getMem_name());
        System.out.println("유저라는 세션에 들어와있는 맴버객채의 성별을 확인하다 : "+memberObject.getGender());

        LocalDateTime plus7Days = now.plusDays(7);
        String sevenFormattedDate = plus7Days.format(formatter);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode infoNode = mapper.createObjectNode();

        Member hrGuy = member_repository.findById(memberId);

        switch (approvalType) {
            case "VACATION":
                infoNode.put("startDate", boardApprovalInfoDTO.getStartDate());
                infoNode.put("endDate", boardApprovalInfoDTO.getEndDate());
                infoNode.put("deduction", boardApprovalInfoDTO.getDeduction());
                break;
            case "OVERTIME":
                infoNode.put("endTime", boardApprovalInfoDTO.getEndTime());
                infoNode.put("bonusAllowance", boardApprovalInfoDTO.getBonusAllowance());
                break;
            case "WORK_HOUR_CHANGE":
                infoNode.put("goToOffice", boardApprovalInfoDTO.getGoToOffice());
                infoNode.put("leaveOffice", boardApprovalInfoDTO.getLeaveOffice());
                break;
        }

        String infoJson = infoNode.toString();

        boardApprovalDTO.setApprovalInfo(infoJson);

        BoardApproval boardApproval=new BoardApproval();
        boardApproval.setBoardTitle(boardTitle);
        boardApproval.setBoardContent(boardApprovalDTO.getBoardContent());
        boardApproval.setCreateDate(formattedDate); // 작성일, 즉 기안 확인 시작날짜
        boardApproval.setUpdateDate(null);  // 수정일
        boardApproval.setBoardWriterObject(memberObject);
        boardApproval.setBoardWriter(memberObject.getMem_name());  //requestorMember
        boardApproval.setDueDate(sevenFormattedDate); // 기안 확인 마감날짜 (작성일로부터 7일)
        boardApproval.setApprovalType(approvalType);
        boardApproval.setApprovalStatus("REQUESTED");
        boardApproval.setApproverMemNum(hrGuy);  // 승인하는 사람의 정보를 담은 맴버객체
        boardApproval.setApprovalInfo(boardApprovalDTO.getApprovalInfo());

        return boardApproval;
    }

    // 각각의 결재게시판 작성로직
    @PostMapping("/board/newapprovalvacationboard")
    @ResponseBody
    public String writeNewBoardApprovalForm1(HttpSession session, @RequestParam("memberId") String memberId, @RequestParam("boardContent") String boardContent, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("deduction") String deduction) {
        BoardApprovalDTO boardApprovalDTO=new BoardApprovalDTO();
        BoardApprovalInfoDTO boardApprovalInfoDTO=new BoardApprovalInfoDTO();

        boardApprovalDTO.setBoardContent(boardContent);
        boardApprovalInfoDTO.setStartDate(startDate);
        boardApprovalInfoDTO.setEndDate(endDate);
        boardApprovalInfoDTO.setDeduction(deduction);

        BoardApproval boardApproval=createNewBoardApproval(session, Long.parseLong(memberId), boardApprovalDTO, boardApprovalInfoDTO, "approval vacation title", "VACATION");
        boardService.createBoardApproval1(boardApproval);

        return "success";
    }

    @PostMapping("/board/newapprovalovertimeboard")
    @ResponseBody
    public String writeNewBoardApprovalForm2(HttpSession session, @RequestParam("memberId") String memberId, @RequestParam("boardContent") String boardContent, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("endTime") String endTime, @RequestParam("bonusAllowance") String bonusAllowance) {
        BoardApprovalDTO boardApprovalDTO=new BoardApprovalDTO();
        BoardApprovalInfoDTO boardApprovalInfoDTO=new BoardApprovalInfoDTO();

        boardApprovalDTO.setBoardContent(boardContent);
        boardApprovalInfoDTO.setStartDate(startDate);
        boardApprovalInfoDTO.setEndDate(endDate);
        boardApprovalInfoDTO.setEndTime(endTime);
        boardApprovalInfoDTO.setBonusAllowance(bonusAllowance);

        BoardApproval boardApproval=createNewBoardApproval(session, Long.parseLong(memberId), boardApprovalDTO, boardApprovalInfoDTO, "approval overtime title", "OVERTIME");
        boardService.createBoardApproval2(boardApproval);

        return "success";
    }

    @PostMapping("/board/newapprovalwocboard")
    @ResponseBody
    public String writeNewBoardApprovalForm3(HttpSession session, @RequestParam("memberId") String memberId, @RequestParam("boardContent") String boardContent, @RequestParam("goToOffice") String goToOffice, @RequestParam("leaveOffice") String leaveOffice) {
        BoardApprovalDTO boardApprovalDTO=new BoardApprovalDTO();
        BoardApprovalInfoDTO boardApprovalInfoDTO=new BoardApprovalInfoDTO();

        boardApprovalDTO.setBoardContent(boardContent);
        boardApprovalInfoDTO.setGoToOffice(goToOffice);
        boardApprovalInfoDTO.setLeaveOffice(leaveOffice);

        BoardApproval boardApproval=createNewBoardApproval(session, Long.parseLong(memberId), boardApprovalDTO, boardApprovalInfoDTO, "approval overtime title", "WORK_HOUR_CHANGE");
        boardService.createBoardApproval3(boardApproval);

        return "success";
    }

    // 결재를 담당할 인사부 멤버 찾는 메서드
    public Member pickOneHrGuy(List<Member> mlist) {
        List<Member> hrMembers=mlist.stream()
                .filter(member->member.getTeam().getTeam_name().equals("인사부"))
                .collect(Collectors.toList());

        // 뽑아온 인사부 멤버들 중에서 랜덤으로 하나를 선택하다.
        int r=(int)(Math.random()*hrMembers.size());
        Member hrGuy=hrMembers.get(r);

        return hrGuy;
    }

    // 결재게시판 삭제
    @DeleteMapping("/board/deleteboardapproval")
    public ResponseEntity<Void> deleteBoardApproval(@RequestBody Map<String, Object> reqData) {
        Long boardId = Long.parseLong(reqData.get("boardId").toString());
        boardService.deleteBoardApprovalById(boardId);

        return ResponseEntity.noContent().build();
    }

    // 결재상태 변경
    @PostMapping("/board/changeapprovalstatus")
    public ResponseEntity<Void> changeApprovalStatus(HttpSession session, @RequestParam Long boardId, @RequestParam String approvalStatus) {
        Member memberObject=(Member)session.getAttribute("user");

        boardService.changeApprovalStatus(boardId, approvalStatus);
        BoardApproval boardApproval=boardService.findApprovalByBoardId(boardId);

        if(boardApproval.getApprovalStatus().equals("APPROVED")) {
            doApprove(memberObject, boardApproval);
        }
        else if(boardApproval.getApprovalStatus().equals("REJECTED")) {
            doReject(memberObject, boardApproval);
        }

        return ResponseEntity.noContent().build();
    }

    // 결재가 승인된 후에 후속처리될 로직들을 작성한 메서드
    private void doApprove(Member approver, BoardApproval boardApproval) {
        Member requestor=member_repository.findById(boardApproval.getBoardWriterObject().getId());

        String approvalInfo=boardApproval.getApprovalInfo();
        ObjectMapper mapper=new ObjectMapper();

        Mail approvedMail=new Mail();

        if(boardApproval.getApprovalType().equals("VACATION")) {
            String startDate="";
            String endDate="";
            int deduction=0;

            try {
                JsonNode infoNode=mapper.readTree(approvalInfo);
                startDate=infoNode.get("startDate").asText();
                endDate=infoNode.get("endDate").asText();
                deduction=infoNode.get("deduction").asInt();
            }
            catch(JsonProcessingException e) {
                e.printStackTrace();
            }

            int totalVac=requestor.getVacation();
            int vac=deduction;
            int curVac=totalVac-vac;

            memberService.updateMemberVacation(requestor, startDate, endDate, curVac);

            approvedMail.setTitle(requestor.getMem_name()+"님의 휴가 신청 결재안이 승인되다.");
            approvedMail.setBody("안녕하세요 "+requestor.getMem_name()+"님,<br><br>인사 담당자 "+approver.getMem_name()+"입니다. 먼저, 귀하께서 요청하신 휴가 신청에 대한 결재 결과를 안내드리게 되어 기쁩니다. <br>"+requestor.getMem_name()+"님이 요청하신 휴가 신청 결재가 승인되었음을 알려드립니다.<br><br>요청하신 휴가 기간은 "+startDate+"부터 "+endDate+"까지입니다. 승인에 따라 귀하의 총 연차 시간에서 "+deduction+"시간이 차감되었음을 알려드립니다. <br>이에 따라 남은 연차 시간을 확인하시기 바랍니다.<br><br>마지막으로, 휴가 기간 동안 편안한 시간 보내시기를 바라며, 필요한 사항이 있으시면 언제든지 연락주시기 바랍니다. 감사합니다.<br><br>인사 담당자 "+approver.getMem_name()+" 드림");
            approvedMail.setSendDate(formattedDate);
            approvedMail.setView(0);
            approvedMail.setSender(approver.getMem_id());
            approvedMail.setSender_name(approver.getMem_name());
            approvedMail.setSender_email(approver.getEmail());
            approvedMail.setReceiver(requestor.getEmail());

            mailSendService.sendMail(approvedMail);
        }
        else if(boardApproval.getApprovalType().equals("OVERTIME")) {

            String startDate="";
            String endDate="";
            String endTime="";
            int bonusAllowance=0;

            try {
                JsonNode infoNode=mapper.readTree(approvalInfo);
                startDate=infoNode.get("startDate").asText();
                endDate=infoNode.get("endDate").asText();
                endTime=infoNode.get("endTime").asText();
                bonusAllowance=infoNode.get("bonusAllowance").asInt();
            }
            catch(JsonProcessingException e) {
                e.printStackTrace();
            }

            approvedMail.setTitle(requestor.getMem_name()+"님의 연장근무 신청 결재안이 승인되다.");
            approvedMail.setBody("안녕하세요 "+requestor.getMem_name()+"님,<br><br>인사 담당자 "+approver.getMem_name()+"입니다. 먼저, 귀하께서 요청하신 추가근무 신청에 대한 결재 결과를 안내드리게 되어 기쁩니다. <br>"+requestor.getMem_name()+"님이 요청하신 추가근무 신청 결재가 승인되었음을 알려드립니다.<br><br>요청하신 추가근무 기간은 "+startDate+"부터 "+endDate+"까지이며, 해당 기간동안, 퇴근 시간으로부터 "+endTime+"까지의 추가근무가 승인되었습니다. 승인에 따라, 추가근무 기간동안 귀하에게 "+bonusAllowance+"원의 추가수당이 매일 지급됨을 알려드립니다. <br><br>마지막으로, 추가 근무 기간 동안 성실히 업무를 수행해주시길 부탁드리며, 회사의 발전과 성장에 기여하는데 있어 더욱 중요한 역할을 담당하시리라 믿습니다. <br>귀하의 헌신적인 노력과 성실함에 대해 깊이 감사드리며, 앞으로도 지속적인 발전과 성공을 기원하겠습니다. 또한, 필요한 사항이 있으시면 언제든지 연락주시기 바랍니다. 감사합니다.<br><br>인사 담당자 "+approver.getMem_name()+" 드림");
            approvedMail.setSendDate(formattedDate);
            approvedMail.setView(0);
            approvedMail.setSender(approver.getMem_id());
            approvedMail.setSender_name(approver.getMem_name());
            approvedMail.setSender_email(approver.getEmail());
            approvedMail.setReceiver(requestor.getEmail());

            mailSendService.sendMail(approvedMail);
        }
        else {
            String goToOffice="";
            String leaveOffice="";

            try {
                JsonNode infoNode=mapper.readTree(approvalInfo);
                goToOffice=infoNode.get("goToOffice").asText();
                leaveOffice=infoNode.get("leaveOffice").asText();
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            memberService.updateMemberWOC(requestor, goToOffice, leaveOffice);

            approvedMail.setTitle(requestor.getMem_name()+"님의 출퇴근시간 변경 신청 결재안이 승인되다.");
            approvedMail.setBody("안녕하세요 "+requestor.getMem_name()+"님,<br><br>인사 담당자 "+approver.getMem_name()+"입니다. 먼저, 귀하께서 요청하신 출퇴근시간 변경 신청에 대한 결재 결과를 안내드리게 되어 기쁩니다. <br>"+requestor.getMem_name()+"님이 요청하신 출퇴근시간 변경 신청 결재가 승인되었음을 알려드립니다.<br><br>현 시간부로, 귀하의 변경된 업무시간은 "+goToOffice+"부터 "+leaveOffice+"까지입니다. <br>아울러, 필요한 사항이 있으시면 언제든지 연락주시기 바랍니다. 감사합니다.<br><br>인사 담당자 "+approver.getMem_name()+" 드림");
            approvedMail.setSendDate(formattedDate);
            approvedMail.setView(0);
            approvedMail.setSender(approver.getMem_id());
            approvedMail.setSender_name(approver.getMem_name());
            approvedMail.setSender_email(approver.getEmail());
            approvedMail.setReceiver(requestor.getEmail());

            mailSendService.sendMail(approvedMail);
        }

    }

    // 결재가 반려된 후에 후속처리될 로직들을 작성한 메서드
    private void doReject(Member approver, BoardApproval boardApproval) {
        Member requestor=member_repository.findById(boardApproval.getBoardWriterObject().getId());

        Mail approvedMail=new Mail();

        if(boardApproval.getApprovalType().equals("VACATION")) {

            approvedMail.setTitle(requestor.getMem_name()+"님의 휴가 신청 결재안이 반려되다.");
            approvedMail.setBody(requestor.getMem_name()+"님,<br><br>인사 담당자 "+approver.getMem_name()+"입니다. 귀하께서 요청하신 휴가 신청에 대한 결재 결과를 안내드립니다. "+requestor.getMem_name()+"님이 요청하신 휴가 신청 결재가 반려되었습니다.<br><br>당신은 꿀을 빨아도 너무 심하게 빨았기 때문에 반려되었습니다.<br><br>무튼, 필요한 사항이 있으시면 언제든지 연락주시기 바랍니다. 감사합니다.<br><br>인사 담당자 "+approver.getMem_name()+" 드림");
            approvedMail.setSendDate(formattedDate);
            approvedMail.setView(0);
            approvedMail.setSender(approver.getMem_id());
            approvedMail.setSender_name(approver.getMem_name());
            approvedMail.setSender_email(approver.getEmail());
            approvedMail.setReceiver(requestor.getEmail());

            mailSendService.sendMail(approvedMail);
        }
        else if(boardApproval.getApprovalType().equals("OVERTIME")) {
            approvedMail.setTitle(requestor.getMem_name()+"님의 연장근무 신청 결재안이 반려되다.");
            approvedMail.setBody(requestor.getMem_name()+"님,<br><br>인사 담당자 "+approver.getMem_name()+"입니다. 귀하께서 요청하신 추가근무 신청에 대한 결재 결과를 안내드립니다. "+requestor.getMem_name()+"님이 요청하신 추가근무 신청 결재가 반려되었습니다.<br><br>당신이 뭐가 예쁘다고 당신에게 저희가 돈을 왜 더 줘야됩니까?<br><br>무튼, 필요한 사항이 있으시면 언제든지 연락주시기 바랍니다. 감사합니다.<br><br>인사 담당자 "+approver.getMem_name()+" 드림");
            approvedMail.setSendDate(formattedDate);
            approvedMail.setView(0);
            approvedMail.setSender(approver.getMem_id());
            approvedMail.setSender_name(approver.getMem_name());
            approvedMail.setSender_email(approver.getEmail());
            approvedMail.setReceiver(requestor.getEmail());

            mailSendService.sendMail(approvedMail);
        }
        else {

            approvedMail.setTitle(requestor.getMem_name()+"님의 출퇴근시간 변경 신청 결재안이 반려되다.");
            approvedMail.setBody(requestor.getMem_name()+"님,<br><br>인사 담당자 "+approver.getMem_name()+"입니다. 귀하께서 요청하신 출퇴근시간 변경 신청에 대한 결재 결과를 안내드립니다. "+requestor.getMem_name()+"님이 요청하신 출퇴근시간 변경 신청 결재가 반려되었습니다.<br><br>필요한 사항이 있으시면 언제든지 연락주시기 바랍니다. 감사합니다.<br><br>인사 담당자 "+approver.getMem_name()+" 드림");
            approvedMail.setSendDate(formattedDate);
            approvedMail.setView(0);
            approvedMail.setSender(approver.getMem_id());
            approvedMail.setSender_name(approver.getMem_name());
            approvedMail.setSender_email(approver.getEmail());
            approvedMail.setReceiver(requestor.getEmail());

            mailSendService.sendMail(approvedMail);
        }
        
    }

    // 결재 확인 기간(7일)을 넘었는데도 여전히 상태가 요청중(REQUESTED)인 결재는 만료됨(EXPIRED)으로 자동으로 변경해버리다.
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 자동으로 이 메서드를 실행하게 해주다.
    public void expireApprovals() {
        System.out.println("컨트롤러 문제없이 실행되다.");
        boardService.expireApprovals();
    }

    @GetMapping("/board/changeSession")
    public String sessionChange(HttpServletRequest req){
        System.out.println("you ve reached here");
        HttpSession session=req.getSession();
        String onOff=(String)session.getAttribute("onOff");
        onOff="on";
        session.setAttribute("onOff",onOff);
        System.out.println("bno");
        return "redirect:/moveProject";
    }


    // 작성된 공지 데이터로 추가
    @PostMapping("/board/newnotice")
    public String writeNewBoardNotice(BoardNoticeDTO boardNoticeDTO) {

        BoardNotice boardNotice=new BoardNotice();    //boardNotice객체
        boardNotice.setBoardTitle(boardNoticeDTO.getBoardTitle());  //제목
        boardNotice.setBoardContent(boardNoticeDTO.getBoardContent());  //내용
        boardNotice.setCreateDate(formattedDate); //작성일
        boardNotice.setBoardWriter("ADMIN"); // 작성자 ADMIN으로 고정

        boardService.createBoardNotice(boardNotice);
        // 데이터 추가 완료 후 공지페이지로 이동
        return "redirect:/moveNotice";
    }

//     //공지 작성 게시판으로 이동
//    @GetMapping("/board/newnotice")
//    public String callBoardNoticeWriteForm(Model model) {
//        model.addAttribute("boardNoticeDTO", new BoardNoticeDTO());
//        return "board/boardNoticeWriteForm";
//    }

//    // 공지 리스트
//    @GetMapping("/board/boardnoticelist")
//    public String boardNoticeList(Model model) {
//        List<BoardNotice> nlist=boardService.getNoticeList();
//        List<Reply> rplist=replyService.findReply();
//        model.addAttribute("nlist", nlist);
//        return "board/notice";
//    }

    //    // 공지 삭제
//    @DeleteMapping("/board/deleteboardNotice")
//    public ResponseEntity<Void> deleteBoardNotice(@RequestBody Map<String, Object> reqData) {
//        Long boardId = Long.parseLong(reqData.get("boardId").toString());
//
//        boardService.deleteBoardTaskById(boardId);
//        return ResponseEntity.noContent().build();
//    }
//
//    // 공지 변경
//    @PostMapping("/board/changetaskprogress")
//    public ResponseEntity<Void> changeTaskProgress(@RequestParam Long boardId, @RequestParam String boardProgress) {
//        boardService.changeTaskProgress(boardId, boardProgress);
//        return ResponseEntity.noContent().build();
//    }


}
