$(function() {

	var refreshSelect = function( id ){
		var item = $( myapi.util.formatId( id ) );
		if( $.mobile && item.selectmenu ){
			item.selectmenu('refresh');
		}
	};
	
	var initTable = function( id ){
		var item = $( myapi.util.formatId( id ) );
		if( $.mobile && item.table ){
			item.table();
		}
	};
	
	var refreshTable = function( id ){
		var item = $( myapi.util.formatId( id ) );
		if( $.mobile && item.table ){
			item.table( "rebuild" );
		}
	};

	var enableTextField = function( id ){
		var item = $( myapi.util.formatId( id ) );
		if( $.mobile && item.textinput ){
			item.textinput( "enable" );
		}		
	};
	
	var disableTextField = function( id ){
		var item = $( myapi.util.formatId( id ) );
		if( $.mobile && item.textinput ){
			item.textinput( "disable" );
		}		
	};
	
	// public API
	myapi.mobile.util.selectmenu.refresh = refreshSelect;
	myapi.mobile.util.table.init = initTable;
	myapi.mobile.util.table.refreshTable = refreshTable;
	myapi.mobile.util.input.enableTextField = enableTextField;
	myapi.mobile.util.input.disableTextField = disableTextField;

});
