package smanageT;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.PatternSyntaxException;
import java.sql.*;
import java.text.*;

public class PayTest extends JPanel implements ActionListener, ItemListener, KeyListener {

	JPanel pay; // ���� ������ ���� ������Ʈ���� ���� �г�

	JPanel pay_top; // ����, ����, ��¥ ����
	JLabel pay_l; // pay_top�� add��. Ÿ��Ʋ
	JPanel date_p; // date���� ���� �г�
	JLabel date_l;
	DurationPanel start_date; // ���� ��¥
	DurationPanel end_date; // ������ ��¥
//	JPanel pay_st; // ������ ���� �г�
//	JComboBox pay_st_b; // ���� ���� ��ӹڽ�
//	JButton pay_st_btn; // ���� ��ư

	JScrollPane scroll_t; // ��� ���̺�
	JTable pay_t; // title, items�� ���� ���̺�
	TableModel data; // pay_t�� ������ Ȱ���� ���� ��
	DefaultTableModel pay_t_model;

	JPanel pay_btm; // �˻�, ��ȸ ����
	JPanel pay_sr_p; // ���� �˻� �г�
	JLabel pay_sr_l; // ���� �˻� ��
	JTextField pay_sr_f; // ���� �˻�â
	JButton pay_sr_b; // ���� �˻� ��ư
	JButton pay_b; // pay_btm�� add��. ��ȸ ��ư

	int row; // data�� Ȱ���ϱ� ���� ����
	int select_row;

	JScrollPane jsp; // main_p�� ��ũ�� ����� �߰���

	JPanel main_p; // �Ʒ� ���� ��� �г�
	JPanel tf_p;

	JPanel grid1; // ���� �Է� ���� 4���� ���� �г�
	JLabel lname; // �̸�
	JTextField fname;
	JLabel ljob; // ����
	JTextField fjob;
	JLabel ldept;
	JTextField fdept;

	Vector<JTextField> fs; // ���� textfield���� ������ ���� �迭

	JPanel grid2;
	JLabel lhida; // �Ի���
	JTextField fhida;
	JLabel lpay; // ������
	JTextField fpay;

	JPanel grid3; // �Ʒ��� �Է� ���� 3���� ���� �г�
	JLabel lbank; // ����
	JTextField fbank;
	JLabel lacc; // ���¹�ȣ
	JTextField facc;
	JLabel lacc_name; // ������
	JTextField facc_name;

	JPanel grid4; // �޿� ���� �г�
	JLabel lsal;
	JTextField fsal;
	JLabel lann;
	JTextField fann;
	JLabel lprov;
	JTextField fprov;
	JLabel hintsal;
	String hint;

	JPanel overlay_p;

	JPanel main_btn_p; // ����, ����, ��� ��ư�� ���� �г�
	JButton main_mod; // ���� ��ư
	JButton main_save; // ���� ��ư
	JButton main_print; // ��� ��ư

	JDialog main_win; // ���� �� ���̾�
	JPanel dial_win; // �׵θ��� �г�
	JLabel d_title; // Ÿ��Ʋ

	JPanel middle_win; // �Ʒ� ~info �г��� ��� �г�

	JPanel d_info; // ���� ���� �г�
	JPanel d_info1;
	JPanel d_info2;
	JPanel d_info3;
	JLabel wname;
	JLabel cname;
	JLabel wnum;
	JLabel cnum;
	JLabel wann;
	JLabel cann;
	JLabel wjob;
	JLabel cjob;
	JLabel whida;
	JLabel chida;
	JLabel wprov;
	JLabel cprov;
	JLabel wdept;
	JLabel cdept;

	JPanel sal_info; // �޿� ���� �г�
	JLabel wsal;
	JLabel csal;
	JLabel wtax;
	JLabel ctax;
	JLabel r_wsal;
	JLabel r_csal;

	JPanel bt_info; // ���ʽ� ���� �г�
	JPanel bonus_info;
	JLabel wsalT;
	JLabel csalT;
	JLabel wannL;
	JLabel cannL;

