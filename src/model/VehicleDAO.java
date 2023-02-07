package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class VehicleDAO extends DatabaseConnection {

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
}
