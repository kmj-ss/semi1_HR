package smanageT;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;

import javax.swing.JPanel;

public class ScadulePan extends JPanel{

	Connection con;
	public ScadulePan(Connection con) {
		this.con = con;
		
		this.setLayout(new BorderLayout());
		Schedule s = new Schedule(con);
		ViewSchedule v = new ViewSchedule(con);
		v.setPreferredSize(new Dimension(400,600));
		s.setPreferredSize(new Dimension(700,600));
		add(s,BorderLayout.CENTER);
		add(v,BorderLayout.EAST);
	}
}
