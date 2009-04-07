/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.client.examples.tabs;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.Timer;

public class AdvancedTabExample extends LayoutContainer {

  private int index = 0;
  private TabPanel advanced;

  public AdvancedTabExample() {
    VerticalPanel vp = new VerticalPanel();
    vp.setSpacing(10);

    Button add = new Button("Add Tab");
    add.addSelectionListener(new SelectionListener<ComponentEvent>() {
      @Override
      public void componentSelected(ComponentEvent ce) {
        addTab();
        advanced.setSelection(advanced.getItem(index - 1));
      }
    });
    vp.add(add);

    advanced = new TabPanel();
    advanced.setSize(600, 250);
    advanced.setMinTabWidth(115);
    advanced.setResizeTabs(true);
    advanced.setAnimScroll(true);
    advanced.setTabScroll(true);

    while (index < 7) {
      addTab();
    }
    vp.add(advanced);
    add(vp);
  }

  @Override
  protected void onAttach() {
    super.onAttach();
    Timer t = new Timer() {
      @Override
      public void run() {
        advanced.setSelection(advanced.getItem(6));
      }
    };
    t.schedule(1100);
  }

  private void addTab() {
    TabItem item = new TabItem();
    item.setText("New Tab " + ++index);
    item.setClosable(index != 1);
    item.addText("Tab Body " + index);
    item.addStyleName("pad-text");
    advanced.add(item);
  }

}
