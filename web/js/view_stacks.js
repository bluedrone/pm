/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */

function app_viewStack(screen, doScroll) {
  switch (screen) {
  case 'signin-screen':
    $('#signin-screen').css({display: "block"});
    $('#dashboard-screen').css({display: "none"});
    $('#patient-summary-screen').css({display: "none"});
    $('#schedule-screen').css({display: "none"});
    $('#messages-screen').css({display: "none"});
    $('#letters-screen').css({display: "none"});
    $('#user-admin-screen').css({display: "none"});
    $('#app-dropdown-logout').css({display: "none"});
    $('#app-dropdown-signin').css({display: "block"});
    $('#app-dropdown-settings').css({display: "none"});
    $('#main-navigation').css({display: "none"});
    break;
  case 'dashboard-screen':
    $('#signin-screen').css({display: "none"});
    $('#dashboard-screen').css({display: "block"});
    $('#patient-summary-screen').css({display: "none"});
    $('#schedule-screen').css({display: "none"});
    $('#messages-screen').css({display: "none"});
    $('#letters-screen').css({display: "none"});
    $('#user-admin-screen').css({display: "none"});
    $('#app-dropdown-logout').css({display: "block"});
    $('#app-dropdown-signin').css({display: "none"});
    $('#app-dropdown-settings').css({display: "block"});
    $('#main-navigation').css({display: "block"});
    break;
  case 'patient-summary-screen':
    $('#signin-screen').css({display: "none"});
    $('#dashboard-screen').css({display: "none"});
    $('#patient-summary-screen').css({display: "block"});
    $('#schedule-screen').css({display: "none"});
    $('#messages-screen').css({display: "none"});
    $('#letters-screen').css({display: "none"});
    $('#user-admin-screen').css({display: "none"});
    $('#app-dropdown-logout').css({display: "block"});
    $('#app-dropdown-signin').css({display: "none"});
    $('#app-dropdown-settings').css({display: "block"});
    break;
  case 'schedule-screen':
    $('#signin-screen').css({display: "none"});
    $('#dashboard-screen').css({display: "none"});
    $('#patient-summary-screen').css({display: "none"});
    $('#schedule-screen').css({display: "block"});
    $('#messages-screen').css({display: "none"});
    $('#letters-screen').css({display: "none"});
    $('#user-admin-screen').css({display: "none"});
    $('#app-dropdown-logout').css({display: "block"});
    $('#app-dropdown-signin').css({display: "none"});
    $('#app-dropdown-settings').css({display: "block"});
    break;        
  case 'messages-screen':
    $('#signin-screen').css({display: "none"});
    $('#dashboard-screen').css({display: "none"});
    $('#patient-summary-screen').css({display: "none"});
    $('#schedule-screen').css({display: "none"});
    $('#messages-screen').css({display: "block"});
    $('#letters-screen').css({display: "none"});
    $('#user-admin-screen').css({display: "none"});
    $('#app-dropdown-logout').css({display: "block"});
    $('#app-dropdown-signin').css({display: "none"});
    $('#app-dropdown-settings').css({display: "block"});
    $('#messages-view').css({display: "none"});
    $('#messages-inbox').css({display: "block"});
    break;
  case 'letters-screen':
    $('#signin-screen').css({display: "none"});
    $('#dashboard-screen').css({display: "none"});
    $('#patient-summary-screen').css({display: "none"});
    $('#schedule-screen').css({display: "none"});
    $('#messages-screen').css({display: "none"});
    $('#letters-screen').css({display: "block"});
    $('#user-admin-screen').css({display: "none"});
    $('#app-dropdown-logout').css({display: "block"});
    $('#app-dropdown-signin').css({display: "none"});
    $('#app-dropdown-settings').css({display: "block"});
    break;
  case 'user-admin-screen':
    $('#signin-screen').css({display: "none"});
    $('#dashboard-screen').css({display: "none"});
    $('#patient-summary-screen').css({display: "none"});
    $('#schedule-screen').css({display: "none"});
    $('#messages-screen').css({display: "none"});
    $('#letters-screen').css({display: "none"});
    $('#user-admin-screen').css({display: "block"});
    $('#app-dropdown-logout').css({display: "block"});
    $('#app-dropdown-signin').css({display: "none"});
    $('#app-dropdown-settings').css({display: "block"});
    break;
  }
  if (doScroll) {scroll(0,0);}
}