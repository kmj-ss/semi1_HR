package smanageT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NoticeBoardApp extends JPanel implements MouseListener {
    JFrame parentFrame;
    JPanel mainPanel;
    CardLayout cardLayout;

    public NoticeBoardApp(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 메인 패널, 카드 레이아웃 설정
        mainPanel = new JPanel(new CardLayout());
        cardLayout = (CardLayout) mainPanel.getLayout();
        add(mainPanel, BorderLayout.CENTER);

        // 공지사항 목록 패널 생성
        JPanel noticeListPanel = createNoticeListPanel();
        mainPanel.add(noticeListPanel, "NoticeList");

        cardLayout.show(mainPanel, "NoticeList");
    }

    JPanel createNoticeListPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 공지사항 타이틀 및 버튼 패널
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("공지사항");
        titlePanel.add(titleLabel, BorderLayout.WEST);

        // 상단 타이틀패널에 버튼추가
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("게시글 작성");
        JButton deleteButton = new JButton("게시글 삭제");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        titlePanel.add(buttonPanel, BorderLayout.EAST);

        // 공지사항 목록 패널
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        // 예제 공지사항 데이터
        String[][] notices = {
                {"1", "1번 게시글", "작성자1", "2024-06-17"},
                {"2", "2번 게시글", "작성자2", "2024-06-17"},
                {"3", "3번 게시글", "작성자3", "2024-06-17"}
        };

        // 공지사항 리스트들 표출
        for (String[] notice : notices) {
            JPanel noticePanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.weightx = 0.1;
            JLabel noticeNumber = new JLabel(notice[0]);
            noticePanel.add(noticeNumber, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.6;
            JLabel noticeTitle = new JLabel(notice[1]);
            noticePanel.add(noticeTitle, gbc);

            gbc.gridx = 2;
            gbc.weightx = 0.2;
            JLabel noticeAuthor = new JLabel(notice[2]);
            noticePanel.add(noticeAuthor, gbc);

            gbc.gridx = 3;
            gbc.weightx = 0.1;
            JLabel noticeDate = new JLabel(notice[3]);
            noticePanel.add(noticeDate, gbc);

            noticePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            noticePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            noticePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            noticePanel.setName(notice[0]);
            noticePanel.addMouseListener(this);

            listPanel.add(noticePanel);
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);

        // 타이틀 및 버튼 패널과 공지사항 목록 패널을 하나의 큰 패널에 추가
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void showNoticeContent(String title, String content) {
        JPanel noticeContentPanel = new JPanel(new BorderLayout());
        noticeContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title);
        noticeContentPanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea contentArea = new JTextArea(content);
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(contentArea);
        noticeContentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("뒤로가기");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "NoticeList"));
        JButton editButton = new JButton("수정");
        JButton deleteButton = new JButton("삭제");
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        noticeContentPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(noticeContentPanel, "NoticeContent");
        cardLayout.show(mainPanel, "NoticeContent");
    }

    String fetchNoticeContent(String noticeNumber) {
        // 공지사항 번호로 해당 공지사항의 내용을 DB에서 가져오는 메소드
        // 예시로 텍스트 리턴하게 만듬
        if ("1".equals(noticeNumber)) {
            return "1번 게시글 내용";
        } else if ("2".equals(noticeNumber)) {
            return "2번 게시글 내용";
        } else if ("3".equals(noticeNumber)) {
            return "3번 게시글 내용";
        } else {
            return null;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JPanel panel = (JPanel) e.getSource();
        String noticeNumber = panel.getName();
        String noticeContent = fetchNoticeContent(noticeNumber);
        if (noticeContent != null) {
            showNoticeContent("공지사항 " + noticeNumber, noticeContent);
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}