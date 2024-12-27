package smanageT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Sql {
	static Connection con;
	public Sql() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String usr = "SEMIHR";
			String pwd = "1234";
			con = DriverManager.getConnection(url, usr, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createTable(Connection con) {
		
		String sql;
		try {
				//사원테이블 생성
				sql = "CREATE TABLE emptable ("
					+ "   empno NUMBER(3) PRIMARY KEY,"
					+ "   ename VARCHAR2(20),"
					+ "   tel VARCHAR2(30),"
					+ "   jlevel VARCHAR2(20),"
					+ "   img VARCHAR2(200),"
					+ "   addr VARCHAR2(50),"
					+ "   dept VARCHAR2(20),"
					+ "   email VARCHAR2(50),"
					+ "   wtype VARCHAR2(20),"
					+ "   msgid VARCHAR2(20),"
					+ "   gender VARCHAR2(10),"
					+ "   bird DATE,"
					+ "   acct VARCHAR2(30) default '계좌번호 입력'"
					+ ")";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.executeQuery();
				
				//사원상태테이블 생성
				sql = "CREATE TABLE jobst ("
						+ "   empno NUMBER(3) NOT NULL,"
						+ "   estate VARCHAR2(10),"
						+ "   hiredate DATE,"
						+ "   quitdate DATE,"
						+ "   CONSTRAINT EMPK PRIMARY KEY (EMPNO),"
						+ "   CONSTRAINT EMFK2 FOREIGN KEY (EMPNO) REFERENCES EMPTABLE(EMPNO)"
						+ ")";
				
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				
				//급여테이블 생성
				sql = "CREATE TABLE PAYTABLE ("
						+ "	empno number(3),"
						+ " paydate number(10) default 20,   "
						+ "	bank varchar2(20) default '농협',"
						+ "	accname varchar2(20) default '임시',"
						+ " sal number(10) default 0,"
						+ " payfin varchar2(2) default 'Y',"
						+ " CONSTRAINT EMPK1 PRIMARY KEY (EMPNO),"
						+ "	CONSTRAINT EMFK FOREIGN KEY (empno) REFERENCES emptable(empno)"
						+ ")";
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				
				//요청테이블 생성
				sql = "create table request("
						+ "    seq number(5),"
						+ "    r_type varchar2(20),"
						+ "    empno number(3),"
						+ "    r_duration varchar2(39),"
						+ "    r_reason varchar2(20),"
						+ "    r_state varchar2(20) default '대기중',"
						+ "    r_date date,"
						+ "		CONSTRAINT EMFK8 FOREIGN KEY (empno) REFERENCES emptable(empno)"
						+ ")";
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				
				//연가정보 테이블 생성
				sql = "create table annual_standard ("
						+ "    n_year varchar2(10),"
						+ "    ann_day number(2)"
						+ ")";
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				
				//연가관리 생성
				sql = "create table annual ("
						+ "    empno NUMBER(3), "
						+ "    ename varchar2(20),"
						+ "    a_left number(2) default 11,"
						+ "    CONSTRAINT EMFK4 FOREIGN KEY (EMPNO) REFERENCES EMPTABLE(EMPNO)"
						+ ")";
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				
				
				//일정테이블 생성
				sql = "CREATE TABLE schedule1 ("
						+ "    s_index NUMBER(5) PRIMARY KEY,"
						+ "    s_title VARCHAR2(20),"
						+ "    s_date VARCHAR2(22),"
						+ "    s_type VARCHAR2(20),"
						+ "    s_info CLOB,"
						+ "    s_type_color VARCHAR(20)"
						+ ")";
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				
				
				sql = "CREATE TABLE NOTICE("
			            + "    NTID NUMBER(3) PRIMARY KEY,"
			            + "    EMPNO NUMBER(3),"
			            + "    TITLE VARCHAR2(100),"
			            + "    ENAME VARCHAR2(50),"
			            + "    DLEVEL VARCHAR2(20),"
			            + "    CONTENT CLOB,"
			            + "    NTDATE DATE"
			            + ")";
				ps = con.prepareStatement(sql);
				ps.executeUpdate();
				
				//게시판시퀀스 생성
				sql = "create sequence NT_SEQ START WITH 1 INCREMENT BY 1";
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				//요청시퀀스 생성
				sql = "create sequence request_sq minvalue 0 start with 1";
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				//일정시퀀스 생성
				sql = "CREATE SEQUENCE schedule1_seq START WITH 1 INCREMENT BY 1";
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				//사원번호시퀀스 생성
				sql = "CREATE SEQUENCE SEQ_EMPNO START WITH 1 INCREMENT BY 1 NOCACHE MINVALUE 1";
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				ps.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//테이블 삭제메소드
	public void dropTable(Connection con) {
		try {
			//급여테이블 삭제
			
			String sql = "drop table paytable";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeQuery();
			//사원상태 테이블 삭제
			sql = "drop table jobst";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//연가테이블 삭제
			sql = "drop table annual";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//요청테이블 삭제
			sql = "drop table REQUEST";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//연가템플릿 삭제
			sql = "drop table ANNUAL_STANDARD";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//사원테이블 삭제
			sql = "drop table emptable";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//일정테이블 삭제
			sql = "drop table schedule1";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//게시판 테이블 삭제
			sql = "drop table notice";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			
			//게시판번호시퀀스 삭제
			sql = "drop sequence NT_SEQ";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//요청번호시퀀스 삭제
			sql = "drop sequence request_sq";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//일정시퀀스삭제
			sql = "drop sequence schedule1_seq";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//사원번호 시퀀스삭제
			sql = "drop sequence SEQ_EMPNO";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//오라클특)개그튼거 남아있음
			sql = "purge recyclebin";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setDefault(Connection con) {
	    try {
	    	PreparedStatement ps = null;
			String[] insertQueries = {
					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '송준협', '010-9876-5432', '차장', 'no img', '서울', '경영', 'junhyeop.song@example.com', '정규직', 'junhyeop', '남', TO_DATE('1988-12-25', 'YYYY-MM-DD'), '계좌번호 입력')",

					// 사원 정보를 입력하는 쿼리
					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '박진우', '010-1234-5678', '사원', 'no img', '서울', '개발', 'jinwoo.park@example.com', '정규직', 'jinwoo', '남', TO_DATE('1990-05-15', 'YYYY-MM-DD'), '계좌번호 입력')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '박형주', '010-2345-6789', '주임', 'no img', '부산', '기획', 'hyeongju.park@example.com', '정규직', 'hyeongju', '남', TO_DATE('1992-07-22', 'YYYY-MM-DD'), '계좌번호 입력')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '김수민', '010-3456-7890', '대리', 'no img', '인천', '디자인', 'sumin.kim@example.com', '정규직', 'sumin', '여', TO_DATE('1994-09-30', 'YYYY-MM-DD'), '계좌번호 입력')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '김민주', '010-4567-8901', '과장', 'no img', '대구', '마케팅', 'minju.kim@example.com', '정규직', 'minju', '여', TO_DATE('1996-11-11', 'YYYY-MM-DD'), '계좌번호 입력')",
					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '고유리', '010-1111-1111', '사원', 'no img', '서울', '경영', 'yuri.ko@example.com', '정규직', 'yuri', '여', TO_DATE('1990-01-01', 'YYYY-MM-DD'), '계좌번호 입력')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '김정주', '010-2222-2222', '사원', 'no img', '서울', '경영', 'jeongju.kim@example.com', '정규직', 'jeongju', '남', TO_DATE('1991-02-02', 'YYYY-MM-DD'), '계좌번호 입력')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '유리윤나', '010-3333-3333', '사원', 'no img', '부산', '기획', 'yunna.yu@example.com', '정규직', 'yunna', '여', TO_DATE('1992-03-03', 'YYYY-MM-DD'), '계좌번호 입력')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '은종혁', '010-4444-4444', '사원', 'no img', '대구', '개발', 'jonghyuk.eun@example.com', '정규직', 'jonghyuk', '남', TO_DATE('1993-04-04', 'YYYY-MM-DD'), '계좌번호 입력')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '이지현', '010-5555-5555', '사원', 'no img', '인천', '디자인', 'jihyun.lee@example.com', '정규직', 'jihyun', '여', TO_DATE('1994-05-05', 'YYYY-MM-DD'), '계좌번호 입력')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '임수아', '010-6666-6666', '사원', 'no img', '부산', '마케팅', 'sua.lim@example.com', '정규직', 'sua', '여', TO_DATE('1995-06-06', 'YYYY-MM-DD'), '계좌번호 입력')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '정다영', '010-7777-7777', '사원', 'no img', '대전', '기획', 'dayoung.jung@example.com', '정규직', 'dayoung', '여', TO_DATE('1996-07-07', 'YYYY-MM-DD'), '계좌번호 입력')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '진예림', '010-8888-8888', '사원', 'no img', '광주', '개발', 'yerim.jin@example.com', '정규직', 'yerim', '여', TO_DATE('1997-08-08', 'YYYY-MM-DD'), '계좌번호 입력')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '송하영', '010-9999-9999', '사원', 'no img', '울산', '디자인', 'hayoung.song@example.com', '정규직', 'hayoung', '여', TO_DATE('1998-09-09', 'YYYY-MM-DD'), '계좌번호 입력')",

					// 사원 상태 정보를 입력하는 쿼리
					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '송준협'), '재직', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '박진우'), '재직', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '박형주'), '재직', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '김수민'), '재직', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '김민주'), '재직', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '고유리'), '재직', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '김정주'), '재직', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '유리윤나'), '재직', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '은종혁'), '재직', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '이지현'), '재직', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '임수아'), '재직', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '정다영'), '재직', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '진예림'), '재직', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '송하영'), '재직', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					// 급여 정보를 입력하는 쿼리
					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '송준협'), 20, '농협', '임시', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '박진우'), 20, '농협', '임시', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '박형주'), 20, '농협', '임시', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '김수민'), 20, '농협', '임시', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '김민주'), 20, '농협', '임시', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '고유리'), 20, '농협', '임시', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '김정주'), 20, '농협', '임시', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '유리윤나'), 20, '농협', '임시', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '은종혁'), 20, '농협', '임시', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '이지현'), 20, '농협', '임시', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '임수아'), 20, '농협', '임시', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '정다영'), 20, '농협', '임시', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '진예림'), 20, '농협', '임시', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '송하영'), 20, '농협', '임시', 0, 'Y')",

					// Annual 테이블에 데이터를 추가하는 쿼리
					"INSERT INTO annual (empno, ename, a_left) " + "VALUES (1, '송준협', 50)",

					"INSERT INTO annual (empno, ename, a_left) " + "VALUES (2, '박진우', 50)",

					"INSERT INTO annual (empno, ename, a_left) " + "VALUES (3, '박형주', 50)",

					"INSERT INTO annual (empno, ename, a_left) " + "VALUES (4, '김수민', 50)",

					"INSERT INTO annual (empno, ename, a_left) " + "VALUES (5, '김민주', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '고유리'), '고유리', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '김정주'), '김정주', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '유리윤나'), '유리윤나', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '은종혁'), '은종혁', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '이지현'), '이지현', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '임수아'), '임수아', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '정다영'), '정다영', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '진예림'), '진예림', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '송하영'), '송하영', 50)" };
	        
	        for (String sql : insertQueries) {
				ps = con.prepareStatement(sql);
				ps.executeUpdate();
			}
	        ps.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void setAnnStd(Connection con) {
		PreparedStatement ps = null;
		try {
			// SQL 문장들
			String[] sqlStatements = { 
					"insert into annual_standard values ('1년미만', 11)",
					"insert into annual_standard values ('1년', 15)", 
					"insert into annual_standard values ('2년', 15)",
					"insert into annual_standard values ('3년', 16)", 
					"insert into annual_standard values ('4년', 16)",
					"insert into annual_standard values ('5년', 17)", 
					"insert into annual_standard values ('6년이상', 17)",
					"insert into annual_standard values ('22년', 25)" 
					};

			// 각 SQL 문장 실행
			for (String sql : sqlStatements) {
				ps = con.prepareStatement(sql);
				ps.executeUpdate();
			}
			
			ps.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setHoliday(Connection con) {
	    PreparedStatement ps = null;
	    try {
	        // SQL 문장들
	        String[] sqlStatements = {
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '설날 전날', '2024-01-23~2024-01-23', '공휴일', '설날 전날', '회사')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '설날', '2024-01-24~2024-01-24', '공휴일', '설날', '회사')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '설날 다음 날', '2024-01-25~2024-01-25', '공휴일', '설날 다음 날', '회사')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '부처님 오신 날', '2024-04-08~2024-04-08', '공휴일', '부처님 오신 날', '회사')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '어린이날', '2024-05-05~2024-05-05', '공휴일', '어린이날', '회사')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '현충일', '2024-06-06~2024-06-06', '공휴일', '현충일', '회사')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '추석 전날', '2024-09-12~2024-09-12', '공휴일', '추석 전날', '회사')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '추석', '2024-09-13~2024-09-13', '공휴일', '추석', '회사')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '추석 다음 날', '2024-09-14~2024-09-14', '공휴일', '추석 다음 날', '회사')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '한글날', '2024-10-09~2024-10-09', '공휴일', '한글날', '회사')"
	        };

	        // 각 SQL 문장 실행
	        for (String sql : sqlStatements) {
	            ps = con.prepareStatement(sql);
	            ps.executeUpdate();
	        }

	        ps.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	public static void main(String[] args) {
		
		try {
			Sql s = new Sql();
			
			//테이블 삭제 메소드
			s.dropTable(con);
			//테이블 생성 메소드
			s.createTable(con);
			//기본 데이터 생성메소드
			s.setDefault(con);
			//연차정보 생성메소드
			s.setAnnStd(con);
			//공휴일 정보 생성 메소드
			s.setHoliday(con);
			
			System.out.println("삭제 및 테이블 생성 완료");
			
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
