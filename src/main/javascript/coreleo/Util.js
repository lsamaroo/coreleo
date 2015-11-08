$(function() {
	
	var TemplateCache = {
	  get: function(selector){
		  if (!this.templates){ this.templates = {}; }
	 
		  var template = this.templates[selector];
		  if (!template){
			  template = $(selector).html();	 
			  // precompile the template
			  template = Handlebars.compile(template);	 
			  this.templates[selector] = template;
		  }	 
		  return template;
	  }
	};	

	var renderTemplateWithCaching = function(selector, data){
		var render = TemplateCache.get( selector );
		return render( data );
	};
	
	var renderTemplate = function(template, data){
		var render = Handlebars.compile(template);
		return render( data );
	};
	
	var isMobileClient = function(){
		if( window.isMobileClient ){
			return window.isMobileClient();
		}
		else {
			return $.mobile ? true : false;
		}			
	};
	
	
	var isSmallScreen = function(){
		if( window.isSmallScreen ){
			return window.isSmallScreen();
		}	
		return false;
	};
	
	var timeoutButton = function( id, time ){
		if( !time ){	
			time = coreleo.util.ONE_SECOND * 2;
		}
		var itemId = coreleo.util.formatId( id );
		$( itemId ).prop( "disabled", true );
		
		setTimeout(function(){
			$( itemId ).prop( "disabled", false );
		}, time );
	};
	
	
	var startsWith = function( str, ch){
		return str.lastIndexOf(ch, 0) === 0;
	};
	
	
	var formatPhone = function( phone ){
		if( isEmpty( phone ) ){
			return "";
		}
	    return '(' + phone.substr(0, 3) + ') ' + phone.substr(3, 3) + '-' + phone.substr(6,4);
	};
	

	var replaceCharAt = function(str,index,chr) {
	    if(index > str.length-1) return str;
	    return str.substr(0,index) + chr + str.substr(index+1);
	};
	
	var currencyFormatted = function(amount)
	{
	    var i = parseFloat(amount);
	    if(isNaN(i)) { i = 0.00; }
	    var minus = '';
	    if(i < 0) { minus = '-'; }
	    i = Math.abs(i);
	    i = parseInt((i + 0.005) * 100);
	    i = i / 100;
	    s = i.toString();
	    if(s.indexOf('.') < 0) { s += '.00'; }
	    if(s.indexOf('.') == (s.length - 2)) { s += '0'; }
	    s = minus + s;
	    return s;
	};

		
	var blankNull = function(text){
		if( isEmpty(text) ){
			return "";
		}			
		else {
			return text;
		}
	};
	
	/*jshint eqnull:true */
	var isEmpty = function(text){
		return ( 
				 text == null || text === null || 
				 text === undefined || 
				 typeof text === 'undefined' ||
				 $.trim(text) == 'null' || 
				 $.trim(text) === '' ||
				 ( $.isArray( text ) && text.length === 0 )
			   );
	};
	
	var isNotEmpty = function(text){
		return !isEmpty(text);
	};

    
    var zeroFill = function (number, size) {
    	if( isEmpty(number) ){
    		return '';
    	}
    	number = number.toString();
    	while (number.length < size) number = "0" + number;
    	return number;
    };
    
    
    var addParamToUrl = function(url, param, value)
    {
       if( typeof param === 'undefined' ){
    	   return url;
       }
	       
       if( typeof value === 'undefined' ){
    	   return url;
       }
	       
       var sep = url.indexOf('?') == -1 ? '?' : '&';
       return url + sep + encodeURIComponent(param) + '=' + encodeURIComponent(value);
    };
    
    var formatId = function(id)
    {
    	id = id.trim();
    	
       if( startsWith( id, ".") ){
    	   return id;
       }
    	
       return startsWith( id, "#") ? id : "#" + id ;
    };
    
	var enableTextField = function( id ){
		var item = $( coreleo.util.formatId( id ) );
		item.attr('disabled', false );
		coreleo.mobile.util.input.enableTextField( id );
	};
	
	var disableTextField = function( id ){
		var item = $( coreleo.util.formatId( id ) );
		item.attr('disabled', true );	
		coreleo.mobile.util.input.disableTextField( id );
	};
	
	var contains = function ( string, subString ){
		if( !string ){
			return false;
		}
		
		if( !subString ){
			return false;
		}
		
		return string.indexOf( subString ) !== -1;
	};
	
	var containsIgnoreCase = function ( string, subString ){
		if( !string ){
			return false;
		}
		
		if( !subString ){
			return false;
		}
		
		return contains( string.toLowerCase(), subString.toLowerCase() );
	};
	
	
	
	var initTableSorter = function( id, options, widgets, widgetOptions, groupFormatter, groupCallback ){
		// don't bother with sorting on mobile client
		if( isMobileClient( ) ){
			return;
		}
		
		var groupFormatterWrapper = function(txt, col, table, c, wo) {
	        txt === "" ? "Empty" : txt;
	        if( !groupFormatter ){
	        	return txt;
	        }	        
	        return groupFormatter( txt, col, table, c, wo);
	    };
		
		var groupCallbackWrapper = function($cell, $rows, column, table){
			if( groupCallback ){
				groupCallback( $cell, $rows, column, table );
			}
		};
		
		var widgetOptionsObject = {
		   	  group_collapsible : true,  // make the group header clickable and collapse the rows below it.
		      group_collapsed   : false, // start with all groups collapsed (if true)
		      group_saveGroups  : false,  // remember collapsed groups
		      group_saveReset   : '.group_reset', // element to clear saved collapsed groups
		      group_count       : " ({num} items)", // if not false, the "{num}" string is replaced with the number of rows in the group
		      group_formatter   : groupFormatterWrapper,
		      group_callback    : groupCallbackWrapper,
		      // event triggered on the table when the grouping widget has finished work
		      group_complete    : "groupingComplete"
		};
		
		if( widgetOptions && $.isPlainObject(widgetOptions) ){
			$.extend( widgetOptionsObject, widgetOptions);
		}
		
		var widgetsArray = [ "group" ];
		if( widgets && $.isArray(widgets) ){
			$.merge( widgetsArray, widgets);
		}
		
		var optionsObject = {
				theme : 'blue',
				widgets: widgetsArray,
				widgetOptions: widgetOptionsObject
		};
		
		if( options && $.isPlainObject(options) ){
			$.extend( optionsObject, options);
		}

		$( coreleo.util.formatId( id ) ).tablesorter( optionsObject );
	};
	
	
	var trimNewLineChar = function ( string ){
		if( !string ){
			return false;
		}
		
		return string.replace(/(\r\n|\n|\r)/gm,"");
	};
	
	
	var trimWhiteSpaceChar = function ( string ){
		if( !string ){
			return false;
		}
		
		return string.replace(/(\s)/gm,"");
	};
	
	
	var redirectPost = function(location, args, target )
    {
		if( !target ){
			target = '_self';
		}
		
        var form = '';
        if( $.isArray( args ) ){
	        $.each( args, function( index, obj ) {
	            form += '<input type="hidden" name="'+ obj.name +'" value="'+obj.value+'">';
	        });       
        }
        else {
	        $.each( args, function( key, value ) {
	            form += '<input type="hidden" name="'+key+'" value="'+value+'">';
	        });
        }
        
        var dynamicForm = '<form data-ajax="false" target="' + target + '" action="' + location + '" method="POST">' + form + '</form>';	
        $( dynamicForm ).appendTo($(document.body)).submit();
    };
    
    
    var formatClass = function(cssClass)
    {
       return startsWith( cssClass, ".") ? cssClass : "." + cssClass ;
    };
    
    
    var hasWhiteSpace = function(s) {
    	  return /\s/g.test(s);
    };
    
    
    var properCase = function( s ) {
       if( !s ){
    	   return s;
       }
	   s = s.toLowerCase();
	   return s.replace(/\b[a-z]/g, function(f){return f.toUpperCase(); });
    };    
    
    
    var getParameterFromUrl = function( urlString, paramName ) {
        return (urlString.split('' + paramName + '=')[1]||'').split('&')[0]; 
    };    
	

	var loading = function( id ){
		var div = $( coreleo.util.formatId( id ) );
		div.empty();
		div.html("<image src='images/ajax-loader2.gif'></image>");
	};
	
	
	var endLoading = function( id ){
		var div = $( coreleo.util.formatId( id ) );
		div.empty();
	};
	
	var toKeyValueHash = function(key, value){
		return {'key': key, 'value': value};
	};

    
	
	// public API
    coreleo.util.ONE_SECOND = 1000;
    coreleo.util.ONE_MINUTE = 60000;
    coreleo.util.redirectPost = redirectPost;
	coreleo.util.formatPhone = formatPhone;
	coreleo.util.blankNull = blankNull;
	coreleo.util.zeroFill = zeroFill;
	coreleo.util.currencyFormatted = currencyFormatted;
	coreleo.util.isEmpty = isEmpty;
	coreleo.util.replaceCharAt = replaceCharAt;
	coreleo.util.formatPhone = formatPhone;
	coreleo.util.isNotEmpty = isNotEmpty;
	coreleo.util.addParamToUrl = addParamToUrl;
	coreleo.util.formatId = formatId;
	coreleo.util.timeoutButton = timeoutButton;
	coreleo.util.enableTextField = enableTextField;
	coreleo.util.disableTextField = disableTextField;
	coreleo.util.initTableSorter = initTableSorter;
	coreleo.util.isMobileClient = isMobileClient;
	coreleo.util.isSmallScreen = isSmallScreen;
	coreleo.util.contains = contains;
	coreleo.util.containsIgnoreCase = containsIgnoreCase;
	coreleo.util.trimNewLineChar = trimNewLineChar;
	coreleo.util.trimWhiteSpaceChar = trimWhiteSpaceChar;
	coreleo.util.formatClass = formatClass;
	coreleo.util.startsWith = startsWith;
	coreleo.util.renderTemplate = renderTemplate;	
	coreleo.util.renderTemplateWithCaching = renderTemplateWithCaching;	
	coreleo.util.hasWhiteSpace = hasWhiteSpace;
	coreleo.util.properCase = properCase;	
	coreleo.util.getParameterFromUrl = getParameterFromUrl;	
	coreleo.util.loading = loading;
	coreleo.util.endLoading = endLoading;
	coreleo.util.toKeyValueHash = toKeyValueHash;

	
});
