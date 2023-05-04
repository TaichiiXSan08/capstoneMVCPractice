/**
 * @Class: DatabaseConnection
 * @Description: All database related queries will be handled
 * by this class. From connection to database, creation
 * of database objects etc.
 * @author CalvinLingat
 * @Created:8/15/2022 
 */
package model;

import java.sql.*;


public class DatabaseConnection {
	private final String URL = "jdbc:mysql://localhost:3306/";
	private final String USERNAME = "root";
	private final String PASSWORD = "test123";
	private final String DBNAME = "capstoned";
	protected final String ACTIVE = "active", INACTIVE = "inactive", CANCELLED = "cancelled";

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
			System.exit(0);
			return con;
		}
	}

	// Creates database if no capstone database detected.
	public void createDatabase(Connection con) {
		try {
			Statement stmt = con.createStatement();
			stmt.execute("CREATE DATABASE IF NOT EXISTS " + DBNAME);
			con = connectDB();
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Database was not created");
			System.exit(0);
			
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

}