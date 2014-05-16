package com.wdeanmedical.pm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "patient_medical_test")
public class PatientMedicalTest extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -3974028308118232534L;

  private Patient patient;
  private MedicalTest medicalTest;
  private MedicalTestStatus status;
  private Date date;
  private Clinician clinician;

  public PatientMedicalTest() {
  }

  @JoinColumn(name = "patient", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  @JoinColumn(name = "medical_test", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public MedicalTest getMedicalTest() {
    return medicalTest;
  }

  public void setMedicalTest(MedicalTest medicalTest) {
    this.medicalTest = medicalTest;
  }

  @Column(name = "date")
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @JoinColumn(name = "status", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public MedicalTestStatus getStatus() {
    return status;
  }

  public void setStatus(MedicalTestStatus status) {
    this.status = status;
  }

  @JoinColumn(name = "clinician", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public Clinician getClinician() {
    return clinician;
  }

  public void setClinician(Clinician clinician) {
    this.clinician = clinician;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((clinician == null) ? 0 : clinician.hashCode());
    result = prime * result + ((date == null) ? 0 : date.hashCode());
    result = prime * result + ((medicalTest == null) ? 0 : medicalTest.hashCode());
    result = prime * result + ((patient == null) ? 0 : patient.hashCode());
    result = prime * result + ((status == null) ? 0 : status.hashCode());
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
    PatientMedicalTest other = (PatientMedicalTest) obj;
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
    if (medicalTest == null) {
      if (other.medicalTest != null)
        return false;
    } else if (!medicalTest.equals(other.medicalTest))
      return false;
    if (patient == null) {
      if (other.patient != null)
        return false;
    } else if (!patient.equals(other.patient))
      return false;
    if (status == null) {
      if (other.status != null)
        return false;
    } else if (!status.equals(other.status))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "PatientMedicalTest [patient=" + patient + ", medicalTest=" + medicalTest + ", status=" + status + ", date="
        + date + ", clinician=" + clinician + "]";
  }

}
