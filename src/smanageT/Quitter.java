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
		setTitle("퇴사자 명단");
		setLayout(new BorderLayout());
		jtop = new JPanel();
		tilab = new JLabel("퇴사자 명단");
		jtop.add(tilab);
		add(jtop,BorderLayout.NORTH);
		jcenter = new JPanel();
		String[] columnNames = { "사원 번호", "사원 이름", "재직 상태", "퇴사일" };
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
                if (e.getClickCount() == 1) { // 한 번 클릭 시
                    int selectedRow = tab.getSelectedRow();
                    int clickedColumn = tab.columnAtPoint(e.getPoint()); 
                    if (selectedRow != -1) { // 유효한 행이 선택된 경우
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
					+ " where estate='퇴사' "
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
