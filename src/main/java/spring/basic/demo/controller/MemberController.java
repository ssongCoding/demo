package spring.basic.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.basic.demo.domain.Member;
import spring.basic.demo.service.MemberService;

import java.util.List;

@Controller
public class MemberController {

    MemberService service;

    @Autowired
    public MemberController(MemberService service) {
        this.service = service;
    }

    @GetMapping("members/new")
    public String createMember() {
        return "members/createForm";
    }

    // URL 안바뀐 상태에서 저장 하고 인덱스로 가는 거
    @PostMapping("members/new")
    public String create(MemberForm memberForm) {
        // 우리는 사용자가 입력한 name 값을 가지고 객체를 만들어요
        Member m = new Member();
        m.setName(memberForm.getName());

        // DB에 넣어야 해요. (DB에 넣어줄 때, Member 객체의 id값을 시스템에서 생성해줌)
        service.join(m);

        return "redirect:/"; // 제일 첫 페이지로 돌아감
    }

    @GetMapping("members/find")
    public String findMember() {
        return "members/findForm";
    }

    @PostMapping("members/find")
    public String find(@RequestParam("id") int id, Model model) {
        // Service를 통해서 id로 Member를 찾아서
        Member m = service.findMemberById(id);

        // 찾은 객체를 통째로 다음 페이지로 넘겨요
        model.addAttribute("member", m);

        // 다음 페이지로 이동!
        return "members/findMember";
    }

    @GetMapping("members") // localhost:8080/members
    public String memberList(Model model) {
        List<Member> members = service.findAllMember();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
