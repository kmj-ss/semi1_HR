package smanageT;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

public class Annual extends JPanel implements TableModelListener, MouseListener, TextListener, ItemListener{

	Connection conn;

	JLabel title;
	JPanel top, center, col_title, jp_list, jp_btns, jp_search;
	JPanel card_request, card_annual;
	JPanel cardBtns;
	JButton jbtn_request, jbtn_annual;
	
	JPanel right;

	
	JButton bt_set, bt_update, bt_insert, bt_delete, bt_person, bt_manage, bt_printAll;
	DurationPanel dp_begin, dp_end;
	DurationPanel in_begin, in_end;
	JScrollPane jsp;
	TextField tf_search;

	JTable table,table_AS;
	DefaultTableModel model, model_AS;
	Vector<String> col_name,col_name_AS;
	
//	Vector<RequestDTO> list;
	DefaultTableCellRenderer renderer;

	JComboBox comSearch;

	String[] str;
	Vector<Vector> outer, outer_AS;

	// 디아로그
	JDialog jdia;
	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	JPanel jp_d_center, jp_d_bot;

	JLabel la_d_title;
	JLabel la_type;
	JLabel la_person;
	JLabel la_requestment;
	JLabel la_reason;
	JLabel la_state;
	JLabel la_note;
	JLabel la_date;
//	JLabel la_manage;

	JTextField tf_type;
	JTextField tf_person;
	JTextField tf_requestment;
	JTextField tf_reason;
	JTextField tf_state;
	JTextField tf_note;
	JTextField tf_date;
//	JTextField tf_manage;

	JButton bt_cancel, bt_commit;

	Vector v_row;
	Vector<String> table_col_name;

	ResultSetMetaData rsmd;
	

