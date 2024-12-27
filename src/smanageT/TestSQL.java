package smanageT;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class TestSQL extends JFrame{
	public TestSQL(Connection con) {
		try {
			JTextArea j = new JTextArea();
			String sql = "SELECT CONTENT FROM NOTICE WHERE NTID = 1";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			System.out.println("¿Ö¾ÈµÊ");
			while (rs.next()) {
				String n = rs.getString(1);
				j.setText(n);
				add(j);
			}
			add(j);
			JButton jb = new JButton("µî·Ï");
			jb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String sql = "update notice set content = '?' where ntid = 2";
					System.out.println(j.getText());
					try {
						PreparedStatement ps1 = con.prepareStatement(sql);
						ps1.setString(1, j.getText());
						int i = ps1.executeUpdate();
						System.out.println(i+"µÊ?");
						System.out.println(j.getText());
						ps1.close();
						con.close();
				} catch (Exception e1) {
					}
				}
			});
			add(jb,BorderLayout.SOUTH);
			ps.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String usr = "scott";
			String pwd = "1234";
			Connection con = DriverManager.getConnection(url, usr, pwd);
			System.out.println("¿¬µ¿¼º°ø");
			TestSQL t = new TestSQL(con);
			
			
			t.setSize(300,300);
			t.setVisible(true);
			
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
