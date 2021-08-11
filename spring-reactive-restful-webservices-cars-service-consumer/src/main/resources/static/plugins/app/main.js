var myInterval;

function checkForCarResult() {
	// Simulate delay to see the preloaders:
	setTimeout("continuousCheck()", 2000);
}

function continuousCheck() {
	myInterval = setInterval("doAjax()", 500);
}

function doAjax() {
	$.ajax({
		url : "/getcache/car",
		method : "GET",
		asynch : true,
		cache : false,
		headers : {
			'Accept' : 'application/json',
		}
	})
	.done(function(response) {
		//console.debug(response);
		
		// Result still not ready:
		if ( (typeof response["car"] == 'undefined') 
		  || (typeof response["callDuration"] == 'undefined') 
		) return;
		
		clearInterval(myInterval);
		//console.debug("Result retrieved successfully.");
		
		if (response["callDuration"]>-1) {
			$("#cached_car").html(response["car"]);
			$("#cached_duration").html("Call duration " + response["callDuration"] + " ms");
			$("#preloader_description").html("");
		}
		else {
			$("#cached_car").html("ERROR!");
			$("#cached_duration").html("Call duration " + response["callDuration"] + " ms");
			$("#preloader_description").html("");
		}
		
	})
	.fail(function() {
		$("#cachedCar").html("ERROR");
		$("#cachedDuration").html("ERROR");
	})
}
