$(function() {
	var idleTime = 0;
	var started = false;
    var idleInterval = null;
    
	
	var startIdleTimer = function( doSomethingOnIdle ){
		if( started ){
			console.log( "Idle timer already started.");
			return false;
		}
		
		started = true;
		
	    //Increment the idle time counter every minute.
	    idleInterval = setInterval( function(){
	    	idleTime++;
	    	doSomethingOnIdle( idleTime * myapi.util.ONE_MINUTE );
	    }, myapi.util.ONE_MINUTE );

	    //Zero the idle timer on mouse movement.
	    $(document).mousemove(function (e) {
	        idleTime = 0;
	    });
	    $(document).keypress(function (e) {
	        idleTime = 0;
	    });		
	    
	    return true;
	};
	
	
	var stopIdleTimer = function(){
		if( idleInterval != null ){
			clearInterval(idleInterval);
			started = false;
			return true;
		}
		
		return false;
	};
	
	
	// public API
	myapi.event.startIdleTimer = startIdleTimer;
	myapi.event.stopIdleTimer = stopIdleTimer;	
});