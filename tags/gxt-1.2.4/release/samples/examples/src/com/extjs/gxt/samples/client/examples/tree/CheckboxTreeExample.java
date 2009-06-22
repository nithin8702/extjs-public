/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.client.examples.tree;

import com.extjs.gxt.samples.resources.client.TestData;
import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.TreeEvent;
import com.extjs.gxt.ui.client.util.TreeBuilder;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.extjs.gxt.ui.client.widget.tree.TreeItem;

public class CheckboxTreeExample extends LayoutContainer {
  private String path;

  public CheckboxTreeExample() {
    final Tree tree = new Tree();
    tree.setCheckable(true);
    tree.getStyle().setLeafIconStyle("icon-music");
    tree.addListener(Events.SelectionChange, new Listener<TreeEvent>() {
      public void handleEvent(TreeEvent te) {
        TreeItem item = te.tree.getSelectedItem();
        if (item != null) {
          Info.display("Selection Changed", "The '{0}' item was selected", item.getText());
        }
      }
    });

    // quick way to build tree from Model instances.
    TreeBuilder.buildTree(tree, TestData.getTreeModel());

    ButtonBar buttonBar = new ButtonBar();
    buttonBar.add(new Button("Copy Selected Path", new SelectionListener<ButtonEvent>() {
      public void componentSelected(ButtonEvent ce) {
        TreeItem item = tree.getSelectedItem();
        if (item == null) return;
        path = item.getPath();
        String[] ids = path.split(",");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ids.length; i++) {
          TreeItem ti = tree.getItemById(ids[i]);
          if (ti != null) {
            sb.append("/" + ti.getText());
          }
        }
        Info.display("Get Path", "The current path is {0}", sb.toString().substring(1));
      }
    }));
    buttonBar.add(new Button("Restore Path", new SelectionListener<ComponentEvent>() {
      public void componentSelected(ComponentEvent ce) {
        tree.expandPath(path);
      }
    }));

    buttonBar.add(new Button("Get Checked", new SelectionListener<ComponentEvent>() {
      public void componentSelected(ComponentEvent ce) {
        StringBuffer sb = new StringBuffer();
        for (TreeItem item : tree.getChecked()) {
          sb.append(", " + item.getText());
        }
        String s = sb.toString();
        if (s.length() > 1) s = s.substring(2);
        if (s.length() > 100) s = s.substring(0, 100) + "...";
        Info.display("Checked Items", s, "");
      }
    }));

    VerticalPanel vp = new VerticalPanel();
    vp.setSpacing(10);

    vp.add(buttonBar);
    vp.add(tree);

    setLayout(new FlowLayout());
    add(vp);
  }
}
