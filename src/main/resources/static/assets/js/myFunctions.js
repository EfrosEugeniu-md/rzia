/*------------------------------------
	Theme Name: myFunction
	Start Date : 28-April-2021
	End Date :
	Last change:
	Version: 1.0
	Assigned to:
	Primary use:
---------------------------------------*/
/*

	+ Navigation through controller

	+ Document On Ready
		-  Navigation through controller


*/
var linkToAnotherSection = "";

function setLinkToAnotherSection(link) {
    var linkToAnotherSection = '';
    if (link.toString().length) linkToAnotherSection = '#' + link;

    localStorage.setItem('linkToAnotherSection', linkToAnotherSection);
}

(function ($) {

    "use strict"

    $(window).on("load", function () {
        var linkToAnotherSection = localStorage.getItem('linkToAnotherSection') || 0;
        if (linkToAnotherSection.length) {
            $([document.documentElement, document.body]).animate({
                scrollTop: $(linkToAnotherSection).offset().top
            }, 2000);
        }
    });
})(jQuery);