	JPanel tax_info; // ���� ���� �г�
	JLabel wpen;
	JLabel cpen;
	JLabel wempI;
	JLabel cempI;
	JLabel whealI;
	JLabel chealI;
	JLabel wincomeI;
	JLabel cincomeI;

	Vector bt; // ����� ���� ��� ����
	Vector<JLabel> pi; // ���� ����

	JPanel d_btns; // ��ư ���� �г�
	JButton btn_w_print;
	JButton btn_w_email;

	JDialog d_alert; // �μ�, �̸��� ��ư�� ���� �� �����ϴ� �˸� dialog
	JLabel alert_l;
	JButton alert_exit; // �˸� ����� ��ư
	JDialog d_save_al;
	JPanel save_labels;
	JLabel al_save_l1;
	JLabel al_save_l2;
	JPanel save_btn;
	JButton save_b_y;
	JButton save_b_n;

	String columnName[];
	String rowData[][];
//	String title[] = { "�����ȣ", "�̸�", "����", "�Ի���", "������", "����", "���¹�ȣ", "������", "����(����)", "�̻�� ����", "���� ����" };
	String title[] = { "�����ȣ", "�̸�", "�μ�", "����", "�Ի���", "������", "����", "���¹�ȣ", "������", "����(����)", "�̻�� ����", "���� ����" };
	String items[][];

	String combo[] = { "�̸�", "����", "�޿�", "�Ի���" };

	int cnt;
	Calendar calen;

	HashMap<String, String> pos_info;

	JFrame jf; // ������
	Dimension framesize; // jf�� ������
	boolean checking;
	boolean mod_on;
	boolean print_on;

	int s_year;
	int s_month;
//	int s_date;

	String s_date;
	String e_date;

	int e_year;
	int e_month;
//	int e_date;

	String cond;
	JButton sr_reset_b;

	Connection conn;

	int comm[];
	String est[];

	public PayTest(Connection conn) {
		this.conn = conn;
		cnt = Cnt();
		columnName = new String[6];
		comm = new int[cnt];
		est = new String[cnt];
//		rowData = new String[cnt][10];
		getDB();

		calen = Calendar.getInstance();

		jf = new JFrame(); // ������ ũ��
		framesize = new Dimension(1200, 700);
		jf.setSize(framesize);

		// ���� â�� ���̾ƿ� ����: ���� ����
		BoxLayout boxl = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(boxl);

		pay = new JPanel(); // ���� ���� ��ȸ �г�. �� ȭ��
		pay.setLayout(new BoxLayout(pay, BoxLayout.Y_AXIS)); // ����

		jsp = new JScrollPane(main_p); // ��ȸ�� ���� ���� �гο� ��ũ�ѹ� ����
		main_p = new JPanel(); // �Ʒ� ȭ��
		main_p.setLayout(new BoxLayout(main_p, BoxLayout.Y_AXIS)); // main_p ���̾ƿ�: ���� ����

		pay_top = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
		pay_l = new JLabel("���� ���", JLabel.CENTER); // Ÿ��Ʋ ��
		// ��Ʈ ����
//		Font f = new Font(getName(), 0, 20);
//		pay_l.setFont(f);
		pay_l.setFont(new Font(getName(), 1, 25));
		pay_top.add(pay_l); // ���� �гο� �� �߰�

		pay_btm = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10)); // �Ʒ� ���� �г�

		// ���̺�
