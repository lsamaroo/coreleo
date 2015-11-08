$(function() {

	var refreshSelect = function( id ){
		var item = $( coreleo.util.formatId( id ) );
		if( $.mobile && item.selectmenu ){
			item.selectmenu('refresh');
		}
	};
	
	var initTable = function( id ){
		var item = $( coreleo.util.formatId( id ) );
		if( $.mobile && item.table ){
			item.table();
		}
	};
	
	var refreshTable = function( id ){
		var item = $( coreleo.util.formatId( id ) );
		if( $.mobile && item.table ){
			item.table( "rebuild" );
		}
	};

	var enableTextField = function( id ){
		var item = $( coreleo.util.formatId( id ) );
		if( $.mobile && item.textinput ){
			item.textinput( "enable" );
		}		
	};
	
	var disableTextField = function( id ){
		var item = $( coreleo.util.formatId( id ) );
		if( $.mobile && item.textinput ){
			item.textinput( "disable" );
		}		
	};
	
	// public API
	coreleo.mobile.util.selectmenu.refresh = refreshSelect;
	coreleo.mobile.util.table.init = initTable;
	coreleo.mobile.util.table.refreshTable = refreshTable;
	coreleo.mobile.util.input.enableTextField = enableTextField;
	coreleo.mobile.util.input.disableTextField = disableTextField;

});
