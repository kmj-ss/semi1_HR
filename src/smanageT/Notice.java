package smanageT;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.*;


public class Notice extends JPanel implements ActionListener {
	
	JTable table;
	DefaultTableModel tableModel;
	JTextField titleField;
	JTextArea contentArea;
	JButton addButton;
	
	public Notice() {
		this.setBackground(new Color(239,246,255));
		JPanel jp = new JPanel(new BorderLayout());
		//스트링 배열을 선언, 원소 3개짜리 배열
		String[] columnNames = { "작성자", "제목", "내용", "작성일" };
		//Jtable 형식을 지정해줌.
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		jp.add(scrollPane, BorderLayout.CENTER);
		
		addButton = new JButton("새 공지사항");
		addButton.addActionListener(this);
		jp.add(addButton,BorderLayout.SOUTH);
		this.add(jp);
		
	}
	
	void addNotice() {

		String title = titleField.getText();
		String content = contentArea.getText();
		String date = java.time.LocalDate.now().toString();

		if (!title.isEmpty() && !content.isEmpty()) {
			tableModel.addRow(new Object[] { title, content, date });
			titleField.setText("");
			contentArea.setText("");
		} else {
			JOptionPane.showMessageDialog(null, "제목과 내용을 입력하세요.", 
			"오류", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		//addButton 실행시,
		if (obj == addButton) {
			// JDialog로 새 창 띄워줌.
			JDialog newntc = new JDialog();
			newntc.setTitle("새 공지사항 추가");
			newntc.setSize(300, 300);
			newntc.setLayout(new BorderLayout());
			newntc.setLocationRelativeTo(null);
			
			//JDialog로 띄워진 새 창에, 컨텐츠들의 위치를 지정하기 위한 패널
			JPanel inputPanel = new JPanel(new BorderLayout());
			newntc.add(inputPanel, BorderLayout.CENTER);
			
			//제목 입력 필드.
			JPanel titlePanel = new JPanel(new BorderLayout());
			titlePanel.add(new JLabel("제목: "), BorderLayout.WEST);
			titleField = new JTextField();
			titlePanel.add(titleField, BorderLayout.CENTER);
			inputPanel.add(titlePanel, BorderLayout.NORTH);
			//내용 입력 필드
			JPanel contentPanel = new JPanel(new BorderLayout());
			contentPanel.add(new JLabel("내용: "), BorderLayout.NORTH);
			contentArea = new JTextArea(5, 20);
			JScrollPane contentScrollPane = new JScrollPane(contentArea);
			contentPanel.add(contentScrollPane, BorderLayout.CENTER);
			inputPanel.add(contentPanel, BorderLayout.CENTER);
			//추가버튼
			JButton addNoticeButton = new JButton("추가");
			addNoticeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//작성된 공지사항을 추가버튼을 이용하여 이벤트가 발생했을 시,
					//addNotice 메소드에서 추가할 지 말지 여부 확인.
					addNotice();
					newntc.dispose();
				}
			});
			inputPanel.add(addNoticeButton, BorderLayout.SOUTH);
			newntc.setVisible(true);
		}
	}
//	확인용 메인
//	public static void main(String[] args) {
//		Notice nt = new Notice();
//		nt.setSize(1200,700);
//		nt.setLocationRelativeTo(null);
//		nt.setVisible(true);
//
//	}

}
