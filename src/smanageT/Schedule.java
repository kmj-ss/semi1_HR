package smanageT;
import java.util.*;
import java.util.Date;
import java.awt.*;

import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.sql.*;

public class Schedule extends JPanel implements ActionListener,MouseListener {

	// 상단
	JPanel selectPane;
	//LocalDate today = LocalDate.now();
	
	
	JPanel datePane_Event;
	JPanel dayPane_User;
	JPanel datePan;
	
	//Scanner sc=new Scanner(System.in);
	
	JLabel lbl;
	JLabel yearLBl; // "년"을 표시할 라벨 추가
	JLabel monthLBl; // "월"을 표시할 라벨추가\
	JLabel dum; //시험용 라벨
	JButton nextBT1;JButton nextBT2;
	// 중단
	JPanel centerPane;

	JPanel titlePane;
	String[] title;
	
	JPanel dayPane;// 위와 동일하며 날짜가 나오게 된다.
	Connection conn;
	String sql;
	String sql_event;
	String sql_delete;
	String lab_db[];
	PreparedStatement ps;
	PreparedStatement ps_event;

	Calendar date; // 달력주입
	int year; // 년과, 월 주입
	int month;
	int lastDay;// = date.getActualMaximum(Calendar.DATE);//라스트데이 전역변수 (삭제예정)
	
