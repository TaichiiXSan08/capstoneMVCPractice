/**
 * @Class: Claim
 * @Description: Claim db object
 * @author CalvinLingat
 * @Created:8/15/2022 
 */
package db;

import java.time.LocalDate;

public class Claim extends Policy {
	private LocalDate dateOfAccident;
	private String claimId, address, description, description2;
	private double estimatedCostOfRepairs;

	public Claim() {
		super();
	}

	public Claim(String claimId, LocalDate dateOfAccident, String address, String description, String description2,
			double estimatedCostOfRepairs) {
		super();
		this.claimId = claimId;
		this.dateOfAccident = dateOfAccident;
		this.address = address;
		this.description = description;
		this.description2 = description2;
		this.estimatedCostOfRepairs = estimatedCostOfRepairs;
	}

	public String getClaimId() {
		return claimId;
	}

	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}

	public LocalDate getDateOfAccident() {
		return dateOfAccident;
	}

	public void setDateOfAccident(LocalDate dateOfAccident) {
		this.dateOfAccident = dateOfAccident;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public double getEstimatedCostOfRepairs() {
		return estimatedCostOfRepairs;
	}

	public void setEstimatedCostOfRepairs(double estimatedCostOfRepairs) {
		this.estimatedCostOfRepairs = estimatedCostOfRepairs;
	}

	public void displayClaim() {
		System.out.printf(
				"\n[CLAIM]" + "\nClaim ID: %s" + "\nPolicy ID: %s" + "\nDate of Accident: %s"
						+ "\nAddress where accident happened: %s" + "\nDescription of Accident: %s"
						+ "\nDescription of Damage: %s" + "\nEstimated cost of repair: $%.2f\n",
				claimId, super.getPolicyId(), dateOfAccident, address, description, description2,
				estimatedCostOfRepairs);
	}

	public void reset() {
		address = null;
		claimId = null;
		dateOfAccident = null;
		description = null;
		description2 = null;
		estimatedCostOfRepairs = 0;
	}
}