//		items = new String[cnt][11];
		posInfo();
		payTable("");

		pay_sr_f = new JTextField();
		// �÷����� ���̺� ����
		pay_t_model = new DefaultTableModel(title, 0); // ���� ���� ���̺�
		pay_t = new JTable(pay_t_model);
		scroll_t = new JScrollPane(pay_t);

		// ���� ���̺� ��ũ�� �߰�
		Dimension pay_t_size = new Dimension(700, 100);
		scroll_t.setPreferredSize(pay_t_size);

		makeTable();

		pay_t.getTableHeader().setReorderingAllowed(false); // �÷� �̵� �ȵ�
		cellEdit(false); // ���̺� ���� ����

		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(pay_t_model);
		pay_t.setRowSorter(sorter);
		pay_sr_f.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				search();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				search();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search();
			}

			private void search() {
				String searchText = pay_sr_f.getText().trim();

				// �˻�� ������ ��� ���� �ٽ� �����ش�.
				if (searchText.isEmpty()) {
					sorter.setRowFilter(null);
					return;

				}
				try {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
				} catch (PatternSyntaxException ex) {
					// �˻�� ���Խ����� ��ȯ���� ������ ��� ���� �����ش�.
					sorter.setRowFilter(null);
				}

			}

		});

		pay_t.setRowSorter(sorter); // �÷��� Ŭ���� ���� ����
		data = pay_t.getModel(); // ���� ���̺��� ���� ����

		pay.add(pay_top); // ������ �гο� �߰�
		pay.add(scroll_t);
		pay.add(pay_btm);
		// pay_btm �׸��
		sr_reset_b = new JButton("�˻� �ʱ�ȭ");
		sr_reset_b.setUI(new RoundedButton());
		pay_sr_p = new JPanel(new BorderLayout(10, 10)); // �˻� �г�
		pay_sr_l = new JLabel("�˻�");
		pay_sr_b = new JButton("�˻�");
		pay_sr_b.setUI(new RoundedButton());
		pay_sr_f.setPreferredSize(new Dimension(200, pay_sr_f.getHeight()));

		pay_b = new JButton("��ȸ�ϱ�");
		pay_b.setUI(new RoundedButton());
		pay_b.addActionListener(this);

		pay_sr_p.add(pay_sr_l, "West");
		pay_sr_p.add(pay_sr_f, "Center");

		// main_p�� ũ�� ����
		Dimension main_size = new Dimension(jf.getWidth() / 12 * 5, jf.getHeight() / 7 * 3);
		main_p.setPreferredSize(main_size);

		tf_p = new JPanel(new GridLayout(4, 1, 20, 20)); // ���� ���� ��� textfield���� ���� �г�
		tf_p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 150)); // �е��ֱ�
		main_p.add(tf_p); // ���� ��ȸ �гο� �߰�

		grid1 = new JPanel(new GridLayout(1, 6, 20, 10)); // tf_p �гο� ��� ������
		lname = new JLabel("�̸�", 4);
		fname = new JTextField();
		ljob = new JLabel("����", 4);
		fjob = new JTextField();
		ldept = new JLabel("�μ�", 4);
		fdept = new JTextField();

		grid2 = new JPanel(new GridLayout(1, 4, 20, 10));
		lhida = new JLabel("�Ի���", 4);
		fhida = new JTextField();
		lpay = new JLabel("������", 4);
		fpay = new JTextField();

		grid3 = new JPanel(new GridLayout(1, 6, 20, 10));
		lbank = new JLabel("����", 4);
		fbank = new JTextField();
		lacc = new JLabel("���¹�ȣ", 4);
		facc = new JTextField();
		lacc_name = new JLabel("������", 4);
		facc_name = new JTextField();

		grid4 = new JPanel(new GridLayout(1, 6, 20, 10));
		lsal = new JLabel("����", 4);
		fsal = new JTextField();
		lann = new JLabel("�̻�� ����", 4);
		fann = new JTextField();
		lprov = new JLabel("�޿� ���� ����", 4);
		fprov = new JTextField();

		fs = new Vector<JTextField>(); // ��ɿ�. ���̺��� ���õ� ������ ������ main�� ������
		fs.add(fname);
		fs.add(fdept);
		fs.add(fjob);
		fs.add(fhida);
		fs.add(fpay);
		fs.add(fbank);
		fs.add(facc);
		fs.add(facc_name);
		fs.add(fsal);
		fs.add(fann);
		fs.add(fprov);

		fs.get(0).setEditable(false);
		fs.get(1).setEditable(false);
		fs.get(2).setEditable(false);
		fs.get(3).setEditable(false);

		grid1.add(lname); // ������Ʈ���� �� grid�� ��� tf_p �гο� �߰�
		grid1.add(fname);
		grid1.add(ldept);
		grid1.add(fdept);
		grid1.add(ljob);
		grid1.add(fjob);
		tf_p.add(grid1);

		grid2.add(lhida);
		grid2.add(fhida);
		grid2.add(lpay);
		grid2.add(fpay);
		tf_p.add(grid2);

		grid3.add(lbank);
		grid3.add(fbank);
		grid3.add(lacc);
		grid3.add(facc);
		grid3.add(lacc_name);
		grid3.add(facc_name);
		tf_p.add(grid3);

		overlay_p = new JPanel();
		overlay_p.setLayout(new OverlayLayout(overlay_p));
		hintsal = new JLabel();
		hintsal.setFont(new Font(this.getName(), Font.ITALIC, 12));
		hintsal.setForeground(Color.gray);

		overlay_p.add(hintsal);
		overlay_p.add(fsal);

		grid4.add(lsal);
		grid4.add(overlay_p);
