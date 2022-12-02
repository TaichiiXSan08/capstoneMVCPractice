/**
 * @Class: InputService
 * @Description: Processes the inputs from the user.
 * @author CalvinLingat
 * @Created:8/15/2022 
 */

package controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Scanner;
import db.AccountHolder;
import db.Claim;
import db.DatabaseConnection;
import db.Policy;
import db.PolicyHolder;
import db.Vehicle;
import view.Menus;

public class InputService {
	/*
	 * takes and validate user birth date input, if valid, assign it to respective
	 * object attribute, else prompts the user again.
	 */
	public LocalDate askBirthdate(PolicyHolder policyHolder, Menus menu, Scanner sc, Validation validate) {
		String input;
		boolean isTrue;

		do {
			menu.askBirthdate();
			input = sc.nextLine();
			isTrue = validate.isInvalidBirthdateInput(input);
			if (!isTrue)
				policyHolder.setDateOfBirth(LocalDate.parse(input));
		} while (isTrue);

		return policyHolder.getDateOfBirth();
	}

	/*
	 * takes and validate user drivers license input, if valid, assign it to
	 * respective object attribute, else prompts the user again.
	 */
	public String askDriversLicense(PolicyHolder policyHolder, Menus menu, Scanner sc, Validation validate) {
		do {
			menu.askDriversLicense();
			policyHolder.setLicenseNumber(sc.nextLine());
		} while (validate.isInvalidStringInput(policyHolder.getLicenseNumber()));

		return policyHolder.getLicenseNumber();
	}

	/*
	 * takes and validate user drivers license issued date input, if valid, assign
	 * it to respective object attribute, else prompts the user again.
	 */
	public LocalDate askDriversLicenseIssuedDate(PolicyHolder policyHolder, Menus menu, Scanner sc,
			Validation validate) {
		String input;
		boolean isTrue;

		do {
			menu.askDriversLicenseIssuedDate();
			input = sc.next();
			isTrue = validate.isInvalidLicenseIssuedDate(input, policyHolder.getDateOfBirth());
			if (!isTrue) // date input is valid
				policyHolder.setLicenseIssued(LocalDate.parse(input));
		} while (isTrue);

		return policyHolder.getLicenseIssued();
	}

	/*
	 * takes and validate user policy effective date input, if valid, assign it to
	 * respective object attribute, else prompts the user again.
	 */
	public LocalDate askEffectiveDate(Policy policy, Menus menu, Scanner sc, Validation validate) {
		String input;
		boolean isTrue;

		do {
			menu.askEffectiveDate();
			input = sc.next();
			isTrue = validate.isInvalidEffectiveDate(input);
			if (!isTrue)
				policy.setEffectiveDate(LocalDate.parse(input));
		} while (isTrue);

		return policy.getEffectiveDate();
	}

	/*
	 * takes and validate user first name input, if valid, assign it to respective
	 * object attribute, else prompts the user again.
	 */
	public String askFirstName(AccountHolder accountHolder, Menus menu, Scanner sc, Validation validate) {
		do {

			menu.askFirstNameInput();
			accountHolder.setFirstName(sc.nextLine());
		} while (validate.isInvalidNameInput(accountHolder.getFirstName()));
		return accountHolder.getFirstName();
	}

	/*
	 * takes and validate user last name input, if valid, assign it to respective
	 * object attribute, else prompts the user again.
	 */
	public String askLastName(AccountHolder accountHolder, Menus menu, Scanner sc, Validation validate) {
		do {
			menu.askLastNameInput();
			accountHolder.setLastName(sc.nextLine());
		} while (validate.isInvalidNameInput(accountHolder.getLastName()));
		return accountHolder.getLastName();
	}

	/*
	 * takes and validate user address input, if valid, assign it to respective
	 * object attribute, else prompts the user again.
	 */
	public String askAddress(AccountHolder accountHolder, Menus menu, Scanner sc, Validation validate) {
		do {
			menu.askAddress();
			accountHolder.setAddress(sc.nextLine());
		} while (validate.isInvalidStringInput(accountHolder.getAddress()));
		return accountHolder.getAddress();
	}

	/*
	 * takes and validate user vehicle make input, if valid, assign it to respective
	 * object attribute, else prompts the user again.
	 */
	public String askmake(Vehicle vehicle, Menus menu, Scanner sc, Validation validate) {
		do {
			menu.askMake();
			vehicle.setMake(sc.next());
		} while (validate.isInvalidStringInput(vehicle.getMake()));
		return vehicle.getMake();
	}

