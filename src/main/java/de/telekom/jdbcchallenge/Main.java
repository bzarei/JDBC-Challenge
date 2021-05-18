package de.telekom.jdbcchallenge;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException {

		final String DRIVER = "org.mariadb.jdbc.Driver";
		Class.forName(DRIVER);

		try (Connection connection = DriverManager
				.getConnection("jdbc:mariadb://localhost:3306/seadb?user=seauser&password=seapass");) {

/*			String delsql = "DELETE FROM personen where id in(1,2)";
			PreparedStatement delps = connection.prepareStatement(delsql);
*/
			String insert = "INSERT INTO personen (ID, ANREDE, VORNAME, NACHNAME) VALUES ( ?, ?, ?, ? )";
			try (PreparedStatement ps = connection.prepareStatement(insert);) {
				ps.setLong(1, 1L);
				ps.setShort(2, (short) 2);
				ps.setString(3, "Baharak");
				ps.setString(4, "Zarei");
				ps.execute();

				ps.setLong(1, 2L);
				ps.setShort(2, (short) 1);
				ps.setString(3, "Henrick");
				ps.setString(4, "Mustermann");
				ps.execute();

				String select = "SELECT * FROM personen";
				try (Statement statement = connection.createStatement();) {
					statement.executeQuery(select);
					try (ResultSet resultset = statement.executeQuery(select);) {
						System.out.println("+----+--------+---------+------------+");
						System.out.println("| ID | ANREDE | VORNAME | NACHNAME   |");
						System.out.println("+----+--------+---------+------------+");
						while (resultset.next()) {
							System.out.println("|  " + resultset.getLong(1) 
								+ " |      " + resultset.getShort(2) 
								+ " | " + resultset.getString(3)
								+ " | " + resultset.getString(4));
						}
					} catch (Exception ex) { } // resultset.close();
				} catch (Exception ex) { } // statement.close();
			} catch (Exception ex) { } // statement.close();
		} catch (Exception ex) { } // connection.close();
	}
} // Ende Klasse Main
