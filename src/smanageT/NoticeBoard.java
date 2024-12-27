package smanageT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class NoticeBoard extends JPanel implements MouseListener {
	JFrame parentFrame;
	JPanel mainPanel;
	CardLayout cardLayout;
	Connection con;
	

	public NoticeBoard(JFrame parentFrame, Connection con) {
		this.parentFrame = parentFrame;
		this.con = con;
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		//���� �г�, ī�� ���̾ƿ� ����
		mainPanel = new JPanel(new CardLayout());
		cardLayout = (CardLayout) mainPanel.getLayout();
		add(mainPanel, BorderLayout.CENTER);

		//�������� ��� �г� ����
		JPanel noticeListPanel = createNoticeListPanel();
		mainPanel.add(noticeListPanel, "NoticeList");

		cardLayout.show(mainPanel, "NoticeList");
	}

	JPanel createNoticeListPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		//�������� Ÿ��Ʋ �� ��ư �г�(titlepanel)
		JPanel titlePanel = new JPanel(new BorderLayout());
		JLabel titleLabel = new JLabel("��������");
		titleLabel.setFont(new Font(getName(), 1, 20));
		titlePanel.add(titleLabel, BorderLayout.WEST);

		//��� Ÿ��Ʋ�гο� ��ư�߰�
		JPanel buttonPanel = new JPanel();
		JButton addButton = new JButton("�Խñ� �ۼ�");
		addButton.setUI(new RoundedButton());
		addButton.setBackground(new Color(51, 77, 117));
		addButton.setForeground(new Color(239, 246, 255));
		addButton.setFocusPainted(false);
		addButton.addActionListener(e -> showAddNoticePanel());
		buttonPanel.add(addButton);
		titlePanel.add(buttonPanel, BorderLayout.EAST);

		//�������� ��� �г�
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

		//DB���� ���������� �ҷ��� ǥ����
		ArrayList<String[]> notices = fetchNoticesFromDB();
		for (String[] notice : notices) {
			JPanel noticePanel = createNoticePanel(notice);
			listPanel.add(noticePanel);
		}

		JScrollPane scrollPane = new JScrollPane(listPanel);

		//ū�гο� �߰�
		panel.add(titlePanel, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);

		return panel;
	}

	ArrayList<String[]> fetchNoticesFromDB() {
		ArrayList<String[]> notices = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			String query = "SELECT NTID, TITLE, ENAME, NTDATE FROM NOTICE ORDER BY NTDATE DESC";
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				String ntid = rs.getString("NTID");
				String title = rs.getString("TITLE");
				String ename = rs.getString("ENAME");
				Date ntdate = rs.getDate("NTDATE");
				String[] notice = {ntid, title, ename, ntdate.toString()};
				notices.add(notice);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return notices;
	}

	JPanel createNoticePanel(String[] noticeInfo) {
		JPanel noticePanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 10, 5, 10);

		//�������� ��ȣ
		JLabel noticeNumber = new JLabel(noticeInfo[0]);
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.WEST;
		noticePanel.add(noticeNumber, gbc);

		//����
		JLabel noticeTitle = new JLabel(noticeInfo[1]);
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		noticePanel.add(noticeTitle, gbc);

		//�ۼ���
		JLabel noticeUsr = new JLabel(noticeInfo[2]);
		gbc.gridx = 2;
		gbc.weightx = 0.2;
		gbc.fill = GridBagConstraints.NONE;
		noticePanel.add(noticeUsr, gbc);

		//�ۼ���
		JLabel noticeDate = new JLabel(noticeInfo[3]);
		gbc.gridx = 3;
		gbc.weightx = 0.1;
		noticePanel.add(noticeDate, gbc);

		noticePanel.setBackground(Color.white);
		noticePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		noticePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		noticePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		//,�־ ���� split���� ���� 
		noticePanel.setName(noticeInfo[0] + "," + noticeInfo[1] + "," + noticeInfo[2] + "," + noticeInfo[3]);
		noticePanel.addMouseListener(this);

		return noticePanel;
	}

	void showAddNoticePanel() {
		JPanel addNoticePanel = new JPanel(new BorderLayout());
		addNoticePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		//���� �Է� �ʵ�
		JTextField titleField = new JTextField();
		JLabel titleLabel = new JLabel("���� :");
		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.add(titleLabel, BorderLayout.WEST);
		titlePanel.add(titleField, BorderLayout.CENTER);
		addNoticePanel.add(titlePanel, BorderLayout.NORTH);

		//���� �Է� �ʵ�
		JTextArea contentArea = new JTextArea();
		JScrollPane contentScrollPane = new JScrollPane(contentArea);
		addNoticePanel.add(contentScrollPane, BorderLayout.CENTER);

		// ��ư �г�
		JPanel buttonPanel = new JPanel();
		JButton saveButton = new JButton("����");
		saveButton.setUI(new RoundedButton());
		saveButton.setBackground(new Color(51, 77, 117));
		saveButton.setForeground(new Color(239, 246, 255));
		saveButton.setFocusPainted(false);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = titleField.getText();
				String content = contentArea.getText();
				String dlevel = "������";	//���� ������ �����ڹۿ��Ⱦ��ϱ� �ھƵ�
				if (!title.isEmpty() && !content.isEmpty()) {
					saveNotice(title, content, dlevel);
				} else {
					JOptionPane.showMessageDialog(null, "���� or ������ �Է��ϼ���.");
				}
			}
		});
		buttonPanel.add(saveButton);

		JButton backButton = new JButton("�ڷΰ���");
		backButton.setUI(new RoundedButton());
		backButton.setBackground(new Color(51, 77, 117));
		backButton.setForeground(new Color(239, 246, 255));
		backButton.setFocusPainted(false);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainPanel, "NoticeList");
			}
		});
		buttonPanel.add(backButton);

		addNoticePanel.add(buttonPanel, BorderLayout.SOUTH);

		mainPanel.add(addNoticePanel, "AddNotice");
		cardLayout.show(mainPanel, "AddNotice");
	}

	void saveNotice(String title, String content, String ename) {
		PreparedStatement ps = null;
		try {
			String query = "INSERT INTO NOTICE (NTID, EMPNO, TITLE, ENAME, DLEVEL, CONTENT, NTDATE) VALUES (NT_SEQ.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE)";
			ps = con.prepareStatement(query);
			ps.setInt(1, 0); //����Ű�������ؼ� �׳� �ھƵ� ���� ������ �����ڸ��Ἥ 1�� �������ѵ���
			ps.setString(2, title);
			ps.setString(3, ename);
			ps.setString(4, "������");
			ps.setString(5, content);

			ps.executeUpdate();
			JOptionPane.showMessageDialog(this, "����Ǿ����ϴ�.");
			reFresh();
			cardLayout.show(mainPanel, "NoticeList");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "�����߻�");
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	void reFresh() {
		JPanel noticeListPanel = createNoticeListPanel();
//		mainPanel.removeAll();
		mainPanel.add(noticeListPanel, "NoticeList");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JPanel clickedPanel = (JPanel) e.getSource();
		String[] noticeInfo = clickedPanel.getName().split(",");
		showNoticeDetail(noticeInfo[0]);
	}

	void showNoticeDetail(String ntid) {
		JPanel detailPanel = new JPanel(new BorderLayout());
		detailPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		//�������� ���� ��ȸ
		String[] noticeContent = fetchNoticeContent(ntid);
		JLabel titleLabel = new JLabel("���� : " + noticeContent[0] + "  �ۼ��� : " + noticeContent[1]);
		detailPanel.add(titleLabel, BorderLayout.NORTH);

		JTextArea contentArea = new JTextArea(noticeContent[2]);
		contentArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(contentArea);
		detailPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		JButton editButton = new JButton("����");
		editButton.setUI(new RoundedButton());
		editButton.setBackground(new Color(51, 77, 117));
		editButton.setForeground(new Color(239, 246, 255));
		editButton.setFocusPainted(false);
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isEdit = editButton.getText().equals("����");
				if (isEdit) {
					editButton.setText("����");
					contentArea.setEditable(true);
				} else {
					editButton.setText("����");
					contentArea.setEditable(false);
					saveEditedContent(ntid, contentArea.getText());
				}
			}
		});
		buttonPanel.add(editButton);

		JButton deleteButton = new JButton("����");
		deleteButton.setUI(new RoundedButton());
		deleteButton.setBackground(new Color(51, 77, 117));
		deleteButton.setForeground(new Color(239, 246, 255));
		deleteButton.setFocusPainted(false);
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?", "Ȯ��", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					deleteNotice(ntid);
				}
			}
		});
		buttonPanel.add(deleteButton);

		JButton backButton = new JButton("�ڷΰ���");
		backButton.setUI(new RoundedButton());
		backButton.setBackground(new Color(51, 77, 117));
		backButton.setForeground(new Color(239, 246, 255));
		backButton.setFocusPainted(false);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainPanel, "NoticeList");
			}
		});
		buttonPanel.add(backButton);

		detailPanel.add(buttonPanel, BorderLayout.SOUTH);

		mainPanel.add(detailPanel, "NoticeDetail");
		cardLayout.show(mainPanel, "NoticeDetail");
	}

	String[] fetchNoticeContent(String ntid) {
		String[] noticeContent = new String[3];
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = "SELECT TITLE, ENAME, CONTENT FROM NOTICE WHERE NTID = ?";
			ps = con.prepareStatement(query);
			ps.setString(1, ntid);
			rs = ps.executeQuery();
			if (rs.next()) {
				noticeContent[0] = rs.getString("TITLE");
				noticeContent[1] = rs.getString("ENAME");
				noticeContent[2] = rs.getString("CONTENT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return noticeContent;
	}

	void saveEditedContent(String ntid, String editedContent) {
		PreparedStatement ps = null;
		try {
			String query = "UPDATE NOTICE SET CONTENT = ? WHERE NTID = ?";
			ps = con.prepareStatement(query);
			ps.setString(1, editedContent);
			ps.setString(2, ntid);

			ps.executeUpdate();
			JOptionPane.showMessageDialog(this, "�����Ǿ����ϴ�.");
			reFresh();
			cardLayout.show(mainPanel, "NoticeList");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "���� �߻�");
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	void deleteNotice(String ntid) {
		PreparedStatement ps = null;
		try {
			String query = "DELETE FROM NOTICE WHERE NTID = ?";
			ps = con.prepareStatement(query);
			ps.setString(1, ntid);

			ps.executeUpdate();
			JOptionPane.showMessageDialog(this, "�����Ǿ����ϴ�.");
			reFresh();
			cardLayout.show(mainPanel, "NoticeList");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "�����߻�");
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
}
