package spring.basic.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.basic.demo.repository.JdbcMemberRepository;
import spring.basic.demo.repository.JdbcTemplateMemberRepository;
import spring.basic.demo.repository.MemberRepository;
import spring.basic.demo.repository.MemberRepositoryInterface;
import spring.basic.demo.service.MemberService;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Controller는 이 페이지에 모으지 않습니다!
     * 왜냐하면, 원래부터 스프링 관할
     */

    // @Service @Autowired
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepositoryInterface());
    }

    @Bean
    public MemberRepositoryInterface memberRepositoryInterface() {
        return new JdbcTemplateMemberRepository(dataSource);
        //return new JdbcMemberRepository(dataSource);
        //return new MemberRepository();
    }
}
