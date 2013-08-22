 //Prueft ob Value ist in Array

Array.prototype.inArray = function (value) {
	var i;
	for (i=0; i < this.length; i++) {
		if (this[i] === value) {
			return true;
		}
	}
	return false;
};

// Sucht element mit entsprechendem id zwieschen parent-Node
// Bsp outerSerch(Evevent.element(e), /.+_\d+/, 5//
var outerSearch=function(element, matchString, maxIteration){
	if(!element || maxIteration<=0)
		return false;
	else{
		if(typeof element.id == "string" && element.id.match(matchString)){
			return element
		} else {
			return outerSearch(element.parentNode, matchString, maxIteration-1);
		}
	}
}

//Prueft ob String ist leer
var isEmpty = function(thisString){
	var response=false
	try {
		if(thisString!=null)
			response=(thisString.replace(/\s/g,'').length==0);
	}catch(Error){
	}
	return response;
}
// Genau wie trim() in Java
var trim = function(thisString){
	var response=thisString;
	try {
		if(thisString!=null){
			response=thisString.replace(/^\s+|\s+$/g,'');
		}
	}catch(Error){
	}
	return response;
}

// enspricht der Java-Funktion
var startsWith = function(mainString, muster){
    if(mainString != null && muster !=null
                  && typeof mainString == 'string'
                   && typeof muster == 'string'
                   && mainString.indexOf(muster)==0)
                   return true;
    return false;
}

// enspricht der Java-Funktion
var endsWith = function(mainString, muster){
    if(mainString != null && muster !=null
                  && typeof mainString == 'string'
                   && typeof muster == 'string'
                   && mainString.lastIndexOf(muster)==(mainString.length-muster.length))
                   return true;
    return false;
}

// Genau wie replaceAll() in Java
 var replaceAll = function(str, search_, replace_){
 	var returnValue = str;
	var startStr='';
	var endStr=str;
	try{
		if(str && search_ && replace_ != null 
			&& typeof str == 'string'
			&& typeof search_ == 'string'
			&& typeof replace_ == 'string'){
			var pos = endStr.search(search_);
			while(pos > -1 ){
				startStr=startStr+endStr.substring(0,pos)+replace_;
				endStr=endStr.substring(pos + search_.length);
				pos = endStr.search(search_);
			}
			returnValue=startStr + endStr;
		}
	}catch(Error){}
	return returnValue;
 }

var isValidEmail = function(thisString){
	var specialChars="\\(\\)><@,;:\\\\\\\"\\[\\]";
	var validChars="\[^\\s" + specialChars + "\]";
	var emailPath="^("+validChars+"+)@("+validChars+"+)\\.("+validChars+"+)$";
    return(thisString.match(emailPath));
}

// Function aus Calendar-bibliothek
var parseDate = function(str, fmt) {
	var Calendar=new Object();
	Calendar._MN = new Array
		("January",
		 "February",
		 "March",
		 "April",
		 "May",
		 "June",
		 "July",
		 "August",
		 "September",
		 "October",
		 "November",
		 "December");

	var today = new Date();
	var y = 0;
	var m = -1;
	var d = 0;
	var a = str.split(/\W+/);
	var b = fmt.match(/%./g);
	var i = 0, j = 0;
	var hr = 0;
	var min = 0;
	for (i = 0; i < a.length; ++i) {
		if (!a[i])
			continue;
		switch (b[i]) {
		    case "%d":
		    case "%e":
			d = parseInt(a[i], 10);
			break;

		    case "%m":
			m = parseInt(a[i], 10) - 1;
			break;

		    case "%Y":
		    case "%y":
			y = parseInt(a[i], 10);
			(y < 100) && (y += (y > 29) ? 1900 : 2000);
			break;

		    case "%b":
		    case "%B":
			for (j = 0; j < 12; ++j) {
				if (Calendar._MN[j].substr(0, a[i].length).toLowerCase() == a[i].toLowerCase()) { m = j; break; }
			}
			break;

		    case "%H":
		    case "%I":
		    case "%k":
		    case "%l":
			hr = parseInt(a[i], 10);
			break;

		    case "%P":
		    case "%p":
			if (/pm/i.test(a[i]) && hr < 12)
				hr += 12;
			else if (/am/i.test(a[i]) && hr >= 12)
				hr -= 12;
			break;

		    case "%M":
			min = parseInt(a[i], 10);
			break;
		}
	}
	if (isNaN(y)) y = today.getFullYear();
	if (isNaN(m)) m = today.getMonth();
	if (isNaN(d)) d = today.getDate();
	if (isNaN(hr)) hr = today.getHours();
	if (isNaN(min)) min = today.getMinutes();
	if (y != 0 && m != -1 && d != 0)
		return new Date(y, m, d, hr, min, 0);
	y = 0; m = -1; d = 0;
	for (i = 0; i < a.length; ++i) {
		if (a[i].search(/[a-zA-Z]+/) != -1) {
			var t = -1;
			for (j = 0; j < 12; ++j) {
				if (Calendar._MN[j].substr(0, a[i].length).toLowerCase() == a[i].toLowerCase()) { t = j; break; }
			}
			if (t != -1) {
				if (m != -1) {
					d = m+1;
				}
				m = t;
			}
		} else if (parseInt(a[i], 10) <= 12 && m == -1) {
			m = a[i]-1;
		} else if (parseInt(a[i], 10) > 31 && y == 0) {
			y = parseInt(a[i], 10);
			(y < 100) && (y += (y > 29) ? 1900 : 2000);
		} else if (d == 0) {
			d = a[i];
		}
	}
	if (y == 0)
		y = today.getFullYear();
	if (m != -1 && d != 0)
		return new Date(y, m, d, hr, min, 0);
	return today;
};




