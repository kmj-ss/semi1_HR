package smanageT;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class test2 extends JDialog {
	public test2() {
		setTitle("새 사원정보 입력");
		setSize(700,500);
//		newins.setLayout(new GridLayout(5,5));
		setLayout(new BorderLayout());
		
		// 입력 전체패널
		JPanel ins_panel = new JPanel(new BorderLayout());
		add(ins_panel, BorderLayout.CENTER);
		
		// 입력상단 패널 및 버튼 추가
		JPanel ins_top = new JPanel();
		ins_top.setLayout(new FlowLayout(0,10,5));
		JButton ins_job1 = new JButton("정규직");
		JButton ins_job2 = new JButton("비정규직");
		ins_job1.setBackground(new Color(46, 139, 87));
		ins_job1.setForeground(new Color(255, 255, 255));
		ins_job2.setBackground(new Color(46, 139, 87));
		ins_job2.setForeground(new Color(255, 255, 255));
		ins_top.add(ins_job1);
		ins_top.add(ins_job2);
		
		ins_top.setBackground(new Color(143, 188, 143));
		ins_panel.add(ins_top, BorderLayout.NORTH);
		
		// 입력센터 패널 및 정보 입력칸 추가
		JPanel ins_center = new JPanel(new GridLayout(5, 4, 20, 30));
		
		// 센터 라벨
		JLabel name, tel, level, addr, dept, hiredate, email, msgid, brd, male;
		name = new JLabel("사원명: ");
		tel = new JLabel("연락처: ");
		level = new JLabel("직급: ");
		addr = new JLabel("주소: ");
		dept = new JLabel("부서: ");
		hiredate = new JLabel("입사일: ");
		email = new JLabel("이메일: ");
		msgid = new JLabel("메신저ID: ");
		brd = new JLabel("생년월일: ");
		male = new JLabel("성별: ");
		
		// 센터 텍스트 필드
		JTextField i_name, i_tel, i_level, i_addr, i_dept, i_hiredate, i_email, i_msgid, i_brd, i_male;
		i_name = new JTextField(10);
		i_tel = new JTextField(20);
		i_level = new JTextField(20);
		i_addr = new JTextField(20);
		i_dept = new JTextField(20);
		i_hiredate = new JTextField(20);
		i_email = new JTextField(20);
		i_msgid = new JTextField(20);
		i_brd = new JTextField(20);
		i_male = new JTextField(20);
		
		// 센터 라벨 및 텍스트 필드 추가
		ins_center.add(name);
		ins_center.add(i_name);
		ins_center.add(tel);
		ins_center.add(i_tel);
		ins_center.add(level);
		ins_center.add(i_level);
		ins_center.add(addr);
		ins_center.add(i_addr);
		ins_center.add(dept);
		ins_center.add(i_dept);
		ins_center.add(hiredate);
		ins_center.add(i_hiredate);
		ins_center.add(email);
		ins_center.add(i_email);
		ins_center.add(msgid);
		ins_center.add(i_msgid);
		ins_center.add(brd);
		ins_center.add(i_brd);
		ins_center.add(male);
		ins_center.add(i_male);
		
		TitledBorder border = BorderFactory.createTitledBorder("상세정보");  // 패널 구분선 생성
		border.setTitleColor(new Color(47, 79, 79));	// 구분선 제목 색상 설정
		border.setBorder(BorderFactory.createLineBorder(Color.GRAY));    // 구분선 색상 설정
		ins_center.setBorder(border);
		
		ins_center.setBackground(new Color(255, 250, 250));
		ins_panel.add(ins_center, BorderLayout.CENTER);
		
		
		// 입력하단 패널 및 버튼 추가
		JPanel ins_bottom = new JPanel();
		ins_bottom.setLayout(new FlowLayout(2,20,5));
		JLabel img = new JLabel("[이미지 첨부파일] ");
		img.setForeground(new Color(47, 79, 79));
		JLabel pathLabel = new JLabel("선택한 파일 경로 여기에 표시됩니다.");
		pathLabel.setForeground(new Color(0, 0, 139));
		JButton chooseButton = new JButton("파일 선택");
		chooseButton.setBackground(new Color(60, 179, 113));
		chooseButton.setForeground(new Color(255, 255, 255));
		JButton i_save = new JButton("저장");
		i_save.setBackground(new Color(60, 179, 113));
		i_save.setForeground(new Color(255, 255, 255));
		JButton i_cancel = new JButton("취소");
		i_cancel.setBackground(new Color(60, 179, 113));
		i_cancel.setForeground(new Color(255, 255, 255));
		
		// 파일 선택 버튼을 눌렀을때 작동
		chooseButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	// 파일 선택기
	        	JFileChooser i_imgfile = new JFileChooser();
	        	// 사용자가 파일 선택하도록함
	        	int returnValue = i_imgfile.showOpenDialog(null);
	        	// 사용자가 열기 버튼을 눌렀을때
	        	if (returnValue == JFileChooser.APPROVE_OPTION) {
		            File selectedFile = i_imgfile.getSelectedFile();
		            // 선택한 파일 경로 변수
		            String filePath = selectedFile.getAbsolutePath();
		            // 경로 '\' 마지막 인덱스 추출
		            int fp_lastIndex = filePath.lastIndexOf('\\');
		            // 선택파일 축소경로
		            String filename = filePath.substring(fp_lastIndex+1);
		            // 축소경로로 라벨 텍스트 재설정
		            pathLabel.setText(filename);
		        }
	        }
		});
		
		// 저장 버튼을 눌렀을때 작동
		i_save.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	// 데이터 삽입
	        	setVisible(false);
		    }
		});
		
		// 취소 버튼을 눌렀을때 작동
		i_cancel.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	
	        	setVisible(false);
		    }
		});
	    
		// 입력하단 버튼 및 라벨 추가
		ins_bottom.add(img);
		ins_bottom.add(pathLabel);
		ins_bottom.add(chooseButton);
		ins_bottom.add(i_save);
		ins_bottom.add(i_cancel);
		
		ins_bottom.setBackground(new Color(240, 255, 240));
		ins_panel.add(ins_bottom, BorderLayout.SOUTH);
		setVisible(true);
		setLocationRelativeTo(null);
		repaint();
		validate();
	}
//	public static void main(String[] args) {
//		test2 t2 = new test2();
//		t2.setTitle("새 사원정보 입력");
//		
//		t2.setVisible(true);
//		t2.setLocationRelativeTo(null);
//		t2.repaint();
//		t2.validate();
//	
//	}

}