	Vector<String> vector;
	Vector<Vector> vector2;
	Vector<String> vector_select;
	Vector<String> vector_event;
	Vector<Object> created;
	int eventDay;
	JLabel Lab;//날짜 이벤트용 패널
	
	
	public Schedule(Connection conn) {
		
		this.conn = conn;
		
		this.setLayout(new BorderLayout());
		selectPane = new JPanel(new BorderLayout()); // 패널생성
		selectPane.setBackground(Color.white);
		yearLBl = new JLabel("년");
		
		nextBT1 = new JButton("<");
		nextBT1.setUI(new RoundedButton());
		nextBT1.setFocusPainted(false);
		nextBT1.setPreferredSize(new Dimension(40,40));
		nextBT1.setBorder(new LineBorder(Color.black,1));
		nextBT2 = new JButton(">");
		nextBT2.setUI(new RoundedButton());
	
		nextBT2.setFocusable(false);
		nextBT2.setPreferredSize(new Dimension(40,40));
		nextBT2.setBorder(new LineBorder(Color.black,1));
		monthLBl = new JLabel("월");
		title = new String[] { "일", "월", "화", "수", "목", "금", "토" };

		centerPane = new JPanel(new BorderLayout());
		centerPane.setBackground(Color.white);
		titlePane = new JPanel(new GridLayout(1, 7));
		titlePane.setBackground(Color.white);
		//titlePane.setBorder(new MatteBorder(0,0,1,0,Color.black));
		dayPane = new JPanel(new GridLayout(0, 7,10,10));//일 칸 나누기
		dayPane.setBackground(Color.white);
		date = Calendar.getInstance();

		year = date.get(Calendar.YEAR);

	 	month = date.get(Calendar.MONTH) + 1;

		yearLBl.setText(year + "년 " + month + "월");
	
		// 상단
		selectPane.add(nextBT1, "West");
		Panel pa = new Panel(new FlowLayout(FlowLayout.CENTER));
		pa.add(yearLBl);
		// selectPane.add(yearLBl,"Center");
		selectPane.add(pa);
		selectPane.add(nextBT2, "East");

		nextBT1.addActionListener(this);
		nextBT2.addActionListener(this);
		// selectPane.add(monthLBl);

		this.add(BorderLayout.NORTH, selectPane);

		setCalendarTitle(); // 일월화수목금토를 만들어놓은 메소드 setCalendarTitle을 호출한다.
		centerPane.add(BorderLayout.NORTH, titlePane); // 센터패널의 위쪽에 title을 넣는다(일월화수목금토)
		add(centerPane);

		centerPane.add(dayPane); // 센터패널에 날짜패널을 추가한다.
		setDay();
	}
	public void setDay() {

		try {
//달력 타이틀 
			sql = "select s_date,s_index,s_title,s_type,s_info,s_type_color from schedule1";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
	
			vector2 = new Vector<Vector>();
			//created = new Vector<Object>();
			
			while(rs.next()) {

			String str[]= rs.getString("s_date").split("~");
			
			vector = new Vector<String>();
			
			str[0]=str[0].replaceAll("-","");
			str[1]=str[1].replaceAll("-","");
			
			vector.add(str[0]);
			vector.add(str[1]);
			vector.add(""+rs.getString("s_index"));
			vector.add(""+rs.getString("s_title"));
			vector.add(""+rs.getString("s_type"));
			vector.add(""+rs.getString("s_info"));
			vector.add(""+rs.getString("s_type_color"));
			
			//vector.set(0, sql);
			vector2.add(vector);
		
			
			}
		} catch (SQLException e) {

		}
		
		// 요일
		date.set(Calendar.DATE, 1); // date를 세팅하는데, 일(day)를 1로 세팅한다.
		int week = date.get(Calendar.DAY_OF_WEEK); // DAY_OF_WEEK는 일월화수목금토이며 이데이터를 받아와서 week에 넣는다.
		// 마지막날
		int lastDay = date.getActualMaximum(Calendar.DATE); // getActualMaximum 는 날짜가 셋팅 된 Calender 가 가질수 있는 값
		// getMaximum 는 Calender 자체가 최대로 가질수 있는 값
		// 마지막날을 불러온다.
		
//달력 시작 공백//
		for (int i = 1; i < week; i++) { // 반복문을 돌린다.
			JLabel lbl = new JLabel(" "); // 들여쓰기
			dayPane.add(lbl);
		}
//달력 끝 공백//
		date.set(Calendar.MONTH, month - 1);
		date.set(Calendar.YEAR, year);
		
//달룍 날짜 입력 시작
		for (int i = 1; i <= lastDay; i++) {
			
			datePan = new JPanel(new BorderLayout());
			//datePan.setPreferredSize(new Dimension(80,80));
		
			datePan.setBackground(Color.white);
			datePan.setBorder(new MatteBorder(1,0,0,0,Color.black));
			
			LocalDate now = LocalDate.now();
			int now_day = now.getDayOfMonth();
			
			if(i==now_day&&year==now.getYear()&&month==now.getMonthValue()) {
				lbl = new JLabel(String.valueOf(i), JLabel.CENTER);
				lbl.setForeground(Color.MAGENTA);
				//lbl.setPreferredSize(new Dimension());
		
		
				
			}else
			lbl = new JLabel(String.valueOf(i), JLabel.CENTER); // 라벨선언해주는데 String.value 는 형변환이다. JLabel을 가운데에
			
			// 입력하게둔다.
			// 출력하는 날짜에 대한 요일
			date.set(Calendar.DATE, i); // 19 ->1
				
			int w = date.get(Calendar.DAY_OF_WEEK); // 요일
			if (w == 1)
				lbl.setForeground(Color.red); // 일월화수목금토 (1~7) 1은 일요일이므로 일요일에 red색깔
			if (w == 7)
				lbl.setForeground(Color.blue); // 7이므로 blue색깔
			
			datePan.add(lbl,"North");

////////////달력 이벤트 추가 if문 시작
				lab_db = new String[2];	

					String monthTest="";
					if(date.get(Calendar.MONTH)+1<10) {
						monthTest="0"+(date.get(Calendar.MONTH)+1);
					} else {
						monthTest=""+(date.get(Calendar.MONTH)+1);

					}
					
					String dateTest="";
					if(i<10) {
						dateTest="0"+i;
					} else {
						dateTest=""+i;
					}eventDay = Integer.parseInt(""+year+monthTest+dateTest);
///////////
						
//이벤트 패널 생성부분 시작			
					for(int j=0; j<vector2.size();j++){
					if(Integer.parseInt((String) vector2.get(j).get(0))<=eventDay&&eventDay<=Integer.parseInt((String) vector2.get(j).get(1))) {
						
						//ln("날짜 들어감");
						datePane_Event = new JPanel(new BorderLayout());
						datePane_Event.setPreferredSize(new Dimension(40,40));
								
						//dayPane_User = new JPanel();
//					
						//Lab= new JLabel(""+vector2.get(j).get(2)+". "+vector2.get(j).get(3));
						
						String event_lab= new String(""+vector2.get(j).get(2)+". "+vector2.get(j).get(3));
						Lab= new JLabel(event_lab,JLabel.CENTER);
						

						//Lab.setFont(new Font(getName(),Font.BOLD,10));
						
						
						
						if(vector2.get(j).get(6).equals("회사")) {
							//ln(vector2.get(j).get(6));
							Lab.setForeground(Color.red);
						}else if(vector2.get(j).get(6).equals("부서")) {
							////ln("실행됐나?");
							Lab.setForeground(Color.blue);
						}
						
						Lab.addMouseListener(this);
						
						datePane_Event.add(Lab);
						datePan.add(datePane_Event);
						
						
					}
					}
//이벤트 패널 생성부분 끝		
			
			dayPane.add(datePan);
		}
		
		
//달력 날짜 입력 끝
		date.set(Calendar.MONTH, month);
		date.set(Calendar.DATE, 1);
		week = date.get(Calendar.DAY_OF_WEEK);
		
	  	for (int i = 1; i < week; i++) { // 반복문을 돌린다.
			JLabel lbl = new JLabel(" "); // 들여쓰기
			dayPane.add(lbl);
		}
		date.set(Calendar.MONTH, month - 1);

	}
	

	
	//패널 클릭이벤트 오버라이드 시작
	@Override
	public void mouseClicked(MouseEvent e) {
		
		Object obj= e.getSource();
//		String monthTest="";
//		for (int i = 1; i <= lastDay; i++) {
//		if(date.get(Calendar.MONTH)+1<10) {
//			monthTest="0"+(date.get(Calendar.MONTH)+1);
//		} else {
//			monthTest=""+(date.get(Calendar.MONTH)+1);
//
//		}
//		
//		String dateTest="";
//		if(i<10) {
//			dateTest="0"+i;
//		} else {
//			dateTest=""+i;
//		}eventDay = Integer.parseInt(""+year+monthTest+dateTest);
//		for(int j=0; j<vector2.size();j++){
//			if(Integer.parseInt((String) vector2.get(j).get(0))<=eventDay&&eventDay<=Integer.parseInt((String) vector2.get(j).get(1))) {
//				String event_lab= new String(""+vector2.get(j).get(2)+". "+vector2.get(j).get(3));
//				Lab= new JLabel(event_lab,JLabel.CENTER);
//			}
//			}
//		}
//		for(int j=0; j<vector2.size();j++){
//			if(Integer.parseInt((String) vector2.get(j).get(0))<=eventDay&&eventDay<=Integer.parseInt((String) vector2.get(j).get(1))) {
//				Lab= new JLabel(""+vector2.get(j).get(2)+". "+vector2.get(j).get(3));
//			}
//			}
	
		//ln("마우스 이벤트값"+obj);
		
		JLabel temp = (JLabel)e.getComponent();
		//ln(temp);
		//temp.setText(Lab.getText());
		
		for(int k=0; k<vector2.size();k++) {
			//ln(k);
			if(temp.getText().startsWith((String)vector2.get(k).get(2)+".")) {
				//ln("인덱스 시작값############### ");
				//ln("스타트위즈 비교값"+(String)vector2.get(k).get(2)+".");
				//ln("찾았다.");
				String temp_vector = (String)vector2.get(k).get(0)+"~"+(String)vector2.get(k).get(1);
				
				ViewSchedule v = new ViewSchedule(conn);
//				v.createNewScheduleDialog();
				v.newntc.setVisible(true);
				v.newntc.setTitle("정보 수정");
				v.inputTF.setText((String) vector2.get(k).get(3));//제목
				v.inputTF1.setText(temp_vector);//날짜
				v.inputTF2.setText((String) vector2.get(k).get(4));//구분
				v.inputTA.setText((String) vector2.get(k).get(5));//내용.
				//v.combo_type.setSelectedItem(vector2.get(k).get(6));//콤보박스
				v.bt4.setText("수정");
				v.bt5.setText("삭제");
				
			
				String index = (String)vector2.get(k).get(2);
				
			
				v.bt4.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {	
					}
					@Override
					public void mousePressed(MouseEvent e) {
					}
					@Override
					public void mouseExited(MouseEvent e) {
					}
					@Override
					public void mouseEntered(MouseEvent e) {
					}
					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							String sql2;
							sql2 = "update schedule1 set s_title = ?,s_date =?,s_type=?,s_info=?,s_type_color=? where s_index = ?";
							
							ps_event = conn.prepareStatement(sql2);
							
							String title = v.inputTF.getText();
							String date = v.inputTF1.getText();
							String type = v.inputTF2.getText();
							String info = v.inputTA.getText();
							String type_color = v.combo_type.getSelectedItem().toString();
							
							ps_event.setString(1,title);
							ps_event.setString(2,date);
							ps_event.setString(3,type);
							ps_event.setString(4,info);
							ps_event.setString(5,type_color);
							
							ps_event.setString(6,index);
							
								
							int a =ps_event.executeUpdate();
							//ln("실형결과::"+a);
							//ln("INDEX"+index);
							v.newntc.setVisible(false);
							//ln("다이얼로그 수정 완료");
							
						
							dayPane.removeAll();
							datePane_Event.removeAll();
							setDay();
							validate();
							v.newntc.setVisible(false);
						} catch (SQLException e2) {
							//ln("수정 오류");
						}
	
					}
				});
				v.bt5.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
					}
					@Override
					public void mousePressed(MouseEvent e) {
					}
					@Override
					public void mouseExited(MouseEvent e) {
					}
					@Override
					public void mouseEntered(MouseEvent e) {
					}
					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							sql_delete = "delete from schedule1 where s_index = ?";
							
							ps_event = conn.prepareStatement(sql_delete);
							ps_event.setString(1,index);
							
							
							int a =ps_event.executeUpdate();
							//ln("실형결과::"+a);
							//ln("삭제완료"+index);
							v.newntc.setVisible(false);
							
							//datePane_Event.setSize(getSize());
							dayPane.setSize(getSize());
							dayPane.removeAll();
							datePane_Event.removeAll();
							setDay();
							validate();
							
						} catch (SQLException e2) {
							//ln("수정 오류");
						}
					}
				});

			}
		}
		
		}
	
