/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreFilter;
import com.extjs.gxt.ui.client.widget.form.TriggerField;

/**
 * A <code>TriggerField</code> that filters its bound stores based on the value
 * of the field.
 * 
 * @param <M> the model type
 */
public abstract class StoreFilterField<M extends ModelData> extends TriggerField<M> {

  protected StoreFilter filter;
  protected List<Store> stores = new ArrayList<Store>();

  private String property;

  /**
   * Creates a new store filter field.
   */
  public StoreFilterField() {
    setAutoValidate(true);
    setValidateOnBlur(false);
    setTriggerStyle("x-form-clear-trigger");
    setWidth(150);
    filter = new StoreFilter<M>() {
      public boolean select(Store<M> store, M parent, M model, String property) {
        String v = getRawValue();
        return doSelect(store, parent, model, property, v);
      }
    };
  }

  /**
   * Binds the store to the field.
   * 
   * @param store the store to add
   */
  public void bind(Store<M> store) {
    store.addFilter(filter);
    stores.add(store);
  }

  /**
   * Returns the current filter property.
   * 
   * @return the property name
   */
  public String getProperty() {
    return property;
  }

  /**
   * Sets the filter property name to be filtered.
   * 
   * @param property the property name
   */
  public void setProperty(String property) {
    this.property = property;
  }

  /**
   * Unbinds the store from the field.
   * 
   * @param store the store to be unbound
   */
  public void unbind(ListStore store) {
    store.removeFilter(filter);
    stores.remove(store);
  }

  protected void applyFilters(Store store) {
    if (getRawValue().length() > 0) {
      store.applyFilters(property);
    } else {
      store.clearFilters();
    }
  }

  protected void onFilter() {
    for (Store s : stores) {
      applyFilters(s);
    }
    focus();
  }

  @Override
  protected void onTriggerClick(ComponentEvent ce) {
    super.onTriggerClick(ce);
    setValue(null);
    onFilter();
  }

  protected abstract boolean doSelect(Store<M> store, M parent, M record, String property,
      String filter);

  @Override
  protected boolean validateValue(String value) {
    boolean ret = super.validateValue(value);
    onFilter();
    return ret;
  }
}
