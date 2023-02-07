package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class PolicyDAO extends DatabaseConnection {

	// insert Policy details to db
	public void insertPolicyToDB(Connection con, AccountHolder accountHolder, Policy policy) {
		PreparedStatement stmt;

		// policy table
		try {
			stmt = con.prepareStatement(
					"INSERT INTO POLICY (accountnumber,effectivedate,expirationdate,policyholder,policypremium) "
							+ "VALUES(?,?,?,?,?)");

			stmt.setString(1, policy.getAccountNumber());
			stmt.setObject(2, policy.getEffectiveDate());
			stmt.setObject(3, policy.getExpirationDate());
			stmt.setString(4, accountHolder.getFirstName() + " " + accountHolder.getLastName());
			stmt.setDouble(5, policy.getPolicyPremium());

			stmt.executeUpdate();

		} catch (SQLException e) {
			// e.printStackTrace();
		}
	}

	// get latest policy record
	public Policy selectNewPolicy(Connection con, Policy policy) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT LPAD(policyid,6,0),accountnumber, effectivedate, expirationdate, policyholder, policypremium,"
							+ "status FROM policy ORDER BY 1 DESC LIMIT 1");
			while (rs.next()) {
				policy.setPolicyId(rs.getString(1));
				policy.setAccountNumber(rs.getString(2));
				policy.setEffectiveDate(LocalDate.parse(rs.getString(3)));
				policy.setExpirationDate(LocalDate.parse(rs.getString(4)));
				policy.setPolicyHolder(rs.getString(5));
				policy.setPolicyPremium(rs.getDouble(6));
				policy.setStatus(rs.getString(7));
			}
			return policy;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return policy;
		}
	}

	// updates a policy to cancelled status.
	public void updatePolicyIsCancelled(Connection con, Policy policy) {
		PreparedStatement stmt;

		try {
			stmt = con.prepareStatement("UPDATE policy set status = ? WHERE policyid = ?");
			stmt.setString(1, CANCELLED);
			stmt.setString(2, policy.getPolicyId());
			stmt.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	// gets a specific valid policy
	public Policy selectValidPolicy(Connection con, Policy policy, String input) {
		PreparedStatement stmt;
		ResultSet rs;

		try {
			stmt = con.prepareStatement("SELECT lpad(policyid,6,0), lpad(accountnumber,4,0),effectivedate,\r\n"
					+ "expirationdate, policyholder, policypremium, status FROM policy\r\n"
					+ "WHERE policyid =? AND (status = ? OR status = ?);");
			stmt.setString(1, input);
			stmt.setString(2, ACTIVE);
			stmt.setString(3, INACTIVE);
			rs = stmt.executeQuery();

			if (rs.next()) {
				do {
					policy.setPolicyId(rs.getString(1));
					policy.setAccountNumber(rs.getString(2));
					policy.setEffectiveDate(LocalDate.parse(rs.getString(3)));
					policy.setExpirationDate(LocalDate.parse(rs.getString(4)));
					policy.setPolicyHolder(rs.getString(5));
					policy.setPolicyPremium(rs.getDouble(6));
					policy.setStatus(rs.getString(7));
				} while (rs.next());
			} else {
				System.out.println("Policy doesn't exist, is cancelled or expired.");
				policy.setPolicyId(null);
			}

			return policy;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return policy;
		}
	}

	// gets a specific policy valid for filing a claim
	public Policy selectClaimablePolicy(Connection con, Policy policy, String input) {
		PreparedStatement stmt;
		ResultSet rs;

		try {
			stmt = con.prepareStatement("SELECT lpad(policyid,6,0), lpad(accountnumber,4,0),effectivedate,\r\n"
					+ "expirationdate, policyholder, policypremium, status FROM policy\r\n"
					+ "WHERE policyid =? AND status = ?;");
			stmt.setString(1, input);
			stmt.setString(2, ACTIVE);
			rs = stmt.executeQuery();

			if (rs.next()) {
				do {
					policy.setPolicyId(rs.getString(1));
					policy.setAccountNumber(rs.getString(2));
					policy.setEffectiveDate(LocalDate.parse(rs.getString(3)));
					policy.setExpirationDate(LocalDate.parse(rs.getString(4)));
					policy.setPolicyHolder(rs.getString(5));
					policy.setPolicyPremium(rs.getDouble(6));
					policy.setStatus(rs.getString(7));
				} while (rs.next());
			} else
				System.out.println("Invalid policy, Policy can be expired, not existing, cancelled or inactive.");

			return policy;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return policy;
		}
	}

	// gets a specific policy
	public Policy selectPolicy(Connection con, Policy policy, String input) {
		PreparedStatement stmt;
		ResultSet rs;

		try {
			stmt = con.prepareStatement("SELECT lpad(policyid,6,0), lpad(accountnumber,4,0),effectivedate,\r\n"
					+ "expirationdate, policyholder, policypremium, status FROM policy\r\n" + "WHERE policyid =?;");
			stmt.setString(1, input);
			rs = stmt.executeQuery();

			if (rs.next()) {
				do {
					policy.setPolicyId(rs.getString(1));
					policy.setAccountNumber(rs.getString(2));
					policy.setEffectiveDate(LocalDate.parse(rs.getString(3)));
					policy.setExpirationDate(LocalDate.parse(rs.getString(4)));
					policy.setPolicyHolder(rs.getString(5));
					policy.setPolicyPremium(rs.getDouble(6));
					policy.setStatus(rs.getString(7));
				} while (rs.next());
			} else {
				System.out.println("Policy doesn't exist.");
			}

			return policy;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return policy;
		}
	}

	// updates policy premium of a certain record
	public Policy updatePolicyPremium(Connection con, double premiumTotal, Policy policy) {
		try {
			PreparedStatement stmt;

			stmt = con.prepareStatement("UPDATE policy SET policypremium = ? where policyid = ?");
			stmt.setDouble(1, premiumTotal);
			stmt.setString(2, policy.getPolicyId());
			stmt.executeUpdate();

			policy.setPolicyPremium(premiumTotal);

			return policy;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return policy;
		}

	}

}
