/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */

var debug = function (log_txt) {
  if (window.console != undefined) {
    console.log(log_txt);
  }
}

function util_checkRegexp(s, regexp) {
  return regexp.test(s);
}

function util_checkPassword(s) {
  if (util_checkRegexp(s,/[a-z]/) && util_checkRegexp(s,/[A-Z]/) && util_checkRegexp(s,/\d/) && util_checkRegexp(s,/\W/)) {
    return true;
  }
  return false;
}



function util_buildFullName(first, middle, last) {
  first = util_nullCheck(first);
  middle = util_nullCheck(middle);
  last = util_nullCheck(last);
  var middleToken = "";
  if (typeof first !== 'undefined' && first.length > 0) {
    if (typeof middle !== 'undefined' && middle.length > 0) {
      middleToken = middle + " ";
    }
    last = (typeof last === 'undefined') ? '' : last;
    return first + " " + middleToken + last;
  }
  else {
      return "";
  }
}


function util_nullCheck(val) {
  if (typeof val !== 'undefined' && val != null) {
    return val;
  }
  return "";
}

function util_checkSessionResponse(obj) {
  idleTime = 0;
  if (obj != undefined){
  if(obj.authenticated == false){
    //util_logout(DO_AUTO_LOGOUT);
    return false;
  }
  return true;
  }
  else {
   DO_AUTO_LOGOUT = false;
   util_logout(DO_AUTO_LOGOUT, DO_AUTO_SERVER_LOGOUT);
   return false;   
  }
}


function util_checkSession() {
  var jsonData = JSON.stringify({sessionId: user.sessionId});
  $.post("auth/checkSession",{data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    if(parsedData.authenticated == false){util_logout(DO_AUTO_LOGOUT);return false;}
  }); 
}


function util_showError(item, message) {
  if (message == null) {
    message = 'required field';
  }
  $(item).text(message);
  $(item).css({opacity: 0.0, visibility: "visible"}).animate({opacity: 1.0}); 
}
