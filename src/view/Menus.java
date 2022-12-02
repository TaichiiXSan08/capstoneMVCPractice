/**
 * @Class: Menus
 * @Description: displays different menus for the user
 * @author CalvinLingat
 * @Created:8/15/2022 
 */
package view;

public class Menus {
	public void displayMainMenu() {
		System.out.print("\n\n[1] Create a new Customer Account\r\n" + "[2] Get a policy quote and buy the policy.\r\n"
				+ "[3] Cancel a specific policy \r\n" + "[4] File an accident claim against a policy.\r\n"
				+ "[5] Search for a Customer account \r\n" + "[6] Search for and display a specific policy\r\n"
				+ "[7] Search for and display a specific claim\r\n" + "[8] Exit the PAS System");
	}

	public void getInput() {
		System.out.print("\n\nEnter input: ");
	}

	public void askFirstNameInput() {
		System.out.print("First Name: ");
	}

	public void askLastNameInput() {
		System.out.print("Last Name: ");
	}

	public void askAddress() {
		System.out.print("Enter Address: ");
	}

	public void askBirthdate() {
		System.out.print("Birthdate[yyyy-mm-dd]: ");
	}

	public void askDriversLicense() {
		System.out.print("Driver's License: ");
	}

	public void askDriversLicenseIssuedDate() {
		System.out.print("Driver's License Issued [yyyy-mm-dd]: ");
	}

	public void askAccountNumber() {
		System.out.print("Account Number: ");
	}

	public void askIfSameAsAccountHolder() {
		System.out.print("Is policy owner same as Account owner? [y,n] ");
	}

	public void askEffectiveDate() {
		System.out.print("Effective Date of Policy [yyyy-mm-dd]: ");
	}

	public void askMake() {
		System.out.print("\nCar Make: ");
	}

	public void askModel() {
		System.out.print("Car Model: ");
	}

	public void askYear() {
		System.out.print("Year: ");
	}

	public void askType() {
		System.out.print("Car Type:" + "\n e.g: 4-door sedan, 2-door sports car, SUV, or truck ");
	}

	public void askFuelType() {
		System.out.print("Car Fuel Type: " + "\n e.g: Diesel, Electric, Petrol ");
	}

	public void askPurchasePrice() {
		System.out.print("Purchase Price: ");
	}

	public void askColor() {
		System.out.print("Car Color: ");
	}

	public void askLicensePlate() {
		System.out.print("License Plate Number: ");
	}

	public void askToAddVehicle() {
		System.out.print("Would you like to add more vehicle? [y/n] ");
	}

	public void askPolicyNumber() {
		System.out.print("Policy Number: ");
	}

	public void confirmCancellation() {
		System.out.print("\nAre you sure you want to cancel the policy? [y/n] : ");
	}

	public void askDateOfAccident() {
		System.out.print("\n\nDate of Accident [yyyy-mm-dd]: ");
	}

	public void askAdressWhereAccidentHappened() {
		System.out.print("Address where accident happened: ");
	}

	public void askDescriptionOfAccident() {
		System.out.print("Description of Accident: ");
	}

	public void askDescriptionOfDamageToVehicle() {
		System.out.print("Description of damage to vehicle: ");
	}

	public void askEstimatedCostOfRepairs() {
		System.out.print("Estimated cost of repairs: ");
	}

	public void askClaimId() {
		System.out.print("Claim ID: ");
	}

	public void confirmPolicyPurchase() {
		System.out.print("\nWill you purchase the policy? [y/n]: ");
	}

}