	public Annual(Connection conn) {
//		try {
//			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//				| UnsupportedLookAndFeelException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			String url = "jdbc:oracle:thin:@localhost:1521:xe";
//			String user="scott";
//			String pwd = "1234";

		this.conn = conn;
		this.setLayout(new BorderLayout(10, 10));

		try {
			PreparedStatement ps = conn.prepareStatement("select * from annual_standard");
			ResultSet rs = ps.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();

			int numberOfColumns = rsmd.getColumnCount();

			table_col_name = new Vector<String>();
			for (int i = 0; i < numberOfColumns; i++) {
				table_col_name.add(rsmd.getColumnName(i + 1));
				//ln(table_col_name.get(i));
			}
			
			rs.close();
			ps.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		jbtn_request = new JButton("요청관리");
		jbtn_request.setUI(new RoundedButton());
		jbtn_request.setFocusPainted(false);
		jbtn_annual = new JButton("연가관리");
		jbtn_annual.setUI(new RoundedButton());
		jbtn_annual.setFocusPainted(false);
		top = new JPanel(new GridLayout(3, 2));
		this.add(top, "North");
		
		title = new JLabel("연가 관리");
		title.setFont(new Font(getName(), 0, 30));
//		top.add(title);
//		top.add(new Label(" "));

		JPanel p_inputDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		top.add(new Label(" "));
//		top.add(p_inputDate);

		dp_begin = new DurationPanel();
		dp_end = new DurationPanel();
		bt_set = new JButton("설정");
		bt_set.setUI(new RoundedButton());
		bt_set.setFocusPainted(false);
		bt_printAll = new JButton("검색 초기화");
		bt_printAll.setUI(new RoundedButton());
		bt_printAll.setFocusPainted(false);
		
		p_inputDate.add(dp_begin);
		p_inputDate.add(dp_end);
		p_inputDate.add(bt_set);
		p_inputDate.add(bt_printAll);
		top.add(new Label(" "));
		top.add(new Label(" "));

		jp_btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		bt_update = new JButton("읽기전용");
		bt_update.setUI(new RoundedButton());
		bt_update.setFocusPainted(false);
		bt_insert = new JButton("삽입");
		bt_insert.setUI(new RoundedButton());
		bt_insert.setFocusPainted(false);
		bt_delete = new JButton("삭제");
		bt_delete.setUI(new RoundedButton());
		bt_delete.setFocusPainted(false);
		

//		top.add(jp_btns);
		jp_btns.add(bt_update);
		jp_btns.add(bt_insert);
		jp_btns.add(bt_delete);

//		top.add(new JLabel(""));
		
		cardBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		cardBtns.add(jbtn_request);
//		cardBtns.add(jbtn_annual);

//		top.add(cardBtns);
		
		top.add(new Label(" "));

		jp_search = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		top.add(jp_search);

		comSearch = new JComboBox();
		comSearch.setUI(new Combotest());
		comSearch.setBackground(Color.white);
		comSearch.setPreferredSize(new Dimension(85, 20));

//		bt_person = new JButton("사람");
//		bt_manage = new JButton("승인여부");
		tf_search = new TextField();

		tf_search.setPreferredSize(new Dimension(100, 25));
		jp_search.add(comSearch);
//		jp_search.add(bt_person);
//		jp_search.add(bt_manage);
		jp_search.add(tf_search);
		
		//dialog세팅 아직
		
		//center
		center = new JPanel(new BorderLayout());
		col_title = new JPanel(new GridLayout(1, 5));
		this.add(center);
		 center.add(col_title,"North");
		initTable();
		
		
		//right
		right = new JPanel(new BorderLayout());
		this.add(right,"East");
		
		initStandardTable();
		
		
		
//		이벤트
//		bt_printAll.addMouseListener(this);
//		bt_set.addMouseListener(this);
//		bt_apply.addMouseListener(this);
//		bt_update.addMouseListener(this);
//		bt_delete.addMouseListener(this);
//		bt_insert.addMouseListener(this);
		tf_search.addTextListener(this);
//		
//		jbtn_request.addMouseListener(this);
//		jbtn_annual.addMouseListener(this);
//
//		model.addTableModelListener(this);
//		table.addMouseListener(this);
		
	}
	
	private void initStandardTable() {

		col_name_AS = new Vector<String>();
		col_name_AS.add("근무년차");
		col_name_AS.add("연차 수");
		

		

		// 컬럼명을 넣음
		model_AS = new DefaultTableModel(col_name_AS, 0) {
			public boolean isCellEditable(int r, int c) {
				return (c == 7) ? true : false;
//				return false;
			}
		};

		// table 생성
		table_AS = new JTable(model_AS);
			

		// 스크롤에 테이블
		JScrollPane scroll_AS = new JScrollPane(table_AS);
		right.add(scroll_AS, "North");

		printAll_AS();

//		table.setEnabled(false);
//		table_AS.setAutoCreateRowSorter(true);
//		TableRowSorter tablesorter = new TableRowSorter(table.getModel());
//		table.setRowSorter(tablesorter);
	}

	public void initTable() {

		// 컬럼명 저장하는 백터
		col_name = new Vector<String>();
		col_name.add("사원번호");
		col_name.add("사원이름");
		col_name.add("남은연차");
		col_name.add("총연차");

		// 검색조건 콤보박스
		for (String data : col_name) {
			comSearch.addItem(data);
		}

		// 컬럼명을 넣음
		model = new DefaultTableModel(col_name, 0) {
			public boolean isCellEditable(int r, int c) {
				return (c == 7) ? true : false;
//				return false;
			}
		};

		// table 생성
		table = new JTable(model);
			

		// 스크롤에 테이블
		JScrollPane scroll = new JScrollPane(table);
		center.add(scroll, "North");

		printAll();

//		table.setEnabled(false);
		table.setAutoCreateRowSorter(true);
		TableRowSorter tablesorter = new TableRowSorter(table.getModel());
		table.setRowSorter(tablesorter);

//		//ln(model.getColumnCount() + "/" + model.getRowCount());
	}
	

	public void printAll() {
	    outer = new Vector<>();
	    String sql = "SELECT an.empno, e.ename, an.a_left, trunc(months_between(sysdate, j.hiredate)/12, 0) as full "
	               + "FROM annual an "
	               + "LEFT OUTER JOIN jobst j ON an.empno = j.empno "
	               + "LEFT OUTER JOIN emptable e ON an.empno = e.empno "
	               + "WHERE j.quitdate is null";
	    try {
	        PreparedStatement ps = conn.prepareStatement(sql);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            int empno = rs.getInt("empno");
	            String ename = rs.getString("ename");
	            int a_left = rs.getInt("a_left");
	            int full = rs.getInt("full");
	            int full_AS = 0;

	            if (full < 1) {
	                full_AS = 11;
	            } else if (full < 3) {
	                full_AS = 15;
	            } else if (full < 5) {
	                full_AS = 16;
	            } else if (full <= 5) {
	                full_AS = 17;
	            } else if (full <= 22) {
	                full_AS = 25;
	            }
	            if (empno == 1 || empno == 2 || empno == 3 || empno == 4 || empno == 5 || empno == 6 || empno == 7 || empno == 8 || empno == 9 ||
	            	empno == 10 || empno == 11 || empno == 12 || empno == 13 || empno == 14) {
	            	full_AS = 50;
	            }

	            Object[] str = {empno, ename, a_left, full_AS};
	            Vector<Object> v2 = makeInVector(str);
	            outer.add(v2);
	        }
	        rs.close();
	        ps.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    model.setDataVector(outer, col_name);
	}
	
	public void printAll_AS() {
		outer_AS = new Vector<>();
		String sql = " SELECT * FROM annual_standard";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String n_year = rs.getString("N_YEAR");
				int ann_day = rs.getInt("ANN_DAY");

//				//ln(n_year);
//				//ln(ann_day);

				Object[] str = { n_year, ann_day};
				Vector<Object> v2 = makeInVector(str);
				outer_AS.add(v2);

			}
			rs.close();
			ps.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model_AS.setDataVector(outer_AS, col_name_AS);

		// 셀을 변경할 테이블과 컬럼의 위치를 매개변수
//		changeCellEditor(table_AS, table_AS.getColumnModel().getColumn(7));

	}
	
	private Vector<Object> makeInVector(Object[] array) {
		Vector<Object> in = new Vector<>();
		for (Object data : array) {
			in.add(data);
		}
		return in;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void textValueChanged(TextEvent e) {
		searchTable();		
	}
	private void searchTable() {

		String field = (String) comSearch.getSelectedItem();

		String word = tf_search.getText();

		Vector<Vector> sresult = new Vector<>();

		int index = 1;

		for (int i = 0; i < col_name.size(); i++) {
			if (field.equals(col_name.get(i))) {
				index = i;
			}
		}

		if (word.equals("")) {
			for (Vector<Object> in : outer) {
				sresult.add(in);
			}
		} else {
			for (Vector<Object> in : outer) {
//				if (word.equals(String.valueOf((in.get(index))))) {
				if (String.valueOf((in.get(index))).contains(word)) {
					sresult.add(in);
				}
			}
		}

		model.setDataVector(sresult, col_name);


	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		if(obj==(Object)513214) {
			
		}else if (obj==jbtn_request) {
		}
		
	}
		

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
	}

}
