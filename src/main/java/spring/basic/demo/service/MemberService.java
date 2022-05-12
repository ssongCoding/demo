package spring.basic.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.basic.demo.domain.Member;
import spring.basic.demo.repository.MemberRepositoryInterface;

import java.util.List;
import java.util.Optional;


public class MemberService {

    private MemberRepositoryInterface repository;

    @Autowired
    public MemberService(MemberRepositoryInterface repository){
        this.repository = repository;
    }

    public void join(Member m) {
        repository.saveMember(m);
    }

    public Member findMemberById(int id) {
        return repository.findById(id);
    }

    public List<Member> findAllMember() {
        return repository.findAll();
    }
}
