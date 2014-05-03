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
  public  MedicalTest getMedicalTest() {
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

}
