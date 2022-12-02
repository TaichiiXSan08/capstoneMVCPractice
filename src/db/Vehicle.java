/**
 * @Class: Vehicle
 * @Description: Vehicle db object
 * @author CalvinLingat
 * @Created:8/15/2022 
 */
package db;

public class Vehicle {
	private int vehicleId, year;
	private String make, model, type, fuelType, color, licensePlate;

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Vehicle() {
		super();
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public double getPremiumCharged() {
		return premiumCharged;
	}

	public void setPremiumCharged(double premiumCharged) {
		this.premiumCharged = premiumCharged;
	}

	private double purchasePrice, premiumCharged;

	public void displayVehicleNoId() {
		System.out.printf(
				"Make: %s" + "\nModel: %s" + "\nType: %s" + "\nColor: %s" + "\nYear: %d" + "\nFuelType: %s"
						+ "\nPurchasePrice: $%.2f" + "\nPremiumCharged: $%.2f\n",
				make, model, type, color, year, fuelType, purchasePrice, premiumCharged);
	}

}
