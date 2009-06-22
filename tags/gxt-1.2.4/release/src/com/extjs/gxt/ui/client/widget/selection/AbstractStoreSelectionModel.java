/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.widget.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseObservable;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionEvent;
import com.extjs.gxt.ui.client.event.SelectionProvider;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;

/**
 * Abstract base class for store based selection models.
 * 
 * <dl>
 * <dt><b>Events:</b></dt>
 * 
 * <dd><b>SelectionChange</b> : SelectionChangedEvent(source, selection)<br>
 * <div>Fires after the selection changes.</div>
 * <ul>
 * <li>source : this</li>
 * <li>selection : the selected items</li>
 * </ul>
 * </dd>
 * 
 * <dd><b>BeforeSelect</b> : SelectionEvent(source, model)<br>
 * <div>Fires before a row is selected. Listeners can set the <code>doit</code>
 * field to <code>false</code> to cancel the operation.</div>
 * <ul>
 * <li>source : this</li>
 * <li>model : the selected item</li>
 * <li>index : the row index</li>
 * </ul>
 * </dd>
 * 
 * @param <M> the model type contained within the store
 */
public abstract class AbstractStoreSelectionModel<M extends ModelData> extends BaseObservable
    implements StoreSelectionModel<M>, SelectionProvider<M> {

  protected ListStore<M> store;
  protected SelectionMode selectionMode = SelectionMode.MULTI;
  protected List<M> selected = new ArrayList<M>();
  protected M lastSelected;
  protected boolean locked;

  protected StoreListener<M> storeListener = new StoreListener<M>() {
    @Override
    public void storeAdd(StoreEvent<M> se) {
      onAdd(se.models);
    }

    @Override
    public void storeClear(StoreEvent<M> se) {
      onClear(se);
    }

    @Override
    public void storeRemove(StoreEvent<M> se) {
      onRemove(se.model);
    }

  };

  public void addSelectionChangedListener(SelectionChangedListener listener) {
    addListener(Events.SelectionChange, listener);
  }

  public void bind(ListStore store) {
    if (this.store != null) {
      this.store.removeStoreListener(storeListener);
    }
    this.store = store;
    if (store != null) {
      store.addStoreListener(storeListener);
    }
  }

  public void deselect(int index) {
    M m = store.getAt(index);
    if (m != null) {
      doDeselect(Arrays.asList(m), false);
    }
  }

  public void deselect(int start, int end) {
    List<M> list = new ArrayList<M>();
    for (int i = start; i < end; i++) {
      M m = store.getAt(i);
      if (m != null) {
        list.add(m);
      }
    }
    doDeselect(list, false);
  }

  public void deselect(List<M> items) {
    doDeselect(items, false);
  }

  public void deselect(M... items) {
    deselect(Arrays.asList(items));
  }

  public void deselect(M item) {
    deselect(Arrays.asList(item));
  }

  public void deselectAll() {
    doDeselect(new ArrayList<M>(selected), false);
  }

  public M getSelectedItem() {
    return lastSelected;
  }

  public List<M> getSelectedItems() {
    return new ArrayList(selected);
  }

  public List<M> getSelection() {
    return getSelectedItems();
  }

  public SelectionMode getSelectionMode() {
    return selectionMode;
  }

  /**
   * Returns true if the selection model is locked.
   * 
   * @return the locked state
   */
  public boolean isLocked() {
    return locked;
  }

  public boolean isSelected(M item) {
    int idx = store.indexOf(item);
    for (M m : selected) {
      if (store.indexOf(m) == idx) {
        return true;
      }
    }
    return false;
  }

  public void refresh() {
    final List<M> sel = new ArrayList<M>();
    for (M m : selected) {
      int idx = store.indexOf(m);
      if (idx != -1) {
        sel.add(m);
      }
    }
    boolean change = sel.size() != selected.size();
    selected.clear();
    lastSelected = null;
    doSelect(sel, false, true);
    if (change) {
      fireSelectionChange();
    }
  }

  public void removeSelectionListener(SelectionChangedListener listener) {
    removeListener(Events.SelectionChange, listener);
  }

  public void select(int index) {
    select(index, index);
  }

  public void select(int start, int end) {
    List<M> sel = new ArrayList<M>();
    if (start <= end) {
      for (int i = start; i <= end; i++) {
        sel.add(store.getAt(i));
      }
    } else {
      for (int i = start; i >= end; i--) {
        sel.add(store.getAt(i));
      }
    }
    doSelect(sel, false, false);
  }

  public void select(List<M> items) {
    doSelect(items, false, false);
  }

  public void select(M... items) {
    select(Arrays.asList(items));
  }

  public void select(M item) {
    select(Arrays.asList(item));
  }

  public void selectAll() {
    select(store.getModels());
  }

  /**
   * True to lock the selection model. When locked, all selection changes are
   * disabled.
   * 
   * @param locked true to lock
   */
  public void setLocked(boolean locked) {
    this.locked = locked;
  }

  public void setSelection(List<M> selection) {
    select(selection);
  }

  public void setSelectionMode(SelectionMode selectionMode) {
    this.selectionMode = selectionMode;
  }

  protected void doDeselect(List<M> models, boolean supressEvent) {
    if (locked) return;
    boolean change = false;
    for (M m : models) {
      if (isSelected(m)) {
        onSelectChange(m, false);
        selected.remove(m);
        if (lastSelected == m) {
          lastSelected = null;
        }
        change = true;
      }
    }
    if (!supressEvent && change) {
      fireSelectionChange();
    }
  }

  protected void doMultiSelect(List<M> models, boolean keepExisting, boolean supressEvent) {
    if (locked) return;
    boolean change = false;
    if (!keepExisting && selected.size() > 0) {
      change = true;
      doDeselect(new ArrayList<M>(selected), true);
    }
    for (M m : models) {
      SelectionEvent e = new SelectionEvent(this, m);
      e.index = store.indexOf(m);
      if (!fireEvent(Events.BeforeSelect, e)) {
        continue;
      }
      change = true;
      onSelectChange(m, true);
      lastSelected = m;
      selected.add(m);
    }

    if (change && !supressEvent) {
      fireSelectionChange();
    }
  }

  protected void doSelect(List<M> models, boolean keepExisting, boolean supressEvent) {
    if (locked) return;
    if (selectionMode == SelectionMode.SINGLE) {
      M m = models.size() > 0 ? models.get(0) : null;
      if (m != null) {
        doSingleSelect(m, supressEvent);
      }
    } else {
      doMultiSelect(models, keepExisting, supressEvent);
    }
  }

  protected void doSingleSelect(M model, boolean supressEvent) {
    if (locked) return;
    SelectionEvent e = new SelectionEvent(this, model);
    e.index = store.indexOf(model);
    if (!fireEvent(Events.BeforeSelect, e)) {
      return;
    }

    if (isSelected(model)) {
      return;
    }

    boolean change = false;
    if (selected.size() > 0 && !isSelected(model)) {
      doDeselect(Arrays.asList(lastSelected), true);
      change = true;
    }
    if (selected.size() == 0) {
      change = true;
    }
    onSelectChange(model, true);
    selected.add(model);
    lastSelected = model;
    if (change && !supressEvent) {
      fireSelectionChange();
    }
  }

  protected void onAdd(List<M> models) {

  }

  protected void onClear(StoreEvent<M> se) {
    int oldSize = selected.size();
    selected.clear();
    lastSelected = null;
    if (oldSize > 0) fireSelectionChange();
  }

  protected void onRemove(M model) {
    if (locked) return;
    if (isSelected(model)) {
      selected.remove(model);
      if (lastSelected == model) {
        lastSelected = null;
      }
      fireSelectionChange();
    }
  }

  protected abstract void onSelectChange(M model, boolean select);

  private void fireSelectionChange() {
    fireEvent(Events.SelectionChange, new SelectionChangedEvent(this, new ArrayList(selected)));
  }

}
