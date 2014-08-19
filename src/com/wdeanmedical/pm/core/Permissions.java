/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */
 
package com.wdeanmedical.pm.core;

import java.util.Map;
import java.util.TreeMap;


public class Permissions {
  public static Map<String, boolean[]> userPermissionsMap = new TreeMap<String, boolean[]>();
  
  public static void buildUserPermissionsMap() {
  
    userPermissionsMap.put("/admin/activateClinician",            new boolean[] {true ,true});
    userPermissionsMap.put("/admin/activateUser",                 new boolean[] {true,true});
    userPermissionsMap.put("/admin/deactivateClinician",          new boolean[] {true ,true});
    userPermissionsMap.put("/admin/deactivateUser",               new boolean[] {true,true});
    userPermissionsMap.put("/admin/getUsers",                     new boolean[] {true,true});
    userPermissionsMap.put("/admin/purgeClinician",               new boolean[] {true ,true});
    userPermissionsMap.put("/admin/purgeUser",                    new boolean[] {true ,true});
    userPermissionsMap.put("/admin/saveNewClinician",             new boolean[] {true ,true});
    userPermissionsMap.put("/admin/saveNewUser",                  new boolean[] {true ,true});
    userPermissionsMap.put("/admin/updateClinician",              new boolean[] {true ,true});
    userPermissionsMap.put("/admin/updateUser",                   new boolean[] {true ,true});
    
    userPermissionsMap.put("/app/changeApptTime",                 new boolean[] {true ,true});
    userPermissionsMap.put("/app/deleteAppt",                     new boolean[] {true ,true});
    userPermissionsMap.put("/app/getAppointment",                 new boolean[] {true ,true});
    userPermissionsMap.put("/app/getAppointments",                new boolean[] {true ,true});
    userPermissionsMap.put("/app/getClinicianMessage",            new boolean[] {true ,true});
    userPermissionsMap.put("/app/getClinicians",                  new boolean[] {true ,true});
    userPermissionsMap.put("/app/getMedicalAdvice",               new boolean[] {true ,true});
    userPermissionsMap.put("/app/getPastAppointments",            new boolean[] {true ,true});
    userPermissionsMap.put("/app/getPatientClinicians",           new boolean[] {true ,true});
    userPermissionsMap.put("/app/getPatientLetters",              new boolean[] {true ,true});
    userPermissionsMap.put("/app/getPatientMedicalTests",         new boolean[] {true ,true});
    userPermissionsMap.put("/app/getPatientToClinicianMessages",  new boolean[] {true ,true});
    userPermissionsMap.put("/app/getPatientMessages",             new boolean[] {true ,true});
    userPermissionsMap.put("/app/getPatientProcedures",           new boolean[] {true ,true});
    userPermissionsMap.put("/app/getPatientProfileImage",         new boolean[] {true,true});
    userPermissionsMap.put("/app/getPatients",                    new boolean[] {true ,true});
    userPermissionsMap.put("/app/getPatientSentMessages",         new boolean[] {true ,true});
    userPermissionsMap.put("/app/getPatientSummary",              new boolean[] {true ,true});
    userPermissionsMap.put("/app/getPatients",                    new boolean[] {true ,true});
    userPermissionsMap.put("/app/getPatientSentMessages",         new boolean[] {true ,true});
    userPermissionsMap.put("/app/getPatientSummary",              new boolean[] {true ,true});
    userPermissionsMap.put("/app/getRecentPatients",              new boolean[] {true ,true});
    userPermissionsMap.put("/app/getUpcomingAppointments",        new boolean[] {true ,true});
    userPermissionsMap.put("/app/logout",                         new boolean[] {true ,true});
    userPermissionsMap.put("/app/newAppt",                        new boolean[] {true ,true});
    userPermissionsMap.put("/app/park",                           new boolean[] {true ,true});
    userPermissionsMap.put("/app/patientSearch",                  new boolean[] {true ,true});
    userPermissionsMap.put("/app/saveNewPatient",                 new boolean[] {true ,true});
    userPermissionsMap.put("/app/suggestApptSlot",                new boolean[] {true ,true});
    userPermissionsMap.put("/app/unpark",                         new boolean[] {true ,true});
    userPermissionsMap.put("/app/updateAppt",                     new boolean[] {true ,true});
    userPermissionsMap.put("/app/uploadProfileImage",             new boolean[] {true ,true});
    
 }
}
