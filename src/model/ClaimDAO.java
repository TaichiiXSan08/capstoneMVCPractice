package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ClaimDAO extends DatabaseConnection {

	// get a specific claim
	public Claim selectClaim(Connection con, Claim claim, String input) {
		PreparedStatement stmt;
		ResultSet rs;

		try {
			stmt = con.prepareStatement("SELECT CONCAT('C',claimid),dateofaccident, address, "
					+ "description, description2, estimatedcostofrepairs," + "policyid FROM CLAIM "
					+ "where claimid = ?;");

			stmt.setString(1, input.substring(1, 6));
			rs = stmt.executeQuery();

			if (rs.next()) {
				do {
					claim.setClaimId(rs.getString(1));
					claim.setDateOfAccident(LocalDate.parse(rs.getString(2)));
					claim.setAddress(rs.getString(3));
					claim.setDescription(rs.getString(4));
					claim.setDescription2(rs.getString(5));
					claim.setEstimatedCostOfRepairs(rs.getDouble(6));
					claim.setPolicyId(rs.getString(7));
				} while (rs.next());
			} else {
				System.out.println("Claim ID not found.");
				return claim;
			}
			return claim;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return claim;
		}
	}

	// insert claims details to db
	public void insertClaims(Connection con, Claim claim, Policy policy) {
		PreparedStatement stmt;

		try {
			stmt = con.prepareStatement(
					"INSERT INTO Claim (dateofaccident, address, description, description2, estimatedcostofrepairs, policyid)"
							+ "VALUES(?,?,?,?,?,?)");
			stmt.setObject(1, claim.getDateOfAccident());
			stmt.setString(2, claim.getAddress());
			stmt.setString(3, claim.getDescription());
			stmt.setString(4, claim.getDescription2());
			stmt.setDouble(5, claim.getEstimatedCostOfRepairs());
			stmt.setString(6, policy.getPolicyId());
			stmt.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	// get latest claimid
	public Claim selectClaimId(Connection con, Claim claim) {
		PreparedStatement stmt;
		ResultSet rs;

		try {
			stmt = con.prepareStatement(
					"SELECT CONCAT('C',claimid),dateofaccident, address, description, description2, estimatedcostofrepairs,\r\n"
							+ "policyid FROM CLAIM ORDER BY 1 DESC LIMIT 1;");
			rs = stmt.executeQuery();

			while (rs.next()) {
				claim.setClaimId(rs.getString(1));
				claim.setDateOfAccident(LocalDate.parse(rs.getString(2)));
				claim.setAddress(rs.getString(3));
				claim.setDescription(rs.getString(4));
				claim.setDescription2(rs.getString(5));
				claim.setEstimatedCostOfRepairs(rs.getDouble(6));
				claim.setPolicyId(rs.getString(7));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return claim;
	}

}
