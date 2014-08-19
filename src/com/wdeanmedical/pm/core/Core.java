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
  public static String smtpauth;
  public static String mailServer;
  public static String factport;
  public static String factclass;
  public static String fallback;
  public static String starttls;
  public static String imageMagickHome;
  public static int sessionTimeout;
  public static String filesHome;  
  public static String patientDirPath;
  public static String appBaseDir;
  public static String appDefaultHeadshot;
  public static String imagesDir;
  public static Map<String, UserSessionData> userSessionMap = Collections.synchronizedMap(new TreeMap<String, UserSessionData>());
  
}
