package de.telekom.jdbcchallenge;

import java.sql.DriverManager;
import java.sql.Connection;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException {

		final String DRIVER = "org.mariadb.jdbc.Driver";
		Class.forName(DRIVER);
		try {
			Connection connection = DriverManager
					.getConnection("jdbc:mariadb://localhost:3306/seadb?user=seauser&password=seapass");
		} catch (Exception e) { }
	}
}
