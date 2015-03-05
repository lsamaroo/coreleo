$(function() {

	var MAX_TABS = 12;
	var tabCount = 1;
	var maxTabs = MAX_TABS;
	

	var isMaxNumTabsOpen = function(){
		return tabCount >= maxTabs;
	};
	
	var incrementTabCount  = function() {
		tabCount++;
	};
	
	var decrementTabCount  = function() {
		tabCount--;
	};
	
	
    var addTab = function(tabContainerId, tabId, tabTitle, tabContent, showCloseIcon) {
	    var tabTemplate = "<li><a id='tab-anchor-{id}' href='#{href}'>{tabTitle}</a>{closeIcon}</li>";
	    var closeIconTemplate = "<span tabIndex='0' class='ui-icon ui-icon-close'>{closeText}</span>";
	    
    	if( showCloseIcon ){
    		closeIconTemplate = closeIconTemplate.replace( "{closeText}", myapi.strings.CLOSE_TAB + " " + tabTitle);
    		tabTemplate = tabTemplate.replace( "{closeIcon}", closeIconTemplate );
    	}
    	else {
    		tabTemplate = tabTemplate.replace( "{closeIcon}", "" );
    	}
    	
    	var tabs = $( myapi.util.formatId( tabContainerId ) );
        var li = $( tabTemplate.replace( "{id}", tabId ).replace( "{href}", tabId ).replace( "{tabTitle}", tabTitle  ) );
        tabs.find( ".ui-tabs-nav" ).append( li );
        tabs.append( "<div id='" + tabId + "'><p>" + tabContent + "</p></div>" );
        tabs.tabs( "refresh" );
    };
    
    var addAjaxTab = function(tabContainerId, tabId, tabTitle, href, showCloseIcon) {
	    var tabTemplate = "<li><a id='tab-anchor-{id}' href='{href}'>{tabTitle}</a>{closeIcon}</li>";
	    var closeIconTemplate = "<span tabIndex='0' class='ui-icon ui-icon-close'>{closeText}</span>";
	    
    	if( showCloseIcon ){
    		closeIconTemplate = closeIconTemplate.replace( "{closeText}", myapi.strings.CLOSE_TAB + " " + tabTitle);
    		tabTemplate = tabTemplate.replace( "{closeIcon}", closeIconTemplate );
    	}
    	else {
    		tabTemplate = tabTemplate.replace( "{closeIcon}", "" );
    	}
    	
    	var tabs = $( myapi.util.formatId(tabContainerId) );
        var li = $( tabTemplate.replace( "{id}", tabId ).replace( "{href}", href ).replace( "{tabTitle}", tabTitle  ) );
        tabs.find( ".ui-tabs-nav" ).append( li );
        tabs.tabs( "refresh" );
    };
	
	var renameTab = function(tabContainerId, tabId, title){
		tabContainerId = myapi.util.formatId( tabContainerId );
		var tabAnchor = $( tabContainerId + ' a[id="tab-anchor-' + tabId + '"]');
		tabAnchor.html( title );
	};
	
	var refresh = function(tabContainerId){
		tabContainerId = myapi.util.formatId( tabContainerId );
		var tabs = $( tabContainerId ).tabs();
		tabs.tabs( "refresh" );
	};
	
	var getTabIndexById = function(tabContainerId, tabId){ 
		tabContainerId = myapi.util.formatId( tabContainerId );
		var tabAnchor = $( tabContainerId + ' a[id="tab-anchor-' + tabId + '"]');
		if( tabAnchor.length == 0 ){
			return -1;
		}
		
		return tabAnchor.parent().index();
	};
	
	var focusTab = function(tabContainerId, tabId){
		tabContainerId = myapi.util.formatId( tabContainerId );
		var tabAnchor = $( tabContainerId + ' a[id="tab-anchor-' + tabId + '"]');
		tabAnchor.focus();
	};
	
	var addCloseFunctionToCloseIcon = function ( tabContainerId ){
		tabContainerId = myapi.util.formatId( tabContainerId );
		var tabs = $( tabContainerId ).tabs();
		
	    // close icon: removing the tab on click
	    tabs.delegate( "span.ui-icon-close", "click", function() {
	      var panelId = $( this ).closest( "li" ).remove().attr( "aria-controls" );
	      $( "#" + panelId ).remove();
	      myapi.ui.tabs.refresh( tabContainerId );
	      myapi.ui.tabs.decrementTabCount();
	    });			
	};
	
	
	var closeTab = function ( tabContainerId, tabId ){
		var panelId = $( tabContainerId + ' a[id="tab-anchor-' + tabId + '"]').closest( "li" ).remove().attr( "aria-controls" );
	      $( "#" + panelId ).remove();
	      myapi.ui.tabs.refresh( tabContainerId );
	      myapi.ui.tabs.decrementTabCount();
	};
	
	
	var getSelectedTabIndex = function( tabContainerId ){
		tabContainerId = myapi.util.formatId( tabContainerId );
		return $(tabContainerId).tabs('option', 'active');
	};
	
	var getSelectedTabId = function( tabContainerId ){
		tabContainerId = myapi.util.formatId( tabContainerId );
		var index = getSelectedTabIndex( tabContainerId );
		var id = ($( tabContainerId + " ul>li a").eq(index).attr('href'));
		return myapi.util.startsWith( id, "#") ? id.substring( 1, id.lenght ) : id;
	}
	
	
	
	// Public API
    myapi.ui.tabs.focusTab = focusTab;
    myapi.ui.tabs.addTab = addTab;
    myapi.ui.tabs.addAjaxTab = addAjaxTab;	
	myapi.ui.tabs.isMaxNumTabsOpen = isMaxNumTabsOpen;
	myapi.ui.tabs.incrementTabCount = incrementTabCount;
	myapi.ui.tabs.decrementTabCount = decrementTabCount;
    myapi.ui.tabs.renameTab = renameTab;
    myapi.ui.tabs.getTabIndexById = getTabIndexById;
    myapi.ui.tabs.refresh = refresh;
    myapi.ui.tabs.addCloseFunctionToCloseIcon = addCloseFunctionToCloseIcon;
    myapi.ui.tabs.closeTab = closeTab;
    myapi.ui.tabs.getSelectedTabIndex = getSelectedTabIndex;
    myapi.ui.tabs.getSelectedTabId = getSelectedTabId;
});