	/*
	 * takes and validate user vehicle model input, if valid, assign it to
	 * respective object attribute, else prompts the user again.
	 */
	public String askModel(Vehicle vehicle, Menus menu, Scanner sc, Validation validate) {
		do {
			menu.askModel();
			vehicle.setModel(sc.next());
		} while (validate.isInvalidStringInput(vehicle.getModel()));
		return vehicle.getModel();
	}

	/*
	 * takes and validate user vehicle color input, if valid, assign it to
	 * respective object attribute, else prompts the user again.
	 */
	public String askColor(Vehicle vehicle, Menus menu, Scanner sc, Validation validate) {
		do {
			menu.askColor();
			vehicle.setColor(sc.next());
		} while (validate.isInvalidStringInput(vehicle.getColor()));
		return vehicle.getColor();
	}

	/*
	 * takes and validate user vehicle year input, if valid, assign it to respective
	 * object attribute, else prompts the user again.
	 */
	public int askYear(Vehicle vehicle, Menus menu, Scanner sc, Validation validate) {
		String input;
		boolean isTrue;

		do {
			menu.askYear();
			input = sc.next();
			isTrue = validate.isInvalidYearInput(input);
			if (!isTrue)
				vehicle.setYear(Integer.parseInt(input));
		} while (isTrue);
		return vehicle.getYear();
	}

	/*
	 * takes and validate user vehicle type input, if valid, assign it to respective
	 * object attribute, else prompts the user again.
	 */
	public String askType(Vehicle vehicle, Menus menu, Scanner sc, Validation validate) {
		do {
			menu.askType();
			menu.getInput();
			vehicle.setType(sc.next());
		} while (validate.isInvalidStringInput(vehicle.getType()));
		return vehicle.getType();
	}

	/*
	 * takes and validate user vehicle fuel type input, if valid, assign it to
	 * respective object attribute, else prompts the user again.
	 */
	public String askFuelType(Vehicle vehicle, Menus menu, Scanner sc, Validation validate) {
		do {
			menu.askFuelType();
			menu.getInput();
			vehicle.setFuelType(sc.next());
		} while (validate.isInvalidStringInput(vehicle.getFuelType()));
		return vehicle.getFuelType();
	}

	/*
	 * takes and validate user vehicle purchase price input, if valid, assign it to
	 * respective object attribute, else prompts the user again.
	 */
	public double askPurchasePrice(Vehicle vehicle, Menus menu, Scanner sc, Validation validate) {
		String input;
		boolean isTrue;

		do {
			menu.askPurchasePrice();
			input = sc.next();
			isTrue = validate.isInvalidPremiumPriceInput(input);
			if (!isTrue)
				vehicle.setPurchasePrice(Double.parseDouble(input));
		} while (isTrue);
		return vehicle.getPurchasePrice();
	}

	/*
	 * takes and validate user vehicle license plate input, if valid, assign it to
	 * respective object attribute, else prompts the user again.
	 */
	public String askLicensePlate(Vehicle vehicle, Menus menu, Scanner sc, Validation validate) {
		do {
			menu.askLicensePlate();
			vehicle.setLicensePlate(sc.next());
		} while (validate.isInvalidStringInput(vehicle.getLicensePlate()));
		return vehicle.getLicensePlate();
	}

	/*
	 * takes and validate user account number input, if valid, assign it to
	 * respective object attribute, else prompts the user again.
	 */
	public String askAccountNumber(Connection con, AccountHolder accountHolder, Menus menu, Scanner sc,
			Validation validate, DatabaseConnection dbCon) {
		String input;

		do {
			do {
				menu.askAccountNumber();
				input = sc.nextLine();
			} while (validate.isInvalidAccountNumberInput(input)); // checks if input is a valid string.
			accountHolder = dbCon.selectAccountHolder(con, accountHolder, input); // selects accountnumber record in
																					// db.
		} while (accountHolder.getAccountNumber() == null);

		return input;
	}

	/*
	 * takes and validate user policy id input, if valid, assign it to respective
	 * object attribute, else prompts the user again.
	 */
	public String askPolicyId(Connection con, Policy policy, Menus menu, Scanner sc, Validation validate,
			DatabaseConnection dbCon) {
		String input;

		do {
			do {
				menu.askPolicyNumber();
				input = sc.nextLine();
			} while (validate.isInvalidPolicyId(input));
			policy = dbCon.selectClaimablePolicy(con, policy, input);
		} while (policy.getPolicyId() == null);

		return policy.getPolicyId();
	}

