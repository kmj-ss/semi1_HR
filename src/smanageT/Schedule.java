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

	// ���
	JPanel selectPane;
	//LocalDate today = LocalDate.now();
	
	
	JPanel datePane_Event;
	JPanel dayPane_User;
	JPanel datePan;
	
	//Scanner sc=new Scanner(System.in);
	
	JLabel lbl;
	JLabel yearLBl; // "��"�� ǥ���� �� �߰�
	JLabel monthLBl; // "��"�� ǥ���� ���߰�\
	JLabel dum; //����� ��
	JButton nextBT1;JButton nextBT2;
	// �ߴ�
	JPanel centerPane;

	JPanel titlePane;
	String[] title;
	
	JPanel dayPane;// ���� �����ϸ� ��¥�� ������ �ȴ�.
	Connection conn;
	String sql;
	String sql_event;
	String sql_delete;
	String lab_db[];
	PreparedStatement ps;
	PreparedStatement ps_event;

	Calendar date; // �޷�����
	int year; // ���, �� ����
	int month;
	int lastDay;// = date.getActualMaximum(Calendar.DATE);//��Ʈ���� �������� (��������)
	
	Vector<String> vector;
	Vector<Vector> vector2;
	Vector<String> vector_select;
	Vector<String> vector_event;
	Vector<Object> created;
	int eventDay;
	JLabel Lab;//��¥ �̺�Ʈ�� �г�
	
	
	public Schedule(Connection conn) {
		
		this.conn = conn;
		
		this.setLayout(new BorderLayout());
		selectPane = new JPanel(new BorderLayout()); // �гλ���
		selectPane.setBackground(Color.white);
		yearLBl = new JLabel("��");
		
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
		monthLBl = new JLabel("��");
		title = new String[] { "��", "��", "ȭ", "��", "��", "��", "��" };

		centerPane = new JPanel(new BorderLayout());
		centerPane.setBackground(Color.white);
		titlePane = new JPanel(new GridLayout(1, 7));
		titlePane.setBackground(Color.white);
		//titlePane.setBorder(new MatteBorder(0,0,1,0,Color.black));
		dayPane = new JPanel(new GridLayout(0, 7,10,10));//�� ĭ ������
		dayPane.setBackground(Color.white);
		date = Calendar.getInstance();

		year = date.get(Calendar.YEAR);

	 	month = date.get(Calendar.MONTH) + 1;

		yearLBl.setText(year + "�� " + month + "��");
	
		// ���
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

		setCalendarTitle(); // �Ͽ�ȭ������並 �������� �޼ҵ� setCalendarTitle�� ȣ���Ѵ�.
		centerPane.add(BorderLayout.NORTH, titlePane); // �����г��� ���ʿ� title�� �ִ´�(�Ͽ�ȭ�������)
		add(centerPane);

		centerPane.add(dayPane); // �����гο� ��¥�г��� �߰��Ѵ�.
		setDay();
	}
	public void setDay() {

		try {
//�޷� Ÿ��Ʋ 
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
		
		// ����
		date.set(Calendar.DATE, 1); // date�� �����ϴµ�, ��(day)�� 1�� �����Ѵ�.
		int week = date.get(Calendar.DAY_OF_WEEK); // DAY_OF_WEEK�� �Ͽ�ȭ��������̸� �̵����͸� �޾ƿͼ� week�� �ִ´�.
		// ��������
		int lastDay = date.getActualMaximum(Calendar.DATE); // getActualMaximum �� ��¥�� ���� �� Calender �� ������ �ִ� ��
		// getMaximum �� Calender ��ü�� �ִ�� ������ �ִ� ��
		// ���������� �ҷ��´�.
		
//�޷� ���� ����//
		for (int i = 1; i < week; i++) { // �ݺ����� ������.
			JLabel lbl = new JLabel(" "); // �鿩����
			dayPane.add(lbl);
		}
//�޷� �� ����//
		date.set(Calendar.MONTH, month - 1);
		date.set(Calendar.YEAR, year);
		
//�ޏ� ��¥ �Է� ����
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
			lbl = new JLabel(String.valueOf(i), JLabel.CENTER); // �󺧼������ִµ� String.value �� ����ȯ�̴�. JLabel�� �����
			
			// �Է��ϰԵд�.
			// ����ϴ� ��¥�� ���� ����
			date.set(Calendar.DATE, i); // 19 ->1
				
			int w = date.get(Calendar.DAY_OF_WEEK); // ����
			if (w == 1)
				lbl.setForeground(Color.red); // �Ͽ�ȭ������� (1~7) 1�� �Ͽ����̹Ƿ� �Ͽ��Ͽ� red����
			if (w == 7)
				lbl.setForeground(Color.blue); // 7�̹Ƿ� blue����
			
			datePan.add(lbl,"North");

////////////�޷� �̺�Ʈ �߰� if�� ����
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
						
//�̺�Ʈ �г� �����κ� ����			
					for(int j=0; j<vector2.size();j++){
					if(Integer.parseInt((String) vector2.get(j).get(0))<=eventDay&&eventDay<=Integer.parseInt((String) vector2.get(j).get(1))) {
						
						//ln("��¥ ��");
						datePane_Event = new JPanel(new BorderLayout());
						datePane_Event.setPreferredSize(new Dimension(40,40));
								
						//dayPane_User = new JPanel();
//					
						//Lab= new JLabel(""+vector2.get(j).get(2)+". "+vector2.get(j).get(3));
						
						String event_lab= new String(""+vector2.get(j).get(2)+". "+vector2.get(j).get(3));
						Lab= new JLabel(event_lab,JLabel.CENTER);
						

						//Lab.setFont(new Font(getName(),Font.BOLD,10));
						
						
						
						if(vector2.get(j).get(6).equals("ȸ��")) {
							//ln(vector2.get(j).get(6));
							Lab.setForeground(Color.red);
						}else if(vector2.get(j).get(6).equals("�μ�")) {
							////ln("����Ƴ�?");
							Lab.setForeground(Color.blue);
						}
						
						Lab.addMouseListener(this);
						
						datePane_Event.add(Lab);
						datePan.add(datePane_Event);
						
						
					}
					}
//�̺�Ʈ �г� �����κ� ��		
			
			dayPane.add(datePan);
		}
		
		
//�޷� ��¥ �Է� ��
		date.set(Calendar.MONTH, month);
		date.set(Calendar.DATE, 1);
		week = date.get(Calendar.DAY_OF_WEEK);
		
	  	for (int i = 1; i < week; i++) { // �ݺ����� ������.
			JLabel lbl = new JLabel(" "); // �鿩����
			dayPane.add(lbl);
		}
		date.set(Calendar.MONTH, month - 1);

	}
	

	
	//�г� Ŭ���̺�Ʈ �������̵� ����
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
	
		//ln("���콺 �̺�Ʈ��"+obj);
		
		JLabel temp = (JLabel)e.getComponent();
		//ln(temp);
		//temp.setText(Lab.getText());
		
		for(int k=0; k<vector2.size();k++) {
			//ln(k);
			if(temp.getText().startsWith((String)vector2.get(k).get(2)+".")) {
				//ln("�ε��� ���۰�############### ");
				//ln("��ŸƮ���� �񱳰�"+(String)vector2.get(k).get(2)+".");
				//ln("ã�Ҵ�.");
				String temp_vector = (String)vector2.get(k).get(0)+"~"+(String)vector2.get(k).get(1);
				
				ViewSchedule v = new ViewSchedule(conn);
//				v.createNewScheduleDialog();
				v.newntc.setVisible(true);
				v.newntc.setTitle("���� ����");
				v.inputTF.setText((String) vector2.get(k).get(3));//����
				v.inputTF1.setText(temp_vector);//��¥
				v.inputTF2.setText((String) vector2.get(k).get(4));//����
				v.inputTA.setText((String) vector2.get(k).get(5));//����.
				//v.combo_type.setSelectedItem(vector2.get(k).get(6));//�޺��ڽ�
				v.bt4.setText("����");
				v.bt5.setText("����");
				
			
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
							//ln("�������::"+a);
							//ln("INDEX"+index);
							v.newntc.setVisible(false);
							//ln("���̾�α� ���� �Ϸ�");
							
						
							dayPane.removeAll();
							datePane_Event.removeAll();
							setDay();
							validate();
							v.newntc.setVisible(false);
						} catch (SQLException e2) {
							//ln("���� ����");
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
							//ln("�������::"+a);
							//ln("�����Ϸ�"+index);
							v.newntc.setVisible(false);
							
							//datePane_Event.setSize(getSize());
							dayPane.setSize(getSize());
							dayPane.removeAll();
							datePane_Event.removeAll();
							setDay();
							validate();
							
						} catch (SQLException e2) {
							//ln("���� ����");
						}
					}
				});

			}
		}
		
		}
	
//�г� Ŭ�� �̺�Ʈ �������̵� ��
	
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
			yearLBl.setText(year + "�� " + (month) + "��");
			
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
			yearLBl.setText(year + "�� " + (month) + "��");
			date.set(Calendar.MONTH, month - 1);
			
			dayPane.setSize(getSize());
			dayPane.removeAll();
			setDay();
		}	
	}
	public void setCalendarTitle() { // �޼ҵ�
		for (int i = 0; i < title.length; i++) { // ������� �迭�� ����ŭ ������.
			JLabel lbl = new JLabel(title[i], JLabel.CENTER); // ������� �迭�� ����ŭ label�� ���Խ�Ű�� ����� �����Ѵ�.
			if (i == 0)
				lbl.setForeground(Color.red); // setForeground��Ʈ�Ӽ��� �������ִµ� ���°�,
			if (i == 6)
				lbl.setForeground(Color.blue);
			titlePane.add(lbl); // Ÿ��Ʋ�гο� ���� �߰���Ų��.
			
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