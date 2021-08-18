package com.maple32768.bms;
import java.sql.*;

public class SQL {

	public Connection connection = null;


	public SQL(String path) {
		try {
			Class.forName("org.sqlite.JDBC");

			connection = DriverManager.getConnection("jdbc:sqlite:" + path);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ResultSet executeQuery(String query) {
		return executeQuery(query, new Object[] {});
	}

	public ResultSet executeQuery(String query, Object[] query_args) {
		ResultSet rs = null;
		try {
			PreparedStatement st = connection.prepareStatement(query);
			for(int i = 0; i < query_args.length; i++) {
				Object arg = query_args[i];
				if (arg instanceof Integer) {
					st.setInt(i + 1, Integer.parseInt(arg.toString()));
				}else if(arg instanceof String) {
					st.setString(i + 1, arg.toString());
				}else if (arg instanceof Double) {
					st.setDouble(i + 1, Double.parseDouble(arg.toString()));
				}
			}
			rs = st.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void executeUpdate(String query) {
		executeUpdate(query, new Object[] {});
	}

	public boolean executeUpdate(String query, Object[] query_args) {
		try {
			PreparedStatement st = connection.prepareStatement(query);
			for(int i = 0; i < query_args.length; i++) {
				Object arg = query_args[i];
				if (arg instanceof Integer) {
					st.setInt(i + 1, Integer.parseInt(arg.toString()));
				}else if(arg instanceof String) {
					st.setString(i + 1, arg.toString());
				}else if (arg instanceof Double) {
					st.setDouble(i + 1, Double.parseDouble(arg.toString()));
				}
			}
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void close() {
		try {
			if(this.connection != null) {
				this.connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
