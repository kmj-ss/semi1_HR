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
		setTitle("�� ������� �Է�");
		setSize(700,500);
//		newins.setLayout(new GridLayout(5,5));
		setLayout(new BorderLayout());
		
		// �Է� ��ü�г�
		JPanel ins_panel = new JPanel(new BorderLayout());
		add(ins_panel, BorderLayout.CENTER);
		
		// �Է»�� �г� �� ��ư �߰�
		JPanel ins_top = new JPanel();
		ins_top.setLayout(new FlowLayout(0,10,5));
		JButton ins_job1 = new JButton("������");
		JButton ins_job2 = new JButton("��������");
		ins_job1.setBackground(new Color(46, 139, 87));
		ins_job1.setForeground(new Color(255, 255, 255));
		ins_job2.setBackground(new Color(46, 139, 87));
		ins_job2.setForeground(new Color(255, 255, 255));
		ins_top.add(ins_job1);
		ins_top.add(ins_job2);
		
		ins_top.setBackground(new Color(143, 188, 143));
		ins_panel.add(ins_top, BorderLayout.NORTH);
		
		// �Է¼��� �г� �� ���� �Է�ĭ �߰�
		JPanel ins_center = new JPanel(new GridLayout(5, 4, 20, 30));
		
		// ���� ��
		JLabel name, tel, level, addr, dept, hiredate, email, msgid, brd, male;
		name = new JLabel("�����: ");
		tel = new JLabel("����ó: ");
		level = new JLabel("����: ");
		addr = new JLabel("�ּ�: ");
		dept = new JLabel("�μ�: ");
		hiredate = new JLabel("�Ի���: ");
		email = new JLabel("�̸���: ");
		msgid = new JLabel("�޽���ID: ");
		brd = new JLabel("�������: ");
		male = new JLabel("����: ");
		
		// ���� �ؽ�Ʈ �ʵ�
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
		
		// ���� �� �� �ؽ�Ʈ �ʵ� �߰�
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
		
		TitledBorder border = BorderFactory.createTitledBorder("������");  // �г� ���м� ����
		border.setTitleColor(new Color(47, 79, 79));	// ���м� ���� ���� ����
		border.setBorder(BorderFactory.createLineBorder(Color.GRAY));    // ���м� ���� ����
		ins_center.setBorder(border);
		
		ins_center.setBackground(new Color(255, 250, 250));
		ins_panel.add(ins_center, BorderLayout.CENTER);
		
		
		// �Է��ϴ� �г� �� ��ư �߰�
		JPanel ins_bottom = new JPanel();
		ins_bottom.setLayout(new FlowLayout(2,20,5));
		JLabel img = new JLabel("[�̹��� ÷������] ");
		img.setForeground(new Color(47, 79, 79));
		JLabel pathLabel = new JLabel("������ ���� ��� ���⿡ ǥ�õ˴ϴ�.");
		pathLabel.setForeground(new Color(0, 0, 139));
		JButton chooseButton = new JButton("���� ����");
		chooseButton.setBackground(new Color(60, 179, 113));
		chooseButton.setForeground(new Color(255, 255, 255));
		JButton i_save = new JButton("����");
		i_save.setBackground(new Color(60, 179, 113));
		i_save.setForeground(new Color(255, 255, 255));
		JButton i_cancel = new JButton("���");
		i_cancel.setBackground(new Color(60, 179, 113));
		i_cancel.setForeground(new Color(255, 255, 255));
		
		// ���� ���� ��ư�� �������� �۵�
		chooseButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	// ���� ���ñ�
	        	JFileChooser i_imgfile = new JFileChooser();
	        	// ����ڰ� ���� �����ϵ�����
	        	int returnValue = i_imgfile.showOpenDialog(null);
	        	// ����ڰ� ���� ��ư�� ��������
	        	if (returnValue == JFileChooser.APPROVE_OPTION) {
		            File selectedFile = i_imgfile.getSelectedFile();
		            // ������ ���� ��� ����
		            String filePath = selectedFile.getAbsolutePath();
		            // ��� '\' ������ �ε��� ����
		            int fp_lastIndex = filePath.lastIndexOf('\\');
		            // �������� ��Ұ��
		            String filename = filePath.substring(fp_lastIndex+1);
		            // ��Ұ�η� �� �ؽ�Ʈ �缳��
		            pathLabel.setText(filename);
		        }
	        }
		});
		
		// ���� ��ư�� �������� �۵�
		i_save.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	// ������ ����
	        	setVisible(false);
		    }
		});
		
		// ��� ��ư�� �������� �۵�
		i_cancel.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	
	        	setVisible(false);
		    }
		});
	    
		// �Է��ϴ� ��ư �� �� �߰�
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
//		t2.setTitle("�� ������� �Է�");
//		
//		t2.setVisible(true);
//		t2.setLocationRelativeTo(null);
//		t2.repaint();
//		t2.validate();
//	
//	}

}
