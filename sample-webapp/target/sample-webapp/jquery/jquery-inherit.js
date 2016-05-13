/**
 * Inheritance plugin
 *
 * @author Dmitry Farafonov (dmitry.farafonov@gmail.com)
 * @version 1.0.0
 */

(function($) {
	$.inherit = function(Child, Parent) {
		var F = function() { }
		F.prototype = Parent.prototype
		var childProto = Child.prototype
		Child.prototype = new F()
		Child.prototype = $.extend(Child.prototype, childProto)
		Child.prototype.constructor = Child
		Child.prototype.super = Parent
		Child.prototype.superproto = Parent.prototype
	}
})(jQuery);