package com.maple32768.bms;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.*;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.SystemColor;
import java.awt.Toolkit;

public class BMS extends JFrame implements KeyListener,ActionListener,FocusListener {

	private static final Color SELECTED_BUTTON_COLOR = Color.decode("#87cefa");
	private static final Color LEND_COLOR = Color.decode("#f6e684");
	private static final Color RETURN_COLOR = Color.decode("#6495ed");
	private static final long serialVersionUID = 1L;
	private static final String[] columns1 = {"貸出限度", "現在貸出","返却期限日"};
	private static final String[] columns2 = {"No.", "資料番号", "貸出日", "期限日"};
	private static final String[] columns3 = {"資料番号", "利用者番号", "貸出日", "返却期限日", "返却日"};
	private static final String[] databases = {"全て", "貸出中", "返却済み"};

	private static String database_path = "./data/system.db";
	private int default_lend_period = 30;
	private int max_lendable_books = 10;
	private boolean is_show_returned_borrower = true;
	private boolean is_show_returned_due_date = false;

	private static SQL sqlite = new SQL(database_path);
	private static int current_mode = 0;

	public static JButton lending;
	public static JButton returning;
	public static JButton searching;
	public static JPanel main_layout;
	public static Component left_space;
	public static Component top_space;
	public static Component right_space;
	public static Component bottom_space;
	public static JPanel contents_panel;
	public static JLabel mode;
	public static JPanel mode_p;
	public static JPanel menu;
	public static JPanel selectors;
	public static JPanel lending_panel;
	public static JPanel lending_form_panel;
	public static JPanel left_form_panel;
	public static JPanel right_form_panel;
	public static JPanel lending_header_panel;
	public static JLabel return_day_lebel;
	public static JPanel return_day_form_panel;
	public static JTextField return_day_textfield;
	public static JPanel borrower_panel;
	public static JLabel borrower_text;
	public static JTextField borrower_textfield;
	public static JPanel book_id_panel;
	public static JLabel book_id_label;
	public static JTextField book_id_textfield;
	public static JPanel left_main_panel;
	public static JTable table;
	public static JScrollPane table_scrollpane;
	public static JPanel right_main_panel;
	public static JPanel right_main_center_panel;
	public static JPanel lu_panel;
	public static JLabel lu_label1;
	public static JLabel lu_label2;
	public static JPanel ru_panel;
	public static JLabel ru_label1;
	public static JLabel ru_label2;
	public static JPanel ld_panel;
	public static JLabel ld_label1;
	public static JLabel ld_label2;
	public static JPanel rd_panel;
	public static JLabel rd_label1;
	public static JLabel rd_label2;
	public static JScrollPane lended_scroll_pane;
	public static JTable lended_table;
	public static JPanel button_panel;
	public static JButton execute_button;
	public static JPanel searching_panel;
	public static JScrollPane hit_scroll_pane;
	public static JTable hit_table;
	public static JPanel setting_panel5;
	public static JPanel setting_panel3;
	public static JLabel setting_database_label;
	public static JComboBox<String> setting_database_combo;
	public static JLabel setting_borrower_id_label;
	public static JTextField setting_borrower_id_textfield;
	public static JPanel setting_panel1;
	public static JLabel setting_book_id_label;
	public static JTextField setting_book_id_textfield;
	public static JPanel setting_panel2;
	public static JPanel setting_panel2_1;
	public static JLabel setting_datelabel1;
	public static JTextField setting_datetextfield1;
	public static JLabel setting_tilda1;
	public static JTextField setting_datetextfield2;
	public static JPanel setting_panel4;
	public static JPanel setting_panel4_1;
	public static JLabel setting_datelabel2;
	public static JTextField setting_datetextfield3;
	public static JLabel setting_tilda2;
	public static JTextField setting_datetextfield4;
	public static JPanel setting_panel6;
	public static JPanel setting_panel6_1;
	public static JLabel setting_datelabel3;
	public static JTextField setting_datetextfield5;
	public static JLabel setting_tilda3;
	public static JTextField setting_datetextfield6;
	public static JPanel setting_panel7;
	public static JPanel buttons_panel;
	public static JButton search_button;
	public static DefaultTableModel tablemodel;
	public static JButton save_file_button;
	public static CardLayout mode_layout;
	public static JPanel tab_panel;
	public static JButton clear_button;



