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
@Table(name = "patient_follow_up")
public class PatientFollowUp extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -8700198827939079441L;
  private Patient patient;
  private Clinician clinician;
  private Date date;
  private Date followUpDate;

  private String level;
  private String when;
  private String condition;
  private String dispenseRx;
  private String USS;
  private String pregnant;
  private String woundCare;
  private String refToSpecialist;
  private String dentalList;
  private String physiotherapy;
  private String bloodLabs;
  private String other;
  private String pulmonaryFXTest;
  private String vision;
  private boolean completed;
  private String notes;

  public PatientFollowUp() {
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

  @Column(name = "follow_up_date")
  public Date getFollowUpDate() {
    return followUpDate;
  }

  public void setFollowUpDate(Date followUpDate) {
    this.followUpDate = followUpDate;
  }

  @Column(name = "level")
  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  @Column(name = "follow_up_when")
  public String getwhen() {
    return when;
  }

  public void setWhen(String when) {
    this.when = when;
  }

  @Column(name = "follow_up_condition")
  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  @Column(name = "dispense_rx")
  public String getDispenseRx() {
    return dispenseRx;
  }

  public void setDispenseRx(String dispenseRx) {
    this.dispenseRx = dispenseRx;
  }

  @Column(name = "uss")
  public String getUSS() {
    return USS;
  }

  public void setUSS(String USS) {
    this.USS = USS;
  }

  @Column(name = "pregnant")
  public String getPregnant() {
    return pregnant;
  }

  public void setPregnant(String pregnant) {
    this.pregnant = pregnant;
  }

  @Column(name = "wound_care")
  public String getWoundCare() {
    return woundCare;
  }

  public void setWoundCare(String woundCare) {
    this.woundCare = woundCare;
  }

  @Column(name = "ref_specialist")
  public String getRefToSpecialist() {
    return refToSpecialist;
  }

  public void setRefToSpecialist(String refToSpecialist) {
    this.refToSpecialist = refToSpecialist;
  }

  @Column(name = "dental_list")
  public String getDentalList() {
    return dentalList;
  }

  public void setDentalList(String dentalList) {
    this.dentalList = dentalList;
  }

  @Column(name = "physiotherapy")
  public String getPhysiotherapy() {
    return physiotherapy;
  }

  public void setPhysiotherapy(String physiotherapy) {
    this.physiotherapy = physiotherapy;
  }

  @Column(name = "blood_labs")
  public String getBloodLabs() {
    return bloodLabs;
  }

  public void setBloodLabs(String bloodLabs) {
    this.bloodLabs = bloodLabs;
  }

  @Column(name = "other")
  public String getOther() {
    return other;
  }

  public void setOther(String other) {
    this.other = other;
  }

  @Column(name = "pulmonary_fx_test")
  public String getPulmonaryFXTest() {
    return pulmonaryFXTest;
  }

  public void setPulmonaryFXTest(String pulmonaryFXTest) {
    this.pulmonaryFXTest = pulmonaryFXTest;
  }

  @Column(name = "vision")
  public String getVision() {
    return vision;
  }

  public void setVision(String vision) {
    this.vision = vision;
  }

  @Column(name = "complete")
  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  @Column(name = "notes")
  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((USS == null) ? 0 : USS.hashCode());
    result = prime * result + ((bloodLabs == null) ? 0 : bloodLabs.hashCode());
    result = prime * result + ((clinician == null) ? 0 : clinician.hashCode());
    result = prime * result + (completed ? 1231 : 1237);
    result = prime * result + ((condition == null) ? 0 : condition.hashCode());
    result = prime * result + ((date == null) ? 0 : date.hashCode());
    result = prime * result + ((dentalList == null) ? 0 : dentalList.hashCode());
    result = prime * result + ((dispenseRx == null) ? 0 : dispenseRx.hashCode());
    result = prime * result + ((followUpDate == null) ? 0 : followUpDate.hashCode());
    result = prime * result + ((level == null) ? 0 : level.hashCode());
    result = prime * result + ((notes == null) ? 0 : notes.hashCode());
    result = prime * result + ((other == null) ? 0 : other.hashCode());
    result = prime * result + ((patient == null) ? 0 : patient.hashCode());
    result = prime * result + ((physiotherapy == null) ? 0 : physiotherapy.hashCode());
    result = prime * result + ((pregnant == null) ? 0 : pregnant.hashCode());
    result = prime * result + ((pulmonaryFXTest == null) ? 0 : pulmonaryFXTest.hashCode());
    result = prime * result + ((refToSpecialist == null) ? 0 : refToSpecialist.hashCode());
    result = prime * result + ((vision == null) ? 0 : vision.hashCode());
    result = prime * result + ((when == null) ? 0 : when.hashCode());
    result = prime * result + ((woundCare == null) ? 0 : woundCare.hashCode());
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
    PatientFollowUp other = (PatientFollowUp) obj;
    if (USS == null) {
      if (other.USS != null)
        return false;
    } else if (!USS.equals(other.USS))
      return false;
    if (bloodLabs == null) {
      if (other.bloodLabs != null)
        return false;
    } else if (!bloodLabs.equals(other.bloodLabs))
      return false;
    if (clinician == null) {
      if (other.clinician != null)
        return false;
    } else if (!clinician.equals(other.clinician))
      return false;
    if (completed != other.completed)
      return false;
    if (condition == null) {
      if (other.condition != null)
        return false;
    } else if (!condition.equals(other.condition))
      return false;
    if (date == null) {
      if (other.date != null)
        return false;
    } else if (!date.equals(other.date))
      return false;
    if (dentalList == null) {
      if (other.dentalList != null)
        return false;
    } else if (!dentalList.equals(other.dentalList))
      return false;
    if (dispenseRx == null) {
      if (other.dispenseRx != null)
        return false;
    } else if (!dispenseRx.equals(other.dispenseRx))
      return false;
    if (followUpDate == null) {
      if (other.followUpDate != null)
        return false;
    } else if (!followUpDate.equals(other.followUpDate))
      return false;
    if (level == null) {
      if (other.level != null)
        return false;
    } else if (!level.equals(other.level))
      return false;
    if (notes == null) {
      if (other.notes != null)
        return false;
    } else if (!notes.equals(other.notes))
      return false;
    if (this.other == null) {
      if (other.other != null)
        return false;
    } else if (!this.other.equals(other.other))
      return false;
    if (patient == null) {
      if (other.patient != null)
        return false;
    } else if (!patient.equals(other.patient))
      return false;
    if (physiotherapy == null) {
      if (other.physiotherapy != null)
        return false;
    } else if (!physiotherapy.equals(other.physiotherapy))
      return false;
    if (pregnant == null) {
      if (other.pregnant != null)
        return false;
    } else if (!pregnant.equals(other.pregnant))
      return false;
    if (pulmonaryFXTest == null) {
      if (other.pulmonaryFXTest != null)
        return false;
    } else if (!pulmonaryFXTest.equals(other.pulmonaryFXTest))
      return false;
    if (refToSpecialist == null) {
      if (other.refToSpecialist != null)
        return false;
    } else if (!refToSpecialist.equals(other.refToSpecialist))
      return false;
    if (vision == null) {
      if (other.vision != null)
        return false;
    } else if (!vision.equals(other.vision))
      return false;
    if (when == null) {
      if (other.when != null)
        return false;
    } else if (!when.equals(other.when))
      return false;
    if (woundCare == null) {
      if (other.woundCare != null)
        return false;
    } else if (!woundCare.equals(other.woundCare))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "PatientFollowUp [patient=" + patient + ", clinician=" + clinician + ", date=" + date + ", followUpDate="
        + followUpDate + ", level=" + level + ", when=" + when + ", condition=" + condition + ", dispenseRx="
        + dispenseRx + ", USS=" + USS + ", pregnant=" + pregnant + ", woundCare=" + woundCare + ", refToSpecialist="
        + refToSpecialist + ", dentalList=" + dentalList + ", physiotherapy=" + physiotherapy + ", bloodLabs="
        + bloodLabs + ", other=" + other + ", pulmonaryFXTest=" + pulmonaryFXTest + ", vision=" + vision
        + ", completed=" + completed + ", notes=" + notes + "]";
  }

}
