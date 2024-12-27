package smanageT;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Attendance extends JPanel implements ActionListener{

	JLabel title;
	JPanel top,center,col_title,jp_list;
	Button bt_set;
	DurationPanel dp_begin,dp_end;
	JScrollPane jsp;
	
	JTable table;
	DefaultTableModel model;
	Vector <String> col_name;

	

	public Attendance() {

		//top
		this.setLayout(new BorderLayout(10, 10));
		top = new JPanel(new GridLayout(2, 2));
		this.add(top, "North");

		title = new JLabel("���� ����");
//		title.setFont(new Font(getName(), 0, 30));
		top.add(title);
		
		
		JPanel p_inputDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
		top.add(new Label(" "));
		top.add(p_inputDate);
		
		dp_begin = new DurationPanel();
		dp_end = new DurationPanel();
		bt_set = new Button("����");
		
		p_inputDate.add(dp_begin);
		p_inputDate.add(dp_end);
		p_inputDate.add(bt_set);
		

		

		//center
		center = new JPanel(new BorderLayout());
		col_title = new JPanel(new GridLayout(1,5));
		this.add(center);
		//center.add(col_title,"North");
		
		col_name = new Vector<String>();
		
		col_name.add("�����ȣ");
		col_name.add("�����");
		col_name.add("��ٽð�");
		col_name.add("��ٽð�");
		col_name.add("����");
		col_name.add("����");
		col_name.add("����");
		
		model = new DefaultTableModel(col_name, 0){
			public boolean isCellEditable(int r, int c){
				return (c!=0) ? true : false;
			}
		};
		
		table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table);
		center.add(scroll,"North");
		
		ArrayList <String>list = new ArrayList<String>();
		list.add("0001,ȫ�浿,09:00,,���,,");
		list.add("0002,�Ѹ�,09:05,,����,,");
		
		for(String data:list){
			Vector<String> v= new Vector<String>();
			
			
			
//			v.add(data.getId());
//			v.add(data.getName());
//			v.add(data.getPwd());
//			v.add(data.getTel());
			model.addRow(data.split(","));


		}
		
		System.out.println(model.getColumnCount());
		
		
//		jp_list = new JPanel(new GridLayout(0,5));
//		center.add(jp_list,"Center");
		
		
		
		
		//�̺�Ʈ
		bt_set.addActionListener(this);


	}



	@Override
	public void actionPerformed(ActionEvent e) {
		
		//System.out.println(dp_begin.jcb_year.getSelectedItem()+"�� "+dp_begin.jcb_month.getSelectedItem()+"�� "+dp_begin.jcb_date.getSelectedItem()+"��");
		//System.out.println(dp_end.jcb_year.getSelectedItem()+"�� "+dp_end.jcb_month.getSelectedItem()+"�� "+dp_end.jcb_date.getSelectedItem()+"��");
		
		System.out.println(dp_begin.getYear()+"/"+dp_begin.getMonth()+"/"+dp_begin.getDate());
		System.out.println(dp_end.getYear()+"/"+dp_end.getMonth()+"/"+dp_end.getDate());


	}
}
