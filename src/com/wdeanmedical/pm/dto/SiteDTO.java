package com.wdeanmedical.pm.dto; 

import java.util.List;

import com.wdeanmedical.pm.entity.PatientMedication;


public class SiteDTO extends AuthorizedDTO {


  private List<PatientMedication> patientMedications;


  public SiteDTO() {
  }

  public List<PatientMedication> getPatientMedications() {
    return patientMedications;
  }
  public void setPatientMedications(List<PatientMedication> patientMedications) {
    this.patientMedications = patientMedications;
  }
 
}