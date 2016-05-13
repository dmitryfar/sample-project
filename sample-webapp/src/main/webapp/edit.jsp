<%@page import="java.util.Arrays"%>
<%@page import="java.lang.reflect.Array"%>
<%@page import="me.objecteditor.test.CoolTestEntity"%>
<%@page import="com.fasterxml.jackson.databind.node.ObjectNode"%>
<%@page import="me.objecteditor.util.ObjectTreeUtil2"%>
<%@page import="me.objecteditor.test.TestClass"%>
<%@include file="init.jspf" %>
<%
TestClass testObject = new CoolTestEntity();

ObjectNode jsonObject = ObjectTreeUtil2.getFullObjectTreeview(testObject);
String xml = xStreamUtil.toXML(testObject);

%>

<%@include file="header.jspf" %>

<div id="treeObject"></div>

<ul id="contextMenu" class="dropdown-menu" role="menu" style="display:none" >
    <li class="disabled create"><a tabindex="-1" href="#" value="create">Create</a></li>
    <li class="disabled edit"><a tabindex="-1" href="#" value="edit">Edit</a></li>
    <li class="disabled delete"><a tabindex="-1" href="#" value="delete">Delete</a></li>
    <li class="divider"></li>
    <li class="disabled add-element"><a tabindex="-1" href="#" value="add-element">Add element</a></li>
    <li class="disabled remove-element"><a tabindex="-1" href="#" value="remove-element">Remove element</a></li>
</ul>

<script>
  var tree_id = "treeObject";

  var objectEditor = new ObjectEditor({
    restUrlPrefix: "/object-editor",
    method: "POST",
    xmlEntity: "<%= xml.replace("\"", "\\\"").replace("\n", "") %>",
    basePackages: <%= ObjectTreeUtil2.getParsePackagesJsonString() %>,
    onChange: function(objectTree) {
      initTreeview(tree_id, objectTree);
    }
  });
  
  // make context menu for fields
  $('#' + tree_id).contextMenu({
    elementSelector: "span.field-wrapper",
    menuSelector: "#contextMenu",
    menuSelected: function ($invokedOn, $selectedMenu) {
      console.log("$invokedOn: %o, $selectedMenu: %o, command: %o", $invokedOn, $selectedMenu, $selectedMenu.attr('value'));
      var treeElement = $invokedOn.closest("li");
      var node = $('#' + tree_id).treeview('getNode', treeElement.attr("data-nodeid"));
      switch ($selectedMenu.attr('value')) {
        case "create":
          var $targetElement = treeElement.find('span.field-class');
          objectEditor.createFieldObject($targetElement, node);
          break;
        case "edit":
          var $targetElement = treeElement.find('span.field-value');
          objectEditor.makeEditField($targetElement, node);
          break;
        case "delete":
          objectEditor.deleteFieldValue(node);
          break;
        case "add-element":
          objectEditor.addElement($targetElement, node);
          break;
        case "remove-element":
          var parentNode = $('#' + tree_id).treeview('getNode', node.parentId);
          objectEditor.removeFieldElement(node, parentNode);
          break;
      }
    },
    onMenuShow: function($invokedOn) {
      $('#' + tree_id + ' .editable').editable('hide');
      
      var treeElement = $invokedOn.closest("li");
      $(treeElement).addClass("current-tree-element");
      
      var currentNode = $('#' + tree_id).treeview('getNode', treeElement.attr("data-nodeid"));
      var parentNode = $('#' + tree_id).treeview('getNode', currentNode.parentId);
      console.log("currentNode: %o", currentNode);
      
      // enable valid menu items
      if (!currentNode.isNull && (currentNode.isList || currentNode.isMap) ) {
        $('#contextMenu li.delete').removeClass("disabled");
        $('#contextMenu li.add-element').removeClass("disabled");
      } else if (currentNode.isNull) {
        if (objectEditor.isEditableType(currentNode.className) || currentNode.isEnum) {
          $('#contextMenu li.edit').removeClass("disabled");
        } else {
          $('#contextMenu li.create').removeClass("disabled");
        }
      } else {
        if (objectEditor.isEditableType(currentNode.className) || currentNode.isEnum) {
          $('#contextMenu li.edit').removeClass("disabled");
          if (!objectEditor.isEditablePrimitive(currentNode.className)) {
            $('#contextMenu li.delete').removeClass("disabled");
          }
        } else {
          $('#contextMenu li.delete').removeClass("disabled");
        }
      }
      
      if (parentNode.isMap || parentNode.isList) {
        $('#contextMenu li.remove-element').removeClass("disabled");
      }
    },
    onMenuHide: function($invokedOn) {
      var treeElement = $invokedOn.closest("li");
      $(treeElement).removeClass("current-tree-element");
      $('#contextMenu li').addClass("disabled");
    }
  });
  
  initTreeview(tree_id, <%= jsonObject.toString() %>);
  
</script>
<%@include file="footer.jspf" %>