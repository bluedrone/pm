/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */
 
package com.wdeanmedical.pm.core;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;

import com.wdeanmedical.pm.util.UserSessionData;

public class Core {

  public static ServletContext servletContext;
  public static String timeZone;
  public static Log logger;
  public static Boolean sendMail;
  public static String mailUserName;
  public static String mailPassword;
  public static String mailAuthenticationUser;
  public static String mailAuthenticationPassword;
  public static String mailFrom;
  public static String smtphost;
  public static String smtpport;
  public static String debug;
  public static String ehrHome;
  public static String portalHome;
  public static String smtpauth;
  public static String mailServer;
  public static String factport;
  public static String factclass;
  public static String fallback;
  public static String starttls;
  public static String imageMagickHome;
  public static int sessionTimeout;
  public static String patientDirPath;
  public static String appBaseDir;
  public static String appDefaultHeadshot;
  public static String imagesDir;
  public static Map<String, UserSessionData> userSessionMap = Collections.synchronizedMap(new TreeMap<String, UserSessionData>());
  public static Map<String, boolean[]> userPermissionsMap = new TreeMap<String, boolean[]>();
  
  public static void buildUserPermissionsMap() {
    userPermissionsMap.put("/activateClinician",              new boolean[] {true ,true});
    userPermissionsMap.put("/activateUser",                   new boolean[] {true,true});
    userPermissionsMap.put("/deactivateClinician",            new boolean[] {true ,true});
    userPermissionsMap.put("/deactivateUser",                 new boolean[] {true,true});
    userPermissionsMap.put("/logout",                         new boolean[] {true ,true});
    userPermissionsMap.put("/patientSearch",                  new boolean[] {true ,true});
    userPermissionsMap.put("/getClinicians",                  new boolean[] {true,true});
    userPermissionsMap.put("/getUsers",                       new boolean[] {true,true});
    userPermissionsMap.put("/getPatientChart",                new boolean[] {true ,true});
    userPermissionsMap.put("/getRecentPatients",              new boolean[] {true ,true});
    userPermissionsMap.put("/getPatientToClinicianMessages",  new boolean[] {true ,true});
    userPermissionsMap.put("/park",                           new boolean[] {true ,true});
    userPermissionsMap.put("/purgeClinician",                 new boolean[] {true ,true});
    userPermissionsMap.put("/purgeUser",                      new boolean[] {true ,true});
    userPermissionsMap.put("/unpark",                         new boolean[] {true ,true});
    userPermissionsMap.put("/getAppointments",                new boolean[] {true ,true});
    userPermissionsMap.put("/getClinicians",                  new boolean[] {true ,true});
    userPermissionsMap.put("/getPatients",                    new boolean[] {true ,true});
    userPermissionsMap.put("/newAppt",                        new boolean[] {true ,true});
    userPermissionsMap.put("/purgeUser",                      new boolean[] {true,true});
    userPermissionsMap.put("/saveNewPatient",                 new boolean[] {true ,true});
    userPermissionsMap.put("/saveNewClinician",               new boolean[] {true ,true});
    userPermissionsMap.put("/saveNewUser",                    new boolean[] {true ,true});
    userPermissionsMap.put("/updateClinician",                new boolean[] {true ,true});
    userPermissionsMap.put("/updateUser",                     new boolean[] {true ,true});
    userPermissionsMap.put("/uploadProfileImage",             new boolean[] {true ,true});
 }
  
}
