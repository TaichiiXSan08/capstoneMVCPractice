/**
 * @Class: AutomobilePASService
 * @Description: Handles logical execution of the main menus.
 * @author CalvinLingat
 * @Created:8/15/2022 
 */
package controller;

import java.sql.*;
import java.util.*;

import db.*;
import view.Menus;

public class AutomobilePASService {
	private AccountHolder accountHolder;
	private Claim claim;
	private Policy policy;
	private PolicyHolder policyHolder;
	private RatingEngine premiumCalculator;
	private Validation validate;
	private DatabaseConnection dbCon;
	private Menus menu;
	private Scanner sc;
	private InputService inputService;

	public AutomobilePASService() {
		initialize();
	}

	public void initialize() {
		accountHolder = new AccountHolder();
		claim = new Claim();
		policy = new Policy();
		policyHolder = new PolicyHolder();
		premiumCalculator = new RatingEngine();
		validate = new Validation();
		dbCon = new DatabaseConnection();
		menu = new Menus();
		sc = new Scanner(System.in);
		inputService = new InputService();
	}

	// option 1 in main menu
	public void processFirstMainMenuOption(Connection con) {

		accountHolder.reset();

		accountHolder.setFirstName(inputService.askFirstName(accountHolder, menu, sc, validate));
		accountHolder.setLastName(inputService.askLastName(accountHolder, menu, sc, validate));
		accountHolder.setAddress(inputService.askAddress(accountHolder, menu, sc, validate));

		dbCon.insertAccountHolderToDB(con, accountHolder);

		accountHolder = dbCon.selectNewAccountHolderID(con, accountHolder);

		accountHolder.displayAccountHolder();
	}

	// Option 2 in main menu
	public void processSecondMainMenuOption(Connection con) {

		accountHolder.reset();
		policy.reset();
		policyHolder.reset();

		ArrayList<Vehicle> vehicleList = new ArrayList<>();
		boolean isTrue = true, isExistingPolicyHolder = false;
		int vehicleCtr = 1;
		
		if(dbCon.selectNewAccountHolderID(con, accountHolder).getAccountNumber() != null)
		{
			accountHolder.setAccountNumber(inputService.askAccountNumber(con, accountHolder, menu, sc, validate, dbCon));
			// get new policyholder if not same with account.
			if (isSameAsAccountHolder(sc)) {
				policyHolder.setFirstName(accountHolder.getFirstName());
				policyHolder.setLastName(accountHolder.getLastName());
				policyHolder.setAddress(accountHolder.getAddress());
			}
			else
			{
				policyHolder.setFirstName(inputService.askFirstName(policyHolder, menu, sc, validate));
				policyHolder.setLastName(inputService.askLastName(policyHolder, menu, sc, validate));
				policyHolder.setAddress(inputService.askAddress(policyHolder, menu, sc, validate));
			}

			if (dbCon.selectSpecificPolicyHolder(con, policyHolder).getDateOfBirth() != null)
				isExistingPolicyHolder = true;
			else {
				// get policy/policyheader details from user
				policyHolder.setDateOfBirth(inputService.askBirthdate(policyHolder, menu, sc, validate));
				policyHolder.setLicenseNumber(inputService.askDriversLicense(policyHolder, menu, sc, validate));
				policyHolder.setLicenseIssued(inputService.askDriversLicenseIssuedDate(policyHolder, menu, sc, validate));

			}
			policy.setAccountNumber(accountHolder.getAccountNumber());
			policy.setPolicyHolder(policyHolder.getFirstName() + " " + policyHolder.getLastName());

			policy.setEffectiveDate(inputService.askEffectiveDate(policy, menu, sc, validate));
			policy.setExpirationDate(policy.getEffectiveDate().plusMonths(6));

			// vehicles
			do {

				vehicleList.add(inputService.askVehicleDetailsFromUser(new Vehicle(), menu, sc, validate));
				// validate if vehicle has existing policy
				if (dbCon.isVehicleExists(con, vehicleList.get(vehicleList.size() - 1))) {
					System.out.println("Vehicle already have a policy.");
					vehicleList.remove(vehicleList.size() - 1);
					isTrue = isAddMoreVehicle();
				} else
					isTrue = isAddMoreVehicle();
				if (vehicleList.isEmpty())
					System.out.println("Please enroll at least one vehicle.");
			} while (isTrue || vehicleList.isEmpty());

			// calculate premium
			policy = premiumCalculator.calculatePremium(policy, vehicleList, policyHolder, con, dbCon);

			// display policy quote summary
			displayPolicyQuote(vehicleList, vehicleCtr);

			// confirm if policy quote will be purchased
			if (inputService.askPolicyPurchase(menu, sc, validate).equalsIgnoreCase("y")) {

				// insert policy/policyheader to db
				if (!isExistingPolicyHolder)
					dbCon.insertPolicyHolderToDB(con, accountHolder, policyHolder);
				dbCon.insertPolicyToDB(con, accountHolder, policy);

				// update policy/holder objects
				policy = dbCon.selectNewPolicy(con, policy);
				policyHolder = dbCon.selectNewPolicyHolderId(con, policyHolder);

				// update policy's policypremium
				policy = dbCon.updatePolicyPremium(con, policy.getPolicyPremium(), policy);

				System.out.println("RECORD SAVED");
				displayPolicyQuote(vehicleList, vehicleCtr);

				// insert data to reference tables
				dbCon.insertAccountPolicyDetail(con, accountHolder, policy, policyHolder);
			}
		}
		else
			System.out.println("No Account has been created yet.\n Going back to Main Menu.");		
	}

