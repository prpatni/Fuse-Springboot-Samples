package org.mycompany;

public class FuelObject {



	//private String enerySource;

	private String fuelCategory;

	private String fuelCategoryRollup;

	

	public String getFuelCategory() {

		return fuelCategory;

	}

	public void setFuelCategory(String fuelCategory) {

		this.fuelCategory = fuelCategory;

	}

	public String getFuelCategoryRollup() {

		return fuelCategoryRollup;

	}

	public void setFuelCategoryRollup(String fuelCategoryRollup) {

		this.fuelCategoryRollup = fuelCategoryRollup;

	}

	/*public String getEnerySource() {

		return enerySource;

	}

	public void setEnerySource(String enerySource) {

		this.enerySource = enerySource;

	}*/

	

	public String toString() {

		return "{" + fuelCategory + "::" + fuelCategoryRollup + "}";

	}
}
