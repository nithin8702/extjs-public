/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.widget.treetable;

import com.extjs.gxt.ui.client.widget.tree.TreeItemUI;
import com.google.gwt.user.client.DOM;

public class RootTreeTableItem extends TreeTableItem {

  public RootTreeTableItem(TreeTable treeTable) {
    super(new String[0]);
    setElement(DOM.createDiv());
    this.tree = treeTable;
    rendered = true;
    getTreeItemUI();
  }

  @Override
  protected TreeItemUI getTreeItemUI() {
    ui = new TreeTableItemUI(this);
    ((TreeTableItemUI) ui).setContainer(getElement());
    setData("loaded", "true");
    return ui;
  }

  @Override
  protected void renderChildren() {
    if (!childrenRendered) {
      super.renderChildren();
    }
  }

}