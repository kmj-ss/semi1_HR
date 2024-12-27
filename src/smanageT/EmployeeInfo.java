package smanageT;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.regex.PatternSyntaxException;

public class EmployeeInfo extends JPanel implements ActionListener {

	// ��ü �г�
	JPanel e_main;

	// ���
	JPanel e_top; // ��� ���� �г� �� ����
	JPanel e_top2; // ��� ��ư �� ��â �г�
	JLabel e_title; // ������� ����
	JButton e_ins, e_sel, e_del, e_dsel; // �Է�, ��ȸ, ���, �����Ȳ ��ư
	JTextField e_selfield; // ��ȸ �ؽ�Ʈ �ʵ�

	// �ϴ�
	JPanel e_bottom; // �ϴ� ū �г�
	JLabel e_btitle; // �ϴ� ����
	JTable e_table; // �ϴ� ������ ���̺�
	DefaultTableModel e_tableModel;

	Connection con;

	public EmployeeInfo(Connection con) {
		this.con = con;
		LevelUp l = new LevelUp(con);
		this.setLayout(new BorderLayout());
		e_main = new JPanel(new BorderLayout());

		// ���
		e_top = new JPanel(new GridLayout(2, 1, 10, 20));

		// ��� ������� ���� �߰�
		e_title = new JLabel("��� ����ȸ");
		e_top.add(e_title);

		// ��ư �� ��â ����
		e_ins = new JButton("��� �Է�");
		e_sel = new JButton("��� �˻�");
		e_del = new JButton("���");
		e_dsel = new JButton("��� ��Ȳ");
		e_selfield = new JTextField(25);
		e_selfield.setEditable(false);
		e_ins.setUI(new RoundedButton());
		e_sel.setUI(new RoundedButton());
		e_del.setUI(new RoundedButton());
		e_dsel.setUI(new RoundedButton());
		// ��� ��ư �� ��â �߰�
		e_top2 = new JPanel();
		e_top2.setLayout(new FlowLayout(2, 20, 5));
		e_top2.add(e_dsel);
		e_top2.add(e_del);
		e_top2.add(e_ins);
		e_top2.add(e_sel);
		e_top2.add(e_selfield);

		e_top.add(e_top2);

		e_main.add(e_top);
		this.add(BorderLayout.NORTH, e_main);

		// �ϴ�
		e_bottom = new JPanel(new BorderLayout());

		// ���̺� �ʱ�ȭ �޼ҵ� ȣ��
		initializeTable();

		// Ŭ���� �̺�Ʈ �߻�
		e_dsel.addActionListener(this);
		e_del.addActionListener(this);
		e_ins.addActionListener(this);
		e_sel.addActionListener(this);
		this.add(BorderLayout.CENTER, e_bottom);
	}