//패널 클릭 이벤트 오버라이드 끝
	
	@Override
	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();
		
		if (obj == nextBT1) {		
			month--;	
			//ln(year);
			if(month==0) {
				month=12;
				year--;
				
				date.set(Calendar.YEAR, year);
				date.set(Calendar.MONTH, month-1);
			}
			yearLBl.setText(year + "년 " + (month) + "월");
			
			date.set(Calendar.MONTH, month - 1);

			dayPane.setSize(getSize());
			dayPane.removeAll();
			setDay();

			
		} else if (obj == nextBT2) {
			month++;
		
			if(month==13) {
				month=1;
				year++;
				
				date.set(Calendar.YEAR, year);
				date.set(Calendar.MONTH, month-1);
			}
			yearLBl.setText(year + "년 " + (month) + "월");
			date.set(Calendar.MONTH, month - 1);
			
			dayPane.setSize(getSize());
			dayPane.removeAll();
			setDay();
		}	
	}
	public void setCalendarTitle() { // 메소드
		for (int i = 0; i < title.length; i++) { // 만들어준 배열의 수만큼 돌린다.
			JLabel lbl = new JLabel(title[i], JLabel.CENTER); // 만들어준 배열의 수만큼 label에 주입시키고 가운데로 오게한다.
			if (i == 0)
				lbl.setForeground(Color.red); // setForeground폰트속성을 변경해주는데 쓰는것,
			if (i == 6)
				lbl.setForeground(Color.blue);
			titlePane.add(lbl); // 타이틀패널에 라벨을 추가시킨다.
			
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}


}