	// option 3 in main menu
	public void processThirdMainMenuOption(Connection con) {
		/*
		 * ask for a policy number to cancel display the policy confirm the cancel
		 * update status of the policy in backend display the policy showing it is
		 * cancelled
		 */
		String input;
		
		if(dbCon.selectNewPolicy(con, policy).getPolicyId() != null)
		{
			policy.reset();
			policy.setPolicyId(inputService.askPolicyId(con, policy, menu, sc, validate, dbCon));
			policy.displayPolicy();

			do {
				menu.confirmCancellation();
				input = sc.nextLine();
			} while (validate.isInvalidYesNo(input));

			if (input.equalsIgnoreCase("y")) {
				dbCon.updatePolicyIsCancelled(con, policy);
				System.out.println("Policy has been cancelled.");
				policy = dbCon.selectPolicy(con, policy, policy.getPolicyId());
				policy.displayPolicy();
			} else {
				System.out.println("Displaying policy");
				policy.displayPolicy();
			}
		}
		else
			System.out.println("No policy has been created yet.\n Going back to Main Menu.");
	}

	// option 4 in main menu
	public void processFourthMainMenuOption(Connection con) {
		/*
		 * ask for a policy number to apply a claim display the policy get claims
		 * details insert into backend display the claims detail to user
		 */
		
		if(dbCon.selectNewPolicy(con, policy).getPolicyId() != null)
		{
			policy.reset();
			claim.reset();
	
			policy.setPolicyId(inputService.askPolicyId(con, policy, menu, sc, validate, dbCon));
			policy.displayPolicy();
	
			claim.setDateOfAccident(inputService.askDateOfAccident(claim, menu, sc, validate));
			claim.setAddress(inputService.askAddressWhereAccidentHappend(claim, menu, sc, validate));
			claim.setDescription(inputService.askDescriptionOfAccident(claim, menu, sc, validate));
			claim.setDescription2(inputService.askDescriptionOfDamageToVehicle(claim, menu, sc, validate));
			claim.setEstimatedCostOfRepairs(inputService.askEstimatedCostOfRepairs(claim, menu, sc, validate));
	
			dbCon.insertClaims(con, claim, policy);
			claim = dbCon.selectClaimId(con, claim);
			claim.displayClaim();
		}
		else
			System.out.println("No policy has been created yet.\n Going back to Main Menu.");
	}

	// option 5 in main menu
	public void processFifthMainMenuOption(Connection con) {
		/*
		 * Ask for an account holder id display account holder details
		 */
		accountHolder.reset();
		
		if(dbCon.selectNewAccountHolderID(con, accountHolder).getAccountNumber() != null)
		{
			accountHolder.setFirstName(inputService.askFirstName(accountHolder, menu, sc, validate));
			accountHolder.setLastName(inputService.askLastName(accountHolder, menu, sc, validate));
			accountHolder = dbCon.selectFirstLastName(con, accountHolder);
			if (!(accountHolder.getAccountNumber() == null))
				accountHolder.displayAccountHolder();
		}
		else
			System.out.println("No Account has been created yet.\n Going back to Main Menu.");
	}

	// option 6 in main menu
	public void processSixthMainMenuOption(Connection con) {
		/*
		 * Ask for a policy id display policy details
		 */
		if(dbCon.selectNewPolicy(con, policy).getPolicyId() != null)
		{
			policy.reset();
			policy.setAccountNumber(inputService.askPolicyId(con, policy, menu, sc, validate, dbCon));
			policy.displayPolicy();
		}
		else
			System.out.println("\"No policy has been created yet.\\n Going back to Main Menu.\"");
	}

	// option 7 in main menu
	public void processSeventhMainMenuOption(Connection con) {
		/*
		 * Ask for a claimid display claim details
		 */
		if(dbCon.selectNewClaim(con, claim).getClaimId() != null)
		{
			claim.reset();
			claim = inputService.askClaimId(con, claim, menu, sc, validate, dbCon);
			claim.displayClaim();
		}
		else
			System.out.println("No claims has been created yet. Going back to Main Menu.");
		
	}

	// confirms if accountholder same with policyholder
	public boolean isSameAsAccountHolder(Scanner sc) {
		String input;

		menu.askIfSameAsAccountHolder();
		input = sc.nextLine();

		switch (input.toLowerCase()) {
		case "y": {
			return true;
		}
		case "n": {
			return false;
		}
		default: {
			System.out.println("Enter either y or n.");
			return false;
		}
		}
	}

	public boolean isAddMoreVehicle() {
		String input;
		boolean isTrue = true;

		do {
			menu.askToAddVehicle();
			input = sc.next();

			isTrue = validate.isInvalidYesNo(input);

			if (input.equals("n"))
				return false;
			else if (input.equals("y"))
				return true;

		} while (isTrue);
		return false; // satisfies compiler's need for return
	}

	public void displayPolicyQuote(ArrayList<Vehicle> vehicleList, int vehicleCtr) {
		accountHolder.displayAccountHolder();
		policy.displayPolicy();
		policyHolder.displayPolicyHolder();
		vehicleCtr = 1;
		for (Vehicle tempVehicle : vehicleList) {
			System.out.println("\nVEHICLE #" + vehicleCtr++);
			tempVehicle.displayVehicleNoId();
		}
	}
}
