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
  public Patient getPatient() { return patient; }
  public void setPatient(Patient patient) { this.patient = patient; }

  @JoinColumn(name = "clinician", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public Clinician getClinician() { return clinician; }
  public void setClinician(Clinician clinician) { this.clinician = clinician; }

  @Column(name = "date")
  public Date getDate() { return date; }
  public void setDate(Date date) { this.date = date; }

  @Column(name = "num_cups_water")
  public Integer getNumCupsWater() { return numCupsWater; }
  public void setNumCupsWater(Integer numCupsWater) { this.numCupsWater = numCupsWater; }

  @Column(name = "num_cups_coffee")
  public Integer getNumCupsCoffee() { return numCupsCoffee; }
  public void setNumCupsCoffee(Integer numCupsCoffee) { this.numCupsCoffee = numCupsCoffee; }

  @Column(name = "num_cups_tea")
  public Integer getNumCupsTea() { return numCupsTea; }
  public void setNumCupsTea(Integer numCupsTea) { this.numCupsTea = numCupsTea; }

  @Column(name = "water_source")
  public String getWaterSource() { return waterSource; }
  public void setWaterSource(String waterSource) { this.waterSource = waterSource; }
  
  @Transient
  public List<EncounterQuestion> getEncounterQuestionList() { return encounterQuestionList; }
  public void setEncounterQuestionList(List<EncounterQuestion> encounterQuestionList) { this.encounterQuestionList = encounterQuestionList; }

}
