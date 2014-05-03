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
@Table(name = "patient_immunization")
public class PatientImmunization extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -210450026605157765L;
  private Patient patient;
  private Immunization immunization;
  private Date date;

  public PatientImmunization() {
  }



  @JoinColumn(name = "patient", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public Patient getPatient() {
    return patient;
  }
  public void setPatient(Patient patient) {
    this.patient = patient;
  }
  
  @JoinColumn(name = "immunization", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public Immunization getImmunization() {
    return immunization;
  }
  public void setImmunization(Immunization immunization) {
    this.immunization = immunization;
  }

  @Column(name = "date")
  public Date getDate() {
    return date;
  }
  public void setDate(Date date) {
    this.date = date;
  }
  
  
 

}
