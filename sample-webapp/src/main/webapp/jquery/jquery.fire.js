/**
 *
 * @author Dmitry Farafonov
 */
(function($){
	$.fire = function(eventName, eventData) {
		var args = [];
		for(var i=1; i<arguments.length; i++) {
			args.push(arguments[i]);
		}
        $(window).trigger(eventName, args);
    };
	$.fireOnce = function(eventName, eventData) {
        var args = [];
		for(var i=1; i<arguments.length; i++) {
			args.push(arguments[i]);
		}
        $(window).trigger(eventName, args);
		$(window).unbind(eventName);
    };

	$.on = function(eventName, callback) {
		$(window).bind(eventName, function(event){
			var args = [];
			for(var i=1; i<arguments.length; i++) {
				args.push(arguments[i]);
			}
			event.details = args;
			event._type = eventName;

			var data = arguments[1];
			if (typeof(data) === "object") {
				for(var i in data) {
					event[i] = data[i];
				}
			}

			callback.apply(event, arguments);
		});
	}
})(jQuery);