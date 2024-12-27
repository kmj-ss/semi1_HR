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
				//������̺� ����
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
					+ "   acct VARCHAR2(30) default '���¹�ȣ �Է�'"
					+ ")";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.executeQuery();
				
				//����������̺� ����
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
				
				//�޿����̺� ����
				sql = "CREATE TABLE PAYTABLE ("
						+ "	empno number(3),"
						+ " paydate number(10) default 20,   "
						+ "	bank varchar2(20) default '����',"
						+ "	accname varchar2(20) default '�ӽ�',"
						+ " sal number(10) default 0,"
						+ " payfin varchar2(2) default 'Y',"
						+ " CONSTRAINT EMPK1 PRIMARY KEY (EMPNO),"
						+ "	CONSTRAINT EMFK FOREIGN KEY (empno) REFERENCES emptable(empno)"
						+ ")";
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				
				//��û���̺� ����
				sql = "create table request("
						+ "    seq number(5),"
						+ "    r_type varchar2(20),"
						+ "    empno number(3),"
						+ "    r_duration varchar2(39),"
						+ "    r_reason varchar2(20),"
						+ "    r_state varchar2(20) default '�����',"
						+ "    r_date date,"
						+ "		CONSTRAINT EMFK8 FOREIGN KEY (empno) REFERENCES emptable(empno)"
						+ ")";
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				
				//�������� ���̺� ����
				sql = "create table annual_standard ("
						+ "    n_year varchar2(10),"
						+ "    ann_day number(2)"
						+ ")";
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				
				//�������� ����
				sql = "create table annual ("
						+ "    empno NUMBER(3), "
						+ "    ename varchar2(20),"
						+ "    a_left number(2) default 11,"
						+ "    CONSTRAINT EMFK4 FOREIGN KEY (EMPNO) REFERENCES EMPTABLE(EMPNO)"
						+ ")";
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				
				
				//�������̺� ����
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
				
				//�Խ��ǽ����� ����
				sql = "create sequence NT_SEQ START WITH 1 INCREMENT BY 1";
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				//��û������ ����
				sql = "create sequence request_sq minvalue 0 start with 1";
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				//���������� ����
				sql = "CREATE SEQUENCE schedule1_seq START WITH 1 INCREMENT BY 1";
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				//�����ȣ������ ����
				sql = "CREATE SEQUENCE SEQ_EMPNO START WITH 1 INCREMENT BY 1 NOCACHE MINVALUE 1";
				ps = con.prepareStatement(sql);
				ps.executeQuery();
				ps.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//���̺� �����޼ҵ�
	public void dropTable(Connection con) {
		try {
			//�޿����̺� ����
			
			String sql = "drop table paytable";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeQuery();
			//������� ���̺� ����
			sql = "drop table jobst";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//�������̺� ����
			sql = "drop table annual";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//��û���̺� ����
			sql = "drop table REQUEST";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//�������ø� ����
			sql = "drop table ANNUAL_STANDARD";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//������̺� ����
			sql = "drop table emptable";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//�������̺� ����
			sql = "drop table schedule1";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//�Խ��� ���̺� ����
			sql = "drop table notice";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			
			//�Խ��ǹ�ȣ������ ����
			sql = "drop sequence NT_SEQ";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//��û��ȣ������ ����
			sql = "drop sequence request_sq";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//��������������
			sql = "drop sequence schedule1_seq";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//�����ȣ ����������
			sql = "drop sequence SEQ_EMPNO";
			ps = con.prepareStatement(sql);
			ps.executeQuery();
			//����ŬƯ)����ư�� ��������
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
							+ "VALUES (seq_empno.NEXTVAL, '������', '010-9876-5432', '����', 'no img', '����', '�濵', 'junhyeop.song@example.com', '������', 'junhyeop', '��', TO_DATE('1988-12-25', 'YYYY-MM-DD'), '���¹�ȣ �Է�')",

					// ��� ������ �Է��ϴ� ����
					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '������', '010-1234-5678', '���', 'no img', '����', '����', 'jinwoo.park@example.com', '������', 'jinwoo', '��', TO_DATE('1990-05-15', 'YYYY-MM-DD'), '���¹�ȣ �Է�')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '������', '010-2345-6789', '����', 'no img', '�λ�', '��ȹ', 'hyeongju.park@example.com', '������', 'hyeongju', '��', TO_DATE('1992-07-22', 'YYYY-MM-DD'), '���¹�ȣ �Է�')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '�����', '010-3456-7890', '�븮', 'no img', '��õ', '������', 'sumin.kim@example.com', '������', 'sumin', '��', TO_DATE('1994-09-30', 'YYYY-MM-DD'), '���¹�ȣ �Է�')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '�����', '010-4567-8901', '����', 'no img', '�뱸', '������', 'minju.kim@example.com', '������', 'minju', '��', TO_DATE('1996-11-11', 'YYYY-MM-DD'), '���¹�ȣ �Է�')",
					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '������', '010-1111-1111', '���', 'no img', '����', '�濵', 'yuri.ko@example.com', '������', 'yuri', '��', TO_DATE('1990-01-01', 'YYYY-MM-DD'), '���¹�ȣ �Է�')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '������', '010-2222-2222', '���', 'no img', '����', '�濵', 'jeongju.kim@example.com', '������', 'jeongju', '��', TO_DATE('1991-02-02', 'YYYY-MM-DD'), '���¹�ȣ �Է�')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '��������', '010-3333-3333', '���', 'no img', '�λ�', '��ȹ', 'yunna.yu@example.com', '������', 'yunna', '��', TO_DATE('1992-03-03', 'YYYY-MM-DD'), '���¹�ȣ �Է�')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '������', '010-4444-4444', '���', 'no img', '�뱸', '����', 'jonghyuk.eun@example.com', '������', 'jonghyuk', '��', TO_DATE('1993-04-04', 'YYYY-MM-DD'), '���¹�ȣ �Է�')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '������', '010-5555-5555', '���', 'no img', '��õ', '������', 'jihyun.lee@example.com', '������', 'jihyun', '��', TO_DATE('1994-05-05', 'YYYY-MM-DD'), '���¹�ȣ �Է�')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '�Ӽ���', '010-6666-6666', '���', 'no img', '�λ�', '������', 'sua.lim@example.com', '������', 'sua', '��', TO_DATE('1995-06-06', 'YYYY-MM-DD'), '���¹�ȣ �Է�')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '���ٿ�', '010-7777-7777', '���', 'no img', '����', '��ȹ', 'dayoung.jung@example.com', '������', 'dayoung', '��', TO_DATE('1996-07-07', 'YYYY-MM-DD'), '���¹�ȣ �Է�')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '������', '010-8888-8888', '���', 'no img', '����', '����', 'yerim.jin@example.com', '������', 'yerim', '��', TO_DATE('1997-08-08', 'YYYY-MM-DD'), '���¹�ȣ �Է�')",

					"INSERT INTO emptable (empno, ename, tel, jlevel, img, addr, dept, email, wtype, msgid, gender, bird, acct) "
							+ "VALUES (seq_empno.NEXTVAL, '���Ͽ�', '010-9999-9999', '���', 'no img', '���', '������', 'hayoung.song@example.com', '������', 'hayoung', '��', TO_DATE('1998-09-09', 'YYYY-MM-DD'), '���¹�ȣ �Է�')",

					// ��� ���� ������ �Է��ϴ� ����
					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), '����', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), '����', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), '����', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '�����'), '����', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '�����'), '����', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), '����', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), '����', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '��������'), '����', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), '����', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), '����', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '�Ӽ���'), '����', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '���ٿ�'), '����', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), '����', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					"INSERT INTO jobst (empno, estate, hiredate) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '���Ͽ�'), '����', TO_DATE('2000-06-26', 'YYYY-MM-DD'))",

					// �޿� ������ �Է��ϴ� ����
					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), 20, '����', '�ӽ�', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), 20, '����', '�ӽ�', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), 20, '����', '�ӽ�', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '�����'), 20, '����', '�ӽ�', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '�����'), 20, '����', '�ӽ�', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), 20, '����', '�ӽ�', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), 20, '����', '�ӽ�', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '��������'), 20, '����', '�ӽ�', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), 20, '����', '�ӽ�', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), 20, '����', '�ӽ�', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '�Ӽ���'), 20, '����', '�ӽ�', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '���ٿ�'), 20, '����', '�ӽ�', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), 20, '����', '�ӽ�', 0, 'Y')",

					"INSERT INTO paytable (empno, paydate, bank, accname, sal, payfin) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '���Ͽ�'), 20, '����', '�ӽ�', 0, 'Y')",

					// Annual ���̺� �����͸� �߰��ϴ� ����
					"INSERT INTO annual (empno, ename, a_left) " + "VALUES (1, '������', 50)",

					"INSERT INTO annual (empno, ename, a_left) " + "VALUES (2, '������', 50)",

					"INSERT INTO annual (empno, ename, a_left) " + "VALUES (3, '������', 50)",

					"INSERT INTO annual (empno, ename, a_left) " + "VALUES (4, '�����', 50)",

					"INSERT INTO annual (empno, ename, a_left) " + "VALUES (5, '�����', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), '������', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), '������', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '��������'), '��������', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), '������', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), '������', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '�Ӽ���'), '�Ӽ���', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '���ٿ�'), '���ٿ�', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '������'), '������', 50)",

					"INSERT INTO annual (empno, ename, a_left) "
							+ "VALUES ((SELECT empno FROM emptable WHERE ename = '���Ͽ�'), '���Ͽ�', 50)" };
	        
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
			// SQL �����
			String[] sqlStatements = { 
					"insert into annual_standard values ('1��̸�', 11)",
					"insert into annual_standard values ('1��', 15)", 
					"insert into annual_standard values ('2��', 15)",
					"insert into annual_standard values ('3��', 16)", 
					"insert into annual_standard values ('4��', 16)",
					"insert into annual_standard values ('5��', 17)", 
					"insert into annual_standard values ('6���̻�', 17)",
					"insert into annual_standard values ('22��', 25)" 
					};

			// �� SQL ���� ����
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
	        // SQL �����
	        String[] sqlStatements = {
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '���� ����', '2024-01-23~2024-01-23', '������', '���� ����', 'ȸ��')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '����', '2024-01-24~2024-01-24', '������', '����', 'ȸ��')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '���� ���� ��', '2024-01-25~2024-01-25', '������', '���� ���� ��', 'ȸ��')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '��ó�� ���� ��', '2024-04-08~2024-04-08', '������', '��ó�� ���� ��', 'ȸ��')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '��̳�', '2024-05-05~2024-05-05', '������', '��̳�', 'ȸ��')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '������', '2024-06-06~2024-06-06', '������', '������', 'ȸ��')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '�߼� ����', '2024-09-12~2024-09-12', '������', '�߼� ����', 'ȸ��')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '�߼�', '2024-09-13~2024-09-13', '������', '�߼�', 'ȸ��')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '�߼� ���� ��', '2024-09-14~2024-09-14', '������', '�߼� ���� ��', 'ȸ��')",
	            
	            "INSERT INTO schedule1 (s_index, s_title, s_date, s_type, s_info, s_type_color) " +
	            "VALUES (schedule1_seq.NEXTVAL, '�ѱ۳�', '2024-10-09~2024-10-09', '������', '�ѱ۳�', 'ȸ��')"
	        };

	        // �� SQL ���� ����
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
			
			//���̺� ���� �޼ҵ�
			s.dropTable(con);
			//���̺� ���� �޼ҵ�
			s.createTable(con);
			//�⺻ ������ �����޼ҵ�
			s.setDefault(con);
			//�������� �����޼ҵ�
			s.setAnnStd(con);
			//������ ���� ���� �޼ҵ�
			s.setHoliday(con);
			
			System.out.println("���� �� ���̺� ���� �Ϸ�");
			
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
