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

public class EmployeeStatisticsPanel extends JPanel {

    private Connection con; // 데이터베이스 연결 객체

    public EmployeeStatisticsPanel(Connection conn) {
        this.con = conn; // 전달받은 연결 객체를 멤버 변수에 할당
        setLayout(new GridLayout(1, 2)); // 그리드 레이아웃으로 패널을 나누기

        // 왼쪽 패널: 그래프 패널
        JPanel graphPanel = createGraphPanel();
        add(graphPanel);

        // 오른쪽 패널: 텍스트 패널
        JPanel textPanel = createTextPanel();
        add(textPanel);
    }

    // 그래프 패널 생성
    private JPanel createGraphPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // 현재 연도와 월 구하기
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        // 데이터셋 생성
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String[] months = {"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"};

        // 쿼리 실행 (EMPSTAT 테이블에서 데이터 조회)
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            for (int month = 1; month <= 12; month++) {
                if (month <= currentMonth) {
                    String query = "SELECT COUNT(*) AS EMP_COUNT " +
                                   "FROM EMPSTAT " +
                                   "WHERE TO_CHAR(HIREDATE, 'YYYYMM') <= ? " +
                                   "AND (TO_CHAR(QUITDATE, 'YYYYMM') > ? OR QUITDATE IS NULL)";

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
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from database.");
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        // 폰트 로드
        Font font = FontInfo.loadFont("/Nanum/NanumSquareB.ttf", 0, 13);

        // 차트 생성
        JFreeChart chart = ChartFactory.createBarChart(
                "2024년 사원수 추이",  // 그래프 제목
                "월",               // x축 레이블
                "사원 수",          // y축 레이블
                dataset,            // 데이터셋
                PlotOrientation.VERTICAL, // 그래프 방향
                true,               // 범례 포함 여부
                true,               // 툴팁 포함 여부
                false               // URL 링크 포함 여부
        );
        

        //폰트설정
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

    // 텍스트 패널 생성
    private JPanel createTextPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 상단 패널: 신규 입사자 정보
        JPanel newHiresPanel = new JPanel(new BorderLayout());
        newHiresPanel.setBorder(BorderFactory.createTitledBorder(getCurrentMonthString() + " 신규 입사자"));

        JTextArea newHiresArea = new JTextArea();
        newHiresArea.setEditable(false);
        JScrollPane newHiresScrollPane = new JScrollPane(newHiresArea);
        newHiresPanel.add(newHiresScrollPane, BorderLayout.CENTER);

        // 하단 패널: 퇴사자 정보
        JPanel resignationsPanel = new JPanel(new BorderLayout());
        resignationsPanel.setBorder(BorderFactory.createTitledBorder(getCurrentMonthString() + " 퇴사자"));

        JTextArea resignationsArea = new JTextArea();
        resignationsArea.setEditable(false);
        JScrollPane resignationsScrollPane = new JScrollPane(resignationsArea);
        resignationsPanel.add(resignationsScrollPane, BorderLayout.CENTER);

        // 현재 월 계산
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        // 데이터베이스 연결 및 쿼리 실행
        PreparedStatement hirePs = null;
        ResultSet hireRs = null;
        PreparedStatement quitPs = null;
        ResultSet quitRs = null;
        try {
            // 현재 월의 신규 입사자 정보 쿼리
            String hireQuery = "SELECT ENAME, HIREDATE " +
                               "FROM EMPSTAT " +
                               "WHERE TO_CHAR(HIREDATE, 'YYYYMM') = ?";

            hirePs = con.prepareStatement(hireQuery); // con 변수를 사용하여 PreparedStatement 생성
            hirePs.setString(1, getCurrentYearMonth());
            hireRs = hirePs.executeQuery();

            // 현재 월의 퇴사자 정보 쿼리
            String quitQuery = "SELECT ENAME, QUITDATE " +
                               "FROM EMPSTAT " +
                               "WHERE TO_CHAR(QUITDATE, 'YYYYMM') = ?";

            quitPs = con.prepareStatement(quitQuery); // con 변수를 사용하여 PreparedStatement 생성
            quitPs.setString(1, getCurrentYearMonth());
            quitRs = quitPs.executeQuery();

            // 신규 입사자 정보 추가
            while (hireRs.next()) {
                String name = hireRs.getString("ENAME");
                Date hireDate = hireRs.getDate("HIREDATE");
                newHiresArea.append(name + " - " + hireDate + "\n");
            }

            // 퇴사자 정보 추가
            while (quitRs.next()) {
                String name = quitRs.getString("ENAME");
                Date quitDate = quitRs.getDate("QUITDATE");
                resignationsArea.append(name + " - " + quitDate + "\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from database.");
        } finally {
            try { if (hireRs != null) hireRs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (hirePs != null) hirePs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (quitRs != null) quitRs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (quitPs != null) quitPs.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        panel.add(newHiresPanel);
        panel.add(resignationsPanel);

        return panel;
    }

    // 현재 연도와 월을 문자열로 반환하는 메서드
    private String getCurrentYearMonth() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        return String.valueOf(currentYear) + String.format("%02d", currentMonth);
    }

    // 현재 월을 문자열로 반환하는 메서드
    private String getCurrentMonthString() {
        String[] months = {"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"};
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        return months[currentMonth];
    }
}
