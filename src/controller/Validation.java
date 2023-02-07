/**
 * @class Validation.java
 * @author CalvinLingat
 * @description Validates user inputs
 * @created 8/15/2022
 */
package controller;

import java.time.LocalDate;
import java.time.format.*;
import java.util.regex.*;

public class Validation {
	/*
	 * validates generic string inputs returns true if invalid, false if valid
	 */
	public boolean isInvalidStringInput(String input) {
		Pattern pattern = Pattern.compile("[^\\w\\- ]");
		Matcher matcher = pattern.matcher(input);

		if (input.isBlank()) {
			System.out.println("Cannot be blank.");
			return true;
		} else if (matcher.find()) {
			System.out.println("Cannot contain special characters.");
			return true;
		} else
			return false;
	}

	/*
	 * validates name inputs returns true if invalid, false if valid
	 */
	public boolean isInvalidNameInput(String input) {
		Pattern pattern = Pattern.compile("[^\\w\\- ]");
		Matcher matcher = pattern.matcher(input);

		if (isInvalidStringInput(input)) {
			return true;
		} else if (matcher.find()) {
			System.out.println("Cannot contain special characters or numbers.");
			return true;
		} else
			return false;
	}

	/*
	 * validates Account Number inputs returns true if invalid, false if valid
	 */
	public boolean isInvalidAccountNumberInput(String input) {
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(input);

		if (input.isBlank()) {
			System.out.println("Cannot be blank.");
			return true;
		} else if (input.length() != 4) {
			System.out.println("Account Number must be 4 digits.");
			return true;
		} else if (matcher.find()) {
			System.out.println("Must be numbers only.");
			return true;
		} else
			return false;
	}

	/*
	 * validates Policy Id inputs returns true if invalid, false if valid
	 */
	public boolean isInvalidPolicyId(String input) {
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(input);

		if (input.isBlank()) {
			System.out.println("Cannot be blank.");
			return true;
		} else if (input.length() != 6) {
			System.out.println("Policy Id must be 6 digits.");
			return true;
		} else if (matcher.find()) {
			System.out.println("Must be numbers only.");
			return true;
		} else
			return false;
	}

	/*
	 * validates Claim Id inputs returns true if invalid, false if valid
	 */
	public boolean isInvalidClaimId(String input) {
		Pattern pattern = Pattern.compile("[Cc]{1}[0-9]{5}$");
		Matcher matcher = pattern.matcher(input);

		if (input.isBlank()) {
			System.out.println("Cannot be blank.");
			return true;
		} else if (input.length() != 6) {
			System.out.println("Claim Id must be 6 digits.");
			return true;
		} else if (!matcher.find()) {
			System.out.println("Must be numbers only starting with \'C\' or \'c\'.");
			return true;
		} else
			return false;
	}

	/*
	 * validates generic integer inputs returns true if invalid, false if valid
	 */
	public boolean isInvalidIntInput(String input) {
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(input + "");

		if ((input + "").isBlank()) {
			System.out.println("Cannot be blank.");
			return true;
		} else if (matcher.find()) {
			System.out.println("Must be numbers only.");
			return true;
		} else
			return false;
	}

	/*
	 * validates generic date inputs returns true if invalid, false if valid
	 */
	public boolean isInvalidDateInput(String input) {
		/*
		 * attempts to parse the input into localdate using the format yyyy-MM-dd if it
		 * fails, the date is invalid
		 */
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
				.withResolverStyle(ResolverStyle.STRICT);

		try {
			dateFormat.parse(input);
			LocalDate.parse(input);
			return false;
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			System.out.println("Invalid date or wrong format should be:yyyy-mm-dd");
			return true;
		}
	}

