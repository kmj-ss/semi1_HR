package smanageT;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class DurationPanel extends JPanel implements ItemListener {

	Calendar cal;
	JComboBox<Integer> jcb_year, jcb_month, jcb_date;
	int year;
	int month;
	int date;

	public DurationPanel() {

//		UIManager.put("ComboBoxUI", "com.alee.laf.combobox.WebComboBoxUI");
		cal = Calendar.getInstance();

		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		date = cal.get(Calendar.DATE);


		jcb_year = new JComboBox();
		jcb_year.setUI(new Combotest());
		jcb_year.setBackground(Color.white);
		jcb_year.setPreferredSize(new Dimension(60, 20));
		jcb_month = new JComboBox();	
		jcb_month.setUI(new Combotest());
		jcb_month.setBackground(Color.white);
		jcb_month.setPreferredSize(new Dimension(40, 20));

		for (int i = year - 50; i < year + 1; i++) {
			jcb_year.addItem(i);
		}

		for (int i = 1; i < 13; i++) {
			jcb_month.addItem(i);
		}

		jcb_date = new JComboBox();
		jcb_date.setUI(new Combotest());
		jcb_date.setBackground(Color.white);
		jcb_date.setPreferredSize(new Dimension(50, 20));

		getMaxDay();

		this.add(jcb_year);
		this.add(jcb_month);
		this.add(jcb_date);

		setFirstDuration();

		jcb_year.addItemListener(this);
		jcb_month.addItemListener(this);

	}

	private void getMaxDay() {

		jcb_date.removeAllItems();

		for (int i = 0; i < cal.getActualMaximum(Calendar.DATE); i++) {

			jcb_date.addItem(i + 1);

		}
//		jcb_date.setSelectedItem(1);

	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		if (e.getStateChange() == 1) {
			cal.set(Calendar.YEAR, (int) jcb_year.getSelectedItem());
			cal.set(Calendar.MONTH, (int) jcb_month.getSelectedItem() - 1);
			
			getMaxDay();
			
			
			
		}

	}

	public int getYear() {
		return (int) jcb_year.getSelectedItem();
	}

	public int getMonth() {
		return (int) jcb_month.getSelectedItem();
	}

	public int getDate() {
//		return (int)jcb_date.getSelectedItem();
		return jcb_date.getSelectedItem() == null ? 1 : (int) jcb_date.getSelectedItem();

	}

	public void setFirstDuration() {
		jcb_year.setSelectedIndex(jcb_year.getItemCount() - 1);
		jcb_month.setSelectedItem(month);
		jcb_date.setSelectedItem(date);

	}

	public void setSecondDuration() {
		jcb_year.setSelectedIndex(jcb_year.getItemCount() - 1);
		jcb_month.setSelectedItem(month);
		jcb_date.setSelectedItem(cal.getActualMaximum(Calendar.DATE));
	}
	
	public void setUpdateDuration(String year, String month, String date) {
		
		jcb_year.setSelectedItem(Integer.parseInt(year));
		jcb_month.setSelectedItem(Integer.parseInt(month));
		jcb_date.setSelectedItem(Integer.parseInt(date));

		
	}

}


