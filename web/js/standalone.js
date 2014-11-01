modulejs.define('app/standalone',  ['app/mode', 'head', 'app/standalone/responder', 'app/standalone/filter', 'jquery', 'underscore'], function (mode, head, responder, filter, $, _) {
	return { 
		ready: function(onReady) {
			if (mode.isStandalone()) {
				$('#wdm-ehr').click(function(){
					$(this).attr('href', '../../ehr/web/app.html')
				})
				var server, recentPatients
				var appUrlRegex = /^app\/.*/
				var adminUrlRegex = /^admin\/.*/
				var templateUrlRegex = /^template\/.*/
			    	var htmlUrlRegex = /^html\/.*/ 
			    var originalParseJson = $.parseJSON
			    var headShotPlaceHolder = "images/headshot-placeholder.jpg"
			    var originalShowScreen = showScreen
			    showScreen = function(screen) {
					originalShowScreen(screen)
					if (screen != signin_cache) {
						$('#main-navigation').css({display:'block'})
					}
				}
			    $.parseJSON = function(data) {
					if ($.isPlainObject(data)) {
						return data
					} else {
						return originalParseJson(data)
					}
				} 
			    	$.ajaxSetup({
			    	 	beforeSend: function(jqXHR, settings) {
			    	 		settings.url = settings.url.replace(/\?.*/,'')
				        if (settings.url.match(templateUrlRegex)){
				        		settings.url = settings.url.replace(/^template/, 'html')
				        }else if (settings.url.match(htmlUrlRegex)) {
				        	settings.url = settings.url.replace(/^html/, 'template')
					   }
			    	 	}    
			    })
				fakeUrl = function(url) {
					return url.match(appUrlRegex) ||
						   url.match(adminUrlRegex) ||
						   url.match(htmlUrlRegex) 
				}
				
				callback = function() {
					server = sinon.fakeServer.create();
					server.xhr.useFilters = true;
					server.autoRespond = true;
					contentType = { "Content-Type": "application/json" }
				    statusCode = 200	
				    server.xhr.addFilter(function(method, url) {
					    //when true response not faked
						return !fakeUrl(url)
					});
					originalProcess = server.processRequest 
					server.processRequest = function(request)  {	
						var url = request.url
						var method = request.method
						var processData = undefined
						if (fakeUrl(url)) {
							if (url.match(htmlUrlRegex)) {
								contentType = { "Content-Type": "text/html" }
								processData = function(data) {
									return data
								}		
							}
							else {
								processData = function(data) {	
									//gets post request data param
									postData = decodeURIComponent(request.requestBody).split('=')[1]
									if (postData) {
										postData = JSON.parse(postData)
									}
									if (url == 'app/patientSearch') {
										return filter.patients().process(recentPatients.patients, postData)
									}
								   return filter.process(url, data, postData)										
								}
							}		
							success = function(data){
								var processedData = processData(data)
								if (url == 'app/getRecentPatients') {
									recentPatients = processedData
								}if (url == 'app/patientSearch') {
									var patients = processedData
									processedData=_.extend({}, recentPatients, {patients:patients})
								}
								if (url.match(htmlUrlRegex)) {
									string = processedData
								}else {
									string = JSON.stringify(processedData)	
								}	
								server.responses = [{
									method: method,
									url: url,	
									response: [statusCode, contentType, string]
								}]
								originalProcess.call(server, request)
	
							if (url.match('app/getPatientSummary')) {
									var headShot = "files/patients/" +processedData.id + '/' + processedData.profileImagePath 
									var tried = false;
									patientHeadshot = headShot
									$('.headshot').attr('src', patientHeadshot);
									$('.headshot').error(function(){
											if (!tried) {
												$('.headshot').attr('src', headShotPlaceHolder)
												$('.headshot').load()
												tried = true;
											}
			
									})
								}
							}
							responder.respond(url).then(success)
						}
					}
				   onReady()
				}
				head.load('js/lib/sinon-server.js', function() {	
					if (head.browser.ie) {
						head.load("js/lib/sinon-ie.js", callback)
					} else {
						callback()
					}
				})		
				
			} else {
				 onReady()
			}
		}
	}
});