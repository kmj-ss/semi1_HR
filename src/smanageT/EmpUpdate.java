package smanageT;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class EmpUpdate extends JDialog{
	
	private File selectedFile;
	
	public EmpUpdate(Connection con,int empno) {
		
		// �Է� ��ü�г�
		JPanel ins_panel = new JPanel(new BorderLayout());
		add(ins_panel, BorderLayout.CENTER);
		
		// �Է»�� �г� �� ��ư �߰�
		JPanel ins_top = new JPanel();
		ins_top.setLayout(new FlowLayout(0,10,5));
		JRadioButton ins_job1 = new JRadioButton("������");
		JRadioButton ins_job2 = new JRadioButton("��������");
		ins_job1.setBackground(new Color(46, 139, 87));
		ins_job1.setForeground(new Color(255, 255, 255));
		ins_job2.setBackground(new Color(46, 139, 87));
		ins_job2.setForeground(new Color(255, 255, 255));
		ButtonGroup group = new ButtonGroup();
		group.add(ins_job1);
		group.add(ins_job2);
		ins_top.add(ins_job1);
		ins_top.add(ins_job2);
		
		ins_top.setBackground(new Color(143, 188, 143));
		ins_panel.add(ins_top, BorderLayout.NORTH);
		
		// �Է¼��� �г� �� ���� �Է�ĭ �߰�
		JPanel ins_center = new JPanel(new GridLayout(5, 2, 20, 30));
		JPanel bgl = new JPanel(new GridLayout(1, 2, 10, 10));
		
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
		
		try {
			String sql = " select ename, addr, tel, bird, gender, email, jlevel, hiredate, wtype, dept, msgid, img "
					+ " from emptable e join jobst j on e.empno = j.empno"
					+ " where e.empno = ? ";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, empno);
			ResultSet rs = ps.executeQuery();
			
		// ���� �ؽ�Ʈ �ʵ�
		JTextField i_name, i_tel, i_level, i_addr, i_dept, i_hiredate, i_email, i_msgid, i_brd, i_male, i_wtype;
		JLabel pathLabel = new JLabel("������ ���� ��� ���⿡ ǥ�õ˴ϴ�.");
		
		i_name = new JTextField(20);
		i_tel = new JTextField(20);
		i_tel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String enteredText = i_tel.getText();
                // ��ȭ��ȣ ������ üũ�ϴ� ���� ǥ���� ����
                Pattern pattern = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");
                Matcher matcher = pattern.matcher(enteredText);

                if (matcher.matches()) {
                    JOptionPane.showMessageDialog(null, "�ùٸ� ��ȭ��ȣ �����Դϴ�.");
                } else {
                    JOptionPane.showMessageDialog(null, "��ȭ��ȣ ������ '010-1234-1234'�� ���� �Է����ּ���.", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                    // �ؽ�Ʈ �ʵ带 �ʱ�ȭ�ϰų� ���� ������ �ǵ�����
                    i_tel.setText("");
                }
            }
        });
		i_level = new JTextField(20);
		i_addr = new JTextField(20);
		i_dept = new JTextField(20);
		i_hiredate = new JTextField(20);
		i_email = new JTextField(20);
		i_msgid = new JTextField(20);
		i_brd = new JTextField(20);
		i_brd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String enteredText = i_brd.getText();
                // ��ȭ��ȣ ������ üũ�ϴ� ���� ǥ���� ����
                Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
                Matcher matcher = pattern.matcher(enteredText);

                if (matcher.matches()) {
                    JOptionPane.showMessageDialog(null, "�ùٸ� ������� �����Դϴ�.");
                } else {
                    JOptionPane.showMessageDialog(null, "������� ������ 'yyyy-mm-dd'�� ���� �Է����ּ���.", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                    // �ؽ�Ʈ �ʵ带 �ʱ�ȭ�ϰų� ���� ������ �ǵ�����
                    i_brd.setText("");
                }
            }
        });
		JRadioButton i_gender1 = new JRadioButton("��");
		JRadioButton i_gender2 = new JRadioButton("��");
		ButtonGroup group2 = new ButtonGroup();
		group2.add(i_gender1);
		group2.add(i_gender2);
		
		while(rs.next()) {
			i_name.setText(rs.getString("ename"));
			i_tel.setText(rs.getString("tel"));
			i_level.setText(rs.getString("jlevel"));
			i_addr.setText(rs.getString("addr"));
			i_dept.setText(rs.getString("dept"));
			i_hiredate.setText(rs.getString("hiredate").substring(0, 10));
			i_email.setText(rs.getString("email"));
			i_msgid.setText(rs.getString("msgid"));
			i_brd.setText(rs.getString("bird").substring(0, 10));
			if(rs.getString("wtype").equals("������")) {
				ins_job1.setSelected(true);
			}else if(rs.getString("wtype").equals("��������")) {
				ins_job2.setSelected(true);
			}
			if(rs.getString("gender").equals("��")) {
				i_gender1.setSelected(true);
			}else if(rs.getString("gender").equals("��")) {
				i_gender2.setSelected(true);
			}
			pathLabel.setText(rs.getString("img"));
		}
		
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
		bgl.add(i_gender1);
		bgl.add(i_gender2);
		ins_center.add(bgl);
		
		TitledBorder border = BorderFactory.createTitledBorder("������");  // �г� ���м� ����
		border.setTitleColor(new Color(47, 79, 79));	// ���м� ���� ���� ����
		border.setBorder(BorderFactory.createLineBorder(Color.GRAY));    // ���м� ���� ����
		ins_center.setBorder(border);
		ins_center.setBackground(new Color(255, 250, 250));
		bgl.setBackground(new Color(255, 250, 250));
		i_gender1.setBackground(new Color(255, 250, 250));
		i_gender2.setBackground(new Color(255, 250, 250));
		ins_panel.add(ins_center, BorderLayout.CENTER);
		
		
		// �Է��ϴ� �г� �� ��ư �߰�
		JPanel ins_bottom = new JPanel();
		ins_bottom.setLayout(new FlowLayout(2,20,5));
		JLabel img = new JLabel("[�̹��� ÷������] ");
		img.setForeground(new Color(47, 79, 79));
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
	        		selectedFile = i_imgfile.getSelectedFile();
		            pathLabel.setText(selectedFile.getName());
		        }
	        }
		});
		
		// ���� ��ư�� �������� �۵�
		i_save.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	JFrame frame = new JFrame("�˸�â");
	        	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        	int confirmed = JOptionPane.showConfirmDialog(frame,
	                    "������ �����Ͻðڽ��ϱ�?", "���� Ȯ��", JOptionPane.OK_CANCEL_OPTION);
				
	        	if (confirmed == JOptionPane.OK_OPTION) {
	        	// ������ ������Ʈ ����

				String sql2 = " update emptable "
						+ " SET ename = ?, "
						+ "    tel = ?, "
						+ "    jlevel = ?, "
						+ "    img = ?, "
						+ "    addr = ?, "
						+ "    dept = ?, "
						+ "    email = ?, "
						+ "    wtype = ?, "
						+ "    msgid = ?, "
						+ "    gender = ?, "
						+ "    bird = ? "
						+ " where empno = ? ";
				
				String sql3 = " update jobst set hiredate= ? "
						+ " where empno = ? ";
				
				PreparedStatement ps2;
				PreparedStatement ps3;
				
				try {
					ps2 = con.prepareStatement(sql2);
					ps3 = con.prepareStatement(sql3);
					
					ps2.setString(1, i_name.getText());
					ps2.setString(2, i_tel.getText());
					ps2.setString(3, i_level.getText());
					ps2.setString(4, pathLabel.getText());
					ps2.setString(5, i_addr.getText());
					ps2.setString(6, i_dept.getText());
					ps2.setString(7, i_email.getText());
					if (ins_job1.isSelected()) {
						ps2.setString(8, ins_job1.getText());
					}else if (ins_job2.isSelected()) {
						ps2.setString(8, ins_job2.getText());
					}
					ps2.setString(9, i_msgid.getText());
					if (i_gender1.isSelected()) {
						ps2.setString(10, i_gender1.getText());
					}else if (i_gender2.isSelected()) {
						ps2.setString(10, i_gender2.getText());
					}
					ps2.setString(11, i_brd.getText());
					ps2.setInt(12, empno);
					ps2.executeUpdate();
					
					ps3.setString(1, i_hiredate.getText());
					ps3.setInt(2, empno);
					ps3.executeUpdate();
					
					ps2.close();
					ps3.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				
				if(selectedFile != null) {
					// ���� ��ο� �̹����� ���ε��ϴ� �޼��� ȣ��
                    try {
						uploadImage(selectedFile);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
	        		dispose();
	        	} else {
	        		setVisible(true);
	        	}
		    }
		});
		
		// ��� ��ư�� �������� �۵�
		i_cancel.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	dispose();
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
		
		setTitle("������� ����");
		setSize(700,400);
		setVisible(true);
		setLocationRelativeTo(null);
		
		rs.close();
		ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ���� ��ο� �̹����� ���ε��ϴ� �޼���
    private void uploadImage(File imageFile) throws IOException {
        // ���ε��� ��� ���� (��� �ٲ��ֱ�)
//    	Path destinationPath = Path.of("D:\\Class\\WorkSpace\\JDBCClass\\HRPro\\src\\Image\\" + imageFile.getName());
    	Path directoryPath = Paths.get("C:\\test");
    	Files.createDirectories(directoryPath);
        Path destinationPath = Path.of(directoryPath + "\\" + imageFile.getName());
        
        // �̹��� ������ ����
        Files.copy(imageFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
