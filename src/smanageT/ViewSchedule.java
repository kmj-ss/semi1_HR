package smanageT;

import java.awt.*;
import java.util.*;

//import javax.naming.spi.DirStateFactory.Result;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.table.*;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ViewSchedule extends JPanel implements ActionListener {
	Scanner sc = new Scanner(System.in);
	LineBorder lb;
	JPanel MainPan; // 메인 패널
	JPanel MainPan_North;
	JPanel ButtonPan; // 하단 버튼용 패널(일정추가,일정삭제)
	JPanel Input_title;
	JPanel Input_date;
	JPanel Input_type;

	JComboBox<String> combo_type;

	JPanel Input_South;
	JButton bt4;// 하단 일정 추가 버튼
	JButton bt5;// 하단 일정 삭제 버튼

	JLabel Lab;
	JLabel Lab2;
	JButton bt1;
	JButton bt2;
	JButton bt3;
	JTextArea inputTA;
	JTextField inputTF;
	JTextField inputTF1;
	JTextField inputTF2;

	JDialog newntc;

	Connection conn;
	String sql;
	String lab_db[];
	// Vector db_v;

	PreparedStatement ps;

//	PreparedStatement ps;
//	ResultSet rs;
	public ViewSchedule(Connection conn) {

		this.conn = conn;

		this.setLayout(new BorderLayout());
		MainPan = new JPanel();
		ButtonPan = new JPanel();
		MainPan_North = new JPanel();

		LocalDate now = LocalDate.now();
		
		MainPan.setBorder(new TitledBorder(new LineBorder(Color.black, 2), now.getYear()+"년 일정"));
		MainPan.setLayout(new BorderLayout());
		ButtonPan.setLayout(new FlowLayout(FlowLayout.LEFT));
		MainPan_North.setLayout(new GridLayout(20,0));
		
		// MainPan_North.setBackground(Color.pink);

//일정 게시판 출력 시작
		sql = "select s_date,s_title,s_info from schedule1";

		try {
			ps = conn.prepareStatement(sql);

			// ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			//db_v = new Vector();

			while (rs.next()) {
				lab_db = new String[3];

				lab_db[0] = rs.getString("s_title");
				lab_db[1] = rs.getString("s_date");
				lab_db[2] = rs.getString("s_info");

				//db_v.add(lab_db);

				//ln(lab_db[0] + "/t" + lab_db[1] + "/t" + lab_db[2]);
				JLabel Lab = new JLabel();
				String a = lab_db[0]+ "/"+ lab_db[1] +"/"+ lab_db[2];

				
				Lab.setText(a);
				
				MainPan_North.add(Lab);

			}

		} catch (SQLException e) {

			e.printStackTrace();
			//ln("select문 오류");
		}

//일정 게시판 출력 끝	
		bt1 = new JButton("일정 추가");
		bt1.setUI(new RoundedButton());
		bt1.setFocusPainted(false);
		//bt2 = new JButton("일정 삭제");
		// 추가 입력란
		ButtonPan.add(bt1);
		//ButtonPan.add(bt2);
		
		MainPan.add(MainPan_North,"North");
		//MainPan_North.add(Lab,"North");
		MainPan.add(ButtonPan, "South");
		
		bt1.addActionListener(this);
		//bt2.addActionListener(this);

		this.add(MainPan);

//일정추가 다이어로그 시작

		newntc = new JDialog();

		newntc.setTitle("새 일정 추가");
		newntc.setSize(400, 400);
		newntc.setLayout(new BorderLayout());
		newntc.setLocationRelativeTo(null);

		// JDialog로 띄워진 새 창에, 컨텐츠들의 위치를 지정하기 위한 패널
		JPanel inputPanel = new JPanel(new BorderLayout());
		newntc.add(inputPanel, BorderLayout.CENTER);

		// 상단 시작//
		JPanel titlePanel = new JPanel(new GridLayout(3, 1));
		Input_title = new JPanel(new BorderLayout());
		Input_date = new JPanel(new BorderLayout());
		Input_type = new JPanel(new BorderLayout());

		titlePanel.add(Input_title);
		titlePanel.add(Input_date);
		titlePanel.add(Input_type);
		inputPanel.add(titlePanel, BorderLayout.NORTH);

		inputTF = new JTextField();
		inputTF1 = new JTextField();
		inputTF2 = new JTextField();

		inputTF.setSize(5, 5);
		//bt3 = new JButton("조회"); 조회버튼삭제예정
//구분 콤보박스
		String type[] = {"회사","부서"};
		combo_type = new JComboBox<String>(type);
		combo_type.setUI(new Combotest());
		combo_type.setPreferredSize(new Dimension(70,23));
		combo_type.setBackground(Color.white);
		
//구분 콤보박스 끝
		Input_title.add(new JLabel("제목 :"), "West");
		Input_title.add(inputTF, "Center");

		Input_date.add(new JLabel("날짜 :"), "West");
		Input_date.add(inputTF1, "Center");

		Input_type.add(new JLabel("구분 :"), "West");
		Input_type.add(inputTF2, "Center");
		//Input_type.add(bt3, "East"); 버튼 부착 삭제예정
		Input_type.add(combo_type, "East");
		//Schedule.chage_backgrount((String)combo_type.getSelectedItem());
			
		
		
		// 상단 끝//
//		 inputTF = new JTextField();
//		 titlePanel.add(inputTF, BorderLayout.CENTER);

		// 중단 시작//
		JPanel contentPanel = new JPanel(new BorderLayout());
		contentPanel.add(new JLabel("내용: "), BorderLayout.NORTH);
		inputTA = new JTextArea(5, 20);
		JScrollPane contentScrollPane = new JScrollPane(inputTA);
		contentPanel.add(contentScrollPane, BorderLayout.CENTER);
		inputPanel.add(contentPanel, BorderLayout.CENTER);
		// 중단 끝//

		// 하단 시작//
		Input_South = new JPanel(new GridLayout(2, 1));
		bt4 = new JButton("추가");
//		bt4.setUI(new RoundedButton());
		bt4.setFocusPainted(false);
		bt5 = new JButton("취소");
//		bt5.setUI(new RoundedButton());
		bt5.setFocusPainted(false);
		Input_South.add(bt4);
		bt5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newntc.setVisible(false);

			}
		});

