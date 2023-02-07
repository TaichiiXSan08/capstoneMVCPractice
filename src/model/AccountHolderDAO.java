package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class AccountHolderDAO extends DatabaseConnection {

	// insert AccountHolder details to db
	public void insertAccountHolderToDB(Connection con, AccountHolder accountHolder) {
		PreparedStatement stmt;

		try {
			stmt = con.prepareStatement("INSERT INTO ACCOUNTHOLDER (firstname,lastname,address) " + "VALUES(?,?,?)");
			stmt.setString(1, accountHolder.getFirstName());
			stmt.setString(2, accountHolder.getLastName());
			stmt.setString(3, accountHolder.getAddress());
			stmt.executeUpdate();

		} catch (SQLException e) {
			//// e.printStackTrace();
			System.out.println("\nRecords below were not saved. Please try again.");
		}
	}

	// selects latest record of AccountHolder
	public AccountHolder selectNewAccountHolderID(Connection con, AccountHolder accountHolder) {
		try {
			PreparedStatement stmt;
			ResultSet rs;

			stmt = con
					.prepareStatement("SELECT lpad(accountnumber,4,'0'),firstname,lastname,address FROM accountholder "
							+ " ORDER BY 1 DESC LIMIT 1");

			rs = stmt.executeQuery();

			if (rs.next()) {
				do {
					accountHolder.setAccountNumber(rs.getString(1));
					accountHolder.setFirstName(rs.getString(2));
					accountHolder.setLastName(rs.getString(3));
					accountHolder.setAddress(rs.getString(4));
				} while (rs.next());
			}

			return accountHolder;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return accountHolder;
		}
	}

	// get specific accountholder record based on account number
	public AccountHolder selectAccountHolder(Connection con, AccountHolder accountHolder, String input) {
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM accountholder WHERE accountnumber = '" + input + "'");
			while (rs.next()) {
				accountHolder.setAccountNumber(rs.getString(1));
				accountHolder.setFirstName(rs.getString(2));
				accountHolder.setLastName(rs.getString(3));
				accountHolder.setAddress(rs.getString(4));
			}

			if (accountHolder.getAccountNumber() == null)
				System.out.println("Account Number not found");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("Connection failed.");
		}
		return accountHolder;

	}

	// get multiple AccountHolderRecords
	public ArrayList<Object> selectAccountHolderRecords(Connection con, ArrayList<Object> recordList,
			AccountHolder accountHolder) {
		PreparedStatement stmt;
		ResultSet rs;
		int ctr = 0;

		try {
			stmt = con.prepareStatement("SELECT a.*,p.*,ph.* FROM accountholder a\r\n"
					+ "JOIN accountpolicydetail apd ON apd.accountnumber = a.accountnumber\r\n"
					+ "JOIN policy p ON p.policyid = apd.policyid\r\n"
					+ "JOIN policyholder ph ON apd.policyholderid = ph.policyholderid\r\n"
					+ "WHERE a.firstname = ? AND a.lastname = ?;");
			stmt.setString(1, ACTIVE);
			stmt.setString(2, ACTIVE);
			rs = stmt.executeQuery();

			if (rs.next()) {
				do {
					accountHolder.setAccountNumber(rs.getString(1));
					accountHolder.setFirstName(rs.getString(2));
					accountHolder.setLastName(rs.getString(3));
					accountHolder.setAddress(rs.getString(4));

					recordList.add(new Policy());
					((Policy) recordList.get(ctr)).setPolicyId(rs.getString(5));
					((Policy) recordList.get(ctr)).setAccountNumber(rs.getString(6));
					((Policy) recordList.get(ctr)).setEffectiveDate(LocalDate.parse(rs.getString(7)));
					((Policy) recordList.get(ctr)).setExpirationDate(LocalDate.parse(rs.getString(8)));
					((Policy) recordList.get(ctr)).setPolicyHolder(rs.getString(9));
					((Policy) recordList.get(ctr)).setPolicyPremium(rs.getDouble(10));
					((Policy) recordList.get(ctr)).setStatus(rs.getString(11));
					ctr++;

					recordList.add(new PolicyHolder());
					((PolicyHolder) recordList.get(ctr)).setPolicyHolderId(rs.getInt(12));
					((PolicyHolder) recordList.get(ctr)).setFirstName(rs.getString(13));
					((PolicyHolder) recordList.get(ctr)).setLastName(rs.getString(14));
					((PolicyHolder) recordList.get(ctr)).setDateOfBirth(LocalDate.parse(rs.getString(15)));
					((PolicyHolder) recordList.get(ctr)).setAddress(rs.getString(16));
					((PolicyHolder) recordList.get(ctr)).setLicenseNumber(rs.getString(17));
					((PolicyHolder) recordList.get(ctr)).setLicenseIssued(LocalDate.parse(rs.getString(18)));
					ctr++;
				} while (rs.next());
			}
			return recordList;
		} catch (Exception e) {
			// TODO: handle exception
			return recordList;
		}
	}

	// get first and lastname of an accountholder
	public AccountHolder selectFirstLastName(Connection con, AccountHolder accountHolder) {
		PreparedStatement stmt;
		ResultSet rs;

		try {
			stmt = con.prepareStatement("SELECT lpad(accountnumber,4,0), firstname, lastname, address "
					+ "FROM accountholder WHERE firstname = ? AND " + "lastname = ?");

			stmt.setString(1, accountHolder.getFirstName());
			stmt.setString(2, accountHolder.getLastName());

			rs = stmt.executeQuery();
			if (rs.next()) {
				do {
					accountHolder.setAccountNumber(rs.getString(1));
					accountHolder.setFirstName(rs.getString(2));
					accountHolder.setLastName(rs.getString(3));
					accountHolder.setAddress(rs.getString(4));
				} while (rs.next());
				return accountHolder;
			} else {
				System.out.println(
						"No record with the name: " + accountHolder.getFirstName() + " " + accountHolder.getLastName());
				accountHolder.reset();
				return accountHolder;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return accountHolder;
		}
	}

}
