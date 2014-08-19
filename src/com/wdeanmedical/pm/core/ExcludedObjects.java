/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */
 
package com.wdeanmedical.pm.core;

import java.util.Map;
import java.util.TreeMap;


public class ExcludedObjects {

  public static String AppDAO_getPatientMessagesByClinician = "password, salt";
  
  public static Map<String, String[]> list = new TreeMap<String, String[]>();

  public ExcludedObjects() {
    list.put("Credentials",   new String[] {"password","salt"});
 }
 
}
