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
@Table(name = "medical_history")
public class MedicalHistory extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 795401034269323351L;

  private Integer patientId;
  private Date date;
  private String pastSM;
  private String famHist;
  private String famHistOther;
  private String famHistNotes;
  private String allergFood;
  private String allergDrug;
  private String allergEnv;
  private String vacc;
  private String vaccNotes;
  private String subst;
  private float smokePksDay;
  private float yearsSmoked;
  private float smokeYearsQuit;
  private float etohUnitsWeek;
  private String currentDrugs;
  private List<EncounterMedication> encounterMedicationList;

  public MedicalHistory() {
  }

  @Column(name = "patient_id")
  public Integer getPatientId() {
    return patientId;
  }

  public void setPatientId(Integer patientId) {
    this.patientId = patientId;
  }

  @Column(name = "date")
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Column(name = "past_sm")
  public String getPastSM() {
    return pastSM;
  }

  public void setPastSM(String pastSM) {
    this.pastSM = pastSM;
  }

  @Column(name = "fam_hist")
  public String getFamHist() {
    return famHist;
  }

  public void setFamHist(String famHist) {
    this.famHist = famHist;
  }

  @Column(name = "fam_hist_other")
  public String getFamHistOther() {
    return famHistOther;
  }

  public void setFamHistOther(String famHistOther) {
    this.famHistOther = famHistOther;
  }

  @Column(name = "fam_hist_notes")
  public String getFamHistNotes() {
    return famHistNotes;
  }

  public void setFamHistNotes(String famHistNotes) {
    this.famHistNotes = famHistNotes;
  }

  @Column(name = "allerg_food")
  public String getAllergFood() {
    return allergFood;
  }

  public void setAllergFood(String allergFood) {
    this.allergFood = allergFood;
  }

  @Column(name = "allerg_drug")
  public String getAllergDrug() {
    return allergDrug;
  }

  public void setAllergDrug(String allergDrug) {
    this.allergDrug = allergDrug;
  }

  @Column(name = "allerg_evn")
  public String getAllergEnv() {
    return allergEnv;
  }

  public void setAllergEnv(String allergEnv) {
    this.allergEnv = allergEnv;
  }

  @Column(name = "vacc")
  public String getVacc() {
    return vacc;
  }

  public void setVacc(String vacc) {
    this.vacc = vacc;
  }

  @Column(name = "vacc_notes")
  public String getVaccNotes() {
    return vaccNotes;
  }

  public void setVaccNotes(String vaccNotes) {
    this.vaccNotes = vaccNotes;
  }

  @Column(name = "subst")
  public String getSubst() {
    return subst;
  }

  public void setSubst(String subst) {
    this.subst = subst;
  }

  @Column(name = "smoke_pks_day")
  public float getSmokePksDay() {
    return smokePksDay;
  }

  public void setSmokePksDay(float smokePksDay) {
    this.smokePksDay = smokePksDay;
  }

  @Column(name = "years_smoked")
  public float getYearsSmoked() {
    return yearsSmoked;
  }

  public void setYearsSmoked(float yearsSmoked) {
    this.yearsSmoked = yearsSmoked;
  }

  @Column(name = "smoke_years_quit")
  public float getSmokeYearsQuit() {
    return smokeYearsQuit;
  }

  public void setSmokeYearsQuit(float smokeYearsQuit) {
    this.smokeYearsQuit = smokeYearsQuit;
  }

  @Column(name = "etoh_units_week")
  public float getEtohUnitsWeek() {
    return etohUnitsWeek;
  }

  public void setEtohUnitsWeek(float etohUnitsWeek) {
    this.etohUnitsWeek = etohUnitsWeek;
  }

  @Column(name = "current_drugs")
  public String getCurrentDrugs() {
    return currentDrugs;
  }

  public void setCurrentDrugs(String currentDrugs) {
    this.currentDrugs = currentDrugs;
  }

  @Transient
  public List<EncounterMedication> getEncounterMedicationList() {
    return encounterMedicationList;
  }

  public void setEncounterMedicationList(List<EncounterMedication> encounterMedicationList) {
    this.encounterMedicationList = encounterMedicationList;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((allergDrug == null) ? 0 : allergDrug.hashCode());
    result = prime * result + ((allergEnv == null) ? 0 : allergEnv.hashCode());
    result = prime * result + ((allergFood == null) ? 0 : allergFood.hashCode());
    result = prime * result + ((currentDrugs == null) ? 0 : currentDrugs.hashCode());
    result = prime * result + ((date == null) ? 0 : date.hashCode());
    result = prime * result + ((encounterMedicationList == null) ? 0 : encounterMedicationList.hashCode());
    result = prime * result + Float.floatToIntBits(etohUnitsWeek);
    result = prime * result + ((famHist == null) ? 0 : famHist.hashCode());
    result = prime * result + ((famHistNotes == null) ? 0 : famHistNotes.hashCode());
    result = prime * result + ((famHistOther == null) ? 0 : famHistOther.hashCode());
    result = prime * result + ((pastSM == null) ? 0 : pastSM.hashCode());
    result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
    result = prime * result + Float.floatToIntBits(smokePksDay);
    result = prime * result + Float.floatToIntBits(smokeYearsQuit);
    result = prime * result + ((subst == null) ? 0 : subst.hashCode());
    result = prime * result + ((vacc == null) ? 0 : vacc.hashCode());
    result = prime * result + ((vaccNotes == null) ? 0 : vaccNotes.hashCode());
    result = prime * result + Float.floatToIntBits(yearsSmoked);
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
    MedicalHistory other = (MedicalHistory) obj;
    if (allergDrug == null) {
      if (other.allergDrug != null)
        return false;
    } else if (!allergDrug.equals(other.allergDrug))
      return false;
    if (allergEnv == null) {
      if (other.allergEnv != null)
        return false;
    } else if (!allergEnv.equals(other.allergEnv))
      return false;
    if (allergFood == null) {
      if (other.allergFood != null)
        return false;
    } else if (!allergFood.equals(other.allergFood))
      return false;
    if (currentDrugs == null) {
      if (other.currentDrugs != null)
        return false;
    } else if (!currentDrugs.equals(other.currentDrugs))
      return false;
    if (date == null) {
      if (other.date != null)
        return false;
    } else if (!date.equals(other.date))
      return false;
    if (encounterMedicationList == null) {
      if (other.encounterMedicationList != null)
        return false;
    } else if (!encounterMedicationList.equals(other.encounterMedicationList))
      return false;
    if (Float.floatToIntBits(etohUnitsWeek) != Float.floatToIntBits(other.etohUnitsWeek))
      return false;
    if (famHist == null) {
      if (other.famHist != null)
        return false;
    } else if (!famHist.equals(other.famHist))
      return false;
    if (famHistNotes == null) {
      if (other.famHistNotes != null)
        return false;
    } else if (!famHistNotes.equals(other.famHistNotes))
      return false;
    if (famHistOther == null) {
      if (other.famHistOther != null)
        return false;
    } else if (!famHistOther.equals(other.famHistOther))
      return false;
    if (pastSM == null) {
      if (other.pastSM != null)
        return false;
    } else if (!pastSM.equals(other.pastSM))
      return false;
    if (patientId == null) {
      if (other.patientId != null)
        return false;
    } else if (!patientId.equals(other.patientId))
      return false;
    if (Float.floatToIntBits(smokePksDay) != Float.floatToIntBits(other.smokePksDay))
      return false;
    if (Float.floatToIntBits(smokeYearsQuit) != Float.floatToIntBits(other.smokeYearsQuit))
      return false;
    if (subst == null) {
      if (other.subst != null)
        return false;
    } else if (!subst.equals(other.subst))
      return false;
    if (vacc == null) {
      if (other.vacc != null)
        return false;
    } else if (!vacc.equals(other.vacc))
      return false;
    if (vaccNotes == null) {
      if (other.vaccNotes != null)
        return false;
    } else if (!vaccNotes.equals(other.vaccNotes))
      return false;
    if (Float.floatToIntBits(yearsSmoked) != Float.floatToIntBits(other.yearsSmoked))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "MedicalHistory [patientId=" + patientId + ", date=" + date + ", pastSM=" + pastSM + ", famHist=" + famHist
        + ", famHistOther=" + famHistOther + ", famHistNotes=" + famHistNotes + ", allergFood=" + allergFood
        + ", allergDrug=" + allergDrug + ", allergEnv=" + allergEnv + ", vacc=" + vacc + ", vaccNotes=" + vaccNotes
        + ", subst=" + subst + ", smokePksDay=" + smokePksDay + ", yearsSmoked=" + yearsSmoked + ", smokeYearsQuit="
        + smokeYearsQuit + ", etohUnitsWeek=" + etohUnitsWeek + ", currentDrugs=" + currentDrugs
        + ", encounterMedicationList=" + encounterMedicationList + "]";
  }

}
