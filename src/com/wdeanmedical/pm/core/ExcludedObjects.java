/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */
 
package com.wdeanmedical.pm.core;

import java.util.Map;
import java.util.TreeMap;

import com.wdeanmedical.pm.entity.Patient;


public class ExcludedObjects {

  public static String AppDAO_getPatientMessagesByClinician = "password, salt";
  
  public static Map<String, String[]> list = new TreeMap<String, String[]>();

  public ExcludedObjects() {
    list.put("Credentials",   new String[] {"password","salt"});
 }
  
  public static void excludeObjects(Object obj) {
	    if (obj == null) {
	      return;
	    }
	    String className = obj.getClass().getSimpleName();
	    
	    if ("Patient".equals(className)) {
	      Patient patient = (Patient)obj;
	      patient.setPfsh(null);
	      patient.setHist(null);
	    }
  }
 
}
