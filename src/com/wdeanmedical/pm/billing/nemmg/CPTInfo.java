package com.wdeanmedical.pm.billing.nemmg;

public class CPTInfo {
	private String CPT;
	private String MOD;
	private int units;
	private double charge;

	public String getCPT() {
		return CPT;
	}

	public void setCPT(String cPT) {
		CPT = cPT;
	}

	public String getMOD() {
		return MOD;
	}

	public void setMOD(String mOD) {
		MOD = mOD;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public double getCharge() {
		return charge;
	}

	public void setCharge(double charge) {
		this.charge = charge;
	}

	@Override
	public String toString() {
		return "CPTInfo [CPT=" + CPT + ", MOD=" + MOD + ", units=" + units
				+ ", charge=" + charge + "]";
	}
}
