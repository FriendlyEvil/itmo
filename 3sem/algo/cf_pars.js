function httpGet(theUrl, fun)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
    xmlHttp.send( null );
    return fun(xmlHttp.responseText);
}

var f = function(s) {
	return httpGet(s, function(resp) {
		var node = $(resp)
		var problem = node.find(".problem-statement");
		var header = problem.find(".header");
		var hc = header.children();

		var v1 = '<h1 align=\"center\">' + hc[0].innerText + '</h1>\n' +
		'<p align=\"center\"><i>' + hc[1].innerText + '<br>\n' + hc[2].innerText + '<br>\n' 
		+ hc[3].innerText + '<br>\n' + hc[4].innerText + '</i></p>\n\n';

		var v2 = '__Условие:__  \n' + problem.children()[1].innerText + '\n\n';
		var v3 = '__Входные данные:__  \n' + problem.children()[2].children[1].innerText + '\n\n';
		var v4 = '__Выходные данные:__  \n' + problem.children()[3].children[1].innerText + '\n\n';

		var test = problem.children()[4].children[1];
		var v5 = '__Пример__  \n';
		$(test.children).each(function(el) {
			var c = $(test.children[el].children[1]).html();
			if (el%2==0) {
				v5 += '>__Входные данные__  \n' + c.replace(new RegExp("<br>",'g'), "<br>\n");
			} else {
				v5 += '__Выходные данные__  \n' + c.replace(new RegExp("<br>",'g'), "<br>\n") + '\n';
			}
		})
		var v = '<a name="' + s.split('/')[s.split('/').length - 1] + '"></a>\n';
		var i = v + v1 + v2 + v3 + v4 + v5 + '\n***\n\n';
		// console.log(i);
		return i;
	})
}


var g = function(s) {
	$.get(s, function(resp) {
		var v = "";
		var node = $(resp)
		var problem = node.find(".problems tbody");
		console.log("statr");
		problem.children().each(function(i) {
			var c = $($(problem.children()[i]).find("a")).attr("href");
			if (c != undefined) {
				var v1 = $($(problem.children()[i]).children()[0]).text().replace(new RegExp("\n",'g'), '')
				.replace(/^\s*/,'').replace(/\s*$/,'');
				var v2 = $($(problem.children()[i]).children()[1].children[0].children[0]).text()
				.replace(new RegExp("\n",'g'), '').replace(/^\s*/,'').replace(/\s*$/,'');
				// console.log('+ ' + v1 + " [" + v2 + "](#" + c.split('/')[c.split('/').length - 1] + ")");
				v += '+ ' + v1 + " [" + v2 + "](#" + c.split('/')[c.split('/').length - 1] + ")\n";
			}
		})
		v += "  \n";
		problem.children().each(function(i) {
			var c = $($(problem.children()[i]).find("a")).attr("href");
			if (c != undefined) {
				try {
				v += f(c);
				} catch(e) {
					console.log("error");
				}
			}
		})
		console.log("end");
		alert(v);
	})
}
g("https://codeforces.com/group/CYMPFXi8zA/contest/231975")
