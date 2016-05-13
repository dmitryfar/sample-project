/////////////////////////////
//// NEED TO BE MODIFIED ////
/////////////////////////////

var AbstractRestController = {};
AbstractRestController.prototype ={
	restServiceUrl: "/activiti-rest/service",
	explorerServiceUrl: "/activiti-explorer/service",
	sendRequest: function (options) {
		var instance = this;

		// url, type, data, success, fail

		var defaultOptions = {
			app: "rest",
			cache: false,
			async: true,
			data: null,
			page: null,
			type: "get",
			dataType: "json",
			// dataType: 'jsonp',
			contentType: "application/json",
			success: function(data, textStatus) {
				console.log("data: %o, status: %o", data, textStatus);
			},
		};
		var options = $.extend(defaultOptions, options);
		options.url = this[options.app+"ServiceUrl"] + options.url;

		if (options.page) {
			options.data = $.extend({}, options.data, options.page);
		}

		if (options.dataType == "json" && options.data != null) {
			options.data = JSON.stringify(options.data);
		}

		$.ajax(options).done(function(data, textStatus) {
			// console.log("ajax done");
		}).fail(function(jqXHR, textStatus, error){
			console.log("fail ", jqXHR, textStatus, error);
			if (options.fail) {
				options.fail.apply(instance, [jqXHR, textStatus, error]);
			}
		});
	}
};

/* ProcessInstanceRestController */

	ProcessInstanceRestController = {
		/**
		 * ProcessInstanceRestController.queryProcessInstances({"excludeSubprocesses": true, includeProcessVariables: true}, {size: 3}, function(response) {console.log(response.data);});
		 */
		queryProcessInstances: function (query, page, successCallback) {
			var url = "/query/process-instances";
			this.prototype.sendRequest({
				url: url,
				data: query,
				type: "post",
				page: page,
				success: successCallback,
			});
		},
		instancesRestart: function (filter, successCallback) {
		  var url = "/pm/job/restart";
		  this.prototype.sendRequest({
		    app: "explorer",
		    url: url,
		    data: filter,
		    type: "post",
		    success: successCallback,
		  });
		},
	}
	$.inherit(ProcessInstanceRestController, AbstractRestController);

/* ProcessInstanceRestController */

	HistoricProcessInstanceRestController = {
			/**
			 * HistoricProcessInstanceRestController.queryProcessInstances({"excludeSubprocesses": true, includeProcessVariables: true}, {size: 3}, function(response) {console.log(response.data);});
			 */
			queryProcessInstances: function (query, page, successCallback) {
				var url = "/query/historic-process-instances";
				this.prototype.sendRequest({
					url: url,
					data: query,
					type: "post",
					page: page,
					success: successCallback,
				});
			},
			/**
			 * HistoricProcessInstanceRestController.getProcessInstances([183923, 183931], function(response) {console.log(response.data);});
			 */
			getProcessInstances: function(processInstanceIds, successCallback) {
				this.queryProcessInstances({processInstanceIds: processInstanceIds}, null, successCallback);
			},
			getTree: function(processInstanceId, successCallback, failCallback) {
				var url = "/pm/process-instance/{{processInstanceId}}/tree";
				url = TemplateEngine(url, {processInstanceId: processInstanceId});
				this.prototype.sendRequest({
					app: "explorer",
					url: url,
					success: successCallback,
					fail: failCallback,
				});
			},
			getHistoricTree: function(processInstanceId, successCallback) {
				var url = "/pm/process-instance/{{processInstanceId}}/historic-tree";
				url = TemplateEngine(url, {processInstanceId: processInstanceId});
				this.prototype.sendRequest({
					app: "explorer",
					url: url,
					success: successCallback,
				});
			},
	}
	$.inherit(HistoricProcessInstanceRestController, AbstractRestController);

/* ProcessDefinitionRestController */

	var ProcessDefinitionRestController = {
		/**
		 * ProcessDefinitionRestController.getList(function(response) {console.log(response.total, response.data)})
		 * FIXME: ProcessDefinitionCollectionResource actually doesn't allow pagination, returns 10 always
		 */
		getList: function(successCallback) {
			this.getListPage({size: 2147483647 /* Integer.MAX_VALUE */}, successCallback);
		},

		/**
		 * ProcessDefinitionRestController.getListPage({size:4, start: 2}, function(response) {console.log(response)})
		 */
		getListPage: function(page, successCallback) {
			var url = "/repository/process-definitions";
			this.prototype.sendRequest({
				url: url,
				page: page,
				success: successCallback,
			});
		},
		getProcessDefinition: function(processDefinitionId, successCallback) {
			var url = "/repository/process-definitions/{{processDefinitionId}}";
			url = TemplateEngine(url, {processDefinitionId: processDefinitionId});
			this.prototype.sendRequest({
				url: url,
				success: successCallback,
			});
		},
	}
	$.inherit(ProcessDefinitionRestController, AbstractRestController);

/* JobManagerRestController */

	var JobManagerRestController = {
			jobRestart: function (filter, successCallback) {
				var url = "/pm/job/restart";
				this.sendCommand(url, filter, successCallback);
			},
			processInstanceRestart: function (filter, successCallback) {
			  var url = "/pm/job/processinstance-restart";
			  this.sendCommand(url, filter, successCallback);
			},
			processInstanceStop: function (filter, successCallback) {
			  var url = "/pm/job/processinstance-stop";
			  this.sendCommand(url, filter, successCallback);
			},
			sendCommand: function (url, filter, successCallback) {
			  this.prototype.sendRequest({
          app: "explorer",
          url: url,
          data: filter,
          type: "post",
          success: successCallback,
        });
			}
	}
	$.inherit(JobManagerRestController, AbstractRestController);