//		grid4.add(fsal);
		grid4.add(lann);
		grid4.add(fann);
		grid4.add(lprov);
		grid4.add(fprov);
		tf_p.add(grid4);
		UpdateInfo(false);

		// ����, ����, �μ� ��ư�� ���� �г� ���̾ƿ�: 20 �������� ���� ����
		main_btn_p = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

		main_mod = new JButton("�����ϱ�");
		main_mod.setUI(new RoundedButton());
		main_save = new JButton("�����ϱ�");
		main_save.setUI(new RoundedButton());
		main_print = new JButton("�μ��ϱ�");
		main_print.setUI(new RoundedButton());

		main_btn_p.add(main_mod);
		main_btn_p.add(main_save);
		main_btn_p.add(main_print);
		main_p.add(main_btn_p, "South");

		this.add(pay);
		this.add(main_p);

		jsp.setViewportView(main_p);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(jsp);

		// ��¥ ����
		start_date = new DurationPanel();
		end_date = new DurationPanel();
		date_p = new JPanel();

		date_l = new JLabel("�Ի��� �˻�");
		JLabel dur = new JLabel("~"); // ����Ʈ�� ���� ����

		date_p.add(date_l);
		date_p.add(start_date);
		date_p.add(dur);
		date_p.add(end_date);

		pay_btm.add(date_p);// ��ġ ����: �Ⱓ, �˻�, ��ȸ
		pay_sr_p.add(pay_b, "East");
		pay_btm.add(pay_sr_p);
		pay_btm.add(sr_reset_b, "East");

		// �׼��� �޾ƿ�
		pay_sr_b.addActionListener(this);
		main_mod.addActionListener(this);
		main_save.addActionListener(this);
		main_print.addActionListener(this);

		// main_win(Dialog) screen
		main_win = new JDialog();
		main_win.setSize(600, 700);

		dial_win = new JPanel();
		dial_win.setLayout(new BorderLayout(10, 10));
		dial_win.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		main_win.add(dial_win);

		d_title = new JLabel("����", JLabel.CENTER);
		d_title.setFont(new Font(getName(), 1, 25));
