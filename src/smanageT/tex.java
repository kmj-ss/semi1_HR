package smanageT;
import java.awt.Component;

import javax.swing.*;

public class tex extends JFrame{
	public static void main(String[] args) {
		JButton okButton = new JButton("Ȯ��");
		okButton.setFocusPainted(false); // ��Ŀ�� ǥ�� ��Ȱ��ȭ

		JButton cancelButton = new JButton("���");
		cancelButton.setFocusPainted(false); // ��Ŀ�� ǥ�� ��Ȱ��ȭ

		Object[] options = {okButton, cancelButton};
		int choice = JOptionPane.showOptionDialog(null, "�޽���", "����", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
	}
}
