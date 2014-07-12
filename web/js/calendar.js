/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */

function app_loadCalendar() {
  request = $.ajax({
    type: "GET",
    contentType: "application/json",
    data:"{}",
    url: "app/getAppointments?sessionId="+user.sessionId,
    dataType: "json",
    success: function(data) {
      $('#app-calendar').fullCalendar('destroy');
      var calendar =  $('#app-calendar').fullCalendar({
        header: {
          left: 'prev,next today',
          center: 'title',
          right: 'month,agendaWeek,agendaDay'
        },
        windowResize: true,
        selectable: true,
        selectHelper: true,
        select: function(date, jsEvent, view) {
          RenderUtil.render('dialog/new_event', {}, function(s) {
            $('#modals-placement').html(s);
            $('#modal-new-event').modal('show'); 
            $('.form_time').timepicker({
                template: false,
                showInputs: false,
                minuteStep: 5
            });
            getClinicians();
            $('#app-new-appt-clinician').on('change',function(){
              selectedClinician = $('#app-new-appt-clinician').val();
              getClinicianPatients();
            });
            $('#app-new-appt-submit').one("click", function (e) { handleNewAppt(e); });
            $( "#foo" ).one( "click", function() {
  alert( "This will be displayed only once." );
});
          });
        },
        lazyFetching: true,
        editable: true,
        eventRender: function(event, element) {
          startDate = event.start.format('h:mm');
          endDate = event.end.format('h:mm');
          eventTitle = event.title; 
          //element.find('.fc-event-time').html(startDate + ' - ' + endDate);
          element.find('.fc-event-time').html(startDate);
          element.find('.fc-event-title').html(eventTitle);
        },
        eventMouseover: function(calEvent, jsEvent) {
          var tooltip = 
          '<div class="tooltipevetn" style="min-width:110px;max-width:400px;min-height:20px;background:#C0C0C0;color: black;'+
          'font-size: small; font-weight: bold;position:absolute;z-index:10001;">' + calEvent.desc + '</div>';
          $("body").append(tooltip);
          $(this).mouseover(function(e) {
            $(this).css('z-index', 10000);
            $('.tooltipevetn').fadeIn('500');
            $('.tooltipevetn').fadeTo('10', 1.9);
          }).mousemove(function(e) {
            $('.tooltipevetn').css('top', e.pageY + 10);
            $('.tooltipevetn').css('left', e.pageX + 20);
          });
        },
        eventMouseout: function(calEvent, jsEvent) {
          $(this).css('z-index', 8);
          $('.tooltipevetn').remove();
        },
        events: $.map(data, function(item, i) {
          var event = new Object();
          if (item != null){
            event.id = item.id;
            event.className = item.className;
            event.start = item.start;
            event.end = item.end;
            event.desc = item.desc;
            event.title = item.title;
            event.allDay = item.allDay;
            return event;
          }
        }),
        allDayDefault: false
      });
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
      debug('There was an error while fetching data for calendar.');
    }
  });
}

function handleNewAppt(e) {
  var isValid = true;
  handleNewAppt_clearErrors();
  
  if($("#app-new-appt-start").val().length < 1) { 
    showError('#app-new-appt-start-validation');
    isValid = false;
  }
  if($("#app-new-appt-end").val().length < 1) { 
    showError('#app-new-appt-end-validation');
    isValid = false;
  }
  if($("#app-new-appt-clinician").val().length < 1) { 
    showError('#app-new-appt-clinician-validation');
    isValid = false;
  }
  if($("#app-new-appt-patient").val().length < 1) { 
    showError('#app-new-appt-patient-validation');
    isValid = false;
  }
  if($("#app-new-appt-desc").val().length < 1) { 
    showError('#app-new-appt-desc-validation');
    isValid = false;
  }
  
  if (isValid == false) {
    return;
  }
  
  
  var startTimeString = dateFormat(new Date(), 'mm/dd/yyyy') + " " + $('#app-new-appt-start').val();
  var startTime = moment (startTimeString, "mm/dd/yyyy HH:mm A");
  var endTimeString = dateFormat(new Date(), 'mm/dd/yyyy') + " " + $('#app-new-appt-end').val();
  var endTime = moment (endTimeString, "mm/dd/yyyy HH:mm A");
 
  
  var jsonData = JSON.stringify({ 
    sessionId: user.sessionId,
    startTime: startTimeString,
    endTime: endTimeString,
    clinician: $('#app-new-appt-clinician').val(), 
    patient: $('#app-new-appt-patient').val(),
    desc: $('#app-new-appt-desc').val() 
  });
  
  $.post("app/newAppt", {data:jsonData}, function(data) {
    handleNewAppt_clearForm();
    var parsedData = $.parseJSON(data);
    displayNotification('New appointment created.');
    var parsedData = $.parseJSON(data);
    $('#modal-new-event').modal('hide');
    //$('#app-calendar').fullCalendar('removeEvents');
    //$('#app-calendar').fullCalendar('removeEventSource', data);
    //$('#app-calendar').fullCalendar('addEventSource', data);
    app_loadCalendar();
  });
 
}

function handleNewAppt_clearForm() {
  $('#app-new-appt-start').val('');
  $('#app-new-appt-end').val('');
  $('#app-new-appt-clinician').val('');
  $('#app-new-appt-patient').val('');
  $('#app-new-appt-desc').val('');
  handleNewAppt_clearErrors();
}

function handleNewAppt_clearErrors() {
  $('#app-new-appt-start-validation').css({visibility: "hidden"});
  $('#app-new-appt-end-validation').css({visibility: "hidden"});
  $('#app-new-appt-clinician-validation').css({visibility: "hidden"});
  $('#app-new-appt-patient-validation').css({visibility: "hidden"});
  $('#app-new-appt-desc-validation').css({visibility: "hidden"});
}