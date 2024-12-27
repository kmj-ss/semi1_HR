package smanageT;
import java.awt.Component;

import javax.swing.*;

public class tex extends JFrame{
	public static void main(String[] args) {
		JButton okButton = new JButton("확인");
		okButton.setFocusPainted(false); // 포커스 표시 비활성화

		JButton cancelButton = new JButton("취소");
		cancelButton.setFocusPainted(false); // 포커스 표시 비활성화

		Object[] options = {okButton, cancelButton};
		int choice = JOptionPane.showOptionDialog(null, "메시지", "제목", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
	}
}
