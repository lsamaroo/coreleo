$(function() {
	
	var pollFunction = function( theFunction, interval ){
       	if( typeof theFunction !== "function"){
       		return;
    	}
       	
		(function loopsiloop(){
			setTimeout(function(){
				
				theFunction();
				
		     	// recurse
		       	loopsiloop();
				
			}, interval);
			})();		
	};
	

	/**
	 * Similar to using setInterval, however this function will only poll again once the original call is complete.
	 */
	var pollUlr = function(  
			/* String */ url,
			/* int */ interval,
			/* function */ successFunction, 
			/* function */ errorFunction, 
			/* object or function */ postData,
			/* string */ dataType
	)
	{		
		if( !postData){
			postData = {};
		}
		
		if( !dataType){
			dataType = "json";
		}
		
		(function loopsiloop(){
		setTimeout(function(){
			var data = {};
			if (typeof(postData) == "function"){
				data = postData();
			}
			else {
				data = postData;
			}
			
			$.ajax({
			   type: "POST",
			   url: url,
			   dataType: dataType,
			   data: data,
			    success: function( response ) {  
			       	if( typeof successFunction === "function"){
			       		successFunction( response );
			    	}
			       	// recurse
			       	loopsiloop();
			    },
			    error: function (jqXHR, textStatus, errorThrown){
			    	if( typeof errorFunction === "function" ){
			    		errorFunction(jqXHR, textStatus, errorThrown);
			    	}
			    	// recurse
			    	loopsiloop();
			    	console.error( "poll: errorThrown=" + errorThrown + "textStatus=" + textStatus );
			    }
			});
		}, interval);
		})();
	};
	
	
	// public API
	coreleo.event.poller.pollUrl = pollUlr;
	coreleo.event.poller.pollFunction = pollFunction;	
	

});
