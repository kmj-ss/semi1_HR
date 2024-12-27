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

    private Connection con; // �����ͺ��̽� ���� ��ü

    public EmployeeStatisticsPanel(Connection conn) {
        this.con = conn; // ���޹��� ���� ��ü�� ��� ������ �Ҵ�
        setLayout(new GridLayout(1, 2)); // �׸��� ���̾ƿ����� �г��� ������

        // ���� �г�: �׷��� �г�
        JPanel graphPanel = createGraphPanel();
        add(graphPanel);

        // ������ �г�: �ؽ�Ʈ �г�
        JPanel textPanel = createTextPanel();
        add(textPanel);
    }

    // �׷��� �г� ����
    private JPanel createGraphPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // ���� ������ �� ���ϱ�
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        // �����ͼ� ����
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String[] months = {"1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��", "9��", "10��", "11��", "12��"};

        // ���� ���� (EMPSTAT ���̺����� ������ ��ȸ)
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            for (int month = 1; month <= 12; month++) {
                if (month <= currentMonth) {
                    String query = "SELECT COUNT(*) AS EMP_COUNT " +
                                   "FROM EMPSTAT " +
                                   "WHERE TO_CHAR(HIREDATE, 'YYYYMM') <= ? " +
                                   "AND (TO_CHAR(QUITDATE, 'YYYYMM') > ? OR QUITDATE IS NULL)";

                    ps = con.prepareStatement(query); // con ������ ����Ͽ� PreparedStatement ����
                    ps.setString(1, String.valueOf(currentYear) + String.format("%02d", month));
                    ps.setString(2, String.valueOf(currentYear) + String.format("%02d", month));
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        int employeeCount = rs.getInt("EMP_COUNT");
                        dataset.addValue(employeeCount, "��� ��", months[month - 1]);
                    } else {
                        dataset.addValue(0, "��� ��", months[month - 1]);
                    }
                } else {
                    dataset.addValue(null, "��� ��", months[month - 1]);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from database.");
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        // ��Ʈ �ε�
        Font font = FontInfo.loadFont("/Nanum/NanumSquareB.ttf", 0, 13);

        // ��Ʈ ����
        JFreeChart chart = ChartFactory.createBarChart(
                "2024�� ����� ����",  // �׷��� ����
                "��",               // x�� ���̺�
                "��� ��",          // y�� ���̺�
                dataset,            // �����ͼ�
                PlotOrientation.VERTICAL, // �׷��� ����
                true,               // ���� ���� ����
                true,               // ���� ���� ����
                false               // URL ��ũ ���� ����
        );
        

        //��Ʈ����
        chart.getTitle().setFont(font);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.getDomainAxis().setLabelFont(font);
        plot.getDomainAxis().setTickLabelFont(font);
        plot.getRangeAxis().setLabelFont(font);
        plot.getRangeAxis().setTickLabelFont(font);
        chart.getLegend().setItemFont(font);
        
        //�׷��� ���� barrender�� ����
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(74,84,196));
        renderer.setBarPainter(new StandardBarPainter());//������ ��������
        if (chart.getLegend() != null) {
            chart.getLegend().setItemFont(font);
        }
        //�Ҽ��� ���ְ� y�౸�м� ����
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        yAxis.setTickMarksVisible(false);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }

    // �ؽ�Ʈ �г� ����
    private JPanel createTextPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ��� �г�: �ű� �Ի��� ����
        JPanel newHiresPanel = new JPanel(new BorderLayout());
        newHiresPanel.setBorder(BorderFactory.createTitledBorder(getCurrentMonthString() + " �ű� �Ի���"));

        JTextArea newHiresArea = new JTextArea();
        newHiresArea.setEditable(false);
        JScrollPane newHiresScrollPane = new JScrollPane(newHiresArea);
        newHiresPanel.add(newHiresScrollPane, BorderLayout.CENTER);

        // �ϴ� �г�: ����� ����
        JPanel resignationsPanel = new JPanel(new BorderLayout());
        resignationsPanel.setBorder(BorderFactory.createTitledBorder(getCurrentMonthString() + " �����"));

        JTextArea resignationsArea = new JTextArea();
        resignationsArea.setEditable(false);
        JScrollPane resignationsScrollPane = new JScrollPane(resignationsArea);
        resignationsPanel.add(resignationsScrollPane, BorderLayout.CENTER);

        // ���� �� ���
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        // �����ͺ��̽� ���� �� ���� ����
        PreparedStatement hirePs = null;
        ResultSet hireRs = null;
        PreparedStatement quitPs = null;
        ResultSet quitRs = null;
        try {
            // ���� ���� �ű� �Ի��� ���� ����
            String hireQuery = "SELECT ENAME, HIREDATE " +
                               "FROM EMPSTAT " +
                               "WHERE TO_CHAR(HIREDATE, 'YYYYMM') = ?";

            hirePs = con.prepareStatement(hireQuery); // con ������ ����Ͽ� PreparedStatement ����
            hirePs.setString(1, getCurrentYearMonth());
            hireRs = hirePs.executeQuery();

            // ���� ���� ����� ���� ����
            String quitQuery = "SELECT ENAME, QUITDATE " +
                               "FROM EMPSTAT " +
                               "WHERE TO_CHAR(QUITDATE, 'YYYYMM') = ?";

            quitPs = con.prepareStatement(quitQuery); // con ������ ����Ͽ� PreparedStatement ����
            quitPs.setString(1, getCurrentYearMonth());
            quitRs = quitPs.executeQuery();

            // �ű� �Ի��� ���� �߰�
            while (hireRs.next()) {
                String name = hireRs.getString("ENAME");
                Date hireDate = hireRs.getDate("HIREDATE");
                newHiresArea.append(name + " - " + hireDate + "\n");
            }

            // ����� ���� �߰�
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

    // ���� ������ ���� ���ڿ��� ��ȯ�ϴ� �޼���
    private String getCurrentYearMonth() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        return String.valueOf(currentYear) + String.format("%02d", currentMonth);
    }

    // ���� ���� ���ڿ��� ��ȯ�ϴ� �޼���
    private String getCurrentMonthString() {
        String[] months = {"1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��", "9��", "10��", "11��", "12��"};
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        return months[currentMonth];
    }
}