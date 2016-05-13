var initTreeview = function(tree_id, objectTree) {
    // make treeview
    $('#' + tree_id).treeview({
      color: "#337ab7",
      showBorder: false,
      expandIcon: "glyphicon glyphicon-chevron-right",
      collapseIcon: "glyphicon glyphicon-chevron-down",
      data : [objectTree],
      levels: 99
    });
 
    // prevent editable's auto-closing
    $('#' + tree_id).on('click', '.field-wrapper', function(e) { 
      e.stopPropagation(); 
    });
  }