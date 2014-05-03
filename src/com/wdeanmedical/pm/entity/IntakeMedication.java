package com.wdeanmedical.pm.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "intake_medication")
public class IntakeMedication extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 548926604494760704L;
  
  private String medication;
  private String dose;
  private String frequency;
  private int patientIntakeId;
  private int patientId;

  public IntakeMedication() {
  }

  @Column(name = "medication")
  public String getMedication() { return medication; }
  public void setMedication(String medication) { this.medication = medication; }

  @Column(name = "dose")
  public String getDose() { return dose; }
  public void setDose(String dose) { this.dose = dose; }

  @Column(name = "frequency")
  public String getFrequency() { return frequency; }
  public void setFrequency(String frequency) { this.frequency = frequency; }

  @Column(name = "patient_intake_id")
  public int getPatientIntakeId() { return patientIntakeId; }
  public void setPatientIntakeId(int patientIntakeId) { this.patientIntakeId = patientIntakeId; }
  
  @Column(name = "patient_id")
  public int getPatientId() { return patientId; }
  public void setPatientId(int patientId) { this.patientId = patientId; }
  
}
