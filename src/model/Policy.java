/**
 * @Class: Policy
 * @Description: Policy db object
 * @author CalvinLingat
 * @Created:8/15/2022 
 */
package model;

import java.time.LocalDate;

public class Policy extends AccountHolder {
	private LocalDate effectiveDate, expirationDate;
	private String policyHolder, policyId, status;
	private double policyPremium;

	public Policy() {
		super();
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getPolicyHolder() {
		return policyHolder;
	}

	public void setPolicyHolder(String policyHolder) {
		this.policyHolder = policyHolder;
	}

	public double getPolicyPremium() {
		return policyPremium;
	}

	public void setPolicyPremium(double policyPremium) {
		this.policyPremium = policyPremium;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void displayPolicy() {
		System.out.printf(
				"\n[POLICY INFO]" + "\nPolicy ID %s: " + "\nAccountNumber:  %s" + "\nPolicyHolder: %s"
						+ "\nEffectiveDate: %s" + "\nExpirationDate: %s" + "\nPolicyPremium: $%.2f" + "\nStatus: %s",
				policyId, getAccountNumber(), policyHolder, effectiveDate, expirationDate, policyPremium, status);
	}

	public void reset() {
		effectiveDate = null;
		expirationDate = null;
		policyHolder = null;
		policyId = null;
		policyPremium = 0;
		status = null;
	}

}
