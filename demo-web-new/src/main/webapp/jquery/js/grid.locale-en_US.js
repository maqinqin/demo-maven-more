(function($) {
	/**   * jqGrid English Translation   * Tony Tomov tony@trirand.com   * http://trirand.com/blog/    * Dual licensed under the MIT and GPL licenses:   * http://www.opensource.org/licenses/mit-license.php   * http://www.gnu.org/licenses/gpl.html  **/
	$.jgrid = {
		defaults : {
			recordtext : "Display {0} - {1} record,  {2} in all",
			emptyrecords : "Record was not found",
			loadtext : "LOADING...",
			pgtext : "No. {0} , {1} in all"
		},
		search : {
			caption : "Find...",
			Find : "confirm",
			Reset : "reset",
			odata : [ '=', '<>', '<', '<=', '>', '>=', 'start with XX', 'not start with XX',
					'is in', 'is not in', 'end with XX', 'not end with XX', 'contain', 'not contain' ],
			groupOps : [ {
				op : "also",
				text : "all"
			}, {
				op : "or",
				text : "any"
			} ],
			matchText : " matching",
			rulesText : " rule"
		},
		edit : {
			addCaption : "add record",
			editCaption : "edit record",
			bSubmit : "commit",
			bCancel : "cancel",
			bClose : "close",
			saveData : "The data has been modified. Do you want to save the changes?",
			bYes : "yes",
			bNo : "no",
			bExit : "cancel",
			msg : {
				required : "Required",
				number : "must be number",
				minValue : "please greater than or equal to ",
				maxValue : "please less than or equal to ",
				email : "e-mail format error",
				integer : "please enter an integer",
				date : "date format error",
				url : "URL format error ('http://' or start with 'https://')"
			}
		},
		view : {
			caption : "display",
			bClose : "close"
		},
		del : {
			caption : "delete",
			msg : "delete selected rows?",
			bSubmit : "delete",
			bCancel : "cancel"
		},
		nav : {
			edittext : "",
			edittitle : "edit selected row",
			addtext : "",
			addtitle : "add new row",
			deltext : "",
			deltitle : "delete selected row",
			searchtext : "",
			searchtitle : "find",
			refreshtext : "",
			refreshtitle : "flash",
			alertcap : "alert",
			alerttext : "please selected row",
			viewtext : "",
			viewtitle : "view selected row"
		},
		col : {
			caption : "display/hide column",
			bSubmit : "commit",
			bCancel : "cancel"
		},
		errors : {
			errcap : "error",
			nourl : "no url",
			norecords : "no record",
			model : "loading data error"
		},
		formatter : {
			integer : {
				thousandsSeparator : " ",
				defaultValue : '0'
			},
			number : {
				decimalSeparator : ".",
				thousandsSeparator : " ",
				decimalPlaces : 2,
				defaultValue : '0.00'
			},
			currency : {
				decimalSeparator : ".",
				thousandsSeparator : " ",
				decimalPlaces : 2,
				prefix : "",
				suffix : "",
				defaultValue : '0.00'
			},
			date : {
				dayNames : [ "Sun.", "Mon.", "Tues.", "Wed.", "Thur.", "Fri.", " Sat.", "Sunday", "Monday",
						"Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", ],
				monthNames : [ "1", "2", "3", "4", "5", "6", "7", "8", "9",
						"10", "11", "12", "Jan", "Feb", "Mar", "Apr", "May", "Jun",
						"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ],
				AmPm : [ "am", "pm", "AM", "PM" ],
				S : function(j) {
					return j < 11 || j > 13 ? [ 'st', 'nd', 'rd', 'th' ][Math
							.min((j - 1) % 10, 3)] : 'th';
				},
				srcformat : 'Y-m-d',
				newformat : 'd/m/Y',
				masks : {
					ISO8601Long : "Y-m-d H:i:s",
					ISO8601Short : "Y-m-d",
					ShortDate : "n/j/Y",
					LongDate : "l, F d, Y",
					FullDateTime : "l, F d, Y g:i:s A",
					MonthDay : "F d",
					ShortTime : "g:i A",
					LongTime : "g:i:s A",
					SortableDateTime : "Y-m-d\\TH:i:s",
					UniversalSortableDateTime : "Y-m-d H:i:sO",
					YearMonth : "F, Y"
				},
				reformatAfterEdit : false
			},
			baseLinkUrl : '',
			showAction : '',
			target : '',
			checkbox : {
				disabled : true
			},
			idName : 'id'
		}
	};
})(jQuery);