//			dbc.exitconn();
		bt4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(bt4.getText().equals("추가")) {
					
				
				
				//ln(e.getSource()+"뷰스케줄 수정중입니다.");
				
				String title, date, type, info,type_combo;
				
				title = inputTF.getText();
				date = inputTF1.getText();
				type = inputTF2.getText();
				type_combo = (String)combo_type.getSelectedItem();
				info = inputTA.getText();

				//ln(title);
				//ln(type);
				//ln(date);
				//ln(info);
				
			
				try {
					String sql = "insert into schedule1 values (schedule1_seq.nextval,?,?,?,?,sysdate)";
					PreparedStatement ps = conn.prepareStatement(sql); // sql문을 담을 statement 문

					ps.setString(1, title);
					ps.setString(2, date);
					ps.setString(3, type);
					ps.setString(4, info);
					////ln();
					Lab = new JLabel(date + "/t" + title + "/t" + info);

					 ps.executeUpdate();
					 //MainPan_North.setSize(getSize());

				} catch (SQLException e2) {
					e2.printStackTrace();
					//ln("sql문 오류");
					}
				} else {
					//ln("뷰 스케줄 실행안됌 >> update문 실행됌");
				
				}
			}
			
			

		});
		Input_South.add(bt5);

		inputPanel.add(Input_South, "South");
		// 하단 끝//
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == bt1) { // 일정 추가 버튼
			newntc.setVisible(true);
//		//ln(obj);
//		//ln(bt4);
		} else if (obj == bt2) { // 일정 삭제 버튼

		} else if (obj == bt3) { // 일정 추가 다이어로그의 조회 버튼(삭제 예정)

		} else if (obj == bt4) { // 일정 추가 다이어로그의 추가 버튼
//		newntc_title[0] = inputTF.getText();
//		//ln(newntc_title[0]);
		} else if (obj == bt5) { // 일정 추가 다이어로그의 삭제 버튼

		}

	}

//	public static void main(String[] args) {
//		dbconn dbc = new dbconn();
//		// dbc.exitconn();
//	}
}