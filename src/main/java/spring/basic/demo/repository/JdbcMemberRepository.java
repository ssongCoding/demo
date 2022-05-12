package spring.basic.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import spring.basic.demo.domain.Member;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JdbcMemberRepository implements MemberRepositoryInterface{

    private DataSource dataSource;
    public int index = 0;

    @Autowired
    public JdbcMemberRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void saveMember(Member m) {
        // 객체에 id값 주고
        // DB에 저장

        //String sql = "INSERT INTO member(id, name) values(?,?)";
        String sql = "INSERT INTO member(name) VALUES(?)"; // identity

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //identity

            //m.setId(index++);

            //pstmt.setInt(1, m.getId());
            pstmt.setString(1, m.getName());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        close(conn);
    }

    @Override
    public Member findById(int id) {

        String sql = "SELECT * FROM member WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();

            if(rs.next()){
                Member m = new Member();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));

                return m;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally { // try, catch 문을 다 실행하고 떠나기 전에 무조건 실행!
            close(conn);
        }

        return null;
    }

    @Override
    public List<Member> findAll() {

        List<Member> memberList = new ArrayList<>();
        // SELECT * FROM member 결과를 담은 리스트

        String sql = "SELECT * FROM member";

        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while(rs.next()){
                Member m = new Member();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));

                memberList.add(m);
            }

            return memberList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally { // try, catch 문을 다 실행하고 떠나기 전에 무조건 실행!
            close(conn);
        }

        return null;
    }


    private void close(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
