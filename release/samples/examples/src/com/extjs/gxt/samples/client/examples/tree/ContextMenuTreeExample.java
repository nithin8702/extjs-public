/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.client.examples.tree;

import com.extjs.gxt.samples.resources.client.TestData;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.TreeBuilder;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.extjs.gxt.ui.client.widget.tree.TreeItem;
import com.google.gwt.user.client.Element;

public class ContextMenuTreeExample extends LayoutContainer {
  private int count = 1;

  @Override
  protected void onRender(Element parent, int pos) {
    super.onRender(parent, pos);

    final Tree tree = new Tree();
    tree.setSelectionMode(SelectionMode.MULTI);
    tree.getStyle().setLeafIconStyle("icon-music");

    // quick way to build tree from Model instances.
    TreeBuilder.buildTree(tree, TestData.getTreeModel());

    Menu contextMenu = new Menu();
    contextMenu.setWidth(130);

    MenuItem insert = new MenuItem();
    insert.setText("Insert Item");
    insert.setIconStyle("icon-add");
    insert.addSelectionListener(new SelectionListener<MenuEvent>() {
      public void componentSelected(MenuEvent ce) {
        TreeItem item = (TreeItem) tree.getSelectionModel().getSelectedItem();
        if (item != null) {
          TreeItem newItem = new TreeItem();
          newItem.setText("Add Child " + count++);
          item.add(newItem);
          item.setExpanded(true);
        }
      }
    });
    contextMenu.add(insert);

    MenuItem remove = new MenuItem();
    remove.setText("Remove Selected");
    remove.setIconStyle("icon-delete");
    remove.addSelectionListener(new SelectionListener<MenuEvent>() {
      public void componentSelected(MenuEvent ce) {
        TreeItem item = (TreeItem) tree.getSelectionModel().getSelectedItem();
        if (item != null) {
          item.getParentItem().remove(item);
        }
      }
    });
    contextMenu.add(remove);

    tree.setContextMenu(contextMenu);

    setLayout(new FlowLayout(8));
    add(tree);
  }
}