var myEscape = function(inString){
	var outString=inString;
	try{
	  outString=escape(inString);
	  outString=outString.replace("+", encodeURIComponent("+"));
	  outString=outString.replace("/", encodeURIComponent("/")); 
//		outString=outString.replace(/?/g,'%E4').replace(/?/g,'%C4').replace(/?/g,'%F6').replace(/?/g,'%D6').replace(/?/g,'%FC').replace(/?/g,'%DC').replace(/?/g,'%DF');
	}catch(Error){}
	return outString;
}

var myEscape1 = function(inString){
	var outString=inString;
	try{
	  outString=outString.replace(/%/g, escape('%')); 
	  outString=outString.replace(/\?/g, escape('?'));
	  outString=outString.replace(/=/g, escape('='));
	  outString=outString.replace(/&/g, escape('&')); 
	  outString=outString.replace(/#/g, escape('#')); 
	}catch(Error){}
	return outString;
}

  // Hilfsfunktion fuer Bearbeitung Request-String
  // wenn newValue!=null attribut wird addiert, sonst entfernt

  var addRemoveRequestParameter =function(request, attribut, newValue){
  	try {
  		request = request || '';
		var regExp1='^('+attribut+'=[^&]+&*)';
		var regExp2='&('+attribut+'=[^&]+&*)';
		
 		var	Ergebnis = request.match(regExp1+'|'+regExp2);
		while(Ergebnis){
			Ergebnis=RegExp.$1 || RegExp.$2;
			request=request.replace(Ergebnis,'');
			Ergebnis = request.match(regExp1+'|'+regExp2);
		}
		if(newValue!=null){
			if(request.length>0)
				request=request+"&";
			request=request+attribut+'='+newValue;	
		}
	}catch(Error){
		//alert(Error);
	}
	return request;
  }
  
  function getStyleClass (className) {
	  if (document.all) {
	    for (var s = 0; s < document.styleSheets.length; s++)
	      for (var r = 0; r < document.styleSheets[s].rules.length; r++) {
	        if (document.styleSheets[s].rules[r].selectorText == className)
	          return document.styleSheets[s].rules[r];
	        if (document.styleSheets[s].rules[r].selectorText == '#' + className)
	          return document.styleSheets[s].rules[r];
			}
	  }
	  else if (document.getElementById) {
	    for (var s = 0; s < document.styleSheets.length; s++)
	      for (var r = 0; r < document.styleSheets[s].cssRules.length; r++) {
	        if (document.styleSheets[s].cssRules[r].selectorText == className)
	          return document.styleSheets[s].cssRules[r];
	        if (document.styleSheets[s].cssRules[r].selectorText == '#' + className)
	          return document.styleSheets[s].cssRules[r];
	      }
	  }
	  return null;
 }
 
  // Hilfsfunction f?r Anzeige von aller Eventlisteners
var showAllObservers=function(){
	var msg= 'Observers:';
 	var observers= Event.observers;
 	if(observers && observers.length>0){
 		msg=msg+'(total: '+observers.length+')\n';
	  	for(var i=0;i<observers.length;i++){
	  		try {
		  		msg=msg+'elementId: '+observers[i][0].id+' eventType: '+observers[i][1]+' callBack: '+observers[i][2]+'\n';
		  	}catch(Error){}
	  	}
 	}
 	alert(msg);
}


var centerDomElement = function(domElement){
	if(domElement && domElement.style){
		if(domElement.style.width){
			domElement.style.left = ((window.innerWidth || document.body.offsetWidth) - parseInt(domElement.style.width)) / 2;
		}
		if(domElement.style.height){
			domElement.style.top = ((window.innerHeight || document.body.offsetHeight) - parseInt(domElement.style.height)) / 2;
		}
	}
}

var setDimensionsAndCenterDomElement = function(domElement, width, height){
	if(domElement && domElement.style){
		if(width && !isNaN(width) && +width>0){
			domElement.style.width=width;
		}
		if(height && !isNaN(height) && +height>0){
			domElement.style.height=height;
		}
		centerDomElement(domElement);
	}
}


var getRegExpFromString = function(inputString, reg_exp, defaultValue){
	var outputString = defaultValue || "";
	if(inputString && typeof inputString == "string" && inputString.match(reg_exp))
			outputString = RegExp.$1;
	return outputString;		
}


var  _getWindowScroll = function() {
    var T, L, W, H;
    with (window.document) {
      if (window.document.documentElement && documentElement.scrollTop) {
        T = documentElement.scrollTop;
        L = documentElement.scrollLeft;
      } else if (window.document.body) {
        T = body.scrollTop;
        L = body.scrollLeft;
      }
      if (window.innerWidth) {
        W = window.innerWidth;
        H = window.innerHeight;
      } else if (window.document.documentElement && documentElement.clientWidth) {
        W = documentElement.clientWidth;
        H = documentElement.clientHeight;
      } else {
        W = body.offsetWidth;
        H = body.offsetHeight
      }
    }
    return { top: T, left: L, width: W, height: H };
  }
  
var scrollbarWidth = function() {
	var w, d = document.createElement("div"), d2 = document.createElement("div");
	d.style.height = "200px";
	d.style.width = "200px";
	d.style.overflow = "hidden";
	
	d.appendChild(d2);
	document.body.appendChild(d);
	w = d2.offsetWidth;
	d.removeChild(d2); // Safari special
	d.style.overflow = "scroll";
	d.appendChild(d2); // Safari special
	w -= d2.offsetWidth;
	document.body.removeChild(d);
	return w;
};  
  