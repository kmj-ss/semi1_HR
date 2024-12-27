package smanageT;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Calendar;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.*;

public class EmpGraph extends JPanel {

    private Connection con; // 데이터베이스 연결 객체

    public EmpGraph(Connection conn) {
        this.con = conn;
        setLayout(new GridLayout(1, 2));

        //그래프 패널
        JPanel graphPanel = createGraphPanel();
        add(graphPanel);

        //입/퇴사자패널
        JPanel textPanel = createTextPanel();
        add(textPanel);
    }

    // 그래프 패널 생성
    private JPanel createGraphPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        //지금 년도 월 받아옴(Calender클래스)
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String[] months = {"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"};

        //jobst 테이블에서 데이터 조회
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            for (int month = 1; month <= 12; month++) {
                if (month <= currentMonth) {
                    String query = "SELECT COUNT(*) AS EMP_COUNT " +
                                   "FROM jobst j, emptable e " +
                                   "WHERE TO_CHAR(j.HIREDATE, 'YYYYMM') <= ? " +
                                   "AND (TO_CHAR(j.QUITDATE, 'YYYYMM') > ? OR j.QUITDATE IS NULL) " +
                                   "AND j.EMPNO = e.EMPNO";

                    ps = con.prepareStatement(query); // con 변수를 사용하여 PreparedStatement 생성
                    ps.setString(1, String.valueOf(currentYear) + String.format("%02d", month));
                    ps.setString(2, String.valueOf(currentYear) + String.format("%02d", month));
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        int employeeCount = rs.getInt("EMP_COUNT");
                        dataset.addValue(employeeCount, "사원 수", months[month - 1]);
                    } else {
                        dataset.addValue(0, "사원 수", months[month - 1]);
                    }
                } else {
                    dataset.addValue(null, "사원 수", months[month - 1]);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "오류남 ㅅㄱ");
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        
        Font font = FontInfo.loadFont("/Nanum/NanumSquareB.ttf", 0, 13);
        
        JFreeChart chart = ChartFactory.createBarChart(
                "2024년 사원수 추이",  // 그래프 제목
                "월",               // x축 레이블
                "사원 수",          // y축 레이블
                dataset,            // 데이터셋
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        

        //폰트설정하고 차트뒷면 흰색으로 함.
        chart.getTitle().setFont(font);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.getDomainAxis().setLabelFont(font);
        plot.getDomainAxis().setTickLabelFont(font);
        plot.getRangeAxis().setLabelFont(font);
        plot.getRangeAxis().setTickLabelFont(font);
        chart.getLegend().setItemFont(font);
        
        //그래프 색상 barrender로 설정
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(74,84,196));
        renderer.setBarPainter(new StandardBarPainter());//ㅈ같은 유광제거
        if (chart.getLegend() != null) {
            chart.getLegend().setItemFont(font);
        }
        //소수점 없애고 y축구분선 제거
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        yAxis.setTickMarksVisible(false);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }

    //텍스트 패널 생성
    private JPanel createTextPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        //상단 패널, 신규 입사자 정보
        JPanel newHiresPanel = new JPanel(new BorderLayout());
        newHiresPanel.setBorder(BorderFactory.createTitledBorder(getCurrentMonthString() + " 신규 입사자"));
        newHiresPanel.setBackground(Color.WHITE);
        JTextArea newHiresArea = new JTextArea();
        newHiresArea.setEditable(false);
        JScrollPane newHiresScrollPane = new JScrollPane(newHiresArea);
        newHiresScrollPane.setBackground(new Color(240, 255, 255));
        newHiresPanel.add(newHiresScrollPane, BorderLayout.CENTER);
     

        // 하단 패널: 퇴사자 정보
        JPanel resignationsPanel = new JPanel(new BorderLayout());
        resignationsPanel.setBackground(Color.WHITE);
        resignationsPanel.setBorder(BorderFactory.createTitledBorder(getCurrentMonthString() + " 퇴사자"));

        JTextArea resignationsArea = new JTextArea();
        resignationsArea.setEditable(false);
        JScrollPane resignationsScrollPane = new JScrollPane(resignationsArea);
        resignationsScrollPane.setBackground(Color.WHITE);
        resignationsPanel.add(resignationsScrollPane, BorderLayout.CENTER);

        //현재 월 구함
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        // 데이터베이스 연결 및 쿼리 실행
        PreparedStatement hirePs = null;
        ResultSet hireRs = null;
        PreparedStatement quitPs = null;
        ResultSet quitRs = null;
        try {
            // 현재 월의 신규 입사자 정보 쿼리
            String hireQuery = "SELECT e.ENAME, j.HIREDATE " +
                               "FROM jobst j, emptable e " +
                               "WHERE TO_CHAR(j.HIREDATE, 'YYYYMM') = ? " +
                               "AND j.EMPNO = e.EMPNO";

            hirePs = con.prepareStatement(hireQuery);
            hirePs.setString(1, getCurrentYearMonth());
            hireRs = hirePs.executeQuery();

            // 현재 월의 퇴사자 정보 쿼리
            String quitQuery = "SELECT e.ENAME, j.QUITDATE " +
                               "FROM jobst j, emptable e " +
                               "WHERE TO_CHAR(j.QUITDATE, 'YYYYMM') = ? " +
                               "AND j.EMPNO = e.EMPNO";

            quitPs = con.prepareStatement(quitQuery);
            quitPs.setString(1, getCurrentYearMonth());
            quitRs = quitPs.executeQuery();

            // 신규 입사자 정보 추가
            while (hireRs.next()) {
                String name = hireRs.getString("ENAME");
                Date hireDate = hireRs.getDate("HIREDATE");
                newHiresArea.append(name + "님이 " + hireDate +"에 입사했습니다 짝짝짝 \n");
            }

            //퇴사자 정보 추가
            while (quitRs.next()) {
                String name = quitRs.getString("ENAME");
                Date quitDate = quitRs.getDate("QUITDATE");
                resignationsArea.append(name + "님이 " + quitDate + "에 퇴사하셨습니다 \n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "db오류");
		} finally {
			try {
				if (hireRs != null)
					hireRs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (hirePs != null)
					hirePs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (quitRs != null)
					quitRs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (quitPs != null)
					quitPs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

        panel.add(newHiresPanel);
        panel.add(resignationsPanel);

        return panel;
    }

    //지금 년도, 월 구하기
    private String getCurrentYearMonth() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        return String.valueOf(currentYear) + String.format("%02d", currentMonth);
    }

    //지금 몇월인지 반환
    private String getCurrentMonthString() {
        String[] months = {"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"};
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        return months[currentMonth];
    }
}