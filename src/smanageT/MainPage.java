package smanageT;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class MainPage extends JFrame implements ActionListener {
    JButton mainPage;
    JButton empBtn;
    JButton scdBtn;
    JButton pyrBtn;
    JButton atdBtn;
    JButton ntcBtn;
    JPanel contentAdd;
    Connection con;

    JButton createNavButton() {
        JButton button = new JButton();
        // button.setUI(new BasicButtonUI());
        button.setPreferredSize(new Dimension(80, 80));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(new Color(167, 207, 233));
        button.setForeground(new Color(226, 238, 247));
        return button;
    }

    public MainPage() {
        this.setTitle("사원 관리 프로그램");
        this.setLayout(new BorderLayout());
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(5, 1));

        empBtn = createNavButton();
        empBtn.setIcon(new ImageIcon("src/image/usr.png"));
        scdBtn = createNavButton();
        scdBtn.setIcon(new ImageIcon("src/image/scd.png"));
        pyrBtn = createNavButton();
        pyrBtn.setIcon(new ImageIcon("src/image/pyr.png"));
        atdBtn = createNavButton();
        atdBtn.setIcon(new ImageIcon("src/image/atd.png"));
        ntcBtn = createNavButton();
        ntcBtn.setIcon(new ImageIcon("src/image/ntc.png"));

        empBtn.addActionListener(this);
        ntcBtn.addActionListener(this);
        atdBtn.addActionListener(this);
        pyrBtn.addActionListener(this);
        scdBtn.addActionListener(this);

        navPanel.add(empBtn);
        navPanel.add(scdBtn);
        navPanel.add(pyrBtn);
        navPanel.add(atdBtn);
        navPanel.add(ntcBtn);

        this.add(navPanel, BorderLayout.WEST);

        JPanel navP = new JPanel();
        navP.setLayout(new BorderLayout());
        navP.setPreferredSize(new Dimension(1200, 80));
        navP.setBackground(new Color(95, 155, 210));
        mainPage = new JButton("Home");
        mainPage.setFont(new Font(mainPage.getName(), Font.BOLD, 20));
        mainPage.setForeground(new Color(35, 56, 90));
        mainPage.setBackground(new Color(95, 155, 210));
        mainPage.setPreferredSize(new Dimension(100, 50));
        mainPage.setBorderPainted(false);
        mainPage.setFocusPainted(false);
        mainPage.addActionListener(this);
        navP.add(mainPage, BorderLayout.WEST);

        this.add(navP, BorderLayout.NORTH);

        contentAdd = new JPanel(new BorderLayout());
        contentAdd.setBackground(new Color(239, 246, 255));

        // 데이터베이스 연결 설정
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "semihr", "1234");
            //ln("DB 연결 성공");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            //ln("DB 연결 실패");
        }

        // EmployeeStatisticsPanel 생성 및 추가
        EmpGraph empPanel = new EmpGraph(con);
        contentAdd.add(empPanel, BorderLayout.CENTER);

        this.add(contentAdd, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if (obj == ntcBtn) {
            btnAct();
            ntcBtn.setBackground(new Color(126, 181, 220));
            contentAdd.removeAll();
            NoticeBoard nb = new NoticeBoard(this, con);
            contentAdd.add(nb);
            add(contentAdd, BorderLayout.CENTER);
            validate();
        } else if (obj == atdBtn) {
            btnAct();
            atdBtn.setBackground(new Color(126, 181, 220));
            contentAdd.removeAll();
            Request r = new Request(con);
            contentAdd.add(r);
            add(contentAdd, BorderLayout.CENTER);
            validate();
        } else if (obj == empBtn) {
            btnAct();
            empBtn.setBackground(new Color(126, 181, 220));
            contentAdd.removeAll();
            EmployeeInfo emp = new EmployeeInfo(con);
            contentAdd.add(emp);
            add(contentAdd, BorderLayout.CENTER);
            validate();
        } else if (obj == pyrBtn) {
            btnAct();
            pyrBtn.setBackground(new Color(126, 181, 220));
            contentAdd.removeAll();
            PayTest pt = new PayTest(con);
            contentAdd.add(pt);
            add(contentAdd, BorderLayout.CENTER);
            validate();
        } else if (obj == scdBtn) {
            btnAct();
            scdBtn.setBackground(new Color(126, 181, 220));
            contentAdd.removeAll();
            ScadulePan s = new ScadulePan(con);
            contentAdd.add(s);
            add(contentAdd, BorderLayout.CENTER);
            validate();
        } else if (obj == mainPage) {
            btnAct();
            contentAdd.removeAll();
            EmpGraph empPanel = new EmpGraph(con);
            contentAdd.add(empPanel);
            validate();
        }
    }

    void btnAct() { // 버튼색 초기화
        empBtn.setBackground(new Color(167, 207, 233));
        scdBtn.setBackground(new Color(167, 207, 233));
        pyrBtn.setBackground(new Color(167, 207, 233));
        atdBtn.setBackground(new Color(167, 207, 233));
        ntcBtn.setBackground(new Color(167, 207, 233));
    }

    public static void main(String[] args) {
        String fontFilePath = "/Nanum/NanumSquareB.ttf";

        // 폰트 로드
        Font customFont = FontInfo.loadFont(fontFilePath, 1, 13);
        if (customFont != null) {
            // 폰트가 성공적으로 로드되었을 경우에만 폰트 설정
            FontInfo.setUIFont(customFont);
        } else {
            // 폰트 로드 실패 시 기본 폰트 사용
            JOptionPane.showMessageDialog(null, "폰트 불러오기 실패");
        }

        MainPage mp = new MainPage();
        mp.setSize(1200, 700);
        mp.setLocationRelativeTo(null);
        mp.setVisible(true);

        mp.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(null,"프로그램을 종료합니다.","확인",JOptionPane.INFORMATION_MESSAGE);
                try {
                    if (mp.con != null && ! mp.con.isClosed()) {
                        mp.con.close();
                        //ln("DB 연결 종료");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                System.exit(0); // 프로그램 종료
            }
        });
    }
}