	public BMS() {

		setTitle("書籍管理システム");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);


		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Color.decode("#ffffff"));

		main_layout = new JPanel();
		getContentPane().add(main_layout, BorderLayout.CENTER);
		main_layout.setLayout(new BorderLayout(0, 0));

		left_space = Box.createHorizontalStrut(20);
		main_layout.add(left_space, BorderLayout.WEST);

		top_space = Box.createVerticalStrut(20);
		main_layout.add(top_space, BorderLayout.NORTH);

		right_space = Box.createHorizontalStrut(20);
		main_layout.add(right_space, BorderLayout.EAST);

		bottom_space = Box.createVerticalStrut(20);
		main_layout.add(bottom_space, BorderLayout.SOUTH);

		contents_panel = new JPanel();
		main_layout.add(contents_panel, BorderLayout.CENTER);
		contents_panel.setLayout(new BorderLayout(0, 0));

		menu = new JPanel(new BorderLayout());

		mode_p = new JPanel(new BorderLayout());
		mode = new JLabel();
		mode.setBackground(new Color(255, 255, 255));
		mode.setFont(resize(24));
		mode.setForeground(Color.black);
		mode_p.setBackground(new Color(250, 250, 210));
		mode_p.add(mode);
		mode_p.setPreferredSize(new Dimension(getSize().width - 100, 50));
		menu.add(mode_p, BorderLayout.NORTH);
		contents_panel.add(menu, BorderLayout.NORTH);

		selectors = new JPanel(new FlowLayout());
		lending = new JButton("貸出", resize_image(new ImageIcon("./img/f7.png"), 50, 24));
		lending.setPreferredSize(new Dimension(120, 50));
		lending.setHorizontalTextPosition(SwingConstants.LEFT);
		returning =  new JButton("返却", resize_image(new ImageIcon("./img/f8.png"), 50, 24));
		returning.setPreferredSize(new Dimension(120, 50));
		returning.setHorizontalTextPosition(SwingConstants.LEFT);
		searching =  new JButton("検索", resize_image(new ImageIcon("./img/f6.png"), 50, 24));
		searching.setPreferredSize(new Dimension(120, 50));
		searching.setHorizontalTextPosition(SwingConstants.LEFT);
		lending.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeLendingTab();
			}
		});
		returning.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeReturningTab();
			}
		});
		searching.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeSearchingTab();
			}
		});
		selectors.add(lending);
		selectors.add(returning);
		selectors.add(searching);

		menu.add(selectors, BorderLayout.CENTER);
		selectors.setLayout(new BoxLayout(selectors, BoxLayout.X_AXIS));


		lending_panel = new JPanel();
		GridBagLayout gbl_lending_panel = new GridBagLayout();
		gbl_lending_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_lending_panel.rowHeights = new int[]{28, 149, 40, 20, 139, 0};
		gbl_lending_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_lending_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		lending_panel.setLayout(gbl_lending_panel);

		lending_header_panel = new JPanel();
		lending_header_panel.setBackground(new Color(250, 250, 210));
		GridBagConstraints gbc_lending_header_panel = new GridBagConstraints();
		gbc_lending_header_panel.insets = new Insets(0, 0, 5, 5);
		gbc_lending_header_panel.fill = GridBagConstraints.BOTH;
		gbc_lending_header_panel.gridx = 1;
		gbc_lending_header_panel.gridy = 0;
		lending_panel.add(lending_header_panel, gbc_lending_header_panel);
		lending_header_panel.setLayout(new BorderLayout(0, 0));

		return_day_form_panel = new JPanel();
		return_day_form_panel.setBackground(new Color(250, 250, 210));
		lending_header_panel.add(return_day_form_panel, BorderLayout.EAST);
		return_day_form_panel.setLayout(new BorderLayout(0, 0));

		return_day_lebel = new JLabel("返却期限日");
		return_day_lebel.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		return_day_form_panel.add(return_day_lebel, BorderLayout.WEST);

		return_day_textfield = new JTextField();
		return_day_form_panel.add(return_day_textfield, BorderLayout.CENTER);
		return_day_textfield.setColumns(10);

		return_day_form_panel.add(Box.createVerticalStrut(5), BorderLayout.SOUTH);

		return_day_form_panel.add(Box.createVerticalStrut(5), BorderLayout.NORTH);

		return_day_form_panel.add(Box.createHorizontalStrut(20), BorderLayout.EAST);

		lending_form_panel = new JPanel();
		GridBagConstraints gbc_lending_form_panel = new GridBagConstraints();
		gbc_lending_form_panel.insets = new Insets(0, 0, 5, 5);
		gbc_lending_form_panel.fill = GridBagConstraints.BOTH;
		gbc_lending_form_panel.gridx = 1;
		gbc_lending_form_panel.gridy = 1;
		lending_panel.add(lending_form_panel, gbc_lending_form_panel);
		lending_form_panel.setLayout(new GridLayout(1, 2, 5, 0));

		left_form_panel = new JPanel();
		left_form_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		lending_form_panel.add(left_form_panel);
		left_form_panel.setLayout(new BorderLayout(0, 0));

		borrower_panel = new JPanel();
		borrower_panel.setBackground(new Color(240, 230, 140));
		left_form_panel.add(borrower_panel, BorderLayout.NORTH);
		borrower_panel.setLayout(new BorderLayout(0, 0));

		borrower_text = new JLabel("    利用者");
		borrower_text.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		borrower_panel.add(borrower_text, BorderLayout.WEST);

		borrower_textfield = new JTextField();
		borrower_textfield.setColumns(10);
		borrower_panel.add(borrower_textfield, BorderLayout.CENTER);

		borrower_panel.add(Box.createHorizontalStrut(20), BorderLayout.EAST);

		borrower_panel.add(Box.createVerticalStrut(10), BorderLayout.NORTH);

		borrower_panel.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

		left_main_panel = new JPanel();
		left_form_panel.add(left_main_panel, BorderLayout.CENTER);
		left_main_panel.setLayout(new BorderLayout(0, 0));

		left_main_panel.add(Box.createVerticalStrut(20), BorderLayout.NORTH);

		left_main_panel.add(Box.createVerticalStrut(31), BorderLayout.SOUTH);

		left_main_panel.add(Box.createHorizontalStrut(20), BorderLayout.WEST);

		left_main_panel.add(Box.createHorizontalStrut(20), BorderLayout.EAST);


		right_form_panel = new JPanel();
		right_form_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		lending_form_panel.add(right_form_panel);
		right_form_panel.setLayout(new BorderLayout(0, 0));

		book_id_panel = new JPanel();
		right_form_panel.add(book_id_panel, BorderLayout.NORTH);
		book_id_panel.setLayout(new BorderLayout(0, 0));

		book_id_label = new JLabel("    資料番号");
		book_id_label.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		book_id_panel.add(book_id_label, BorderLayout.WEST);

		book_id_textfield = new JTextField();
		book_id_textfield.setColumns(10);
		book_id_panel.add(book_id_textfield, BorderLayout.CENTER);

		book_id_panel.add(Box.createHorizontalStrut(20), BorderLayout.EAST);

		book_id_panel.add(Box.createVerticalStrut(10), BorderLayout.NORTH);

		book_id_panel.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

		table = new JTable(new Object[][]{{"","",""}}, columns1);
		table.setFont(resize(14));
		table.setRowHeight(25);
		table.getColumnModel().getColumn(0).setPreferredWidth(62);
		table.getColumnModel().getColumn(1).setPreferredWidth(63);
		table.getColumnModel().getColumn(2).setPreferredWidth(97);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setFont(new Font(table.getTableHeader().getFont().getName(), Font.BOLD, 14));
		table.getTableHeader().setBackground(Color.decode("#808080"));
		table.setBackground(Color.decode("#d0d0d0"));
		table.setEnabled(false);


		table_scrollpane = new JScrollPane(table);
		left_main_panel.add(table_scrollpane, BorderLayout.CENTER);

		right_main_panel = new JPanel();
		right_form_panel.add(right_main_panel, BorderLayout.CENTER);
		right_main_panel.setLayout(new BorderLayout(0, 0));

		right_main_panel.add(Box.createHorizontalStrut(20), BorderLayout.WEST);

		right_main_panel.add(Box.createHorizontalStrut(20), BorderLayout.EAST);

		right_main_panel.add(Box.createVerticalStrut(20), BorderLayout.NORTH);

		right_main_panel.add(Box.createVerticalStrut(20), BorderLayout.SOUTH);

		right_main_center_panel = new JPanel();
		right_main_panel.add(right_main_center_panel, BorderLayout.CENTER);
		right_main_center_panel.setLayout(new GridLayout(2, 2, 5, 5));

		lu_panel = new JPanel();
		lu_panel.setBackground(SystemColor.controlHighlight);
		right_main_center_panel.add(lu_panel);
		lu_panel.setLayout(new BorderLayout(0, 0));

		lu_label1 = new JLabel("貸出限度冊数");
		lu_label1.setFont(new Font("MS UI Gothic", Font.BOLD, 14));
		lu_panel.add(lu_label1, BorderLayout.WEST);

		lu_label2 = new JLabel("0冊");
		lu_label2.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		lu_panel.add(lu_label2, BorderLayout.EAST);

		ru_panel = new JPanel();
		ru_panel.setBackground(SystemColor.controlHighlight);
		right_main_center_panel.add(ru_panel);
		ru_panel.setLayout(new BorderLayout(0, 0));

		ru_label1 = new JLabel("現在貸出冊数");
		ru_label1.setFont(new Font("MS UI Gothic", Font.BOLD, 14));
		ru_panel.add(ru_label1, BorderLayout.WEST);

		ru_label2 = new JLabel("0冊");
		ru_label2.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		ru_panel.add(ru_label2, BorderLayout.EAST);

		ld_panel = new JPanel();
		ld_panel.setBackground(SystemColor.controlHighlight);
		right_main_center_panel.add(ld_panel);
		ld_panel.setLayout(new BorderLayout(0, 0));

		ld_label1 = new JLabel("貸出可能冊数");
		ld_label1.setFont(new Font("MS UI Gothic", Font.BOLD, 14));
		ld_panel.add(ld_label1, BorderLayout.WEST);

		ld_label2 = new JLabel("0冊");
		ld_label2.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		ld_panel.add(ld_label2, BorderLayout.EAST);

		rd_panel = new JPanel();
		rd_panel.setBackground(SystemColor.controlHighlight);
		right_main_center_panel.add(rd_panel);
		rd_panel.setLayout(new BorderLayout(0, 0));

		rd_label1 = new JLabel("現在延滞冊数");
		rd_label1.setFont(new Font("MS UI Gothic", Font.BOLD, 14));
		rd_panel.add(rd_label1, BorderLayout.WEST);

		rd_label2 = new JLabel("0冊");
		rd_label2.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		rd_panel.add(rd_label2, BorderLayout.EAST);
		left_main_panel.add(table_scrollpane, BorderLayout.CENTER);

		button_panel = new JPanel();
		GridBagConstraints gbc_button_panel = new GridBagConstraints();
		gbc_button_panel.insets = new Insets(0, 0, 5, 5);
		gbc_button_panel.fill = GridBagConstraints.BOTH;
		gbc_button_panel.gridx = 1;
		gbc_button_panel.gridy = 2;
		lending_panel.add(button_panel, gbc_button_panel);
		button_panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		execute_button = new JButton("実行");
		execute_button.setFont(new Font("MS UI Gothic", Font.BOLD, 14));
		execute_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(current_mode == 0) checkLend();
				else if (current_mode == 1) checkReturn();
			}
		});

		clear_button = new JButton("クリア");
		clear_button.setFont(new Font("MS UI Gothic", Font.BOLD, 14));
		clear_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearParams();
			}
		});
		button_panel.add(clear_button);
		button_panel.add(execute_button);

		lended_scroll_pane = new JScrollPane();
		GridBagConstraints gbc_lended_scroll_pane = new GridBagConstraints();
		gbc_lended_scroll_pane.insets = new Insets(0, 0, 0, 5);
		gbc_lended_scroll_pane.fill = GridBagConstraints.BOTH;
		gbc_lended_scroll_pane.gridx = 1;
		gbc_lended_scroll_pane.gridy = 4;
		lending_panel.add(lended_scroll_pane, gbc_lended_scroll_pane);

		lended_table = new JTable();
		lended_table.setRowHeight(30);
		lended_table.setFont(resize(14));
		lended_table.setModel(new DefaultTableModel(new Object[][] {},columns2));
		lended_table.getColumnModel().getColumn(0).setPreferredWidth(50);
		lended_table.getColumnModel().getColumn(0).setMaxWidth(50);
		lended_table.getTableHeader().setResizingAllowed(false);
		lended_table.getTableHeader().setReorderingAllowed(false);
		lended_table.getTableHeader().setFont(new Font(lended_table.getTableHeader().getFont().getName(), Font.BOLD, 14));
		lended_scroll_pane.setViewportView(lended_table);

		searching_panel = new JPanel();
		GridBagLayout gbl_searching_panel = new GridBagLayout();
		gbl_searching_panel.columnWidths = new int[]{0, 288, 0, 0, 0};
		gbl_searching_panel.rowHeights = new int[]{28, 48, 48, 48, 48, 139, 0};
		gbl_searching_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_searching_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		searching_panel.setLayout(gbl_searching_panel);

		setting_panel1 = new JPanel();
		GridBagConstraints gbc_setting_panel1 = new GridBagConstraints();
		gbc_setting_panel1.insets = new Insets(0, 0, 5, 5);
		gbc_setting_panel1.fill = GridBagConstraints.HORIZONTAL;
		gbc_setting_panel1.gridx = 1;
		gbc_setting_panel1.gridy = 1;
		searching_panel.add(setting_panel1, gbc_setting_panel1);
		setting_panel1.setLayout(new BorderLayout(0, 0));

		setting_panel1.add(Box.createHorizontalStrut(20), BorderLayout.EAST);

		setting_panel1.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

		setting_book_id_label = new JLabel("    資料番号");
		setting_book_id_label.setFont(new Font("MS UI Gothic", Font.BOLD, 14));
		setting_panel1.add(setting_book_id_label, BorderLayout.WEST);

		setting_panel1.add(Box.createVerticalStrut(10), BorderLayout.NORTH);

		setting_book_id_textfield = new JTextField();
		setting_book_id_textfield.setColumns(10);
		setting_panel1.add(setting_book_id_textfield, BorderLayout.CENTER);

		setting_panel2 = new JPanel();
		GridBagConstraints gbc_setting_panel2 = new GridBagConstraints();
		gbc_setting_panel2.insets = new Insets(0, 0, 5, 5);
		gbc_setting_panel2.fill = GridBagConstraints.HORIZONTAL;
		gbc_setting_panel2.gridx = 2;
		gbc_setting_panel2.gridy = 1;
		searching_panel.add(setting_panel2, gbc_setting_panel2);
		setting_panel2.setLayout(new BorderLayout(0, 0));

		setting_panel2.add(Box.createVerticalStrut(10), BorderLayout.NORTH);

		setting_panel2.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

		setting_panel2_1 = new JPanel();
		setting_panel2.add(setting_panel2_1, BorderLayout.CENTER);
		setting_panel2_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		setting_panel2_1.add(Box.createHorizontalStrut(20));

		setting_datelabel1 = new JLabel("貸出日 期間");
		setting_datelabel1.setFont(new Font("MS UI Gothic", Font.BOLD, 15));
		setting_panel2_1.add(setting_datelabel1);

		setting_panel2_1.add(Box.createHorizontalStrut(25));

		setting_datetextfield1 = new JTextField();
		setting_panel2_1.add(setting_datetextfield1);
		setting_datetextfield1.setColumns(10);

		setting_tilda1 = new JLabel("～");
		setting_tilda1.setFont(new Font("MS UI Gothic", Font.BOLD, 14));
		setting_panel2_1.add(setting_tilda1);

		setting_datetextfield2 = new JTextField();
		setting_datetextfield2.setColumns(10);
		setting_panel2_1.add(setting_datetextfield2);

		setting_panel4 = new JPanel();
		GridBagConstraints gbc_setting_panel4 = new GridBagConstraints();
		gbc_setting_panel4.insets = new Insets(0, 0, 5, 5);
		gbc_setting_panel4.fill = GridBagConstraints.HORIZONTAL;
		gbc_setting_panel4.gridx = 2;
		gbc_setting_panel4.gridy = 2;
		searching_panel.add(setting_panel4, gbc_setting_panel4);
		setting_panel4.setLayout(new BorderLayout(0, 0));

		setting_panel4.add(Box.createVerticalStrut(10), BorderLayout.NORTH);

		setting_panel4.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

		setting_panel4_1 = new JPanel();
		setting_panel4.add(setting_panel4_1, BorderLayout.CENTER);
		setting_panel4_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		setting_panel4_1.add(Box.createHorizontalStrut(20));

		setting_datelabel2 = new JLabel("返却期限日 期間");
		setting_datelabel2.setFont(new Font("MS UI Gothic", Font.BOLD, 15));
		setting_panel4_1.add(setting_datelabel2);

		setting_datetextfield3 = new JTextField();
		setting_datetextfield3.setColumns(10);
		setting_panel4_1.add(setting_datetextfield3);

		setting_tilda2 = new JLabel("～");
		setting_tilda2.setFont(new Font("MS UI Gothic", Font.BOLD, 14));
		setting_panel4_1.add(setting_tilda2);

		setting_datetextfield4 = new JTextField();
		setting_datetextfield4.setColumns(10);
		setting_panel4_1.add(setting_datetextfield4);

		setting_panel5 = new JPanel();
		GridBagConstraints gbc_setting_panel5 = new GridBagConstraints();
		gbc_setting_panel5.insets = new Insets(0, 0, 5, 5);
		gbc_setting_panel5.fill = GridBagConstraints.HORIZONTAL;
		gbc_setting_panel5.gridx = 1;
		gbc_setting_panel5.gridy = 3;
		searching_panel.add(setting_panel5, gbc_setting_panel5);
		setting_panel5.setLayout(new BorderLayout(0, 0));

		setting_database_label = new JLabel("    データベース種");
		setting_database_label.setFont(new Font("MS UI Gothic", Font.BOLD, 14));
		setting_panel5.add(setting_database_label, BorderLayout.WEST);

		setting_panel5.add(Box.createHorizontalStrut(20), BorderLayout.EAST);

		setting_database_combo = new JComboBox<String>(databases);
		setting_database_combo.setSelectedIndex(0);
		setting_panel5.add(setting_database_combo, BorderLayout.CENTER);

		setting_panel5.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

		setting_panel5.add(Box.createVerticalStrut(10), BorderLayout.NORTH);

		setting_panel3 = new JPanel();
		GridBagConstraints gbc_setting_panel3 = new GridBagConstraints();
		gbc_setting_panel3.insets = new Insets(0, 0, 5, 5);
		gbc_setting_panel3.fill = GridBagConstraints.HORIZONTAL;
		gbc_setting_panel3.gridx = 1;
		gbc_setting_panel3.gridy = 2;
		searching_panel.add(setting_panel3, gbc_setting_panel3);
		setting_panel3.setLayout(new BorderLayout(0, 0));

		setting_panel3.add(Box.createHorizontalStrut(20), BorderLayout.EAST);

		setting_panel3.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

		setting_panel3.add(Box.createVerticalStrut(10), BorderLayout.NORTH);

		setting_borrower_id_label = new JLabel("    利用者番号");
		setting_borrower_id_label.setFont(new Font("MS UI Gothic", Font.BOLD, 14));
		setting_panel3.add(setting_borrower_id_label, BorderLayout.WEST);

		setting_borrower_id_textfield = new JTextField();
		setting_panel3.add(setting_borrower_id_textfield, BorderLayout.CENTER);
		setting_borrower_id_textfield.setColumns(10);

		setting_panel6 = new JPanel();
		GridBagConstraints gbc_setting_panel6 = new GridBagConstraints();
		gbc_setting_panel6.insets = new Insets(0, 0, 5, 5);
		gbc_setting_panel6.fill = GridBagConstraints.HORIZONTAL;
		gbc_setting_panel6.gridx = 2;
		gbc_setting_panel6.gridy = 3;
		searching_panel.add(setting_panel6, gbc_setting_panel6);
		setting_panel6.setLayout(new BorderLayout(0, 0));

		setting_panel6.add(Box.createVerticalStrut(10), BorderLayout.NORTH);

		setting_panel6.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

		setting_panel6_1 = new JPanel();
		setting_panel6.add(setting_panel6_1, BorderLayout.CENTER);
		setting_panel6_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		setting_panel6_1.add(Box.createHorizontalStrut(20));

		setting_datelabel3 = new JLabel("返却日 期間");
		setting_datelabel3.setFont(new Font("MS UI Gothic", Font.BOLD, 15));
		setting_panel6_1.add(setting_datelabel3);

		setting_panel6_1.add(Box.createHorizontalStrut(25));

		setting_datetextfield5 = new JTextField();
		setting_datetextfield5.setColumns(10);
		setting_panel6_1.add(setting_datetextfield5);

		setting_tilda3 = new JLabel("～");
		setting_tilda3.setFont(new Font("MS UI Gothic", Font.BOLD, 14));
		setting_panel6_1.add(setting_tilda3);

		setting_datetextfield6 = new JTextField();
		setting_datetextfield6.setColumns(10);
		setting_panel6_1.add(setting_datetextfield6);

		setting_panel7 = new JPanel();
		GridBagConstraints gbc_setting_panel7 = new GridBagConstraints();
		gbc_setting_panel7.insets = new Insets(0, 0, 5, 5);
		gbc_setting_panel7.fill = GridBagConstraints.BOTH;
		gbc_setting_panel7.gridx = 1;
		gbc_setting_panel7.gridy = 4;
		searching_panel.add(setting_panel7, gbc_setting_panel7);
		setting_panel7.setLayout(new BorderLayout(0, 0));

		setting_panel7.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

		setting_panel7.add(Box.createVerticalStrut(10), BorderLayout.NORTH);

		buttons_panel = new JPanel();
		setting_panel7.add(buttons_panel, BorderLayout.CENTER);
		buttons_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		search_button = new JButton("検索");
		search_button.setFont(new Font("MS UI Gothic", Font.BOLD, 14));
		search_button.addKeyListener(this);
		buttons_panel.add(search_button);

		save_file_button = new JButton("保存");
		save_file_button.setFont(new Font("MS UI Gothic", Font.BOLD, 14));
		save_file_button.addActionListener(this);
		buttons_panel.add(save_file_button);
		search_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkSearch();
			}
		});

		hit_scroll_pane = new JScrollPane();
		GridBagConstraints gbc_hit_scroll_pane = new GridBagConstraints();
		gbc_hit_scroll_pane.gridwidth = 2;
		gbc_hit_scroll_pane.insets = new Insets(0, 0, 0, 5);
		gbc_hit_scroll_pane.fill = GridBagConstraints.BOTH;
		gbc_hit_scroll_pane.gridx = 1;
		gbc_hit_scroll_pane.gridy = 5;
		searching_panel.add(hit_scroll_pane, gbc_hit_scroll_pane);

		tablemodel = new DefaultTableModel(columns3, 0);
		hit_table = new JTable();
		hit_table.setFont(resize(14));
		hit_table.setRowHeight(25);
		hit_table.setModel(tablemodel);
		hit_table.getColumnModel().getColumn(0).setPreferredWidth(100);
		hit_table.getColumnModel().getColumn(0).setMinWidth(100);
		hit_table.getColumnModel().getColumn(0).setMaxWidth(200);
		hit_table.getColumnModel().getColumn(1).setPreferredWidth(100);
		hit_table.getColumnModel().getColumn(1).setMinWidth(100);
		hit_table.getColumnModel().getColumn(1).setMaxWidth(200);
		hit_table.getTableHeader().setResizingAllowed(false);
		hit_table.getTableHeader().setReorderingAllowed(false);
		hit_table.getTableHeader().setFont(new Font(hit_table.getTableHeader().getFont().getName(), Font.BOLD, 14));
		hit_table.getTableHeader().setResizingAllowed(false);
		hit_table.getTableHeader().setReorderingAllowed(false);
		hit_table.setEnabled(false);
		hit_scroll_pane.setViewportView(hit_table);


		mode_layout = new CardLayout();
		tab_panel = new JPanel(mode_layout);
		contents_panel.add(tab_panel, BorderLayout.CENTER);
		tab_panel.add(lending_panel, "lend");
		tab_panel.add(searching_panel, "search");

		addKeyListener(this);

		changeLendingTab();

	}

	private ImageIcon resize_image(ImageIcon ii, int width, int height) {
		BufferedImage img = new BufferedImage(width, height,BufferedImage.TYPE_4BYTE_ABGR);
        img.getGraphics().drawImage(ii.getImage(), 0, 0, width, height, null);
        return new ImageIcon(img);
	}

	private Font resize(int size) {
		return new Font(Font.DIALOG,Font.BOLD, size);
	}


	public void changeLendingTab() {
		current_mode = 0;
		mode.setText("貸出");
		lending.setBackground(SELECTED_BUTTON_COLOR);
		returning.setBackground(new JButton().getBackground());
		searching.setBackground(new JButton().getBackground());
		mode_layout.show(tab_panel,"lend");
		borrower_panel.setBackground(LEND_COLOR);
		borrower_textfield.setEnabled(true);
		borrower_textfield.setBackground(Color.white);
		book_id_textfield.setText("");
		borrower_textfield.setText("");
		return_day_textfield.setText("");
		clearParams();
		borrower_textfield.requestFocus();
	}

	public void changeReturningTab() {
		current_mode = 1;
		mode.setText("返却");
		lending.setBackground(new JButton().getBackground());
		returning.setBackground(SELECTED_BUTTON_COLOR);
		searching.setBackground(new JButton().getBackground());
		mode_layout.show(tab_panel,"lend");
		borrower_textfield.setEnabled(false);
		borrower_textfield.setBackground(Color.lightGray);
		borrower_panel.setBackground(RETURN_COLOR);
		book_id_textfield.setText("");
		borrower_textfield.setText("");
		return_day_textfield.setText("");
		clearParams();
		book_id_textfield.requestFocus();
	}

	public void changeSearchingTab() {
		current_mode = 2;
		mode.setText("データベース検索");
		lending.setBackground(new JButton().getBackground());
		returning.setBackground(new JButton().getBackground());
		searching.setBackground(SELECTED_BUTTON_COLOR);
		mode_layout.show(tab_panel,"search");
	}

	public void executeLend(Lend lend) {
		try {
			if(sqlite.executeQuery("SELECT id FROM statuses WHERE book_id = '" + lend.book_id + "' AND is_returned = '0'").next()) {
				attensionError("この資料は既に貸出済みです。");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(sqlite.executeUpdate(Lend.getInsertCommand(), lend.getInsertArgs())) {
			book_id_textfield.setText("");
			updateUserData();
		}else {
			attensionError("貸出処理中にエラーが発生しました。もう一度お試しください。");
		}
	}

	public void executeReturn() {
		String book_id = book_id_textfield.getText();
		try {
			if(!sqlite.executeQuery("SELECT id FROM statuses WHERE book_id = '" + book_id + "' AND is_returned = '0'").next()) {
				attensionError("この資料は貸出中ではありません。");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = sqlite.executeQuery("SELECT borrower_id FROM statuses WHERE book_id = '" + book_id_textfield.getText() +"' AND is_returned = '0'");
		try {
			rs.next();
			borrower_textfield.setText(String.valueOf(rs.getString("borrower_id")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(sqlite.executeUpdate(Lend.getReturnCommand(),Lend.getReturnArgs(book_id))) {
			updateUserData();
			book_id_textfield.setText("");
		}else {
			attensionError("返却処理中にエラーが発生しました。");
			return;
		}
	}

	public void checkLend() {
		String return_date = completeZero(return_day_textfield.getText());
		boolean has_valid_return_day = false;
		if(!(return_day_textfield.getText().equals("") || return_date != null)) {
			attensionError("返却日のフォーマットが無効です");
			return;
		}else if(!return_day_textfield.getText().equals("")) {
			has_valid_return_day = true;
		}

		if(!Lend.borrower_pattern.matcher(borrower_textfield.getText()).find()) {
			attensionError("利用者番号のフォーマットが無効です");
			return;
		}

		if(!Lend.book_pattern.matcher(book_id_textfield.getText()).find()) {
			if(Lend.borrower_pattern.matcher(book_id_textfield.getText()).find()) {
				borrower_textfield.setText(book_id_textfield.getText());
				book_id_textfield.setText("");
			}else attensionError("資料番号のフォーマットが無効です");
			return;
		}
		if(Integer.parseInt(String.valueOf(ld_label2.getText().replaceAll("冊", ""))) <= 0) {
			attensionError("利用者の貸出可能冊数の上限に達しています。");
			return;
		}
		Lend lend = new Lend(book_id_textfield.getText(),Integer.parseInt(borrower_textfield.getText()),Calendar.getInstance(), default_lend_period);
		if(has_valid_return_day) lend = new Lend(book_id_textfield.getText(),Integer.parseInt(borrower_textfield.getText()),Calendar.getInstance(), Lend.getCalendar(return_day_textfield.getText()));
		executeLend(lend);
	}

	public void checkReturn() {
		if(!Lend.book_pattern.matcher(book_id_textfield.getText()).find()) {
			attensionError("資料番号のフォーマットが無効です");
			return;
		}
		executeReturn();
	}

	public void checkSearch() {
		List<String> date_data = new ArrayList<String>();
		String str = setting_book_id_textfield.getText();
		if(!(str.equals("") || Lend.book_pattern.matcher(str).find())) {
			setting_book_id_textfield.setBorder(new LineBorder(Color.RED));
			attensionError("資料番号のフォーマットが無効です");
			return;
		}

		str = setting_borrower_id_textfield.getText();
		if(!(str.equals("") || Lend.borrower_pattern.matcher(str).find())) {
			setting_borrower_id_textfield.setBorder(new LineBorder(Color.RED));
			attensionError("利用者番号のフォーマットが無効です");
			return;
		}else if(!str.equals("")) {
			setting_database_combo.setSelectedIndex(1);
		}

		if(!((setting_datetextfield3.getText().equals("") && setting_datetextfield4.getText().equals("")) || (setting_datetextfield5.getText().equals("") && setting_datetextfield6.getText().equals("")))) {
			attensionError("返却予定日 期間と返却日を同時に指定することは出来ません。");
			return;
		}

		str = setting_datetextfield1.getText();
		if(!(str.equals("") || Lend.date_pattern.matcher(str).find())) {
			setting_datetextfield1.setBorder(new LineBorder(Color.RED));
			attensionError("日付のフォーマットが無効です");
			return;
		}else if(!str.equals("")) {
			date_data.add(" AND lend_date >= '" + completeZero(str) + "'");
		}


		str = setting_datetextfield2.getText();
		if(!(str.equals("") || Lend.date_pattern.matcher(str).find())) {
			setting_datetextfield2.setBorder(new LineBorder(Color.RED));
			attensionError("日付のフォーマットが無効です");
			return;
		}else if(!str.equals("")) {
			date_data.add(" AND lend_date <= '" + completeZero(str) + "'");
		}

		str = setting_datetextfield3.getText();
		if(!(str.equals("") || Lend.date_pattern.matcher(str).find())) {
			setting_datetextfield3.setBorder(new LineBorder(Color.RED));
			attensionError("日付のフォーマットが無効です");
			return;
		}else if(!str.equals("")) {
			date_data.add(" AND due_date >= '" + completeZero(str) + "'");
		}


		str = setting_datetextfield4.getText();
		if(!(str.equals("") || Lend.date_pattern.matcher(str).find())) {
			setting_datetextfield4.setBorder(new LineBorder(Color.RED));
			attensionError("日付のフォーマットが無効です");
			return;
		}else if(!str.equals("")) {
			date_data.add(" AND due_date <= '" + completeZero(str) + "'");
		}

		str = setting_datetextfield5.getText();
		if(!(str.equals("") || Lend.date_pattern.matcher(str).find())) {
			setting_datetextfield5.setBorder(new LineBorder(Color.RED));
			attensionError("日付のフォーマットが無効です");
			return;
		}else if(!str.equals("")) {
			date_data.add(" AND returned_date >= '" + completeZero(str) + "'");
		}

		str = setting_datetextfield6.getText();
		if(!(str.equals("") || Lend.date_pattern.matcher(str).find())) {
			setting_datetextfield6.setBorder(new LineBorder(Color.RED));
			attensionError("日付のフォーマットが無効です");
			return;
		}else if(!str.equals("")) {
			date_data.add(" AND returned_date <= '" + completeZero(str) + "'");
		}

		executeSearch(date_data);
	}

	public void executeSearch(List<String> additional_args) {
		if(!(setting_datetextfield3.getText().equals("") && setting_datetextfield4.getText().equals(""))) {
			setting_database_combo.setSelectedIndex(1);
		}else if(!(setting_datetextfield5.getText().equals("") && setting_datetextfield6.getText().equals(""))) {
			setting_database_combo.setSelectedIndex(2);
			additional_args.add(" AND is_returned = '1'");
		}
		String query = "SELECT book_id, borrower_id, lend_date, due_date, returned_date FROM statuses WHERE true";

		String str = setting_book_id_textfield.getText();
		if(!str.equals("")) {
			query += " AND book_id = '" + str + "'";
		}
		str = setting_borrower_id_textfield.getText();
		if(!str.equals("")) {
			query += " AND borrower_id = " + str;
		}
		if(setting_database_combo.getSelectedIndex() == 1) {
			additional_args.add(" AND is_returned = '0'");
		}else if(setting_database_combo.getSelectedIndex() == 2) {
			additional_args.add(" AND is_returned = '1'");
		}


		for(String con : additional_args) {
			query += con;
		}
		ResultSet rs = sqlite.executeQuery(query + " ORDER BY book_id");
		if(rs != null) {
			List<Object[]> resultdata = new ArrayList<Object[]>();
			try {
				while(rs.next()){
					Object[] row = {rs.getString("book_id"), rs.getString("borrower_id"), rs.getString("lend_date"), rs.getString("due_date"), rs.getString("returned_date")};

					if(!this.is_show_returned_borrower && row[4] != null) row[1] = "-";
					if(!this.is_show_returned_due_date && row[4] != null) row[3] = "-";
					if(row[4] == null) row[4] = "-";
					resultdata.add(row);
				}
				tablemodel = new DefaultTableModel(columns3, 0);
				hit_table.setModel(tablemodel);
				hit_table.getColumnModel().getColumn(0).setPreferredWidth(100);
				hit_table.getColumnModel().getColumn(0).setMinWidth(100);
				hit_table.getColumnModel().getColumn(0).setMaxWidth(200);
				hit_table.getColumnModel().getColumn(1).setPreferredWidth(100);
				hit_table.getColumnModel().getColumn(1).setMinWidth(100);
				hit_table.getColumnModel().getColumn(1).setMaxWidth(200);


				for(Object[] row : resultdata) {
					tablemodel.addRow(row);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			attensionError("検索処理中にエラーが発生しました。再度お試しください。");
		}

	}

	public void clearParams() {
		borrower_textfield.setText("");
		book_id_textfield.setText("");
		clearUserData();
	}


	public String completeZero(String str) {
		if(!Lend.date_pattern.matcher(str).find()) return null;
		String[] splited = str.split("/");
		for(int j = 1; j < 3; j++) {
			if(splited[j].length() == 1) {
				splited[j] = '0' + splited[j];
			}
		}
		return splited[0] + '/' + splited[1] + '/' + splited[2];
	}

	public void attensionError(String message) {
		Toolkit.getDefaultToolkit().beep();
		JLabel l = new JLabel(message);
		l.setFont(new Font(l.getFont().getName(), Font.BOLD, 14));
		JOptionPane.showMessageDialog(this, l);
	}

	public void updateUserData() {
		ResultSet rs = sqlite.executeQuery("SELECT * FROM statuses WHERE borrower_id = " + borrower_textfield.getText() + " AND is_returned = '0' ORDER BY lend_date"),
				 rs1 = sqlite.executeQuery("SELECT * FROM statuses WHERE borrower_id = " + borrower_textfield.getText() + " AND is_returned = '0' AND due_date <= '" + Lend.getString(Calendar.getInstance()) + "'");
		if(rs != null && rs1 != null) {
			try {
				int lending_count = 0, over_dues = 0;
				DefaultTableModel user_model = new DefaultTableModel(columns2,0) {
					private static final long serialVersionUID = 1L;
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				List<Object[]> result_data = new ArrayList<Object[]>();
				while(rs.next()) {
					lending_count++;
					Object[] row = {lending_count, rs.getString("book_id"), rs.getString("lend_date"), rs.getString("due_date")};
					result_data.add(row);
				}

				while(rs1.next()) {
					over_dues++;
				}

				for(Object[] row : result_data) {
					user_model.addRow(row);
				}
				lended_table.setModel(user_model);
				lended_table.getColumnModel().getColumn(0).setPreferredWidth(50);
				lended_table.getColumnModel().getColumn(0).setMaxWidth(50);
				if (result_data.size() >= 1) {
					table.setModel(new DefaultTableModel(new Object[][] {{max_lendable_books, lending_count, result_data.get(0)[3]}}, columns1) {
						private static final long serialVersionUID = 1L;
						@Override
						public boolean isCellEditable(int row, int column) {
							return false;
						}
					});
				}else {
					table.setModel(new DefaultTableModel(new Object[][] {{max_lendable_books, lending_count, ""}}, columns1) {
						private static final long serialVersionUID = 1L;
						@Override
						public boolean isCellEditable(int row, int column) {
							return false;
						}
					});
				}
				table.setEnabled(true);
				table.getColumnModel().getColumn(0).setPreferredWidth(62);
				table.getColumnModel().getColumn(1).setPreferredWidth(63);
				table.getColumnModel().getColumn(2).setPreferredWidth(97);

				lu_label2.setText(String.valueOf(max_lendable_books) + "冊");
				ru_label2.setText(String.valueOf(lending_count) + "冊");
				ld_label2.setText(String.valueOf(max_lendable_books - lending_count) + "冊");
				rd_label2.setText(String.valueOf(over_dues) + "冊");


			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void clearUserData() {
		table.setModel(new DefaultTableModel(new Object[][]{{"","",""}},columns1));
		table.getColumnModel().getColumn(0).setPreferredWidth(62);
		table.getColumnModel().getColumn(1).setPreferredWidth(63);
		table.getColumnModel().getColumn(2).setPreferredWidth(97);
		table.setEnabled(false);
		lended_table.setModel(new DefaultTableModel(columns2, 0));
		lended_table.getColumnModel().getColumn(0).setPreferredWidth(50);
		lended_table.getColumnModel().getColumn(0).setMaxWidth(50);
		lu_label2.setText("0冊");
		ru_label2.setText("0冊");
		ld_label2.setText("0冊");
		rd_label2.setText("0冊");
	}

	public void setDefault_lend_period(int default_lend_period) {
		this.default_lend_period = default_lend_period;
	}

	public void setMax_lendable_books(int max_lendable_books) {
		this.max_lendable_books = max_lendable_books;
	}

	public void setIs_show_returned_borrower(boolean is_show_returned_borrower) {
		this.is_show_returned_borrower = is_show_returned_borrower;
	}

	public void setIs_show_returned_due_date(boolean is_show_returned_due_date) {
		this.is_show_returned_due_date = is_show_returned_due_date;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_F7:
				lending.doClick();
				break;
			case KeyEvent.VK_F8:
				returning.doClick();
				break;
			case KeyEvent.VK_F6:
				searching.doClick();
				break;
			case KeyEvent.VK_ENTER:
				Component c = e.getComponent();
				if(c.equals(borrower_textfield)) {
					book_id_textfield.requestFocus();
				}else if(c.equals(book_id_textfield) && current_mode == 0 || current_mode == 1) execute_button.doClick();
				break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(save_file_button)) {
			FileDialog fd = new FileDialog(this, "名前を付けて保存", FileDialog.SAVE);
			try {
				fd.setVisible(true);
				if(fd.getFile() != null) {
					File file = new File(fd.getDirectory() + fd.getFile());
				      try {
						PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
						TableModel tm = hit_table.getModel();
						for(int i = 0; i < tm.getRowCount(); i++) {
							for(int j = 0; j < tm.getColumnCount(); j++) {
								if(j != 0)  pw.write(", ");
								pw.print(tm.getValueAt(i, j).toString());
							}
							pw.println();
						}
						pw.close();
				      } catch (IOException e1) {
				    	  e1.printStackTrace();
				      }
				}
			} finally {
				fd.dispose();
			}
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		Component c = e.getComponent();
		if(c instanceof JTextField) {
			JTextField txt = (JTextField)c;
			txt.setBorder(new LineBorder(SELECTED_BUTTON_COLOR, 2));
		}

	}

	@Override
	public void focusLost(FocusEvent e) {
		Component c = e.getComponent();
		if(c instanceof JTextField) {
			JTextField txt = (JTextField)c;
			txt.setBorder(new JTextField().getBorder());
			if(txt.equals(borrower_textfield) && Lend.borrower_pattern.matcher(borrower_textfield.getText()).find()) {
				updateUserData();
			}
		}
	}
}
