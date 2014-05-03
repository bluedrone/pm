package com.wdeanmedical.pm.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "intake_question")
public class IntakeQuestion extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -5235097606726399045L;
  private String question = "&nbsp;";
  private String response = "&nbsp;";
  private int patientIntakeId;
  private int patientId;

  public IntakeQuestion() {
  }

  @Column(name = "question")
  public String getQuestion() { return question; }
  public void setQuestion(String question) { this.question = question; }

  @Column(name = "response")
  public String getResponse() { return response; }
  public void setResponse(String response) { this.response = response; }

  @Column(name = "patient_intake_id")
  public int getPatientIntakeId() { return patientIntakeId; }
  public void setPatientIntakeId(int patientIntakeId) { this.patientIntakeId = patientIntakeId; }
  
  @Column(name = "patient_id")
  public int getPatientId() { return patientId; }
  public void setPatientId(int patientId) { this.patientId = patientId; }
  
}
