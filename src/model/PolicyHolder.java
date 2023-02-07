/**
 * @Class: PolicyHolder
 * @Description: PolicyHolder db object
 * @author CalvinLingat
 * @Created:8/15/2022 
 */
package model;

import java.time.LocalDate;

public class PolicyHolder extends AccountHolder {
	private int policyHolderId;
	private String firstName, lastName, address, licenseNumber;
	private LocalDate dateOfBirth, licenseIssued;

	public PolicyHolder() {
		super();
	}

	public PolicyHolder(int policyHolderId, String firstName, String lastName, String address, String licenseNumber,
			LocalDate dateOfBirth, LocalDate licenseIssued) {
		this.policyHolderId = policyHolderId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.licenseNumber = licenseNumber;
		this.dateOfBirth = dateOfBirth;
		this.licenseIssued = licenseIssued;
	}

	public int getPolicyHolderId() {
		return policyHolderId;
	}

	public void setPolicyHolderId(int policyHolderId) {
		this.policyHolderId = policyHolderId;
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

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDate getLicenseIssued() {
		return licenseIssued;
	}

	public void setLicenseIssued(LocalDate licenseIssued) {
		this.licenseIssued = licenseIssued;
	}

	public void displayPolicyHolder() {
		System.out.println("\n\n[POLICY HOLDER INFO]" + "\nPolicyHolderID: " + getPolicyHolderId() + "\nFirstName "
				+ getFirstName() + "\nLastName: " + getLastName() + "\nDriversLicenseNumber: " + getLicenseNumber()
				+ "\nLicenseIssued: " + getLicenseIssued() + "\nDateOfBirth: " + getDateOfBirth());
	}

	public void reset() {
		address = null;
		dateOfBirth = null;
		firstName = null;
		lastName = null;
		licenseIssued = null;
		licenseNumber = null;
		policyHolderId = 0;
	}
}