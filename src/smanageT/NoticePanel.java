//package smanageT;
//
//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;
//
//
//public class NoticePanel extends JPanel implements ActionListener{
//
//	public NoticePanel() {
//		// TODO Auto-generated constructor stub
//		JPanel panel = new JPanel(new BorderLayout());
//        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//        // 공지사항 목록
//        JTextArea noticeListArea = new JTextArea();
//        noticeListArea.setEditable(false);	
//        noticeListArea.setText("1. [2024-06-17] 첫 번째 공지사항\n"
//                + "2. [2024-06-18] 두 번째 공지사항\n"
//                + "3. [2024-06-19] 세 번째 공지사항");
//        
//        JScrollPane scrollPane = new JScrollPane(noticeListArea);
//        panel.add(scrollPane, BorderLayout.CENTER);
//        
//        noticeListArea.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                JTextArea area = (JTextArea) evt.getSource();
//                int offset = area.viewToModel(evt.getPoint());
//                try {
//                    int start = javax.swing.text.Utilities.getRowStart(area, offset);
//                    int end = javax.swing.text.Utilities.getRowEnd(area, offset);
//                    String line = area.getText(start, end - start).trim();
//                    String[] parts = line.split("\\. ", 2); // Split by ". "
//                    if (parts.length > 1) {
//                        String noticeContent = fetchNoticeContent(parts[0]);
//                        if (noticeContent != null) {
//                            showNoticeDialog(parentFrame, parts[1], noticeContent);
//                        }
//                    }
//                } catch (javax.swing.text.BadLocationException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//	}
//	
//    private static String fetchNoticeContent(String noticeNumber) {
//        // 공지사항 번호를 기반으로 해당 공지사항의 내용을 DB에서 가져오는 메서드 (예: SQL 쿼리 실행)
//        // 여기서는 예시로 간단하게 반환
//        if ("1".equals(noticeNumber)) {
//            return "첫 번째 공지사항의 내용입니다.";
//        } else if ("2".equals(noticeNumber)) {
//            return "두 번째 공지사항의 내용입니다.";
//        } else if ("3".equals(noticeNumber)) {
//            return "세 번째 공지사항의 내용입니다.";
//        } else {
//            return null;
//        }
//    }
//    
//    private static void showNoticeDialog(JFrame parentFrame, String title, String content) {
//        JOptionPane.showMessageDialog(parentFrame, content, title, JOptionPane.INFORMATION_MESSAGE);
//    }
//	
//}
