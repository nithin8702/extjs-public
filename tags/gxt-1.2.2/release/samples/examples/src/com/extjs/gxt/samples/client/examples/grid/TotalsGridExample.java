/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.client.examples.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.samples.resources.client.TestData;
import com.extjs.gxt.samples.resources.client.model.Task;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GroupSummaryView;
import com.extjs.gxt.ui.client.widget.grid.SummaryColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.SummaryRenderer;
import com.extjs.gxt.ui.client.widget.grid.SummaryType;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.i18n.client.NumberFormat;

public class TotalsGridExample extends LayoutContainer {

  public TotalsGridExample() {
    setLayout(new FlowLayout(10));

    List<Task> tasks = TestData.getTasks();
    GroupingStore<Task> store = new GroupingStore<Task>();
    store.groupBy("project");
    store.add(tasks);

    List<ColumnConfig> columns = new ArrayList<ColumnConfig>();

    SummaryColumnConfig desc = new SummaryColumnConfig("description", "Task", 80);
    desc.setSummaryType(SummaryType.COUNT);
    desc.setSummaryRenderer(new SummaryRenderer() {
      public String render(Double value, Map<String, Double> data) {
        return value > 1 ? "(" + value.intValue() + " Tasks)" : "(1 Task)";
      }
    });

    SummaryColumnConfig project = new SummaryColumnConfig("project", "Project", 80);
    SummaryColumnConfig due = new SummaryColumnConfig("due", "Due Date", 20);

    SummaryColumnConfig estimate = new SummaryColumnConfig("estimate", "Estimate", 20);
    estimate.setRenderer(new GridCellRenderer() {
      public String render(ModelData model, String property, ColumnData config, int rowIndex,
          int colIndex, ListStore store) {
        return model.get(property) + " hours";
      }
    });
    estimate.setSummaryType(SummaryType.SUM);
    estimate.setSummaryRenderer(new SummaryRenderer() {
      public String render(Double value, Map<String, Double> data) {
        return value.intValue() + " hours";
      }
    });
    estimate.setEditor(new CellEditor(new NumberField()));

    SummaryColumnConfig rate = new SummaryColumnConfig("rate", "Rate", 20);
    rate.setNumberFormat(NumberFormat.getCurrencyFormat());
    rate.setSummaryFormat(NumberFormat.getCurrencyFormat());
    rate.setSummaryType(SummaryType.AVG);
    
    NumberField nf = new NumberField();
    nf.setAutoValidate(true);
    CellEditor ce = new CellEditor(nf);
    ce.setRevertInvalid(true);
    ce.setCancelOnEsc(true);
    rate.setEditor(ce);

    SummaryColumnConfig cost = new SummaryColumnConfig("cost", "Cost", 20);
    cost.setSummaryFormat(NumberFormat.getCurrencyFormat());
    cost.setSummaryType(new SummaryType() {
      @Override
      public double render(Object v, ModelData m, String field, Map<String, Object> data) {
        if (v == null) {
          v = 0d;
        }
        Task task = (Task) m;
        return ((Double) v).doubleValue() + (task.getEstimate() * task.getRate());
      }

    });
    cost.setRenderer(new GridCellRenderer() {
      public String render(ModelData model, String property, ColumnData config, int rowIndex,
          int colIndex, ListStore store) {
        Task task = (Task) model;
        return NumberFormat.getCurrencyFormat().format(task.getRate() * task.getEstimate());
      }
    });

    columns.add(desc);
    columns.add(project);
    columns.add(due);
    columns.add(estimate);
    columns.add(rate);
    columns.add(cost);
    ColumnModel cm = new ColumnModel(columns);

    GroupSummaryView summary = new GroupSummaryView();
    summary.setForceFit(true);
    summary.setShowGroupedColumn(false);

    EditorGrid<Task> grid = new EditorGrid<Task>(store, cm);
    grid.setBorders(true);
    grid.setView(summary);
    grid.getView().setShowDirtyCells(false);

    ContentPanel panel = new ContentPanel();
    panel.setHeading("Sponsored Projects");
    panel.setIconStyle("icon-table");
    panel.setCollapsible(true);
    panel.setFrame(true);
    panel.setSize(700, 450);
    panel.setLayout(new FitLayout());
    panel.add(grid);
    add(panel);
  }

}