	// ���̺� �ʱ�ȭ �޼ҵ�
	private void initializeTable() {
		e_btitle = new JLabel("��� ���");
		e_bottom.add(e_btitle, BorderLayout.NORTH);

		// �ϴ� �÷� ���� - ������ ����
		Vector<String> e_columns = new Vector<>();
		e_columns.add("�����ȣ");
		e_columns.add("����̸�");
		e_columns.add("����");
		e_columns.add("�������");
		e_columns.add("�μ�");
		e_columns.add("����");
		e_columns.add("��������");
		e_columns.add("��������");

		// �ʱ� ������ ��ȸ �� ���̺� �� ����
		try {
			// ���� �����ȸ ����
			String sql = " select e.empno, ename, gender, bird, dept, jlevel, wtype "
					+ " from emptable e join jobst j on e.empno = j.empno " + " where estate='����' "
					+ " order by e.empno ";

			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			// �����͸� ���� ArrayList ����
			ArrayList<ArrayList<Object>> e_datas = new ArrayList<>();

			while (rs.next()) {
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename");
				String gender = rs.getString("gender");
				String bird = rs.getString("bird").substring(0, 10);
				String dept = rs.getString("dept");
				String jlevel = rs.getString("jlevel");
				String wtype = rs.getString("wtype");

				// ������ ���� ������ �迭 ����
				ArrayList<Object> temp = new ArrayList<>();
				temp.add(empno);
				temp.add(ename);
				temp.add(gender);
				temp.add(bird);
				temp.add(dept);
				temp.add(jlevel);
				temp.add(wtype);
				temp.add(Boolean.FALSE); // üũ�ڽ� ���� ���� �ʱⰪ

				// ������ ��ü�� ������ �迭�� ����
				e_datas.add(temp);
			}

			// DefaultTableModel�� ������ ������ ���� Vector�� ��ȯ
			Vector<Vector<Object>> dataVector = new Vector<>();
			for (ArrayList<Object> row : e_datas) {
				Vector<Object> rowData = new Vector<>(row);
				dataVector.add(rowData);
			}

			// ���̺� �� ����
			e_tableModel = new DefaultTableModel(dataVector, e_columns) {
				@Override
				public Class<?> getColumnClass(int columnIndex) {
					if (columnIndex == 0) {
						return Integer.class; // ù ��° �÷��� Integer Ÿ������ ����
					} else if (columnIndex == getColumnCount() - 1) {
						return Boolean.class; // ������ ���� Boolean Ÿ�� (üũ�ڽ� ���)
					}
					return super.getColumnClass(columnIndex);
				}

				@Override
				public void setValueAt(Object value, int row, int column) {
					if (column == getColumnCount() - 1 && value instanceof Boolean) {
						super.setValueAt(value, row, column);
					} else if (column == 0 && value instanceof Integer) {
						super.setValueAt(value, row, column);
					}
				}

				@Override
				public boolean isCellEditable(int row, int column) {
					return column == getColumnCount() - 1;
				}
			};

			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		e_table = new JTable(e_tableModel);

		// ���̺� ������ ���콺 Ŭ���� �߻��ϴ� �̺�Ʈ ����
		e_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) { // �� �� Ŭ�� ��
					int selectedRow = e_table.getSelectedRow();
					int clickedColumn = e_table.columnAtPoint(e.getPoint()); // �÷� �ε��� ������
					if (selectedRow != -1 && clickedColumn != 7) { // ��ȿ�� ���� ���õ� ���
						int empno = (int) e_table.getValueAt(selectedRow, 0); // �����ȣ ������
						EmpInfoDetail emi = new EmpInfoDetail(con, empno); // ������ �۾��� �� �����ȣ�� �Ѱ��ֱ�
						emi.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosed(WindowEvent e) {
								refreshTableData();
							}

							@Override
							public void windowClosing(WindowEvent e) {
								refreshTableData();
							}
						});
					}
				}
			}
		});

		e_table.setBackground(new Color(240, 248, 255));
		e_table.setGridColor(new Color(105, 105, 105));
		// ù��° �� ��������
		e_table.getColumnModel().getColumn(0).setCellRenderer(new LeftAlignRenderer());
		JScrollPane e_scroll = new JScrollPane(e_table);
		e_bottom.add(e_scroll, BorderLayout.CENTER);
	}
	
	private void refreshTableData() {
		e_bottom.removeAll(); // ������ ���̺��� ����
		e_table.removeAll();
		// ���̺� ���� �ٽ� �ʱ�ȭ
		initializeTable();

		// ���� �ʱ�ȭ�� ���̺��� �ٽ� e_bottom�� �߰�
		e_bottom.add(new JScrollPane(e_table), BorderLayout.CENTER);

		// �г��� �ٽ� �׸����� ȣ��
		revalidate();
		repaint();
	}

	// Ŭ���� �̺�Ʈ �߻�
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == e_dsel) {
			// �����Ȳ
			Quitter qt = new Quitter(con);
		} else if (obj == e_del) {
			// ��� ó�� ����
			deleteEmployees();
			refreshTableData();
		} else if (obj == e_ins) {
			// ������ �Է� ��ư ���ý�
			try {
				// �Է� â�� ����� ǥ���ϱ�
				EmpInsert empins = new EmpInsert(con);
				empins.setVisible(true); // �Է� â�� ���̰� �Ѵ�

				// �Է� â�� ���� �� ������ �۾� ����
				empins.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						refreshTableData();
					}

					@Override
					public void windowClosing(WindowEvent e) {
						refreshTableData();
					}
				});
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (obj.toString().contains("��� �˻�")) {
			// ��ȸ ��ư ���ý�
			e_selfield.setEditable(true);
			searchEmployees();
			e_sel.setText("�˻� ����");
		} else if (obj.toString().contains("�˻� ����")) {
			e_selfield.setEditable(false);
			e_selfield.setText("");
			e_sel.setText("��� �˻�");
		}
	}

	// ��� ó�� �޼ҵ�
	private void deleteEmployees() {
		int result = 0;
		// ���̺��� üũ�� �׸��� Ȯ���Ͽ� ��� ó��
		for (int i = 0; i < e_table.getRowCount(); i++) {
			int isempno = (int) e_table.getValueAt(i, 0);
			boolean selected = (boolean) e_table.getValueAt(i, 7);
			if (selected) {
				try {
					// ���ó�� ����
					String sql = " update jobst " + " set estate='���', quitdate = trunc(sysdate) "
							+ " where empno = ? ";

					PreparedStatement ps = con.prepareStatement(sql);
					ps.setInt(1, isempno);
					result = ps.executeUpdate();

					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if (result > 0) {
			JOptionPane.showMessageDialog(null, "��� ó���Ǿ����ϴ�.");
		}
	}

	// ��� �˻� �޼ҵ�
	private void searchEmployees() {
		// �˻��ʵ� �ʱ�ȭ
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(e_tableModel);
		e_table.setRowSorter(sorter);

		e_selfield.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				search();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				search();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search();
			}

			private void search() {
				String searchText = e_selfield.getText().trim();

				// �˻�� ������ ��� ���� �����ش�
				if (searchText.isEmpty()) {
					sorter.setRowFilter(null);
					return;
				}

				try {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
				} catch (PatternSyntaxException ex) {
					System.err.println("�˻��� ����: " + ex.getMessage());
					sorter.setRowFilter(null);
				}
			}
		});
	}

	// ����� ���� �� ������ (���� ����)
	private static class LeftAlignRenderer extends DefaultTableCellRenderer {
		public LeftAlignRenderer() {
			setHorizontalAlignment(SwingConstants.LEFT);
			validate();
		}
	}
}