//		d_title.setFont(f);

		middle_win = new JPanel();
		middle_win.setLayout(new BoxLayout(middle_win, BoxLayout.Y_AXIS));

		d_info = new JPanel();
		d_info.setLayout(new GridLayout(3, 1));
		d_info.setBorder(new LineBorder(Color.gray));

		d_info1 = new JPanel(new GridLayout(1, 4));
		d_info2 = new JPanel(new GridLayout(1, 6));
		d_info3 = new JPanel(new GridLayout(1, 4));

		pi = new Vector<JLabel>();

		wnum = new JLabel("�����ȣ", 0);
		wname = new JLabel("����", 0);
		wdept = new JLabel("�μ�", 0);
		wjob = new JLabel("����", 0);
		whida = new JLabel("�Ի���", 0);
		wprov = new JLabel("��������", 0);
		wann = new JLabel("�̻�� ����", 0);

		cnum = new JLabel("");
		cname = new JLabel("");
		cdept = new JLabel("");
		cann = new JLabel("");
		cjob = new JLabel("");
		chida = new JLabel("");
		cprov = new JLabel("");

		pi.add(cnum);
		pi.add(cname);
		pi.add(cdept);
		pi.add(cjob);
		pi.add(chida);
		pi.add(cprov);
		pi.add(cann);

		d_info1.add(wnum);
		d_info1.add(cnum);
		d_info1.add(wname);
		d_info1.add(cname);
		d_info2.add(wdept);
		d_info2.add(cdept);
		d_info2.add(wjob);
		d_info2.add(cjob);
		d_info2.add(whida);
		d_info2.add(chida);
		d_info3.add(wprov);
		d_info3.add(cprov);
		d_info3.add(wann);
		d_info3.add(cann);

		d_info.add(d_info1);
		d_info.add(d_info2);
		d_info.add(d_info3);

		d_btns = new JPanel(new FlowLayout());
		btn_w_print = new JButton("�μ�");
		btn_w_print.setUI(new RoundedButton());
		btn_w_email = new JButton("�̸���");
		btn_w_email.setUI(new RoundedButton());

		sal_info = new JPanel(new GridLayout(1, 6, 10, 0));
		sal_info.setBorder(new LineBorder(Color.gray));
		wsal = new JLabel("�����Ѿ�", 0);
		csal = new JLabel("");
		wtax = new JLabel("�����Ѿ�", 0);
		ctax = new JLabel("");
		r_wsal = new JLabel("�����޾�", 0);
		r_csal = new JLabel("");

		pi.add(csal);
		pi.add(ctax);
		pi.add(r_csal);

		sal_info.add(wsal);
		sal_info.add(csal);
		sal_info.add(wtax);
		sal_info.add(ctax);
		sal_info.add(r_wsal);
		sal_info.add(r_csal);

		d_btns.add(btn_w_print);
		d_btns.add(btn_w_email);

		bt_info = new JPanel(new GridLayout(1, 2));

		bonus_info = new JPanel(new GridLayout(3, 2));
		bonus_info.setBorder(new LineBorder(Color.gray));
		wsalT = new JLabel("�⺻��", 0);
		csalT = new JLabel("");
		wannL = new JLabel("��������", 0);
		cannL = new JLabel("");

		pi.add(csalT);
		pi.add(cannL);

		tax_info = new JPanel(new GridLayout(4, 2));
		tax_info.setBorder(new LineBorder(Color.gray));
		wincomeI = new JLabel("�ҵ漼", 0);
		cincomeI = new JLabel("");
		wpen = new JLabel("���ο���", 0);
		cpen = new JLabel("");
		wempI = new JLabel("��뺸��", 0);
		cempI = new JLabel("");
		whealI = new JLabel("�ǰ�����", 0);
		chealI = new JLabel("");

		pi.add(cincomeI);
		pi.add(cpen);
		pi.add(cempI);
		pi.add(chealI);

		bt_info.add(bonus_info);
		bt_info.add(tax_info);

		bonus_info.add(wsalT);
		bonus_info.add(csalT);
		bonus_info.add(wannL);
		bonus_info.add(cannL);

		tax_info.add(wincomeI);
		tax_info.add(cincomeI);
		tax_info.add(wpen);
		tax_info.add(cpen);
		tax_info.add(wempI);
		tax_info.add(cempI);
		tax_info.add(whealI);
		tax_info.add(chealI);

		middle_win.add(d_info);
		middle_win.add(sal_info);
		middle_win.add(bt_info);

		dial_win.add(d_title, "North");
		dial_win.add(middle_win, "Center");
		dial_win.add(d_btns, "South");

		d_alert = new JDialog();
		d_alert.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 40));
		d_alert.setSize(250, 180);

		alert_l = new JLabel("", 0);
		d_alert.add(alert_l);
		alert_exit = new JButton("Ȯ��");
		alert_exit.setUI(new RoundedButton());
		d_alert.add(alert_exit);

		d_save_al = new JDialog();
		d_save_al.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 30));
		d_save_al.setSize(250, 180);

		save_labels = new JPanel(new GridLayout(2, 1));
		d_save_al.add(save_labels);
		al_save_l1 = new JLabel("������� �ʾҽ��ϴ�.", 0);
		al_save_l2 = new JLabel("�����Ͻðڽ��ϱ�?", 0);
		save_labels.add(al_save_l1);
		save_labels.add(al_save_l2);
		save_btn = new JPanel(new FlowLayout(1, 10, 0));
		save_b_y = new JButton("��");
		save_b_y.setUI(new RoundedButton());
		save_b_n = new JButton("�ƴϿ�");
		save_b_n.setUI(new RoundedButton());
		d_save_al.add(save_btn);
		save_btn.add(save_b_y);
		save_btn.add(save_b_n);

		checking = false;
		mod_on = false;
		print_on = false;

		fsal.addKeyListener(this);
		btn_w_print.addActionListener(this);
		btn_w_email.addActionListener(this);
		alert_exit.addActionListener(this);
		sr_reset_b.addActionListener(this);
		save_b_y.addActionListener(this);
		save_b_n.addActionListener(this);

		start_date.jcb_year.addItemListener(this);
		start_date.jcb_month.addItemListener(this);
		start_date.jcb_date.addItemListener(this);

		end_date.jcb_year.addItemListener(this);
		end_date.jcb_month.addItemListener(this);
		end_date.jcb_date.addItemListener(this);

		main_win.setLocationRelativeTo(null);
		d_alert.setLocationRelativeTo(null);
		d_save_al.setLocationRelativeTo(null);

		// ���� ���̺�

	}

	public void actionPerformed(ActionEvent e) {
		Object select = e.getSource();

		row = pay_t.getSelectedRow();

		try {
			if (select == pay_b) {
				for (int i = 1; i < fs.size() + 1; i++) {
					fs.get(i - 1).setText((String) items[row][i]);
				}

				select_row = row;
				checking = true;
				print_on = true;

				if (!fs.get(2).getText().equals("����")) {
					hint = "   �⺻��  " + pos_info.get(fs.get(2).getText()) + "����";
					hintsal.setText(hint);
				} 
				

			} else if (select == main_mod) {
				if (checking) {
					UpdateInfo(true);
					mod_on = true;
				} else { // checking==false
					alert_l.setText("������ ������ �����ϴ�.");
					d_alert.setVisible(true);

				}

			} else if (select == main_save) {
				if (mod_on) {
					SaveInfo();
					checking = false;
				} else { // mod_on==false
					alert_l.setText("������ ������ �����ϴ�.");
					d_alert.setVisible(true);
				}
			} else if (select == btn_w_print) {
				alert_l.setText("�μ� �غ���");
				d_alert.setVisible(true);
			} else if (select == btn_w_email) {
				alert_l.setText("�̸��� ������");
				d_alert.setVisible(true);
			} else if (select == alert_exit) {
				d_alert.dispose();
				main_win.dispose();
			} else if (select == main_print) {
				if (checking && mod_on) {
					d_save_al.setVisible(true);
				} else if (print_on) {
					PrintInfo();
					main_win.setVisible(true);
				} else {
					alert_l.setText("�μ��� ������ �����ϴ�");
					d_alert.setVisible(true);
				}
			} else if (select == sr_reset_b) {
				pay_sr_f.setText("");
				print_on = false;
				clearFields();
				makeTable();
			} else if (select == save_b_y) {
				d_save_al.dispose();
				SaveInfo();
				PrintInfo();
				main_win.setVisible(true);
			} else if (select == save_b_n) {
				checking = false;
				d_save_al.dispose();

				PrintInfo();
				main_win.setVisible(true);
			} else if (select == fsal) {

			}

		} catch (ArrayIndexOutOfBoundsException ex) {
			alert_l.setText("����� �������ּ���.");
			d_alert.setVisible(true);
		}

		revalidate();
	}

	public void getDB() {

		try {
			String sql = "select COLUMN_NAME from user_tab_columns where table_name='PAYTABLE'";

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			int column_n = 0;
			while (rs.next()) {
				columnName[column_n] = rs.getString("column_name");
				column_n++;
			}

			rs.close();
		} catch (Exception excep) {
			excep.printStackTrace();
		}

	}

	public void payTable(String str) {
		items = new String[cnt][12];

		try {
			String sql = "select emptable.empno, emptable.ename, dept,jlevel, to_char(hiredate,'yyyy-mm-dd') as hiredate,paydate, bank, acct, accname, sal, a_left, payfin, estate from emptable, jobst, paytable, annual where emptable.empno=jobst.empno and emptable.empno=paytable.empno and emptable.empno=annual.empno"
					+ str;
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			int items_n = 0;
			while (rs.next()) {
				if (rs.getString("estate").equals("����")) {

					items[items_n][0] = rs.getString("empno");
					items[items_n][1] = rs.getString("ename");
					items[items_n][2] = rs.getString("dept");
					items[items_n][3] = rs.getString("jlevel");

					items[items_n][4] = rs.getString("hiredate");
					items[items_n][5] = rs.getString("paydate");
					items[items_n][6] = rs.getString("bank");
					items[items_n][7] = rs.getString("acct");
					if (rs.getString("accname").equals("�ӽ�")) {
						items[items_n][8] = rs.getString("ename");
					} else {
						items[items_n][8] = rs.getString("accname");
					}
					if (rs.getString("sal").equals("0")) {
						items[items_n][9] = pos_info.get(rs.getString("jlevel"));
					} else {
						items[items_n][9] = rs.getString("sal");
					}
					est[items_n] = rs.getString("estate");

					items[items_n][10] = rs.getString("a_left");
					if (rs.getString("payfin") != null) {
						items[items_n][11] = rs.getString("payfin").toUpperCase();
					}
					items_n++;
				}
			}

//			for (int i = 0; i < items.length; i++) {
//				for (int j = 0; j < items[i].length; j++) {
//					System.out.print(items[i][j] + " ");
//				}
//				System.out.println();
//			}

			rs.close();
		} catch (Exception excep) {
			excep.printStackTrace();
		}
	}

	public int Cnt() {
		int num = 0;
		try {
			String sql = "select count(paytable.empno) from paytable, jobst where paytable.empno= jobst.empno and estate='����'";

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet Cnt_rs = ps.executeQuery();

			while (Cnt_rs.next()) {
				num = Cnt_rs.getInt(1);
			}

			Cnt_rs.close();

		} catch (Exception excep) {
			excep.printStackTrace();
		}

		return num;
	}

	public void posInfo() {
		pos_info = new HashMap<String, String>();
		pos_info.put("���", "300");
		pos_info.put("����", "320");
		pos_info.put("�븮", "380");
		pos_info.put("����", "490");
		pos_info.put("����", "540");
		pos_info.put("����", "720");
	}

	public void UpdateInfo(boolean update) {

		for (int i = 4; i < fs.size(); i++) {
			fs.get(i).setEditable(update);
		}

	}

	public void SaveInfo() {
		String selected = items[select_row][0]; // ���õ� row�� empno
		String sql = "";

		for (int j = 0; j < fs.size(); j++) { // �̸�~ 12��
			items[select_row][j + 1] = fs.get(j).getText(); // empno ����
		}

		try {
			for (int i = 6; i < items[select_row].length; i++) {
				if (i == 6) {
					sql = "update paytable set " + columnName[2] + "=? where empno=" + selected;
				} else if (i == 7) {
					sql = "update emptable set acct =? where empno=" + selected;
				} else if (i == 8) {
					sql = "update paytable set " + columnName[3] + "=? where empno=" + selected;
				} else if (i == 10) {
					sql = "update annual set a_left = ? where empno=" + selected;
				} else if (i == 9) {
					sql = "update paytable set " + columnName[i - 5] + "=? where empno=" + selected;
				} else {
					sql = "update paytable set " + columnName[i - 6] + "=? where empno=" + selected;
				}
				PreparedStatement ps = conn.prepareStatement(sql);

				if (i == 9 || i == 10) {
					if (items[select_row][i].equals("")) {
						ps.setInt(1, 0);
					} else {
						ps.setInt(1, Integer.parseInt(items[select_row][i]));
					}
				} else {
					ps.setString(1, items[select_row][i]);
				}

				int count = ps.executeUpdate();
			}

//			String sql2 = "update paytable set comm = ? where empno=" + selected;
//			PreparedStatement ps2 = conn.prepareStatement(sql2);
//			ps2.setInt(1, comm[select_row]);
//
//			int count2 = ps2.executeUpdate();
		} catch (Exception excep) {
			excep.printStackTrace();
		}

//		payTable(conn, "");
		makeTable();

		for (int i = 0; i < fs.size(); i++) {
			fs.get(i).setEditable(false);
		}

	}

	public void PrintInfo() {
		int nowmonth = calen.get(Calendar.MONTH) + 1;

		DecimalFormat df = new DecimalFormat();
		df.applyLocalizedPattern("#,###,###,###");

		pi.get(0).setText(items[select_row][0]); // ��ȣ
		pi.get(1).setText(items[select_row][1]); // �̸�
		pi.get(2).setText(items[select_row][2]); // �μ�
		pi.get(3).setText(items[select_row][3]); // ����
		pi.get(4).setText(items[select_row][4]); // ������
		pi.get(5).setText(nowmonth + "�� " + items[select_row][5] + "��");
		pi.get(6).setText(items[select_row][10] + "��"); // ����

		int sal = Integer.parseInt(items[select_row][9]) * 10000;
		int day_sal = sal / 209 * 8;
		int ann_sal = day_sal * Integer.parseInt(items[select_row][10]);
		int tax1 = sal * 45 / 1000;
		long tax2 = (long) sal * 3545 / 100000;
		int tax3 = sal * 9 / 1000;
		int tax4 = sal * 3 / 100;
		int tax = tax1 + (int) tax2 + tax3 + tax4;
		int total_sal = sal + ann_sal;
		int real_sal = total_sal - tax;

		pi.get(7).setText(df.format(total_sal) + "��");
		pi.get(8).setText(df.format(tax) + "��");
		pi.get(9).setText(df.format(real_sal) + "��");
		pi.get(10).setText(df.format(sal) + "");
		pi.get(11).setText(df.format(ann_sal) + "");
		pi.get(12).setText(df.format(tax4) + "");
		pi.get(13).setText(df.format(tax1) + "");
		pi.get(14).setText(df.format(tax2) + "");
		pi.get(15).setText(df.format(tax3) + "");

		mod_on = false;
	}

	public void makeTable() {

		while (pay_t_model.getRowCount() > 0) {
			pay_t_model.removeRow(0);
		}

		cellEdit(true);

		for (int i = 0; i < items.length; i++) {
			pay_t_model.addRow(items[i]);
		}

		cellEdit(false);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		DecimalFormat df2 = new DecimalFormat();
		df2.applyLocalizedPattern("00");

		if (e.getStateChange() == 1) {
			s_date = start_date.getYear() + "-" + df2.format(start_date.getMonth()) + "-"
					+ df2.format(start_date.getDate());
			e_date = end_date.getYear() + "-" + df2.format(end_date.getMonth()) + "-" + df2.format(end_date.getDate());
		}

		if (s_date != null && e_date != null) {
			cond = " and hiredate between '" + s_date + "' and '" + e_date + "'";

			payTable(cond);
		}

		makeTable();
	}

	public void clearFields() {
		Calendar c = Calendar.getInstance();
		String now_year = c.get(Calendar.YEAR) + "";
		String now_month = c.get(Calendar.MONTH) + 1 + "";
		String now_day = c.get(Calendar.DATE) + "";
		for (int i = 0; i < fs.size(); i++) {
			fs.get(i).setText("");
		}

		start_date.setUpdateDuration(now_year, now_month, now_day);
		end_date.setUpdateDuration(now_year, now_month, now_day);

		payTable("");
	}

	public void cellEdit(boolean ce) {
		if (ce) {
			pay_t.setModel(new DefaultTableModel(items, title) { // ���� �ȵ�
				public boolean isCellEditable(int row, int column) {
					return true;
				}
			});
		} else if (ce == false) {
			pay_t.setModel(new DefaultTableModel(items, title) { // ���� �ȵ�
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (fsal.getText().equals("")) {
			hintsal.setVisible(true);
		} else {
			hintsal.setVisible(false);
		}

	}
}
