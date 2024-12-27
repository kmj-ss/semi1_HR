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

	// 전체 패널
	JPanel e_main;

	// 상단
	JPanel e_top; // 상단 제목 패널 및 구조
	JPanel e_top2; // 상단 버튼 및 텍창 패널
	JLabel e_title; // 사원메인 제목
	JButton e_ins, e_sel, e_del, e_dsel; // 입력, 조회, 퇴사, 퇴사현황 버튼
	JTextField e_selfield; // 조회 텍스트 필드

	// 하단
	JPanel e_bottom; // 하단 큰 패널
	JLabel e_btitle; // 하단 제목
	JTable e_table; // 하단 데이터 테이블
	DefaultTableModel e_tableModel;

	Connection con;

	public EmployeeInfo(Connection con) {
		this.con = con;
		LevelUp l = new LevelUp(con);
		this.setLayout(new BorderLayout());
		e_main = new JPanel(new BorderLayout());

		// 상단
		e_top = new JPanel(new GridLayout(2, 1, 10, 20));

		// 상단 사원메인 제목 추가
		e_title = new JLabel("사원 상세조회");
		e_top.add(e_title);

		// 버튼 및 텍창 생성
		e_ins = new JButton("사원 입력");
		e_sel = new JButton("사원 검색");
		e_del = new JButton("퇴사");
		e_dsel = new JButton("퇴사 현황");
		e_selfield = new JTextField(25);
		e_selfield.setEditable(false);
		e_ins.setUI(new RoundedButton());
		e_sel.setUI(new RoundedButton());
		e_del.setUI(new RoundedButton());
		e_dsel.setUI(new RoundedButton());
		// 상단 버튼 및 텍창 추가
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

		// 하단
		e_bottom = new JPanel(new BorderLayout());

		// 테이블 초기화 메소드 호출
		initializeTable();

		// 클릭시 이벤트 발생
		e_dsel.addActionListener(this);
		e_del.addActionListener(this);
		e_ins.addActionListener(this);
		e_sel.addActionListener(this);
		this.add(BorderLayout.CENTER, e_bottom);
	}

	// 테이블 초기화 메소드
	private void initializeTable() {
		e_btitle = new JLabel("사원 목록");
		e_bottom.add(e_btitle, BorderLayout.NORTH);

		// 하단 컬럼 제목 - 데이터 삽입
		Vector<String> e_columns = new Vector<>();
		e_columns.add("사원번호");
		e_columns.add("사원이름");
		e_columns.add("성별");
		e_columns.add("생년월일");
		e_columns.add("부서");
		e_columns.add("직급");
		e_columns.add("직원구분");
		e_columns.add("재직관리");

		// 초기 데이터 조회 및 테이블 모델 설정
		try {
			// 메인 사원조회 쿼리
			String sql = " select e.empno, ename, gender, bird, dept, jlevel, wtype "
					+ " from emptable e join jobst j on e.empno = j.empno " + " where estate='재직' "
					+ " order by e.empno ";

			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			// 데이터를 담을 ArrayList 생성
			ArrayList<ArrayList<Object>> e_datas = new ArrayList<>();

			while (rs.next()) {
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename");
				String gender = rs.getString("gender");
				String bird = rs.getString("bird").substring(0, 10);
				String dept = rs.getString("dept");
				String jlevel = rs.getString("jlevel");
				String wtype = rs.getString("wtype");

				// 데이터 값을 저장할 배열 생성
				ArrayList<Object> temp = new ArrayList<>();
				temp.add(empno);
				temp.add(ename);
				temp.add(gender);
				temp.add(bird);
				temp.add(dept);
				temp.add(jlevel);
				temp.add(wtype);
				temp.add(Boolean.FALSE); // 체크박스 값을 위한 초기값

				// 데이터 전체값 일차원 배열로 저장
				e_datas.add(temp);
			}

			// DefaultTableModel에 데이터 삽입을 위해 Vector로 변환
			Vector<Vector<Object>> dataVector = new Vector<>();
			for (ArrayList<Object> row : e_datas) {
				Vector<Object> rowData = new Vector<>(row);
				dataVector.add(rowData);
			}

			// 테이블 모델 설정
			e_tableModel = new DefaultTableModel(dataVector, e_columns) {
				@Override
				public Class<?> getColumnClass(int columnIndex) {
					if (columnIndex == 0) {
						return Integer.class; // 첫 번째 컬럼은 Integer 타입으로 설정
					} else if (columnIndex == getColumnCount() - 1) {
						return Boolean.class; // 마지막 열은 Boolean 타입 (체크박스 사용)
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

		// 테이블 데이터 마우스 클릭시 발생하는 이벤트 설정
		e_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) { // 한 번 클릭 시
					int selectedRow = e_table.getSelectedRow();
					int clickedColumn = e_table.columnAtPoint(e.getPoint()); // 컬럼 인덱스 가져옴
					if (selectedRow != -1 && clickedColumn != 7) { // 유효한 행이 선택된 경우
						int empno = (int) e_table.getValueAt(selectedRow, 0); // 사원번호 가져옴
						EmpInfoDetail emi = new EmpInfoDetail(con, empno); // 데이터 작업할 때 사원번호로 넘겨주기
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
		// 첫번째 열 왼쪽정렬
		e_table.getColumnModel().getColumn(0).setCellRenderer(new LeftAlignRenderer());
		JScrollPane e_scroll = new JScrollPane(e_table);
		e_bottom.add(e_scroll, BorderLayout.CENTER);
	}
	
	private void refreshTableData() {
		e_bottom.removeAll(); // 기존의 테이블을 제거
		e_table.removeAll();
		// 테이블 모델을 다시 초기화
		initializeTable();

		// 새로 초기화한 테이블을 다시 e_bottom에 추가
		e_bottom.add(new JScrollPane(e_table), BorderLayout.CENTER);

		// 패널을 다시 그리도록 호출
		revalidate();
		repaint();
	}

	// 클릭시 이벤트 발생
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == e_dsel) {
			// 퇴사현황
			Quitter qt = new Quitter(con);
		} else if (obj == e_del) {
			// 퇴사 처리 로직
			deleteEmployees();
			refreshTableData();
		} else if (obj == e_ins) {
			// 데이터 입력 버튼 선택시
			try {
				// 입력 창을 만들고 표시하기
				EmpInsert empins = new EmpInsert(con);
				empins.setVisible(true); // 입력 창을 보이게 한다

				// 입력 창이 닫힌 후 실행할 작업 정의
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
		} else if (obj.toString().contains("사원 검색")) {
			// 조회 버튼 선택시
			e_selfield.setEditable(true);
			searchEmployees();
			e_sel.setText("검색 종료");
		} else if (obj.toString().contains("검색 종료")) {
			e_selfield.setEditable(false);
			e_selfield.setText("");
			e_sel.setText("사원 검색");
		}
	}

	// 퇴사 처리 메소드
	private void deleteEmployees() {
		int result = 0;
		// 테이블에서 체크된 항목을 확인하여 퇴사 처리
		for (int i = 0; i < e_table.getRowCount(); i++) {
			int isempno = (int) e_table.getValueAt(i, 0);
			boolean selected = (boolean) e_table.getValueAt(i, 7);
			if (selected) {
				try {
					// 퇴사처리 쿼리
					String sql = " update jobst " + " set estate='퇴사', quitdate = trunc(sysdate) "
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
			JOptionPane.showMessageDialog(null, "퇴사 처리되었습니다.");
		}
	}

	// 사원 검색 메소드
	private void searchEmployees() {
		// 검색필드 초기화
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

				// 검색어가 없으면 모든 행을 보여준다
				if (searchText.isEmpty()) {
					sorter.setRowFilter(null);
					return;
				}

				try {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
				} catch (PatternSyntaxException ex) {
					System.err.println("검색어 오류: " + ex.getMessage());
					sorter.setRowFilter(null);
				}
			}
		});
	}

	// 사용자 지정 셀 렌더러 (왼쪽 정렬)
	private static class LeftAlignRenderer extends DefaultTableCellRenderer {
		public LeftAlignRenderer() {
			setHorizontalAlignment(SwingConstants.LEFT);
			validate();
		}
	}
}