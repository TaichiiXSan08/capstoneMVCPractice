/*
 * @Class: RatingEngine
 * @Description: calculates and updates the policy premium
 * @author CalvinLingat
 * @Created:8/15/2022 
 */
package controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

import model.*;

public class RatingEngine {

	// policy premium computation based on capstone requirement
	public Policy calculatePremium(Policy policy, ArrayList<Vehicle> vehicleList, PolicyHolder policyHolder,
			Connection con, VehicleDAO vehicleDAO) {
		/*
		 * P (premium) = (vp x vpf) + ((vp/100)/dlx) P = calculated premium vp = vehicle
		 * purchase price vpf = vehicle price factor dlx = num of years since driver
		 * license was first issued
		 */
		double premium, purchasePrice, priceFactor, dlx, premiumTotal = 0;

		for (Vehicle tempVehicle : vehicleList) {
			purchasePrice = tempVehicle.getPurchasePrice();
			priceFactor = vehicleDAO.selectPriceFactor(tempVehicle, con);
			dlx = (LocalDate.now().getYear()) - (policyHolder.getLicenseIssued().getYear());
			
			if (dlx == 0)
				dlx = 1;

			/*round is used in case there are divide by 0 scenarios where the result is "infinity" not sure if this is the correct approach
			 * since rounding with money is generally frowned upon. 
			 */
			premium = (purchasePrice * priceFactor) + ((purchasePrice / 100) / dlx);

			tempVehicle.setPremiumCharged(premium);

			vehicleDAO.insertVehicleToDB(con, tempVehicle, policyHolder, policy);

			premiumTotal += premium;
		}
		policy.setPolicyPremium(premiumTotal);
		return policy;
	}
}
