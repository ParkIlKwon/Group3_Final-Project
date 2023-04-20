    package com.jpa.intra.controller;

    import com.jpa.intra.domain.Address;
    import com.jpa.intra.domain.Attendance;
    import com.jpa.intra.domain.Member;
    import com.jpa.intra.domain.Team;
    import com.jpa.intra.query.MemberDTO;
    import com.jpa.intra.repository.Member_Repository;
    import com.jpa.intra.service.AttendanceService;
    import com.jpa.intra.service.FileService;
    import com.jpa.intra.service.MemberService;
    import com.jpa.intra.util.TeamConverter;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.*;
    import java.text.SimpleDateFormat;

    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpSession;
    import javax.validation.Valid;
    import java.io.IOException;
    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;


    @Controller //model 을 지정 , view를 반환 시켜주는 컨트롤러 역활
    @RequiredArgsConstructor //생성자 주입
    @RequestMapping("/members") //공통된 폴더명 하나로 묶어서 사용
    public class MemberController {

        private final MemberService service;
        private final Member_Repository member_repository;
        private final TeamConverter tc;
        @GetMapping()
        public String addForm(Model model){

            model.addAttribute("memberDTO",new MemberDTO());
            //memberDTO 형식으로 생성자 만들어서 보내줌 .
            return "pages/joinForm";
        }

        final private FileService fileService;
        @PostMapping() //form 에서 post형식으로
                                    //받아오면 여기로 들어옴 .
        public String addPro(HttpServletRequest request, @Valid MemberDTO getmember, BindingResult result, Model model)
        throws IOException {

            Member m = new Member(); // 받아온 정보 Member 객체로 파싱
            m.setMem_id(getmember.getId());
            m.setMem_pw(getmember.getPw());
            m.setMem_name(getmember.getName());
            Address address = new Address(getmember.getAddress_name(),getmember.getAddress_road());
            m.setAddress(address); //Address 객체생성후 받아온 주소정보 각각 넣어줌
            m.setStatus("offline");
            m.setVacation(120);
            m.setGender(getmember.getGender());
            m.setEmail(getmember.getMemail());



            Team t = tc.convert("1");


            fileService.uploadProfileImage(getmember.getProfileFile(),m);

            service.Join(m,t); //서비스로 등록 영속성 - DB 반영

            return "redirect:/moveDashboard"; //홈 페이지로 리디렉션.
        }

      /*  @GetMapping("/login")
        public String LoginForm(Model model){

            model.addAttribute("memberDTO",new MemberDTO());
            //memberDTO 형식으로 생성자 만들어서 보내줌 .
            return "pages/loginForm";
        }
*/
        @GetMapping("/logout")
        public String Logout(HttpServletRequest request){
            HttpSession session = request.getSession();
            session.invalidate();
            return "login";
        }

        @PostMapping("/login")
        @ResponseBody
        public String login(@RequestParam("id") String id,
                            @RequestParam("pw") String pw,
                            HttpServletRequest request) {
            System.out.println(id + pw);
            HttpSession session = request.getSession();
            Member m = service.Login(id,pw);

            if (m != null) {
                session.setAttribute("log",id);
                session.setAttribute("user",m);
                session.setAttribute("teamName",m.getTeam().getTeam_name());
                session.setAttribute("memberList",member_repository.getAllMemberList());

                return id;
            } else {
                return null;
            }
        }

        @GetMapping("/main")
        public String membersMain(Model model){
            List<Member> memberList = member_repository.getAllMemberList();
            model.addAttribute("memberList",memberList);
            return "members/main";
        }


        @PostMapping("/main")
        @ResponseBody
        public List<String> memberMain(@RequestParam("id") Long id){
            Member member = member_repository.findById(id);
            List<String> list = new ArrayList<>();
            if(member != null) {
                list.add(member.getMem_img());
                list.add(member.getMem_name());
                list.add(member.getTeam().getTeam_name());
                list.add(member.getEmp_type());
                list.add(String.valueOf(member.getId()));
//                list.add(member.getMem_id());
                list.add(member.getInline_tel());
                list.add(member.getOutline_tel());
                list.add(member.getEmail());
                list.add(member.getAddress().getAddress_name());
                list.add(member.getAddress().getRoad_address_name());
                list.add(String.valueOf(member.getBirthday()));
                list.add(member.getGender());
                list.add(String.valueOf(member.getReg_date()));
                list.add(String.valueOf(member.getVacation()));

                return list;
            }
            return null;
        }

        private final AttendanceService attendanceService;
        @GetMapping("/AttendanceControl") //출퇴근 로직
        public String gettingStart(HttpServletRequest request){

            HttpSession session = request.getSession();
            Member m = (Member)session.getAttribute("user"); //현재 세션에 저장된 유저 객체를 불러와서
            Attendance attendance = new Attendance(); //출석부 객체 생성 필드 == (유저아이디,출근시간,퇴근시간 )

            Date now = new Date(); //시간데이터 받아옴 .
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd a HH:mm:ss");
            //0000년 00 월 00일 오후 00시 00분
            attendance.setUserId(m.getMem_id());

            if(m.getStatus().equals("offline")){ //오프라인이면 온라인으로 그 반대면 반대로 .

                attendance.setTodayInWorkTime(simpleDateFormat.format(now));
                attendanceService.addAttendance(attendance); //출석부에 시간 데이터랑 현재 유저 넣어줌.
                m.setStatus("online");
            }else{

                attendance.setTodayOutWorkTime(simpleDateFormat.format(now));
                attendanceService.setoutTime(attendance);
                //퇴근시에는 출근데이터에 퇴근시간 데이터를 set 해줌 .
                m.setStatus("offline");
            }


            
           


            service.Update(m); //리포지토리 JPQL 문에서 업데이트 , 저장 시켜줌 .
            session.setAttribute("user",m); //다시 업데이트 된 유저 정보를 반영 view 로 보내줌 .
            return "redirect:/moveDashboard";
        }

        // 마이페이지 눌렀을 때 members/profile.html
        @GetMapping("/profile")
        public String memberAttendance(Model model , HttpServletRequest request){ //마이페이지로 가는 로직
            HttpSession session = request.getSession();
            String userId = (String) session.getAttribute("log");
            model.addAttribute("page", "마이페이지");
            model.addAttribute("AttendList" , attendanceService.getAllAttendList(userId));

            // 부서 사람들
            List<Member> memberList = member_repository.getAllMemberList();
            Member member = (Member) session.getAttribute("user");
            List<Member> teamMemberList = new ArrayList<>();
            for(int i=0; i<memberList.size(); i++){
                if(memberList.get(i).getTeam().getTeam_name().equals(member.getTeam().getTeam_name())){
                    teamMemberList.add(memberList.get(i));
                }
            }
            model.addAttribute("teamMemberList",teamMemberList);
            return "members/profile";
        }

        // 비밀번호 찾기에서 인증 완료 후 비밀번호 재설정
        @PostMapping("/findPw")
        @ResponseBody
        public String findMemberPw(@RequestParam("memberId")String memberId, @RequestParam("email")String email){
            service.rePassword(memberId, email);
            return "비밀번호가 초기화 됬습니다.";
        }


    }
