package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class PolicyHolderDAO extends DatabaseConnection {

	// insert PolicyHolder details to db
	public void insertPolicyHolderToDB(Connection con, AccountHolder accountHolder, PolicyHolder policyHolder) {
		PreparedStatement stmt;

		try {
			stmt = con.prepareStatement(
					"INSERT INTO POLICYHOLDER (firstname,lastname,dateofbirth,address,driverslicensenumber,licenseissued) "
							+ "VALUES(?,?,?,?,?,?)");

			stmt.setString(1, policyHolder.getFirstName());
			stmt.setString(2, policyHolder.getLastName());
			stmt.setObject(3, policyHolder.getDateOfBirth());
			stmt.setString(4, policyHolder.getAddress());
			stmt.setString(5, policyHolder.getLicenseNumber());
			stmt.setObject(6, policyHolder.getLicenseIssued());

			stmt.executeUpdate();

		} catch (SQLException e) {
			// e.printStackTrace();
		}
	}

	// checks if policyholder exists
	public PolicyHolder selectSpecificPolicyHolder(Connection con, PolicyHolder policyHolder) {
		PreparedStatement stmt;
		ResultSet rs;
		try {
			stmt = con.prepareStatement("SELECT * FROM policyHolder where firstName = ? and lastname = ?");
			stmt.setString(1, policyHolder.getFirstName());
			stmt.setString(2, policyHolder.getLastName());
			rs = stmt.executeQuery();

			if (rs.next()) {
				policyHolder.setAccountNumber(rs.getString(1));
				policyHolder.setFirstName(rs.getString(2));
				policyHolder.setLastName(rs.getString(3));
				policyHolder.setDateOfBirth(LocalDate.parse(rs.getString(4)));
				policyHolder.setAddress(rs.getString(5));
				policyHolder.setLicenseNumber(rs.getString(6));
				policyHolder.setLicenseIssued(LocalDate.parse(rs.getString(7)));
				return policyHolder;
			} else
				throw new Exception();

		} catch (Exception e) {
			return policyHolder;
		}
	}

	// get latest policy holder record
	public PolicyHolder selectNewPolicyHolderId(Connection con, PolicyHolder policyHolder) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT policyholderid FROM policyholder ORDER BY 1 DESC LIMIT 1");
			while (rs.next()) {
				policyHolder.setPolicyHolderId(rs.getInt(1));
			}
			return policyHolder;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return policyHolder;
		}
	}

}
