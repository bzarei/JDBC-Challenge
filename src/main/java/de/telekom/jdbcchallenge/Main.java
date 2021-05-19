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
			
			// delete 2 resords in table personen
			String query = "DELETE FROM personen WHERE id in(1,2)";
			try (PreparedStatement delps = connection.prepareStatement(query);) {
				delps.execute();
				
				// adding 2 resords (persos) into the table personen
				query = "INSERT INTO personen (ID, ANREDE, VORNAME, NACHNAME) VALUES ( ?, ?, ?, ? )";
				try (PreparedStatement ps = connection.prepareStatement(query);) {
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
					
					// change lastname of a person with id=2
					query = "UPDATE personen SET nachname='Changed' WHERE id=2";
					PreparedStatement updateps = connection.prepareStatement(query);
					updateps.execute();
					
					/**
					 * create SELECT statement and execute it
					 */
					query = "SELECT * FROM personen";
					try (Statement statement = connection.createStatement();) {
						try (ResultSet resultset = statement.executeQuery(query);) {
							System.out.println("+----+--------+---------+------------+");
							System.out.println("| ID | ANREDE | VORNAME | NACHNAME   |");
							System.out.println("+----+--------+---------+------------+");
							while (resultset.next()) {
								System.out.println("|  " + resultset.getLong(1) + " |      " + resultset.getShort(2)
										+ " | " + resultset.getString(3) + " | " + resultset.getString(4));
							}
						} catch (Exception ex) {
							System.out.println(" Fehler beim SELECT: executeQuery! ");
						  } // resultset.close();
					} catch (Exception ex) {
						System.out.println(" Fehler beim SELECT: createStatement! ");
					  } // statement.close();
				} catch (Exception ex) {
					System.out.println(" Fehler beim INSERT: prepareStatement(insert)! ");
				  } // statement.close();
			} catch (Exception ex) {
				System.out.println(" Fehler beim DELETE: prepareStatement(delete)! ");
			  } // statement.close();
		} catch (Exception ex) {
			System.out.println(" Fehler beim Connection! ");
		  } // connection.close();
	}
} // Ende Klasse Main
