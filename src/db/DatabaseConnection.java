/**
 * @Class: DatabaseConnection
 * @Description: All database related queries will be handled
 * by this class. From connection to database, creation
 * of database objects etc.
 * @author CalvinLingat
 * @Created:8/15/2022 
 */
package db;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseConnection {
	private final String URL = "jdbc:mysql://localhost:3306/";
	private final String USERNAME = "root";
	private final String PASSWORD = "test123";
	private final String DBNAME = "capstoned";
	private final String ACTIVE = "active", INACTIVE = "inactive", CANCELLED = "cancelled";

	// connects to the database using the constant values for URL,USERNAME and
	// PASSWORD
	public Connection connectServer() {
		try {
			Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			return con;
		} catch (SQLException e) {
			Connection con = null;
			System.out.println("Connection to the Server failed\n" + e);
			return con;
		}
	}

	// connects to the database and server using the constant values for
	// URL,USERNAME and PASSWORD
	public Connection connectDB() {
		try {
			Connection con = DriverManager.getConnection(URL + DBNAME, USERNAME, PASSWORD);
			return con;
		} catch (SQLException e) {
			Connection con = null;
			System.out.println("Connection to the DB failed\n" + e);
			return con;
		}
	}

	// Creates database if no capstone database detected.
	public void createDatabase(Connection con) {
		try {
			Statement stmt = con.createStatement();
			stmt.execute("CREATE DATABASE IF NOT EXISTS " + DBNAME);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Create core tables, seed data for ease of checking. real-life scenario this
	 * wont be needed since database are already existing before a system is
	 * created.
	 */
	public void createTables(Connection con) {
		try {
			Statement stmt = con.createStatement();
			stmt.execute("USE " + DBNAME + ";");
			stmt.execute("CREATE TABLE IF NOT EXISTS `accountholder` (\r\n"
					+ "  `accountnumber` int(4) unsigned zerofill NOT NULL AUTO_INCREMENT,\r\n"
					+ "  `firstname` varchar(45) DEFAULT NULL,\r\n" + "  `lastname` varchar(45) DEFAULT NULL,\r\n"
					+ "  `address` varchar(450) DEFAULT NULL,\r\n" + "  PRIMARY KEY (`accountnumber`),\r\n"
					+ "  UNIQUE KEY `account holder_UNIQUE` (`accountnumber`),\r\n"
					+ "  UNIQUE KEY `firstname_UNIQUE` (`firstname`),\r\n"
					+ "  UNIQUE KEY `lastname_UNIQUE` (`lastname`)\r\n"
					+ ") ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `policy` (\r\n"
					+ "  `policyid` int(6) unsigned zerofill NOT NULL AUTO_INCREMENT,\r\n"
					+ "  `accountnumber` int(4) unsigned zerofill DEFAULT NULL,\r\n"
					+ "  `effectivedate` date DEFAULT NULL,\r\n" + "  `expirationdate` date DEFAULT NULL,\r\n"
					+ "  `policyholder` varchar(150) NOT NULL,\r\n" + "  `policypremium` decimal(7,2) DEFAULT NULL,\r\n"
					+ "  `status` varchar(50) NOT NULL DEFAULT 'inactive',\r\n" + "  PRIMARY KEY (`policyid`),\r\n"
					+ "  UNIQUE KEY `policyid_UNIQUE` (`policyid`),\r\n"
					+ "  KEY `account_num_fk_idx` (`accountnumber`),\r\n"
					+ "  CONSTRAINT `account_num_fk` FOREIGN KEY (`accountnumber`) REFERENCES `accountholder` (`accountnumber`)\r\n"
					+ ") ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

			stmt.execute("CREATE TABLE IF NOT EXISTS `policyholder` (\r\n"
					+ "  `policyholderid` int NOT NULL AUTO_INCREMENT,\r\n"
					+ "  `firstname` varchar(45) DEFAULT NULL,\r\n" + "  `lastname` varchar(45) DEFAULT NULL,\r\n"
					+ "  `dateofbirth` date DEFAULT NULL,\r\n" + "  `address` varchar(450) DEFAULT NULL,\r\n"
					+ "  `driverslicensenumber` varchar(100) DEFAULT NULL,\r\n"
					+ "  `licenseissued` date DEFAULT NULL,\r\n" + "  PRIMARY KEY (`policyholderid`),\r\n"
					+ "  UNIQUE KEY `policyholderid_UNIQUE` (`policyholderid`),\r\n"
					+ "  UNIQUE KEY `firstname_UNIQUE` (`firstname`),\r\n"
					+ "  UNIQUE KEY `lastname_UNIQUE` (`lastname`)\r\n"
					+ ") ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

			stmt.execute("CREATE TABLE IF NOT EXISTS `claim` (\r\n"
					+ "  `claimid` int(5) unsigned zerofill NOT NULL AUTO_INCREMENT,\r\n"
					+ "  `dateofaccident` date DEFAULT NULL,\r\n" + "  `address` varchar(500) DEFAULT NULL,\r\n"
					+ "  `description` varchar(255) DEFAULT NULL,\r\n"
					+ "  `description2` varchar(255) DEFAULT NULL,\r\n"
					+ "  `estimatedcostofrepairs` double(25,2) DEFAULT NULL,\r\n"
					+ "  `policyid` int(6) unsigned zerofill NOT NULL,\r\n" + "  PRIMARY KEY (`claimid`),\r\n"
					+ "  UNIQUE KEY `claimid_UNIQUE` (`claimid`),\r\n" + "  KEY `policyid` (`policyid`),\r\n"
					+ "  CONSTRAINT `claim_policy_policyid_fk` FOREIGN KEY (`policyid`) REFERENCES `policy` (`policyid`)\r\n"
					+ ") ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

			stmt.execute("CREATE TABLE IF NOT EXISTS `vehicle` (\r\n" + "  `vehicleid` int NOT NULL AUTO_INCREMENT,\r\n"
					+ "  `make` varchar(100) DEFAULT NULL,\r\n" + "  `model` varchar(100) DEFAULT NULL,\r\n"
					+ "  `year` int DEFAULT NULL,\r\n" + "  `type` varchar(100) DEFAULT NULL,\r\n"
					+ "  `fueltype` varchar(45) DEFAULT NULL,\r\n" + "  `purchaseprice` decimal(7,2) DEFAULT NULL,\r\n"
					+ "  `color` varchar(45) DEFAULT NULL,\r\n" + "  `premiumcharged` decimal(7,2) DEFAULT NULL,\r\n"
					+ "  `policyid` int(6) unsigned zerofill DEFAULT NULL,\r\n"
					+ "  `licenseplate` varchar(45) NOT NULL,\r\n" + "  PRIMARY KEY (`vehicleid`),\r\n"
					+ "  UNIQUE KEY `vehicleid_UNIQUE` (`vehicleid`),\r\n" + "  KEY `policyid` (`policyid`),\r\n"
					+ "  CONSTRAINT `vehicle_policy_policyid_fk` FOREIGN KEY (`policyid`) REFERENCES `policy` (`policyid`)\r\n"
					+ ") ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

			stmt.execute("CREATE TABLE IF NOT EXISTS `accountpolicydetail` (\r\n"
					+ "  `id` int NOT NULL AUTO_INCREMENT,\r\n"
					+ "  `policyid` int(6) unsigned zerofill DEFAULT NULL,\r\n"
					+ "  `policyholderid` int DEFAULT NULL,\r\n"
					+ "  `accountnumber` int(4) unsigned zerofill NOT NULL,\r\n" + "  PRIMARY KEY (`id`),\r\n"
					+ "  UNIQUE KEY `id_UNIQUE` (`id`),\r\n" + "  KEY `fk_idx` (`policyid`),\r\n"
					+ "  KEY `fk2_idx` (`policyholderid`),\r\n" + "  KEY `fk3_idx` (`accountnumber`),\r\n"
					+ "  CONSTRAINT `fk1` FOREIGN KEY (`policyid`) REFERENCES `policy` (`policyid`),\r\n"
					+ "  CONSTRAINT `fk2` FOREIGN KEY (`policyholderid`) REFERENCES `policyholder` (`policyholderid`),\r\n"
					+ "  CONSTRAINT `fk3` FOREIGN KEY (`accountnumber`) REFERENCES `accountholder` (`accountnumber`)\r\n"
					+ ") ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

			stmt.execute("CREATE TABLE IF NOT EXISTS `vehiclepricefactor` (\r\n"
					+ "  `id` int NOT NULL AUTO_INCREMENT,\r\n" + "  `vehicleage` int DEFAULT NULL,\r\n"
					+ "  `vehiclepricefactor` decimal(10,4) DEFAULT NULL,\r\n" + "  PRIMARY KEY (`id`)\r\n"
					+ ") ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

			stmt.execute("INSERT INTO vehiclepricefactor (vehicleage,vehiclepricefactor)\r\n" + "SELECT * FROM(\r\n"
					+ "SELECT 1,0.01 FROM DUAL UNION ALL\r\n" + "SELECT 3,0.08 FROM DUAL UNION ALL\r\n"
					+ "SELECT 5,0.07 FROM DUAL UNION ALL\r\n" + "SELECT 10,0.06 FROM DUAL UNION ALL\r\n"
					+ "SELECT 15,0.04 FROM DUAL UNION ALL\r\n" + "SELECT 20,0.02 FROM DUAL UNION ALL\r\n"
					+ "SELECT 40,0.01 FROM dual) a\r\n" + "WHERE NOT EXISTS (SELECT NULL FROM vehiclepricefactor);");
		} catch (SQLException e) {
			System.out.println("Table creation failed, please re-run program.");
		}
	}

	// closes database connection
	public void closeDBConnection(Connection con) throws SQLException {
		con.close();
	}

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

	// insert vehicles details to db
	public void insertVehicleToDB(Connection con, Vehicle vehicle, PolicyHolder policyHolder, Policy policy) {
		PreparedStatement stmt;
		ResultSet rs;

		try {
			stmt = con.prepareStatement(
					"INSERT INTO VEHICLE (make,model,year,type,fueltype,purchaseprice,color,premiumcharged,policyid,licenseplate) "
							+ "VALUES(?,?,?,?,?,?,?,?,?,?)");

			stmt.setString(1, vehicle.getMake());
			stmt.setString(2, vehicle.getModel());
			stmt.setInt(3, vehicle.getYear());
			stmt.setString(4, vehicle.getType());
			stmt.setString(5, vehicle.getFuelType());
			stmt.setDouble(6, vehicle.getPurchasePrice());
			stmt.setString(7, vehicle.getColor());
			stmt.setDouble(8, vehicle.getPremiumCharged());
			stmt.setString(9, policy.getPolicyId());
			stmt.setString(10, vehicle.getLicensePlate());
			stmt.executeUpdate();

			rs = stmt.executeQuery("SELECT vehicleid FROM vehicle ORDER BY 1 DESC LIMIT 1;");

			while (rs.next()) {
				vehicle.setVehicleId(rs.getInt(1));
			}

		} catch (SQLException e) {
			// e.printStackTrace();
		}
	}

	// insert account and policy details to db
	public void insertAccountPolicyDetailToDB(Connection con, AccountHolder accountHolder, PolicyHolder policyHolder,
			Policy policy) {
		PreparedStatement stmt;

		try {
			stmt = con.prepareStatement(
					"INSERT INTO accountpolicydetail (policyid,policyholderid,accountnumber) " + "VALUES(?,?,?)");

			stmt.setString(1, policy.getPolicyId());
			stmt.setInt(2, policyHolder.getPolicyHolderId());
			stmt.setString(3, accountHolder.getAccountNumber());

			stmt.executeUpdate();

		} catch (SQLException e) {
			// e.printStackTrace();
		}
	}

	// selects latest record of AccountHolder
	public AccountHolder selectNewAccountHolderID(Connection con, AccountHolder accountHolder) {
		try {
			PreparedStatement stmt;
			ResultSet rs;

			stmt = con.prepareStatement("SELECT lpad(accountnumber,4,'0'),firstname,lastname,address FROM accountholder "
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

	// get vehicle price factor appropriate for the vehicle's age.
	public double selectPriceFactor(Vehicle vehicle, Connection con) {
		double priceFactor = 0;
		int vehicleAge = (LocalDate.now().getYear() - vehicle.getYear());

		try {
			Statement stmt = con.createStatement();
			
			if (vehicleAge == 0)
				vehicleAge = 1;
			
			ResultSet rs = stmt.executeQuery("SELECT vehiclepricefactor FROM vehiclepricefactor WHERE vehicleage <= '"
					+ vehicleAge + "'" + "ORDER BY 1 DESC LIMIT 1 ");
			while (rs.next())
				priceFactor = rs.getDouble(1);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return priceFactor;
	}

	// get latest record of vehicle
	public Vehicle selectNewVehicleID(Connection con, Vehicle vehicle) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT vehicleid FROM vehicle ORDER BY 1 DESC LIMIT 1");
			while (rs.next()) {
				vehicle.setVehicleId(rs.getInt(1));
			}
			return vehicle;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return vehicle;
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

	// inserts account and policy details to db
	public void insertAccountPolicyDetail(Connection con, AccountHolder accountHolder, Policy policy,
			PolicyHolder policyHolder) {
		PreparedStatement stmt;

		try {
			stmt = con.prepareStatement(
					"INSERT INTO AccountPolicyDetail (policyid, policyholderid, accountnumber)" + "VALUES (?,?,?)");
			stmt.setString(1, policy.getPolicyId());
			stmt.setInt(2, policyHolder.getPolicyHolderId());
			stmt.setString(3, accountHolder.getAccountNumber());
			stmt.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	// checks if a vehicle exists and is in a valid policy
	public boolean isVehicleExists(Connection con, Vehicle vehicle) {
		PreparedStatement stmt;
		ResultSet rs;
		try {
			stmt = con.prepareStatement("SELECT licenseplate , status\r\n" + "FROM vehicle v\r\n" + "join policy p\r\n"
					+ "	on v.policyid = p.policyid\r\n" + "where licenseplate = ? AND\r\n"
					+ "(status = ? OR status = ?);");
			stmt.setString(1, vehicle.getLicensePlate());
			stmt.setString(2, ACTIVE);
			stmt.setString(3, INACTIVE);

			rs = stmt.executeQuery();
			while (rs.next()) {
				return true;
			}
			return false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return true;
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

	public Claim selectNewClaim(Connection con, Claim claim)
	{
		PreparedStatement stmt;
		ResultSet rs;
		try {
			stmt = con.prepareStatement("SELECT * FROM claim ORDER BY 1 DESC LIMIT 1;");
			rs = stmt.executeQuery();
			while(rs.next())
			{
				claim.setClaimId(rs.getString(1));
				claim.setDateOfAccident(LocalDate.parse(rs.getString(2)));
				claim.setAddress(rs.getString(3));
				claim.setDescription(rs.getString(4));
				claim.setDescription2(rs.getString(5));
				claim.setEstimatedCostOfRepairs(rs.getDouble(6));
				claim.setPolicyId(rs.getString(7));
			}
			return claim;
		} catch (Exception e) {
			return claim;
		}
	}
	
	public ArrayList<Object> selectAccountHolderRecords(Connection con, ArrayList<Object> recordList, AccountHolder accountHolder)
	{
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
			
			if(rs.next())
			{
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
	
}