	/*
	 * validates birthdate inputs returns true if invalid, false if valid
	 */
	public boolean isInvalidBirthdateInput(String input) {
		LocalDate lDate, legalAgeBirthdate;
		// DateTimeFormatter dateFormat =
		// DateTimeFormatter.ofPattern("yyyy-MM-dd").withResolverStyle(ResolverStyle.STRICT);

		if (isInvalidDateInput(input))
			return true;
		else
			lDate = LocalDate.parse(input);
		legalAgeBirthdate = lDate.plusYears(18);

		if (lDate.isAfter(LocalDate.now())) {
			System.out.println("BirthDate cannot be a future date.");
			return true;
		} else if (LocalDate.now().isBefore(legalAgeBirthdate)) {
			System.out.println("You need to be at least 18 years old.");
			return true;
		} else
			return false;
	}

	/*
	 * validates accident date inputs returns true if invalid, false if valid
	 */
	public boolean isInvalidAccidentDateInput(String input) {
		LocalDate lDate;
		// DateTimeFormatter dateFormat =
		// DateTimeFormatter.ofPattern("yyyy-MM-dd").withResolverStyle(ResolverStyle.STRICT);

		if (isInvalidDateInput(input))
			return true;
		else
			lDate = LocalDate.parse(input);

		if (lDate.isAfter(LocalDate.now())) {
			System.out.println("Accident date cannot be a future date.");
			return true;
		} else
			return false;
	}

	/*
	 * validates effective date inputs returns true if invalid, false if valid
	 */
	public boolean isInvalidEffectiveDate(String input) {
		LocalDate lDate;

		if (isInvalidDateInput(input))
			return true;
		else
			lDate = LocalDate.parse(input);

		if (lDate.isBefore(LocalDate.now())) {
			System.out.println("Effectivity Date cannot be in the past.");
			return true;
		} else
			return false;
	}

	/*
	 * validates license issued date inputs returns true if invalid, false if valid
	 */
	public boolean isInvalidLicenseIssuedDate(String input, LocalDate birthdate) {
		LocalDate lDate;

		if (isInvalidDateInput(input))
			return true;
		else
			lDate = LocalDate.parse(input);

		if (lDate.isAfter(LocalDate.now())) {
			System.out.println("License Issued Date cannot be a future date.");
			return true;
		} else if (lDate.isBefore(birthdate)) {
			System.out.println("You can't have your license before your birthday.");
			return true;
		} else if (lDate.isBefore(birthdate.plusYears(18))) {
			System.out.println("You can't have your license before your 18th birthday.");
			return true;
		} else
			return false;
	}

	/*
	 * validates year inputs returns true if invalid, false if valid
	 */
	public boolean isInvalidYearInput(String input) {
		LocalDate lDate = LocalDate.now();

		if (isInvalidIntInput(input))
			return true;
		else if (Integer.parseInt(input) > lDate.getYear()) {
			System.out.println("Year cannot be more than the current year.");
			return true;
		} else
			return false;
	}

	/*
	 * validates generic numbers with decimal points(double) inputs returns true if
	 * invalid, false if valid
	 */
	public boolean isInvalidDoubleInput(String input) {
		try {
			Double.parseDouble(input);
			return false;
		} catch (NullPointerException e) {
			// e.printStackTrace();
			System.out.println("Cannot be blank");
			return true;
		} catch (NumberFormatException e) {
			if (input.toCharArray().length == 1)
				System.out.println("Please input a number.");
			return true;
		}
	}

	/*
	 * validates premium price inputs returns true if invalid, false if valid
	 */
	public boolean isInvalidPremiumPriceInput(String input) {
		if (isInvalidDoubleInput(input))
			return true;
		else if (Double.parseDouble(input) <= 0) {
			System.out.println("Premium price should be more than 0.");
			return true;
		} else
			return false;
	}

	/*
	 * valiates yes or no inputs returns true if invalid, false if valid
	 */
	public boolean isInvalidYesNo(String input) {
		if (isInvalidStringInput(input))
			return true;
		else if (input.equalsIgnoreCase("y"))
			return false;
		else if (input.equalsIgnoreCase("n"))
			return false;
		else {
			System.out.println("Please enter y or n;");
			return true;
		}
	}
}
