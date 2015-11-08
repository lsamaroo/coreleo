$(function() {
	var confirmDialogTmpl = "<div data-role='popup' data-shadow='false' data-dismissible='false'";
	confirmDialogTmpl = confirmDialogTmpl + "id='my-confirm-dialog' ";
	confirmDialogTmpl = confirmDialogTmpl + "class='ui-dialog dialog confirm-dialog ui-corner-all' ";
	confirmDialogTmpl = confirmDialogTmpl + "title='{title}'>";
	confirmDialogTmpl = confirmDialogTmpl + "{header}<div class='icon-content {iconClass}'/><div class='ui-dialog-content text-content'>{text}</div>";
	confirmDialogTmpl = confirmDialogTmpl + "<div class='dialog-footer'>";
	confirmDialogTmpl = confirmDialogTmpl + "<button class='save' type='button'>Ok</button>";
	confirmDialogTmpl = confirmDialogTmpl + "<button class='cancel' type='button'>Cancel</button>";
	confirmDialogTmpl = confirmDialogTmpl + "</div>";
	confirmDialogTmpl = confirmDialogTmpl + "</div>";

	var alertDialogTmpl = "<div data-role='popup' data-shadow='false' data-dismissible='false' title='{title}'";
	alertDialogTmpl = alertDialogTmpl + "class='ui-dialog dialog alert-dialog ui-corner-all'>{header}";
	alertDialogTmpl = alertDialogTmpl + "<div class='icon-content {iconClass}'/><div class='text-content'>{text}</div></div>";

	
	var closeButtonTmpl = "<button class='ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close' role='button' aria-disabled='false' title='close'><span class='ui-button-icon-primary ui-icon ui-icon-closethick'></span><span class='ui-button-text'>close</span></button>"; 
		
	var open = function( id ){
		var itemId = coreleo.util.formatId( id );
		
		if( $(itemId).panel ){
			$(itemId).panel("open");
		}
		else { 
			$( itemId ).dialog("open");
		}	
	};
	
	var close = function( id ){
		var itemId = coreleo.util.formatId( id );
		
		if( $(itemId).panel ){
			$(itemId).panel("close");
		}
		else { 
			$( itemId ).dialog("close");
		}
	};
	
	var init = function( id, width, height ){
		if( !coreleo.util.isMobileClient() ){
			$( coreleo.util.formatId( id ) ).dialog({
				autoOpen : false,
				height : height,
				width : width,
				modal : true
			});
		}
	};
	
	var confirm = function ( title, text, successFunction, iconClass ){	
		if( !iconClass ){
			iconClass = "";
		}
		
		if( coreleo.util.isMobileClient() ){
			showConfirmPopup( title, text, successFunction, iconClass );			
		}
		else{
			showConfirmDialog( title, text, successFunction, iconClass );
		}
	};
	
	var showConfirmPopup = function ( title, text, successFunction, iconClass ){
		var template = confirmDialogTmpl;
		template = template.replace( "{title}", title );
		template = template.replace( "{text}", text );
		var header = "<div class='header ui-dialog-titlebar ui-widget-header ui-corner-all' data-role='header'>{title}</div>".replace( "{title}", title );
		template = template.replace( "{header}", header );
		template = template.replace( "{iconClass}", iconClass );

		var item = $( template );
		$( '.save', item ).click(function( eventObject ) {
			successFunction(item);
			closeDialogOrPopup( "#my-confirm-dialog" );
			destroyDialogOrPopup( "#my-confirm-dialog" );
		});
		
		$( '.cancel', item ).click(function( eventObject ) {
			closeDialogOrPopup( "#my-confirm-dialog" );
			destroyDialogOrPopup( "#my-confirm-dialog" );
		});
		
		item.popup();	
		item.popup("open");
	};
	
	
	var showConfirmDialog = function ( title, text, successFunction, iconClass ){
		var template = confirmDialogTmpl;
		template = template.replace( "{title}", title );
		template = template.replace( "{text}", text );
		template = template.replace( "{header}", "" );
		template = template.replace( "{iconClass}", iconClass );
		
		var item = $( template );
		$( '.save', item ).click(function( eventObject ) {
			successFunction(item);
			closeDialogOrPopup( "#my-confirm-dialog" );
			destroyDialogOrPopup( "#my-confirm-dialog" );
		});
		
		$( '.cancel', item ).click(function( eventObject ) {
			closeDialogOrPopup( "#my-confirm-dialog" );
			destroyDialogOrPopup( "#my-confirm-dialog" );
		});
		
		item.dialog({
			 autoOpen : false,
			 resizable: true,
			 modal: true
		  });
		
		item.dialog("open");			
	};
	

	var destroyDialogOrPopup = function ( id ){
		var item = null;
		if( typeof id == 'string' ){
			var itemId = coreleo.util.formatId( id );
			item = $(itemId);
		}
		else {
			item = id;
		}
		
		if( item.popup ){
			item.popup("destroy").remove();
		}
		else { 
			item.dialog('destroy').remove();
		}		
	};
	
	var closeDialogOrPopup = function( id ){
		var item = null;
		if( typeof id == 'string' ){
			var itemId = coreleo.util.formatId( id );
			item = $(itemId);
		}
		else {
			item = id;
		}
					
		if( item.popup ){
			item.popup("close");
		}
		else { 
			item.dialog("close");
		}
	};
	
	var showLoadingDialog = function( title, text, loadingImageClass ){			
		if( !text ){
			text = "Loading, please wait...";
		}
		
		if( !loadingImageClass ){
			loadingImageClass = "loading-image";
		}
		
		var template = "<div data-role='popup' data-shadow='false' data-dismissible='false' ";
		template = template + " class='ui-dialog dialog loading-dialog ui-corner-all' ";
		template = template + "id='my-loading-dialog' title='{title}'>";
		template = template + "<div class='" + loadingImageClass + "'></div>{text}</div>";
		
		template = template.replace( "{title}", title );
		template = template.replace( "{text}", text );
		
		var item = $( template );
		if( coreleo.util.isMobileClient() ){
			item.popup();	
			item.popup("open");
		}
		else {
			item.dialog({
				autoOpen : false,
				closeOnEscape: false,
				modal: true,
				dialogClass: "loading-dialog-contentpane",
				height: 70,
				open: function(event, ui) { 
						$('.loading-dialog-contentpane .ui-dialog-titlebar-close').hide();
						if( !title ){
							$('.loading-dialog-contentpane .ui-dialog-titlebar').hide();
						}
					  }
				}
			);	
			item.dialog("open");
		}
	};
	
	
	var hideLoadingDialog = function(){
		closeDialogOrPopup( "#my-loading-dialog" );
		destroyDialogOrPopup( "#my-loading-dialog" );
	};
	
	
	var alert = function( title, text, iconClass ){
		if( !iconClass ){
			iconClass = "";
		}
		
		var template = alertDialogTmpl;
		template = template.replace( "{title}", title );
		template = template.replace( "{text}", text );
		template = template.replace( "{iconClass}", iconClass );
		
		if( coreleo.util.isMobileClient() ){
			var header = "<div class='header ui-dialog-titlebar ui-widget-header ui-corner-all' data-role='header'><span class='ui-dialog-title'>{title}</span>{close}</div>".replace( "{title}", title );
			header = header.replace( "{close}", closeButtonTmpl );
			template = template.replace( "{header}", header );

			var item = $( template );
			
			$( '.header', item ).click(function( eventObject ) {
				closeDialogOrPopup( item );
				destroyDialogOrPopup( item );
			});
			
			item.popup();	
			item.popup("open");
		}
		else {
			template = template.replace( "{header}", "" );
			template = template.replace( "{close}", "" );
			$( template ).dialog();
		}
	};


	// public API
	coreleo.ui.dialog.open = open;
	coreleo.ui.dialog.close = close;
	coreleo.ui.dialog.init = init;
	coreleo.ui.dialog.confirm = confirm;
	coreleo.ui.dialog.showLoadingDialog = showLoadingDialog;
	coreleo.ui.dialog.hideLoadingDialog = hideLoadingDialog;
	coreleo.ui.dialog.alert = alert;

});
