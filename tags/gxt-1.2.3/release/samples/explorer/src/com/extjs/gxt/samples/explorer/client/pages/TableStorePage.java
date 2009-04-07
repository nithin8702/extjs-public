/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.explorer.client.pages;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.samples.resources.client.TestData;
import com.extjs.gxt.samples.resources.client.model.Stock;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.binder.TableBinder;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.table.CellRenderer;
import com.extjs.gxt.ui.client.widget.table.DateTimeCellRenderer;
import com.extjs.gxt.ui.client.widget.table.NumberCellRenderer;
import com.extjs.gxt.ui.client.widget.table.Table;
import com.extjs.gxt.ui.client.widget.table.TableColumn;
import com.extjs.gxt.ui.client.widget.table.TableColumnModel;
import com.extjs.gxt.ui.client.widget.table.TableItem;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.TextToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.RootPanel;

public class TableStorePage extends LayoutContainer implements EntryPoint {

  public void onModuleLoad() {
    RootPanel.get().add(this);
  }

  public TableStorePage() {
    setLayout(new FlowLayout(10));
    setMonitorResize(false);

    final NumberFormat currency = NumberFormat.getCurrencyFormat();
    final NumberFormat number = NumberFormat.getFormat("0.00");

    List<TableColumn> columns = new ArrayList<TableColumn>();

    TableColumn col = new TableColumn("name", "Company", 150);
    col.setMinWidth(75);
    col.setMaxWidth(300);
    columns.add(col);

    col = new TableColumn("symbol", "Symbol", 50);
    columns.add(col);

    col = new TableColumn("last", "Last", 100);
    col.setMaxWidth(100);
    col.setAlignment(HorizontalAlignment.RIGHT);
    col.setRenderer(new NumberCellRenderer(currency));
    columns.add(col);

    col = new TableColumn("change", "Change", 100);
    col.setAlignment(HorizontalAlignment.RIGHT);
    col.setRenderer(new CellRenderer<TableItem>() {
      public String render(TableItem item, String property, Object value) {
        double val = (Double) value;
        String style = val < 0 ? "red" : "green";
        return "<span style='color:" + style + "'>" + number.format(val) + "</span>";
      }
    });
    columns.add(col);

    col = new TableColumn("date", "Last Updated", 100);
    col.setAlignment(HorizontalAlignment.RIGHT);
    col.setRenderer(new DateTimeCellRenderer("MM/d/y"));
    columns.add(col);

    TableColumnModel cm = new TableColumnModel(columns);

    Table tbl = new Table(cm);
    tbl.setSelectionMode(SelectionMode.MULTI);
    tbl.setHorizontalScroll(true);

    ListStore<Stock> store = new ListStore<Stock>();
    store.add(TestData.getStocks());

    TableBinder<Stock> binder = new TableBinder<Stock>(tbl, store);
    binder.init();

    final ContentPanel panel = new ContentPanel();
    panel.setCollapsible(true);
    panel.setFrame(true);
    panel.setAnimCollapse(false);
    panel.setButtonAlign(HorizontalAlignment.CENTER);
    panel.setIconStyle("icon-table");
    panel.setHeading("Table Store Demo");
    panel.setLayout(new FitLayout());
    panel.add(tbl);
    panel.setSize(575, 350);

    // built in support for top component
    ToolBar toolBar = new ToolBar();
    toolBar.add(new TextToolItem("Add", "icon-add"));
    toolBar.add(new SeparatorToolItem());
    toolBar.add(new TextToolItem("Remove", "icon-delete"));
    toolBar.add(new SeparatorToolItem());
    toolBar.add(new TextToolItem("Configure", "icon-plugin"));
    panel.setTopComponent(toolBar);

    // add buttons
    panel.addButton(new Button("Save"));
    panel.addButton(new Button("Cancel"));

    add(panel);
  }

}
