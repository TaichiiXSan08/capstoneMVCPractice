/**
 * @Class: AccountHolder
 * @Description: AccountHolder db object
 * @author CalvinLingat
 * @Created:8/15/2022 
 */
package db;

public class AccountHolder {
	private String firstName, lastName, address, accountNumber;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void displayAccountHolder() {
		System.out.println("\n[ACCOUNT HOLDER INFO]" + "\nAccountNumber: " + getAccountNumber() + "\nFirstName: "
				+ getFirstName() + "\nLastName: " + getLastName() + "\nAddress: " + getAddress());
	}

	public void reset() {
		accountNumber = null;
		address = null;
		firstName = null;
		lastName = null;
	}
}
