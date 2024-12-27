package smanageT;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.event.*;

public class Quitter extends JDialog {
	
	JPanel jtop,jcenter;
	JLabel tilab;
	JTable tab;
	DefaultTableModel df;
	JScrollPane sP;
	
	public Quitter(Connection con) {
		setTitle("����� ���");
		setLayout(new BorderLayout());
		jtop = new JPanel();
		tilab = new JLabel("����� ���");
		jtop.add(tilab);
		add(jtop,BorderLayout.NORTH);
		jcenter = new JPanel();
		String[] columnNames = { "��� ��ȣ", "��� �̸�", "���� ����", "�����" };
		df = new DefaultTableModel(columnNames,0) {
			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
		};
		tab = new JTable(df);
		
		tab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // �� �� Ŭ�� ��
                    int selectedRow = tab.getSelectedRow();
                    int clickedColumn = tab.columnAtPoint(e.getPoint()); 
                    if (selectedRow != -1) { // ��ȿ�� ���� ���õ� ���
                        int empno = (int) tab.getValueAt(selectedRow, 0);
                        EmpInfoDetail emi = new EmpInfoDetail(con,empno);
                        emi.upda.setVisible(false);
                        emi.del.setVisible(false);
                    }
                }
            }
        });
		
		sP = new JScrollPane(tab);
		jcenter.add(sP); 
		add(jcenter,"Center");
		try {
			setSql(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setSize(500,500);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	void setSql(Connection con) {
		try { 
			String sql = " select e.empno, ename, estate, quitdate "
					+ " from emptable e join jobst j on e.empno = j.empno "
					+ " where estate='���' "
					+ " order by empno ";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int snum = rs.getInt(1);
				String sname = rs.getString(2);
				String stat = rs.getString(3);
				Date date = rs.getDate(4);
				df.addRow(new Object[] { snum,sname,stat,date});
				
			}
		} catch (Exception e) {
			
		}
	}
	
}
