package smanageT;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class EmpInfoDetail extends JDialog implements ActionListener {

	JLabel name, tel, level, addr, dept, hiredate, email, msgid, brd, male, wtype, quitdate;
	JLabel d_name, d_tel, d_level, d_addr, d_dept, d_hiredate, d_quitdate, d_email, d_msgid, d_brd, d_male, d_wtype;
	JLabel img;
	JButton upda, del, canc;
	JPanel imgtxt, imgp, imgval; // �̹��� ���� �г�
	int empno;
	JPanel entype;
	JPanel entitle;
	JPanel info;
	Connection con;

	public EmpInfoDetail(Connection con, int empno) {
		this.con = con;
		this.empno = empno;
		setTitle("��� �󼼺���");
		setBackground(Color.white);
		setLayout(new BorderLayout());
		entitle = new JPanel(new GridLayout(1, 2));

		imgp = new JPanel();
		imgp.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
		img = new JLabel();
		img.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

		initializeTable();

		// ����, ����, ��ҹ�ư
		JPanel butn = new JPanel(new FlowLayout(2, 10, 5));
		upda = new JButton("����");
		upda.setUI(new RoundedButton());
		del = new JButton("����");
		del.setUI(new RoundedButton());
		canc = new JButton("���");
		canc.setUI(new RoundedButton());
		upda.setForeground(new Color(245, 245, 245));
		del.setForeground(new Color(245, 245, 245));
		canc.setForeground(new Color(245, 245, 245));
		butn.add(upda);
		butn.add(del);
		butn.add(canc);

		entype.setBackground(new Color(112, 128, 144));
		entype.setForeground(new Color(245, 245, 245));
		imgp.setBackground(new Color(240, 255, 255));
		butn.setBackground(new Color(112, 128, 144));

		upda.addActionListener(this);
		del.addActionListener(this);
		canc.addActionListener(this);

		add(entype, BorderLayout.NORTH);
		add(entitle, BorderLayout.CENTER);
		add(butn, BorderLayout.SOUTH);

		setSize(900, 700);
		setVisible(true);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
	}
	
	// ���̺� �ʱ�ȭ �޼ҵ�
	private void initializeTable() {
		// ��������
		try {
			String sql = " select ename, addr, tel, bird, gender, email, jlevel, hiredate, quitdate, wtype, dept, msgid, img "
					+ " from emptable e join jobst j on e.empno = j.empno" + " where e.empno = ? ";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, empno);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				d_name = new JLabel(rs.getString("ename"));
				d_tel = new JLabel(rs.getString("tel"));
				d_level = new JLabel(rs.getString("jlevel"));
				d_addr = new JLabel(rs.getString("addr"));
				d_dept = new JLabel(rs.getString("dept"));
				d_hiredate = new JLabel(rs.getString("hiredate").substring(0, 10));
				if (rs.getString("quitdate") != null) {
					d_quitdate = new JLabel(rs.getString("quitdate").substring(0, 10));
				} else if ((rs.getString("quitdate") == null)) {
					d_quitdate = new JLabel("-");
				}
				d_email = new JLabel(rs.getString("email"));
				d_msgid = new JLabel(rs.getString("msgid"));
				d_brd = new JLabel(rs.getString("bird").substring(0, 10));
				d_male = new JLabel(rs.getString("gender"));
				d_wtype = new JLabel(rs.getString("wtype"));
				if (rs.getString("img").equals("no img")) {
					img.setIcon(new ImageIcon("src/image/image.jpg"));
				} else {
					//img.setIcon(new ImageIcon("src/image/" + rs.getString("img")));
					img.setIcon(new ImageIcon("C:\\test\\"+rs.getString("img")));
				}
			}
			
			imgp.add(img);
			entitle.add(imgp);

			entype = new JPanel(new FlowLayout(2, 10, 5));

			info = new JPanel();
			info.setLayout(new GridLayout(2, 1));

			name = new JLabel("�̸�");
			tel = new JLabel("��ȣ");
			addr = new JLabel("�ּ�");
			brd = new JLabel("����");
			male = new JLabel("����");
			email = new JLabel("�̸���");
			JPanel userinfo = new JPanel();
			JPanel u_topinfo = new JPanel(new GridLayout(3, 2));
			JPanel u_udinfo = new JPanel(new GridLayout(3, 2));
			userinfo.setLayout(new GridLayout(2, 1));
			u_topinfo.add(name);
			u_topinfo.add(d_name);
			u_topinfo.add(addr);
			u_topinfo.add(d_addr);
			u_topinfo.add(tel);
			u_topinfo.add(d_tel);
			u_udinfo.add(brd);
			u_udinfo.add(d_brd);
			u_udinfo.add(male);
			u_udinfo.add(d_male);
			u_udinfo.add(email);
			u_udinfo.add(d_email);

			// ������ ���ȭ��
			u_topinfo.setBackground(new Color(240, 255, 255));
			u_udinfo.setBackground(new Color(240, 255, 255));

			userinfo.setLayout(new GridLayout(2, 1));
			userinfo.add(u_topinfo);
			userinfo.add(u_udinfo);
			userinfo.setBackground(new Color(240, 255, 255));

			// TitledBorder ����
			TitledBorder titledBorder = BorderFactory.createTitledBorder("�λ�ī��");

			// ���м� ���� ����
			titledBorder.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

			// EmptyBorder ���� (���� ����)
			Border emptyBorder = BorderFactory.createEmptyBorder(10, 20, 20, 10); // ���鰪 ���� ����

			// CompoundBorder�� TitledBorder�� EmptyBorder ����
			Border compoundBorder = BorderFactory.createCompoundBorder(titledBorder, emptyBorder);

			userinfo.setBorder(compoundBorder);

			// ȸ�系 ����
			level = new JLabel("����");
			hiredate = new JLabel("�Ի���");
			wtype = new JLabel("[ Ÿ�� ]");
			dept = new JLabel("�μ�");
			msgid = new JLabel("�޽���id");
			quitdate = new JLabel("�����");
			JPanel cpninfo = new JPanel();
			JPanel c_topinfo = new JPanel(new GridLayout(5, 2));
			cpninfo.setLayout(new BorderLayout());
			c_topinfo.add(level);
			c_topinfo.add(d_level);
			c_topinfo.add(hiredate);
			c_topinfo.add(d_hiredate);
			entype.add(wtype);
			entype.add(d_wtype);
			wtype.setForeground(new Color(245, 245, 245));
			d_wtype.setForeground(new Color(245, 245, 245));

			c_topinfo.add(dept);
			c_topinfo.add(d_dept);
			c_topinfo.add(msgid);
			c_topinfo.add(d_msgid);
			c_topinfo.add(quitdate);
			c_topinfo.add(d_quitdate);

			c_topinfo.setBackground(new Color(240, 255, 255));
			c_topinfo.setBackground(new Color(240, 255, 255));

			cpninfo.add(c_topinfo);

			cpninfo.setBackground(new Color(240, 255, 255));

			TitledBorder border2 = BorderFactory.createTitledBorder("�λ�����");
			border2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // ���м� ���� ����
			Border emptyBorder2 = BorderFactory.createEmptyBorder(10, 20, 20, 10); // ���鰪 ���� ����
			Border compoundBorder2 = BorderFactory.createCompoundBorder(border2, emptyBorder2);
			cpninfo.setBorder(compoundBorder2);

			info.add(userinfo);
			info.add(cpninfo);

			entitle.add(info);

			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void refreshTableData() {
		entitle.removeAll();
		initializeTable();
		entitle.add(info, BorderLayout.CENTER);

		// �г��� �ٽ� �׸����� ȣ��
		revalidate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == upda) { // ������ư ��������
			try {
				EmpUpdate empins = new EmpUpdate(con, this.empno);
				empins.setVisible(true); // �Է� â�� ���̰� �Ѵ�

				// �Է� â�� ���� �� ������ �۾� ����
				empins.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						LevelUp l = new LevelUp(con);
						refreshTableData();
					}

					@Override
					public void windowClosing(WindowEvent e) {
						LevelUp l = new LevelUp(con);
						refreshTableData();
					}
				});
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else if (obj == del) { // ������ư ��������
			JFrame frame = new JFrame("�˸�â");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			int confirmed = JOptionPane.showConfirmDialog(frame, "������ �����Ͻðڽ��ϱ�?", "���� Ȯ��",
					JOptionPane.OK_CANCEL_OPTION);

			if (confirmed == JOptionPane.OK_OPTION) {
				// ������ ���� ����
				try {
					String sql2 = " DELETE from jobst " + " WHERE empno=? ";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setInt(1, empno);
					ps2.executeUpdate();
					
					
					sql2 = " DELETE from annual " + " WHERE empno=? ";
					ps2 = con.prepareStatement(sql2);
					ps2.setInt(1, empno);
					ps2.executeUpdate();
					
					sql2 = " DELETE from paytable " + " WHERE empno=? ";
					ps2 = con.prepareStatement(sql2);
					ps2.setInt(1, empno);
					ps2.executeUpdate();
					
					sql2 = " DELETE from emptable " + " WHERE empno=? ";
					ps2 = con.prepareStatement(sql2);
					ps2.setInt(1, empno);
					ps2.executeUpdate();

					ps2.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				dispose();
			} else {
				this.setVisible(true);
			}

		} else if (obj == canc) { // ��ҹ�ư ��������
			dispose();
		}
	}

}
