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
		//��Ʈ�� �迭�� ����, ���� 3��¥�� �迭
		String[] columnNames = { "�ۼ���", "����", "����", "�ۼ���" };
		//Jtable ������ ��������.
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		jp.add(scrollPane, BorderLayout.CENTER);
		
		addButton = new JButton("�� ��������");
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
			JOptionPane.showMessageDialog(null, "����� ������ �Է��ϼ���.", 
			"����", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		//addButton �����,
		if (obj == addButton) {
			// JDialog�� �� â �����.
			JDialog newntc = new JDialog();
			newntc.setTitle("�� �������� �߰�");
			newntc.setSize(300, 300);
			newntc.setLayout(new BorderLayout());
			newntc.setLocationRelativeTo(null);
			
			//JDialog�� ����� �� â��, ���������� ��ġ�� �����ϱ� ���� �г�
			JPanel inputPanel = new JPanel(new BorderLayout());
			newntc.add(inputPanel, BorderLayout.CENTER);
			
			//���� �Է� �ʵ�.
			JPanel titlePanel = new JPanel(new BorderLayout());
			titlePanel.add(new JLabel("����: "), BorderLayout.WEST);
			titleField = new JTextField();
			titlePanel.add(titleField, BorderLayout.CENTER);
			inputPanel.add(titlePanel, BorderLayout.NORTH);
			//���� �Է� �ʵ�
			JPanel contentPanel = new JPanel(new BorderLayout());
			contentPanel.add(new JLabel("����: "), BorderLayout.NORTH);
			contentArea = new JTextArea(5, 20);
			JScrollPane contentScrollPane = new JScrollPane(contentArea);
			contentPanel.add(contentScrollPane, BorderLayout.CENTER);
			inputPanel.add(contentPanel, BorderLayout.CENTER);
			//�߰���ư
			JButton addNoticeButton = new JButton("�߰�");
			addNoticeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//�ۼ��� ���������� �߰���ư�� �̿��Ͽ� �̺�Ʈ�� �߻����� ��,
					//addNotice �޼ҵ忡�� �߰��� �� ���� ���� Ȯ��.
					addNotice();
					newntc.dispose();
				}
			});
			inputPanel.add(addNoticeButton, BorderLayout.SOUTH);
			newntc.setVisible(true);
		}
	}
//	Ȯ�ο� ����
//	public static void main(String[] args) {
//		Notice nt = new Notice();
//		nt.setSize(1200,700);
//		nt.setLocationRelativeTo(null);
//		nt.setVisible(true);
//
//	}

}
