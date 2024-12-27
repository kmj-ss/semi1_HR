package smanageT;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import smanageT.DurationPanel;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Request extends JPanel implements TableModelListener, MouseListener, TextListener, ItemListener {

	Connection conn;

	CardLayout cardLayout;

	JLabel title;
	JPanel top, center, col_title, jp_list, jp_btns, jp_search;
	JPanel card_request;
	Annual card_annual;
	JPanel cardBtns, jp_cards;
	JButton jbtn_request, jbtn_annual;

	JButton bt_set, bt_update, bt_insert, bt_delete, bt_person, bt_manage, bt_printAll;
	DurationPanel dp_begin, dp_end;
	DurationPanel in_begin, in_end;
	JScrollPane jsp;
	TextField tf_search;

	JTable table;
	DefaultTableModel model;
	Vector<String> col_name;
	DefaultTableCellRenderer renderer;

	JComboBox comSearch;

	String[] str;
	Vector<Vector> outer;

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

	JTextField tf_type;
	JTextField tf_person;
	JTextField tf_requestment;
	JTextField tf_reason;
	JTextField tf_state;
	JTextField tf_note;
	JTextField tf_date;

	JButton bt_cancel, bt_commit;

	Vector v_row;
	Vector<String> table_col_name;

	ResultSetMetaData rsmd;

	String changedCheck;
	
	

	public Request(Connection conn) {


		this.conn = conn;

		try {
			PreparedStatement ps = conn.prepareStatement("select * from Request");
			ResultSet rs = ps.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();

			int numberOfColumns = rsmd.getColumnCount();

			table_col_name = new Vector<String>();
			for (int i = 0; i < numberOfColumns; i++) {
				table_col_name.add(rsmd.getColumnName(i + 1));
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		card_request = new JPanel(new BorderLayout(10, 10));
		card_annual = new Annual(conn);

//		card_annual = new JPanel();
		jp_cards = new JPanel(new CardLayout());

//         jp_cards.add(card_annual,"card2");
//        cardLayout.show(jp_cards,"card1");

//		this.add(card_request);

		jbtn_request = new JButton("요청관리");
		jbtn_request.setUI(new RoundedButton());
		jbtn_request.setFocusPainted(false);
		jbtn_annual = new JButton("연가관리");
		jbtn_annual.setUI(new RoundedButton());
		jbtn_annual.setFocusPainted(false);
		

		this.setLayout(new BorderLayout(10, 10));
		// top
		top = new JPanel(new GridLayout(3, 2));
		card_request.add(top, "North");
//		this.add(top,"North");

		title = new JLabel("요청 관리");
		title.setFont(new Font(getName(), 0, 30));
		JPanel wholeTop = new JPanel(new BorderLayout());
		this.add(wholeTop, "North");
		wholeTop.add(title, "West");

		cardBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
		cardBtns.add(jbtn_request);
		cardBtns.add(jbtn_annual);

		wholeTop.add(cardBtns, "East");

//		title = new JLabel("요청 관리");
//		title.setFont(new Font(getName(), 0, 30));
//		top.add(title);

		JPanel p_inputDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		top.add(new Label(" "));
		top.add(p_inputDate);

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

		top.add(jp_btns);
		jp_btns.add(bt_update);
		jp_btns.add(bt_insert);
		jp_btns.add(bt_delete);

//		top.add(new JLabel(""));

		jp_search = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		top.add(jp_search);

		comSearch = new JComboBox();
		comSearch.setUI(new Combotest());
		comSearch.setPreferredSize(new Dimension(80,23));
		comSearch.setBackground(Color.white);

//		bt_person = new JButton("사람");
//		bt_manage = new JButton("승인여부");
		tf_search = new TextField();

		tf_search.setPreferredSize(new Dimension(100, 25));
		jp_search.add(comSearch);
//		jp_search.add(bt_person);
//		jp_search.add(bt_manage);
		jp_search.add(tf_search);

		// dialog 세팅
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		setDialog();

		// center
		center = new JPanel(new BorderLayout());
		col_title = new JPanel(new GridLayout(1, 5));
		card_request.add(center);
//		this.add(center);
		// center.add(col_title,"North");
		initTable();

		cardLayout = (CardLayout) jp_cards.getLayout();

		this.add(jp_cards);
		jp_cards.add(card_request, "card1");
		jp_cards.add(card_annual, "card2");

		bt_printAll.addMouseListener(this);
		bt_set.addMouseListener(this);
		bt_update.addMouseListener(this);
		bt_delete.addMouseListener(this);
		bt_insert.addMouseListener(this);
		tf_search.addTextListener(this);
		

		jbtn_request.addMouseListener(this);
		jbtn_annual.addMouseListener(this);

		model.addTableModelListener(this);
		table.addMouseListener(this);

	}

	public void initTable() {

		// 컬럼명 저장하는 백터
		col_name = new Vector<String>();
		col_name.add("번호");
		col_name.add("요청종류");
		col_name.add("사원번호");
		col_name.add("요청사항");
		col_name.add("요청사유");
		col_name.add("상태");
		col_name.add("신청일자");
		col_name.add("관리");

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

	private void printAll() {
		outer = new Vector<>();
		String sql = " SELECT * FROM REQUEST ORDER BY seq ";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int seq = rs.getInt("SEQ");
				String r_type = rs.getString("R_TYPE");
				int empno = rs.getInt("empno");
				String r_duration = rs.getString("R_DURATION");
				String r_reason = rs.getString("R_REASON");
				String r_state = rs.getString("R_STATE");
				java.sql.Date r_date = rs.getDate("R_DATE");

				Object[] str = { seq, r_type, empno, r_duration, r_reason, r_state, r_date };
				Vector<Object> v2 = makeInVector(str);
				outer.add(v2);

			}
			rs.close();
			ps.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.setDataVector(outer, col_name);

		// 셀을 변경할 테이블과 컬럼의 위치를 매개변수
		changeCellEditor(table, table.getColumnModel().getColumn(7));

	}

	private void printAfterSetDuration(java.sql.Date sql_begin, java.sql.Date sql_end) {
		outer = new Vector<>();
		String sql = " SELECT * FROM REQUEST WHERE R_DATE BETWEEN ? AND ? ";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setDate(1, sql_begin);
			ps.setDate(2, sql_end);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int seq = rs.getInt("SEQ");
				String r_type = rs.getString("R_TYPE");
				int empno = rs.getInt("EMPNO");
				String r_duration = rs.getString("R_DURATION");
				String r_reason = rs.getString("R_REASON");
				String r_state = rs.getString("R_STATE");
				java.sql.Date r_date = rs.getDate("R_DATE");
//				String r_manage = rs.getString("R_MANAGE");

				Object[] str = { seq, r_type, empno, r_duration, r_reason, r_state, r_date };
				Vector<Object> v2 = makeInVector(str);
				outer.add(v2);

			}
			rs.close();
			ps.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.setDataVector(outer, col_name);

		// 셀을 변경할 테이블과 컬럼의 위치를 매개변수
		changeCellEditor(table, table.getColumnModel().getColumn(7));

	}

	private Vector<Object> makeInVector(Object[] array) {
		Vector<Object> in = new Vector<>();
		for (Object data : array) {
			in.add(data);
		}
		return in;
	}

	public void setDialog() {

		jdia = new JDialog();

		gbc.insets = new Insets(10, 10, 10, 10);

		jdia.setSize(700, 500);

		jdia.setLayout(new BorderLayout(10, 10));

		la_d_title = new JLabel("title");
		la_d_title.setFont(new Font(getName(), Font.BOLD, 30));

		jp_d_center = new JPanel(gbl);
		jp_d_bot = new JPanel(new FlowLayout(FlowLayout.CENTER));

		jdia.add(la_d_title, "North");
		jdia.add(jp_d_center);
		jdia.add(jp_d_bot, "South");
//		String type;
//		String person;
//		String job;
//		String reason;
//		String state;
//		String note;
//		String date;
//		JPanel manage;

		la_type = new JLabel("요청종류");
		la_person = new JLabel("사원번호");
		la_requestment = new JLabel("요청사항");
		la_reason = new JLabel("요청사유");
		la_state = new JLabel("상태");
//		la_note = new JLabel("상태");
//		la_date = new JLabel("신청일자");
//		la_manage = new JLabel("관리");

		tf_type = new JTextField();
		tf_person = new JTextField();

		// 수정구간
		tf_requestment = new JTextField();
		tf_requestment.setEditable(false);

		tf_reason = new JTextField();
		tf_state = new JTextField();
//		tf_note = new JTextField("상태");
//		tf_date = new JTextField("신청일자");
//		tf_manage = new JTextField();

		jp_d_center.add(la_type);
		jp_d_center.add(tf_type);
		jp_d_center.add(la_person);
		jp_d_center.add(tf_person);

		jp_d_center.add(la_requestment);
		// 수정구간
//		jp_d_center.add(tf_requestment);
		JPanel p_dur;
		p_dur = new JPanel(new BorderLayout());
		jp_d_center.add(p_dur);

		in_begin = new DurationPanel();
		in_end = new DurationPanel();

		in_begin.setFirstDuration();
		in_end.setSecondDuration();

		in_begin.jcb_year.addItemListener(this);
		in_begin.jcb_month.addItemListener(this);
		in_begin.jcb_date.addItemListener(this);

		in_end.jcb_year.addItemListener(this);
		in_end.jcb_month.addItemListener(this);
		in_end.jcb_date.addItemListener(this);

//		in_begin.setPreferredSize(new Dimension(60,40));
//		in_end.setPreferredSize(new Dimension(60,40));

		JPanel p_dur2;
		p_dur2 = new JPanel(new GridLayout(2, 1));
		p_dur2.add(in_begin);
		p_dur2.add(in_end);

		p_dur.add(p_dur2, "North");
//		p_dur.add(p_dur2,"South");
		p_dur.add(tf_requestment, "Center");

		jp_d_center.add(la_reason);
		jp_d_center.add(tf_reason);
		jp_d_center.add(la_state);
		jp_d_center.add(tf_state);
//		jp_d_center.add(la_note);
//		jp_d_center.add(tf_note);
//		jp_d_center.add(la_date);
//		jp_d_center.add(tf_date);
//		jp_d_center.add(la_manage);
//		jp_d_center.add(tf_manage);

		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.weightx = 0.2;

		gbc.weighty = 1.0;
		// 아래의 make함수를 지정
		make(la_type, 0, 0, 1, 1);
		make(la_person, 2, 0, 1, 1);
		make(la_requestment, 0, 1, 1, 1);
		make(la_reason, 2, 1, 1, 1);
		make(la_state, 0, 2, 1, 1);
//		make(la_note, 2, 2, 1, 1);
//		make(la_date, 0, 3, 1, 1);
//		make(la_manage, 2, 2, 1, 1);

		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		make(tf_type, 1, 0, 1, 1);
		make(tf_person, 3, 0, 1, 1);
		// 수정구간
//		make(tf_requestment, 1, 1, 1, 1);
		make(p_dur, 1, 1, 1, 1);
		make(tf_reason, 3, 1, 1, 1);
		make(tf_state, 1, 2, 1, 1);
//		make(tf_note, 3, 2, 1, 1);
//		make(tf_date, 1, 3, 1, 1);
//		make(tf_manage, 3, 2, 1, 1);

//		make(la_type, 0, 0, 1, 1); 
//		make(tf_type, 1, 0, 1, 1);
//		make(la_person, 2, 0, 1, 1); 
//		make(tf_person, 3, 0, 1, 1);
//		
//		make(tf_requestment, 0, 1, 1, 1); 
//		make(tf_requestment, 1, 1, 1, 1);
//		make(la_reason, 2, 1, 1, 1); 
//		make(tf_reason, 3, 1, 1, 1);
//		
//		make(la_state, 0, 2, 1, 1); 
//		make(tf_state, 1, 2, 1, 1);
//		make(la_note, 2, 2, 1, 1); 
//		make(tf_note, 3, 2, 1, 1);
//		
//		make(la_date, 0, 3, 1, 1); 
//		make(tf_date, 1, 3, 1, 1);
//		make(la_manage, 2, 3, 1, 1); 
//		make(tf_manage, 3, 3, 1, 1);

		bt_commit = new JButton("삽입하기");
		bt_commit.setUI(new RoundedButton());
		bt_commit.setFocusPainted(false);
		bt_cancel = new JButton("취소");
		bt_cancel.setUI(new RoundedButton());
		bt_cancel.setFocusPainted(false);

		jp_d_bot.add(bt_commit);
		jp_d_bot.add(bt_cancel);

		bt_cancel.addMouseListener(this);
		bt_commit.addMouseListener(this);

	}

	public void make(JComponent c, int x, int y, int w, int h) {

		gbc.gridx = x;

		gbc.gridy = y;

		gbc.gridwidth = w;

		gbc.gridheight = h;

		gbl.setConstraints(c, gbc);

		// GridBagLayout의 GridBagConstraints의 set하는 방법

	}

	@Override
	public void tableChanged(TableModelEvent e) {

//		//ln(model.getValueAt(e.getLastRow(), e.getColumn()));

		Integer getColumn = e.getColumn();
		Integer getLastRow = e.getLastRow();

		if (table.isEnabled() && e.getColumn() == 7) {
			if (getColumn != null && getLastRow != null && model.getValueAt(getLastRow, getColumn) != null) {
				String getVal = (String) model.getValueAt(getLastRow, getColumn);

				String temp = (String) model.getValueAt(getLastRow, 5);
				String temp_type = (String) model.getValueAt(getLastRow, 1);

				if (getVal.equals("승인")) {
					model.setValueAt("승인", getLastRow, 5);

				} else if (getVal.equals("미승인")) {

					model.setValueAt("미승인", getLastRow, 5);
				}
				String date = (String)model.getValueAt(e.getLastRow(), 3);
				int re = dateBetween(date);
				
				
				
				
				String sql = "update request set r_state=? where seq=?";

				int seq = (int) model.getValueAt(e.getLastRow(), 0);

				try {

					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, getVal);
					ps.setInt(2, seq);
					ps.executeUpdate();

					ps.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

				if ((temp.equals("승인")) && (temp_type.equals("연가")||temp_type.equals("연차"))) {
					String sql2 = "update annual set a_left=(select a_left+? from annual where empno = ?) where empno=? ";
					int empno = (int) model.getValueAt(e.getLastRow(), 2);
					
					
					
					try {

						PreparedStatement ps = conn.prepareStatement(sql2);
						ps.setInt(1, re+1);
						ps.setInt(2, empno);
						ps.setInt(3, empno);
						ps.executeUpdate();

						ps.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if ((temp.equals("미승인") || temp.equals("")||temp.equals("대기중")) && temp_type.equals("연가") && model.getValueAt(getLastRow, 5).equals("승인")) {
					String sql2 = " update annual set a_left= (select a_left-? from annual where empno = ?) where empno=? ";
					int empno = (int) model.getValueAt(e.getLastRow(), 2);
					

					try {

						PreparedStatement ps = conn.prepareStatement(sql2);
						ps.setInt(1, re+1);
						ps.setInt(2, empno);
						ps.setInt(3, empno);
						ps.executeUpdate();

						ps.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		}


	}

	private int dateBetween(String date) {

		String dates[] = date.split("~");
		dates[0]=dates[0].replace("-", "");
		dates[1]=dates[1].replace("-", "");

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		try {
	        Date from = simpleDateFormat.parse(dates[0]);
	        Date to = simpleDateFormat.parse(dates[1]);
	        
	        //ln(dates[0]);
	        //ln(dates[1]);
	        
	        long diff = to.getTime() - from.getTime();
	        int re = (int) (diff / 86400000L);
	        return re;
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return -1;
	}

	void changeCellEditor(JTable table, TableColumn column) {

		JComboBox comboBox = new JComboBox();

		comboBox.addItem("승인");
		comboBox.addItem("미승인");
		comboBox.addItem("");

		column.setCellEditor(new DefaultCellEditor(comboBox));
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

//		renderer.setToolTipText("클릭하면 콤보박스로 변경됩니다.");
		column.setCellRenderer(renderer);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		Object obj = e.getSource();

//		//ln(table.getRowCount());

		if (obj == bt_update) {

			if (table.isEnabled()) {
				table.setEnabled(false);
				bt_update.setText("읽기 전용 해제");
			}

			else {
				table.setEnabled(true);
				bt_update.setText("읽기 전용");
			}

		} else if (obj == bt_delete) {

//			jdia.setVisible(true);
//			la_d_title.setText("요청사항 삭제");

//			//ln(table.getSelectedColumn());
//			//ln(table.getSelectedRow());
//			model.removeRow(table.getSelectedRow());
//			//ln();

//			JDialog alert = new JDialog("aaa");
//			JOptionPane.showMessageDialog(this, "Are you Okay?", "Question", JOptionPane.QUESTION_MESSAGE);

			if (table.getSelectedRow() != -1) {

				if (!table.getValueAt(table.getSelectedRow(), 5).equals("승인")) {
					int check = JOptionPane.showOptionDialog(this, "정말로 삭제하시겠습니까?", "경고", JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, null, null);

					if (check == 0) {
						int seq = (int) model.getValueAt(table.getSelectedRow(), 0);
						deleteRequest(seq);
					}
				} else {
					JOptionPane.showMessageDialog(this, "승인된 요청은 삭제할 수 없습니다.", "경고", JOptionPane.INFORMATION_MESSAGE);
				}

			}

		} else if (obj == bt_insert) {

			jdia.setVisible(true);
			la_d_title.setText("요청사항 삽입");
			bt_commit.setText("삽입하기");

			tf_type.setText("");
			tf_person.setText("");
			int in_year1 = in_begin.getYear();
			int in_month1 = in_begin.getMonth();
			int in_date1 = in_begin.getDate();

			int in_year2 = in_end.getYear();
			int in_month2 = in_end.getMonth();
			int in_date2 = in_end.getDate();

			String start = in_year1 + "-" + (in_month1 < 10 ? "0" + in_month1 : in_month1) + "-"
					+ (in_date1 < 10 ? "0" + in_date1 : in_date1);
			String end = in_year2 + "-" + (in_month2 < 10 ? "0" + in_month2 : in_month2) + "-"
					+ (in_date2 < 10 ? "0" + in_date2 : in_date2);

			tf_requestment.setText(start + "~" + end);
			;
			tf_reason.setText("");
			tf_state.setText("대기중");
			tf_type.setText("");
			tf_state.setEditable(false);
//			tf_manage.setText("");

			in_begin.setFirstDuration();
			in_end.setSecondDuration();

		} else if (obj == table) {
			if (e.getClickCount() > 1) {

				int rowIndex = table.getSelectedRow();
				int columnIndex = table.getSelectedColumn();
				if (rowIndex != -1 || columnIndex != -1) {

					v_row = new Vector();
					for (int i = 0; i < model.getColumnCount(); i++) {
						v_row.add(model.getValueAt(rowIndex, i));
						//ln(v_row.get(i));
					}

					if (!v_row.get(5).equals("승인")) {
						bt_commit.setText("수정하기");
						la_d_title.setText(v_row.get(0) + "번 수정");
						tf_type.setText((String) v_row.get(1));
						tf_person.setText("" + v_row.get(2));

						changedCheck = tf_person.getText();

						tf_requestment.setText((String) v_row.get(3));
						tf_reason.setText((String) v_row.get(4));
						tf_state.setText((String) v_row.get(5));
//						tf_manage.setText((String) v_row.get(7));

						splitDuration((String) v_row.get(3));

						tf_state.setEditable(false);
//						tf_manage.setEditable(false);

						jdia.setVisible(true);
					} else {
						// alert
						JOptionPane.showMessageDialog(this, "승인된 요청은 수정할 수 없습니다.", "경고",
								JOptionPane.INFORMATION_MESSAGE);

					}

				}
			}

		} else if (obj == bt_cancel) {
			jdia.setVisible(false);

		} else if (obj == bt_commit) {
			if (bt_commit.getText().equals("삽입하기")) {

				insertRequest();
				printAll();

			} else if (bt_commit.getText().equals("수정하기")) {

				updateRequest();
				printAll();
			}

		} else if (obj == bt_set) {

			setDuration();

		} else if (obj == bt_printAll) {
			printAll();
		} else if (obj == jbtn_request) {
			cardLayout.show(jp_cards, "card1");
			title.setText("요청 관리");

		} else if (obj == jbtn_annual) {
			cardLayout.show(jp_cards, "card2");
			title.setText("연가 관리");
			card_annual.printAll();
			;

		}

	}

	private void splitDuration(String str) {

		String arrDur[] = str.split("~");
		String final_Dur[] = arrDur[0].split("-");

		in_begin.setUpdateDuration(final_Dur[0], final_Dur[1], final_Dur[2]);

		final_Dur = arrDur[1].split("-");

		in_end.setUpdateDuration(final_Dur[0], final_Dur[1], final_Dur[2]);

	}

	private void updateRequest() {

//		tf_type.setText((String) v_row.get(1));
//		tf_person.setText((String) v_row.get(2));
//		tf_requestment.setText((String) v_row.get(3));					
//		tf_reason.setText((String) v_row.get(4));
//		tf_state.setText((String) v_row.get(5));
//		tf_manage.setText((String) v_row.get(7));
		String sql = " update Request set (" + table_col_name.get(1) + "," + table_col_name.get(2) + ","
				+ table_col_name.get(3) + "," + table_col_name.get(4) + "," + table_col_name.get(5) + ")"
				+ "=(select ?,?,?,?,? from dual) where " + table_col_name.get(0) + "= ?";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, tf_type.getText());
			ps.setString(2, tf_person.getText());
			ps.setString(3, tf_requestment.getText());
			ps.setString(4, tf_reason.getText());
			ps.setString(5, tf_state.getText());
			ps.setInt(6, (int) v_row.get(0));

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		jdia.setVisible(false);

	}

	public void setDuration() {
//		dp_begin
//		dp_end

		Calendar cal_begin = Calendar.getInstance();
		cal_begin.set(dp_begin.getYear(), dp_begin.getMonth() - 1, dp_begin.getDate());
		Date date_begin = cal_begin.getTime();
		SimpleDateFormat df_begin = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date sql_begin = java.sql.Date.valueOf(df_begin.format(date_begin));

		Calendar cal_end = Calendar.getInstance();
		cal_end.set(dp_end.getYear(), dp_end.getMonth() - 1, dp_end.getDate());
		Date date_end = cal_end.getTime();
		SimpleDateFormat df_end = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date sql_end = java.sql.Date.valueOf(df_end.format(date_end));

		//ln(df_begin.format(date_begin));
		//ln(df_end.format(date_end));

		printAfterSetDuration(sql_begin, sql_end);

//		Vector<Vector> sresult = new Vector<>();

//		for (Vector<Object> in : outer) {
//			if(sql_begin.before((java.sql.Date)in.get(6))) {
//				sresult.add(in);
//			}
//		}
//		model.setDataVector(sresult, col_name);
//		changeCellEditor(table, table.getColumnModel().getColumn(7));

		String begin = "" + dp_begin.getYear() + "-" + dp_begin.getMonth() + "-" + dp_begin.getDate();
		String end = "" + dp_end.getYear() + "-" + dp_end.getMonth() + "-" + dp_end.getDate();

	}

	public void deleteRequest(int val) {

		String sql = "delete from Request where seq = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, val);

			ps.executeUpdate();

			printAll();

			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void insertRequest() {

		Vector<Object> v = new Vector<Object>();

		v.add(tf_type.getText());
		v.add(tf_person.getText());
		v.add(tf_requestment.getText());
		v.add(tf_reason.getText());
//		v.add(tf_note.getText());
		v.add(tf_state.getText());
//		v.add(tf_date.getText());
//		v.add(tf_manage.getText());

		String sql = " Insert into request values (request_sq.nextval, ?, ?, ?, ?, ?, sysdate) ";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, (String) v.get(0));
			ps.setString(2, (String) v.get(1));
			ps.setString(3, (String) v.get(2));
			ps.setString(4, (String) v.get(3));
			ps.setString(5, (String) v.get(4));

			ps.executeUpdate();

			ps.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		java.util.Date utilDate = new java.util.Date();
//
//	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd a HH:mm:ss");
//	    String formattedDate = simpleDateFormat.format(utilDate);
//	    java.sql.Date sqlDate = java.sql.Date.valueOf(formattedDate);		
//		v.add(sqlDate);

//		model.setDataVector(outer,col_name);
//		changeCellEditor(table, table.getColumnModel().getColumn(6));

		// 수정사항임
		jdia.setVisible(false);
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
		changeCellEditor(table, table.getColumnModel().getColumn(7));

	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		Object obj = e.getSource();

		int in_year1 = in_begin.getYear();
		int in_month1 = in_begin.getMonth();
		int in_date1 = in_begin.getDate();

		int in_year2 = in_end.getYear();
		int in_month2 = in_end.getMonth();
		int in_date2 = in_end.getDate();

		String start = in_year1 + "-" + (in_month1 < 10 ? "0" + in_month1 : in_month1) + "-"
				+ (in_date1 < 10 ? "0" + in_date1 : in_date1);
		String end = in_year2 + "-" + (in_month2 < 10 ? "0" + in_month2 : in_month2) + "-"
				+ (in_date2 < 10 ? "0" + in_date2 : in_date2);

		tf_requestment.setText(start + "~" + end);
	}

}
