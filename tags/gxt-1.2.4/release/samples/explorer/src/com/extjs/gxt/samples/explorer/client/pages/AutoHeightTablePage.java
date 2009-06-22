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
import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.ToolBarEvent;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.table.CellRenderer;
import com.extjs.gxt.ui.client.widget.table.DateTimeCellRenderer;
import com.extjs.gxt.ui.client.widget.table.NumberCellRenderer;
import com.extjs.gxt.ui.client.widget.table.Table;
import com.extjs.gxt.ui.client.widget.table.TableColumn;
import com.extjs.gxt.ui.client.widget.table.TableColumnModel;
import com.extjs.gxt.ui.client.widget.table.TableItem;
import com.extjs.gxt.ui.client.widget.toolbar.AdapterToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.TextToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.RootPanel;

public class AutoHeightTablePage extends LayoutContainer implements EntryPoint {

  public void onModuleLoad() {
    RootPanel.get().add(this);
  }

  public AutoHeightTablePage() {
    // next line is only used to pass layout to containing container
    // this will have NO effect outside of the explorer demo
    setData("layout", new FitLayout());

    setScrollMode(Scroll.AUTO);

    LayoutContainer inner = new LayoutContainer();
    inner.setLayout(new FlowLayout(8));
    add(inner);

    NumberFormat currency = NumberFormat.getCurrencyFormat();
    final NumberFormat number = NumberFormat.getFormat("0.00");

    List<TableColumn> columns = new ArrayList<TableColumn>();

    TableColumn col = new TableColumn("Company", 150);
    columns.add(col);

    col = new TableColumn("Symbol", 50);
    columns.add(col);

    col = new TableColumn("Last", 100);
    col.setMaxWidth(100);
    col.setAlignment(HorizontalAlignment.RIGHT);
    col.setRenderer(new NumberCellRenderer(currency));
    columns.add(col);

    col = new TableColumn("Change", 100);
    col.setAlignment(HorizontalAlignment.RIGHT);
    col.setRenderer(new CellRenderer() {
      public String render(Component item, String property, Object value) {
        double val = (Double) value;
        String style = val < 0 ? "red" : "green";
        return "<span style='color:" + style + "'>" + number.format(val) + "</span>";
      }
    });
    columns.add(col);

    col = new TableColumn("Last Updated", 100);
    col.setAlignment(HorizontalAlignment.RIGHT);
    col.setRenderer(new DateTimeCellRenderer("MM/d/y"));
    columns.add(col);

    final TableColumnModel cm = new TableColumnModel(columns);

    final Table tbl = new Table(cm);
    tbl.setAutoHeight(true);
    tbl.setWidth(550);
    tbl.setSelectionMode(SelectionMode.MULTI);
    tbl.setHorizontalScroll(true);

    ToolBar toolBar = new ToolBar();
    toolBar.setStyleAttribute("background", "none");
    toolBar.setStyleAttribute("border", "none");

    final NumberField rows = new NumberField();
    rows.setValue(5);
    rows.setWidth(40);
    toolBar.add(new AdapterToolItem(rows));

    TextToolItem item = new TextToolItem();
    item.setText("Create Table");
    item.addListener(Events.Select, new Listener<ToolBarEvent>() {

      public void handleEvent(ToolBarEvent be) {
        tbl.removeAll();
        List<Stock> stocks = TestData.getStocks();

        int count = Math.min(40, rows.getValue().intValue());
        for (int i = 0; i < count; i++) {
          Stock stock = stocks.get(i);
          Object[] values = new Object[5];
          values[0] = stock.getName();
          values[1] = stock.getSymbol();
          values[2] = stock.getLast();
          values[3] = stock.getLast() - stock.getOpen();
          values[4] = stock.getLastTrans();

          TableItem item = new TableItem(values);
          tbl.add(item);
        }
        tbl.recalculate();
      }

    });
    toolBar.add(item);

    inner.add(toolBar);
    inner.add(new Html("<br>"));
    inner.add(tbl);
    add(inner);
  }

}
