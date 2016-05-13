var ObjectEditor = function(options) {
  var instance = this;
  this.instance = instance;
  instance.options = options;
  instance.xmlEntity = options.xmlEntity;
  instance.basePackages = options.basePackages;
  instance.onChange = options.onChange || function(){};
  instance.ajaxOptions = {
    method: options.method,
    cache: false,
    async: true,
    dataType: "json",
    contentType: "application/json",
    success: function(data, textStatus) {
      console.log(">>> data: %o, status: %o", data, textStatus);
    }
  };
  
  instance.editableOptionDefaults = {
      type: "text",
      url: options.restUrlPrefix,
      ajaxOptions: instance.ajaxOptions,
      send: 'always',
      error: function(response, newValue) {
        console.log("error response: %o, newValue: %o", response, newValue);
        return 'Some error';
      }
    };
}

ObjectEditor.prototype = {
    editableTypes: [
      "java.lang.String",
      "java.lang.Integer",
      "java.lang.Long",
      "java.lang.Boolean",
      "int",
      "long",
      "boolean",
    ],
    editablePrimitives: [
      "int",
      "long",
      "boolean",
    ],
    activatedValidators: {},
    validatorMessages: {},
      
      
    isEditableType: function(checkingType) {
      result = $.inArray(checkingType,this.editableTypes) != -1; 
      return result;
    },
    
    isEditablePrimitive: function(checkingType) {
      result = $.inArray(checkingType,this.editablePrimitives) != -1; 
      return result;
    },

    makeEditField: function($targetElement, node) {
      var instance = this;
      console.log("$targetElement: %o, node: %o", $targetElement, node);
      
      var editableOptions = $.extend({}, this.editableOptionDefaults); 
      
      if (node.isNull) {
        editableOptions.value = "";
      }
      
      var currentOptions = {}
      
      var commonParams = {
//          xmlEntity: instance.xmlEntity,
          path: node.path,
          variable: {}
      };
      // prepare customized editable-options for field
      var currentOptions;
      
      if (instance.isEditableType(node.className)) {
        currentOptions = instance.makeEditFieldOptions(node.path, node.className, node.value);
      } else if (node.isEnum) {
        currentOptions = instance.makeEditFieldEnumOptions(node.path, node.className, node.value, node.enumValues)
      } else {
        return;
      }

      // attach editable to field
      if (currentOptions) {
        editableOptions = $.extend({}, editableOptions, currentOptions);
        editableOptions.url += "/value/update";
        instance.attachEditable($targetElement, editableOptions);
      }
    },
    
    makeEditFieldOptions: function(path, className, value) {
      var instance = this;
      
      var commonParams = {
          path: path,
          variable: {}
      };
      
      // prepare customized editable-options for field
      var currentOptions;
      switch (className) {
        case "java.lang.String":
          currentOptions = {
            params: function(params) {
              data = $.extend({}, commonParams);
              data.xmlEntity = instance.xmlEntity;
              data.variable.name = path;
              data.variable.type = 'string';
              data.variable.value = params.value;
              return JSON.stringify(data);
            },
          };
          break;

        case "long":
        case "java.lang.Long":
          currentOptions = {
            params: function(params) {
              data = $.extend({}, commonParams);
              data.xmlEntity = instance.xmlEntity;
              data.variable.name = path;
              data.variable.type = 'long';
              data.variable.value = parseInt(params.value);
              return JSON.stringify(data);
            },
            validate: function(value) {
              return instance.validateInput(this, {
                signedDigits: true,
                required: instance.isEditablePrimitive(className),
                range: [Number.MIN_SAFE_VALUE, Number.MAX_SAFE_VALUE] // use javascript's Number.MIN/MAX_SAFE_VALUE
              });
            }
          };
          break;
          
        case "int":
        case "java.lang.Integer":
          currentOptions = {
            params: function(params) {
              data = $.extend({}, commonParams);
              data.xmlEntity = instance.xmlEntity;
              data.variable.name = path;
              data.variable.type = 'integer';
              data.variable.value = parseInt(params.value);
              return JSON.stringify(data);
            },
            validate: function(value) {
              return instance.validateInput(this, {
                signedDigits: true,
                required: instance.isEditablePrimitive(className),
                range: [-2147483648, 2147483647] // use Java'a Integer.MIN/MAX_VALUE
              });
            } 
          };
          break;

        case "boolean":
        case "java.lang.Boolean":
          currentOptions = {
            type: 'select',
            value: value ? value + "" : "false",
            source: [
                     {value: 'true', text: 'true'},
                     {value: 'false', text: 'false'}
            ],
            params: function(params) {
              data = $.extend({}, commonParams);
              data.xmlEntity = instance.xmlEntity;
              data.variable.name = path;
              data.variable.type = 'boolean';
              data.variable.value = params.value === 'true';
              return JSON.stringify(data);
            }
          };
          break;
      } // switch end
      return currentOptions
    },
    
    makeEditFieldEnumOptions: function(path, className, value, enumValues) {
      var instance = this;
      
      var commonParams = {
          path: path,
          variable: {}
      };
      sourceValues = [];
      for (i = 0; i < enumValues.length; i++) {
        sourceValues.push({value: enumValues[i], text: enumValues[i]});
      }
      var currentOptions = {
          type: 'select',
          value: value + "",
          source: sourceValues,
          params: function(params) {
            data = $.extend({}, commonParams);
            data.xmlEntity = instance.xmlEntity;
            data.variable.name = path;
            data.variable.type = 'enum';
            data.variable.value = params.value;
            // use valueUrl for transferring Type name
            data.variable.valueUrl = className;
            return JSON.stringify(data);
          },
      };
      return currentOptions;
    },
    
    attachEditable: function($targetElement, editableOptions) {
      var instance = this;
      
      editableOptions.success = function(response, newValue) {
        console.log("response: %o, newValue: %o", response, newValue);
        instance.xmlEntity = response.xmlEntity;
        instance.onChange.apply(this, [response.objectTree]);
      };
      $targetElement.editable(editableOptions);
      $targetElement.removeClass('editable-click');
      $targetElement.editable('show');
      $targetElement.editable('activate');
    },
    
    validateInput: function(targetObj, rules) {
      var instance = this;
      if (targetObj.$element) {
        // if targetObj instanceof Editable - get source element
        var $element = targetObj.$element;
      } else {
        var $element = $(targetObj);
      }
      
      // init validation if necessary
      var popoverId = $element.attr('aria-describedby');
      if (!instance.activatedValidators[popoverId]) {
        instance.initValidation(popoverId, rules);
        instance.activatedValidators[popoverId] = true;
      }
      // return result message
      return instance.validatorMessages[popoverId];
    },
    
    initValidation: function(popoverId, rules) {
      var instance = this;
      // enable validation plugin
      var $form = $('#'+popoverId+' form');
      $form.validate({
        showErrors: function(errorMap, errorList) {
          if (errorList.length > 0) {
            instance.validatorMessages[popoverId] = errorList[0].message;
            $form.find('.editable-error-block').text(errorList[0].message);
            $form.find('.form-group').addClass('has-error');
            $form.find('.editable-error-block').show();
          } else {
            instance.validatorMessages[popoverId] = "";
            $form.find('.form-group').removeClass('has-error');
            $form.find('.editable-error-block').hide();
          }
        }
      });
      // add rule for input
      $('#'+popoverId+' input').rules('add',rules);
      // check validness
      $form.valid();
    },
    
    sendAjaxRequest: function(url, data) {
      var instance = this;
      
      var currentAjaxOptions = $.extend({}, instance.ajaxOptions, {
        data: JSON.stringify(data),
        success: function(response) {
          instance.xmlEntity = response.xmlEntity;
          instance.onChange.apply(this, [response.objectTree]);
        }
      });
      
      $.ajax(url, currentAjaxOptions);
    },
    
    deleteFieldValue: function(node) {
      var instance = this;

      data ={
        xmlEntity: instance.xmlEntity,
        path: node.path
      };
      
      instance.sendAjaxRequest(instance.options.restUrlPrefix + "/value/delete", data);
    },
    
    removeFieldElement: function(node, parentNode) {
      var instance = this;
      
      var data = {
          xmlEntity: instance.xmlEntity,
          path: parentNode.path
      };

      var url = instance.options.restUrlPrefix + "";
      if (parentNode.isMap) {
        url += "/value/map/remove";
        data.key = node.key;
      } else if (parentNode.isList) {
        url += "/value/list/remove";
        data.index = node.key;
      } else {
        return false;
      } 
      
      instance.sendAjaxRequest(url, data);
    },
    
    createFieldObject: function($targetElement, node) {
      var instance = this;
      
      var data = {
          xmlEntity: instance.xmlEntity,
          path: node.path
      };

      var url = instance.options.restUrlPrefix + "/value/create";
      
      if (node.isMap || node.isList) {
        // if field is map or list - use predefined classes
        if (node.isMap) {
          url += "/map";
        } else if (node.isList) {
          url += "/list";
        } else {
          return;
        }
        instance.sendAjaxRequest(url, data);
      } else {
        // for other field classes - get possible class implementations before creating
        var editableOptions = $.extend({}, this.editableOptionDefaults, {
          url: url,
          type: 'select',
          source: instance.options.restUrlPrefix + "/subtypes/" + node.className + "/list",
          sourceOptions: {
            data: JSON.stringify(instance.basePackages),
            type: "POST",
            contentType: "application/json"
          },
          params: function(params) {
            data = {};
            data.xmlEntity = instance.xmlEntity;
            data.path = node.path;
            data.className = params.value;
            return JSON.stringify(data);
          }/*,
          success: function(response, newValue) {
            console.log("response: %o, newValue: %o", response, newValue);
            instance.xmlEntity = response.xmlEntity;
            instance.onChange.apply(this, [response.objectTree]);
          }*/
        });
        instance.attachEditable($targetElement, editableOptions);
      }
    },
    
    addElement: function($targetElement, node) {
      
    }
}