/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */

var hide_element_list_cache = $('#signin-screen, #dashboard-screen, #patient-summary-screen, #schedule-screen, #messages-screen, #letters-screen, #user-admin-screen, #app-dropdown-logout, #app-dropdown-settings, #app-dropdown-signin, #main-navigation, #messages-view, #messages-inbox');
  
var signin_cache = $('#signin-screen, #app-dropdown-signin');
var dashboard_cache = $('#dashboard-screen, #app-dropdown-settings, #app-dropdown-logout, #main-navigation');
var patient_summary_cache = $('#patient-summary-screen, #app-dropdown-settings, #app-dropdown-logout');
var schedule_cache = $('#schedule-screen, #app-dropdown-settings, #app-dropdown-logout');
var messages_cache = $('#messages-screen, #app-dropdown-settings, #app-dropdown-logout, #messages-inbox');
var letters_cache = $('#letters-screen, #app-dropdown-settings, #app-dropdown-logout');
var user_admin_cache = $('#user-admin-screen, #app-dropdown-settings, #app-dropdown-logout');

function showScreen(screen) {
  hide_element_list_cache.css({display: "none"});
  screen.css({display: "block"});
}

function app_viewStack(screen, doScroll) {
  switch (screen) {
  case 'signin-screen':
    showScreen(signin_cache);
    break;
  case 'dashboard-screen':
    showScreen(dashboard_cache);
    break;
  case 'patient-summary-screen':
    showScreen(patient_summary_cache);
    break;
  case 'schedule-screen':
    showScreen(schedule_cache);
    break;        
  case 'messages-screen':
    showScreen(messages_cache);
    break;
  case 'letters-screen':
    showScreen(letters_cache);
    break;
  case 'user-admin-screen':
    showScreen(user_admin_cache);
    break;
  }
  if (doScroll) {scroll(0,0);}
}