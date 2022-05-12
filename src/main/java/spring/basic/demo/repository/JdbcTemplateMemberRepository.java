package spring.basic.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import spring.basic.demo.domain.Member;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepositoryInterface{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * //Connection
     * //PreparedStatement
     * SQL : String sql = "INSERT INTO member(name) VALUES(?)";
     * PS  SQL, id값 자동 생성 요청
     * name값을 넣어주기
     * Connection  PS
     * PS 실행
     */
    @Override
    public void saveMember(Member m) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        // String : 열의 이름, Object : 그 자리에 들어갈 값
        parameters.put("name", m.getName());

        jdbcInsert.execute(parameters);
    }

    @Override
    public Member findById(int id) {
        return jdbcTemplate.query("SELECT * FROM member WHERE id = ?"
                , memberRowMapper(), id).get(0);
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("SELECT * FROM member", memberRowMapper());
    }

    /**
     * Select 했을 때, 사용할 결과 행들 (ResultSet)
     */
    private RowMapper<Member> memberRowMapper() {
        // RowMapper를 반환해주는 메소드를 짤거에요
        return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member m = new Member();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));
                return m;
            }
        };
    }
}
