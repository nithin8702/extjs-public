/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.client.examples.list;

import java.util.List;

import com.extjs.gxt.samples.resources.client.TestData;
import com.extjs.gxt.samples.resources.client.model.Stock;
import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.DataList;
import com.extjs.gxt.ui.client.widget.DataListItem;
import com.extjs.gxt.ui.client.widget.DataListSelectionModel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.google.gwt.user.client.Element;

public class DataListExample extends LayoutContainer {

  private int count;

  @Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    HorizontalPanel hp = new HorizontalPanel();
    hp.setSpacing(8);

    Listener<ComponentEvent> l = new Listener<ComponentEvent>() {
      public void handleEvent(ComponentEvent ce) {
        DataList l = (DataList) ce.component;
        int count = l.getSelectedItems().size();
        Info.display("Selection Changed", "There are {0} items selected", "" + count);
      }
    };

    final DataList list = new DataList();
    list.setSelectionMode(SelectionMode.MULTI);
    final DataListSelectionModel sm = list.getSelectionModel();
    list.setSelectionModel(sm);
    list.setBorders(false);
    list.addListener(Events.SelectionChange, l);
    list.setWidth(190);

    Menu contextMenu = new Menu();

    MenuItem insert = new MenuItem();
    insert.setText("Insert Item");
    insert.setIconStyle("icon-add");
    insert.addSelectionListener(new SelectionListener<ComponentEvent>() {
      public void componentSelected(ComponentEvent ce) {
        DataListItem item = list.getSelectedItem();
        if (item != null) {
          int index = list.indexOf(item);
          DataListItem newItem = new DataListItem();
          newItem.setText("New Item " + count++);
          newItem.setIconStyle(item.getIconStyle());
          list.insert(newItem, ++index);
        }
      }
    });
    contextMenu.add(insert);

    MenuItem remove = new MenuItem();
    remove.setText("Remove Selected");
    remove.setIconStyle("icon-delete");
    remove.addSelectionListener(new SelectionListener<ComponentEvent>() {
      public void componentSelected(ComponentEvent ce) {
        for (DataListItem item : list.getSelectedItems()) {
          list.remove(item);
        }
      }
    });
    contextMenu.add(remove);

    list.setContextMenu(contextMenu);

    List<Stock> stocks = TestData.getStocks();
    for (int i = 0; i < 6; i++) {
      Stock stock = stocks.get(i);
      DataListItem item = new DataListItem();

      item.setText(stock.getName());
      item.setIconStyle("icon-chart");
      list.add(item);
    }
    hp.add(list);

    ContentPanel frame = new ContentPanel();
    frame.setFrame(true);
    frame.setCollapsible(true);
    frame.setAnimCollapse(false);
    frame.setHeading("Framed List");
    frame.setSize(210, 200);

    final DataList list2 = new DataList();
    list2.setFlatStyle(true);

    list2.addListener(Events.SelectionChange, l);
    stocks = TestData.getStocks();
    for (Stock stock : stocks) {
      DataListItem item = new DataListItem();
      item.setText(stock.getName());
      list2.add(item);
    }

    frame.setLayout(new FitLayout());
    frame.add(list2);

    hp.add(frame);

    VerticalPanel vp = new VerticalPanel();
    vp.setSpacing(8);
    ButtonBar buttonBar = new ButtonBar();
    buttonBar.add(new Button("Select All", new SelectionListener<ComponentEvent>() {
      public void componentSelected(ComponentEvent ce) {
        sm.selectAll();
      }
    }));
    buttonBar.add(new Button("Select Last", new SelectionListener<ComponentEvent>() {
      public void componentSelected(ComponentEvent ce) {
        DataListItem item = list2.getItem(list2.getItemCount() - 1);
        list2.getSelectionModel().select(item);
        list2.scrollIntoView(item);
      }
    }));

    vp.add(buttonBar);
    vp.add(hp);
    add(vp);
  }

}
