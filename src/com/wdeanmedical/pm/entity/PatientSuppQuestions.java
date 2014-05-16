package com.wdeanmedical.pm.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "patient_supp_questions")
public class PatientSuppQuestions extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -7215757912004945158L;

	private Patient patient;
	private Clinician clinician;
	private Date date;
	private Integer numCupsWater;
	private Integer numCupsCoffee;
	private Integer numCupsTea;
	private String waterSource;
	private List<EncounterQuestion> encounterQuestionList;

	public PatientSuppQuestions() {
	}

	@JoinColumn(name = "patient", referencedColumnName = "id")
	@ManyToOne(optional = false)
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@JoinColumn(name = "clinician", referencedColumnName = "id")
	@ManyToOne(optional = false)
	public Clinician getClinician() {
		return clinician;
	}

	public void setClinician(Clinician clinician) {
		this.clinician = clinician;
	}

	@Column(name = "date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "num_cups_water")
	public Integer getNumCupsWater() {
		return numCupsWater;
	}

	public void setNumCupsWater(Integer numCupsWater) {
		this.numCupsWater = numCupsWater;
	}

	@Column(name = "num_cups_coffee")
	public Integer getNumCupsCoffee() {
		return numCupsCoffee;
	}

	public void setNumCupsCoffee(Integer numCupsCoffee) {
		this.numCupsCoffee = numCupsCoffee;
	}

	@Column(name = "num_cups_tea")
	public Integer getNumCupsTea() {
		return numCupsTea;
	}

	public void setNumCupsTea(Integer numCupsTea) {
		this.numCupsTea = numCupsTea;
	}

	@Column(name = "water_source")
	public String getWaterSource() {
		return waterSource;
	}

	public void setWaterSource(String waterSource) {
		this.waterSource = waterSource;
	}

	@Transient
	public List<EncounterQuestion> getEncounterQuestionList() {
		return encounterQuestionList;
	}

	public void setEncounterQuestionList(
			List<EncounterQuestion> encounterQuestionList) {
		this.encounterQuestionList = encounterQuestionList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((clinician == null) ? 0 : clinician.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime
				* result
				+ ((encounterQuestionList == null) ? 0 : encounterQuestionList
						.hashCode());
		result = prime * result
				+ ((numCupsCoffee == null) ? 0 : numCupsCoffee.hashCode());
		result = prime * result
				+ ((numCupsTea == null) ? 0 : numCupsTea.hashCode());
		result = prime * result
				+ ((numCupsWater == null) ? 0 : numCupsWater.hashCode());
		result = prime * result + ((patient == null) ? 0 : patient.hashCode());
		result = prime * result
				+ ((waterSource == null) ? 0 : waterSource.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatientSuppQuestions other = (PatientSuppQuestions) obj;
		if (clinician == null) {
			if (other.clinician != null)
				return false;
		} else if (!clinician.equals(other.clinician))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (encounterQuestionList == null) {
			if (other.encounterQuestionList != null)
				return false;
		} else if (!encounterQuestionList.equals(other.encounterQuestionList))
			return false;
		if (numCupsCoffee == null) {
			if (other.numCupsCoffee != null)
				return false;
		} else if (!numCupsCoffee.equals(other.numCupsCoffee))
			return false;
		if (numCupsTea == null) {
			if (other.numCupsTea != null)
				return false;
		} else if (!numCupsTea.equals(other.numCupsTea))
			return false;
		if (numCupsWater == null) {
			if (other.numCupsWater != null)
				return false;
		} else if (!numCupsWater.equals(other.numCupsWater))
			return false;
		if (patient == null) {
			if (other.patient != null)
				return false;
		} else if (!patient.equals(other.patient))
			return false;
		if (waterSource == null) {
			if (other.waterSource != null)
				return false;
		} else if (!waterSource.equals(other.waterSource))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PatientSuppQuestions [patient=" + patient + ", clinician="
				+ clinician + ", date=" + date + ", numCupsWater="
				+ numCupsWater + ", numCupsCoffee=" + numCupsCoffee
				+ ", numCupsTea=" + numCupsTea + ", waterSource=" + waterSource
				+ ", encounterQuestionList=" + encounterQuestionList + "]";
	}

}
