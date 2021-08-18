package com.maple32768.bms;

import java.util.Calendar;
import java.util.regex.Pattern;

public class Lend {

		public static final Pattern borrower_pattern = Pattern.compile("^[0-9]{5,8}$");
		public static final Pattern book_pattern = Pattern.compile("^[A-Z]{1}[0-9]{7}$");
		public static final Pattern date_pattern = Pattern.compile("^[0-9]{4}/[0-9]{1,2}/[0-9]{1,2}$");

		public String book_id;
		public int borrower_id;
		public Calendar lend_date = Calendar.getInstance();
		public Calendar due_date =  Calendar.getInstance();


	public Lend(String book_id, int borrower_id, Calendar lend_day, Calendar due_day) {
		this(book_id, borrower_id, lend_day, 0);
		this.due_date = (Calendar) due_day.clone();
	}

	public Lend(String book_id, int borrower_id, Calendar lend_day, int lend_period) {
		this.book_id = book_id;
		this.borrower_id = borrower_id;
		this.lend_date = (Calendar) lend_day.clone();
		this.due_date = (Calendar) this.lend_date.clone();
		this.due_date.add(Calendar.DATE, lend_period);
	}

	public static String getInsertCommand() {
		return "INSERT INTO statuses(book_id, borrower_id, lend_date, due_date) values(?, ?, ?, ?)";
	}

	public Object[] getInsertArgs() {
		String lend = String.valueOf(this.lend_date.get(Calendar.YEAR)) + '/' + String.valueOf(this.lend_date.get(Calendar.MONTH) + 1) + '/' + String.valueOf(this.lend_date.get(Calendar.DATE)),
				due = String.valueOf(this.due_date.get(Calendar.YEAR)) + '/' + String.valueOf(this.due_date.get(Calendar.MONTH) + 1) + '/' + String.valueOf(this.due_date.get(Calendar.DATE));
			lend = completeZero(lend);
			due = completeZero(due);
			return new Object[] {this.book_id, this.borrower_id, lend, due};
	}

	public static String getReturnCommand() {
		return "UPDATE statuses SET is_returned = '1', returned_date = ? WHERE book_id = ? AND is_returned = '0'";
	}

	public static Object[] getReturnArgs(String book_id) {
		return getReturnArgs(book_id, Calendar.getInstance());
	}

	private static Object[] getReturnArgs(String book_id, Calendar return_date) {
		String returned = String.valueOf(return_date.get(Calendar.YEAR)) + '/' + String.valueOf(return_date.get(Calendar.MONTH) + 1) + '/' + String.valueOf(return_date.get(Calendar.DATE));
			returned = completeZero(returned);
		return new Object[] {returned, book_id};
	}

	public static Calendar getCalendar(int y, int m, int d) {
		Calendar result = Calendar.getInstance();
		result.set(y, m - 1, d);
		return result;
	}

	public static Calendar getCalendar(String str) {
		String[] splited = str.split("/");
		int[] values = new int[3];
		for(int i = 0; i < 3; i++) {
			values[i] = Integer.parseInt(splited[i]);
		}
		return getCalendar(values[0], values[1], values[2]);
	}

	public static int[] getDate(Calendar cl) {
		int[] result = new int[3];
		result[0] = cl.get(Calendar.YEAR);
		result[1] = cl.get(Calendar.MONTH) + 1;
		result[2] = cl.get(Calendar.DAY_OF_MONTH);
		return result;
	}

	public static String getString(Calendar cl) {
		String date = String.valueOf(cl.get(Calendar.YEAR)) + '/' + String.valueOf((cl.get(Calendar.MONTH) + 1)) + '/' + String.valueOf(cl.get(Calendar.DATE));
		return completeZero(date);
	}

	public static String completeZero(String date) {
		String[] splited = date.split("/");
		for(int i = 1; i < 3; i++) {
			if(splited[i].length() == 1) {
				splited[i] = '0' + splited[i];
			}
		}
		return splited[0] + '/' + splited[1] + '/' + splited[2];
	}

}
