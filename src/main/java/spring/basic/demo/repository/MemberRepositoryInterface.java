package spring.basic.demo.repository;

import spring.basic.demo.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryInterface {

    void saveMember(Member m);
    Member findById(int id);
    List<Member> findAll();
}