	/*
	 * takes and validate user Claim Id input, if valid, assign it to respective
	 * object attribute, else prompts the user again.
	 */
	public Claim askClaimId(Connection con, Claim claim, Menus menu, Scanner sc, Validation validate,
			DatabaseConnection dbCon) {
		String input;

		do {
			do {
				menu.askClaimId();
				input = sc.nextLine();
			} while (validate.isInvalidClaimId(input));
			claim = dbCon.selectClaim(con, claim, input);
		} while (claim.getClaimId() == null);

		return claim;
	}

	/*
	 * takes and validate user date of accident input, if valid, assign it to
	 * respective object attribute, else prompts the user again.
	 */
	public LocalDate askDateOfAccident(Claim claim, Menus menu, Scanner sc, Validation validate) {
		String input;
		boolean isTrue;

		do {
			menu.askDateOfAccident();
			input = sc.nextLine();
			isTrue = validate.isInvalidAccidentDateInput(input);
			if (!isTrue)
				claim.setDateOfAccident(LocalDate.parse(input));
		} while (isTrue);
		return claim.getDateOfAccident();
	}

	/*
	 * takes and validate user address of accident input, if valid, assign it to
	 * respective object attribute, else prompts the user again.
	 */
	public String askAddressWhereAccidentHappend(Claim claim, Menus menu, Scanner sc, Validation validate) {
		do {
			menu.askAdressWhereAccidentHappened();
			claim.setAddress(sc.nextLine());
		} while (validate.isInvalidStringInput(claim.getAddress()));
		return claim.getAddress();
	}

	/*
	 * takes and validate user description of accident input, if valid, assign it to
	 * respective object attribute, else prompts the user again.
	 */
	public String askDescriptionOfAccident(Claim claim, Menus menu, Scanner sc, Validation validate) {
		do {
			menu.askDescriptionOfAccident();
			;
			claim.setDescription(sc.nextLine());
		} while (validate.isInvalidStringInput(claim.getDescription()));
		return claim.getDescription();
	}

	/*
	 * takes and validate user description of damage to vehicle input, if valid,
	 * assign it to respective object attribute, else prompts the user again.
	 */
	public String askDescriptionOfDamageToVehicle(Claim claim, Menus menu, Scanner sc, Validation validate) {
		do {
			menu.askDescriptionOfDamageToVehicle();
			claim.setDescription2(sc.nextLine());
		} while (validate.isInvalidStringInput(claim.getDescription2()));
		return claim.getDescription2();
	}

	/*
	 * takes and validate user estimated cost of repairs. input, if valid, assign it
	 * to respective object attribute, else prompts the user again.
	 */
	public double askEstimatedCostOfRepairs(Claim claim, Menus menu, Scanner sc, Validation validate) {
		String input;
		boolean isTrue;

		do {
			menu.askEstimatedCostOfRepairs();
			input = sc.nextLine();
			isTrue = validate.isInvalidDoubleInput(input);
			if (!isTrue)
				claim.setEstimatedCostOfRepairs(Double.parseDouble(input));
		} while (isTrue);

		return claim.getEstimatedCostOfRepairs();
	}

	/*
	 * takes and validate user vehicle related inputs, if valid, assign it to
	 * respective object attribute, else prompts the user again.
	 */
	public Vehicle askVehicleDetailsFromUser(Vehicle vehicle, Menus menu, Scanner sc, Validation validate) {

		askmake(vehicle, menu, sc, validate);
		askModel(vehicle, menu, sc, validate);
		askColor(vehicle, menu, sc, validate);
		askYear(vehicle, menu, sc, validate);
		askType(vehicle, menu, sc, validate);
		askFuelType(vehicle, menu, sc, validate);
		askPurchasePrice(vehicle, menu, sc, validate);
		askLicensePlate(vehicle, menu, sc, validate);

		return vehicle;
	}

	public String askPolicyPurchase(Menus menu, Scanner sc, Validation validate) {
		String input;

		do {
			menu.confirmPolicyPurchase();
			input = sc.next();
		} while (validate.isInvalidYesNo(input));

		return input;
	}
}
