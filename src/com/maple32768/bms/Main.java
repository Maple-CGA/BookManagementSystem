package com.maple32768.bms;

import java.awt.Component;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

import com.maple32768.bms.BMS;

public class Main {

	public static String property_path = "./data/bms.properties";


	public static void main(String[] args) {
		BMS frame = new BMS();
		frame.setVisible(true);
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(property_path));
			String default_lend_period = prop.getProperty("default_lend_period"),
					max_lendable_books = prop.getProperty("max_lendable_books"),
					is_show_returned_borrower = prop.getProperty("is_show_returned_borrower"),
					is_show_returned_due_date = prop.getProperty("is_show_returned_due_date");
			if(default_lend_period == null || max_lendable_books == null || is_show_returned_borrower == null || is_show_returned_due_date == null) {
				frame.attensionError("設定ファイルに必要な値がありません。設定を確認してください。\n現在各設定は既定値になっています。");
			}else {
				frame.setDefault_lend_period(Integer.parseInt(default_lend_period));
				frame.setMax_lendable_books(Integer.parseInt(max_lendable_books));
				frame.setIs_show_returned_borrower(Boolean.parseBoolean(is_show_returned_borrower));
				frame.setIs_show_returned_due_date(Boolean.parseBoolean(is_show_returned_due_date));
			}
		} catch (IOException e) {
			e.printStackTrace();
			frame.attensionError("設定ファイルを読み込めませんでした。ファイルを確認の上、再度お試しください。\n現在各設定は既定値として設定されています。");
		}
		Class<?> clazz;
		try {
			clazz = Class.forName("com.maple32768.bms.BMS");
			for(Field field : clazz.getFields()){
				Object o = field.get(null);
				if(o instanceof Component) {
			        ((Component) o).addFocusListener(frame);
			        ((Component) o).addKeyListener(frame);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}
}
