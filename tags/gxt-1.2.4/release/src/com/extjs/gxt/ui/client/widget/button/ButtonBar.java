/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.widget.button;

import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.XDOM;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonBarEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ContainerEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.ComponentHelper;
import com.extjs.gxt.ui.client.widget.Container;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableRowLayout;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.user.client.Element;

/**
 * A horizontal row of buttons.
 * 
 * <dl>
 * <dt><b>Events:</b></dt>
 * <dd><b>Select</b> : ButtonBarEvent(container, item)<br>
 * <div>Fires when a button is selected.</div>
 * <ul>
 * <li>container : the button bar</li>
 * <li>item : the button that was clicked</li>
 * </ul>
 * </dd>
 * 
 * <dd><b>BeforeAdd</b> : ButtonEvent(container, item, index)<br>
 * <div>Fires before a widget is added or inserted. Listeners can set the
 * <code>doit</code> field to <code>false</code> to cancel the action.</div>
 * <ul>
 * <li>container : this</li>
 * <li>item : the widget being added</li>
 * <li>index : the index at which the widget will be added</li>
 * </ul>
 * </dd>
 * 
 * <dd><b>BeforeRemove</b> : ButtonEvent(container, item)<br>
 * <div>Fires before a widget is removed. Listeners can set the
 * <code>doit</code> field to <code>false</code> to cancel the action.</div>
 * <ul>
 * <li>container : this</li>
 * <li>item : the button being removed</li>
 * </ul>
 * </dd>
 * 
 * <dd><b>Add</b> : ButtonEvent(container, item, index)<br>
 * <div>Fires after a widget has been added or inserted.</div>
 * <ul>
 * <li>container : this</li>
 * <li>items : the button that was added</li>
 * <li>index : the index at which the button was added</li>
 * </ul>
 * </dd>
 * 
 * <dd><b>Remove</b> : ButtonEvent(container, item)<br>
 * <div>Fires after a widget has been removed.</div>
 * <ul>
 * <li>container : this</li>
 * <li>item : the button that was removed</li>
 * </ul>
 * </dd>
 * </dl>
 * 
 * <dl>
 * <dt>Inherited Events:</dt>
 * <dd>BoxComponent Move</dd>
 * <dd>BoxComponent Resize</dd>
 * <dd>Component Enable</dd>
 * <dd>Component Disable</dd>
 * <dd>Component BeforeHide</dd>
 * <dd>Component Hide</dd>
 * <dd>Component BeforeShow</dd>
 * <dd>Component Show</dd>
 * <dd>Component Attach</dd>
 * <dd>Component Detach</dd>
 * <dd>Component BeforeRender</dd>
 * <dd>Component Render</dd>
 * <dd>Component BrowserEvent</dd>
 * <dd>Component BeforeStateRestore</dd>
 * <dd>Component StateRestore</dd>
 * <dd>Component BeforeStateSave</dd>
 * <dd>Component SaveState</dd>
 * </dl>
 * 
 * <dt><b>CSS:</b></dt>
 * <dd>.my-btn-bar (the button bar itself)</dd>
 * </dl>
 */
public class ButtonBar extends Container<Button> {

  private int buttonWidth = 75;
  private int cellSpacing = -1;
  private HorizontalAlignment buttonAlign = HorizontalAlignment.LEFT;
  private Button buttonPressed;
  private El inner;
  private Listener listener = new Listener() {
    public void handleEvent(BaseEvent be) {
      if (be instanceof ButtonEvent) {
        ButtonEvent e = (ButtonEvent) be;
        switch (be.type) {
          case Events.BeforeSelect:
            buttonPressed = e.button;
            break;
          case Events.Select:
            onButtonPressed(e);
            break;
        }
      }

    }
  };

  /**
   * Creates a left aligned button bar.
   */
  public ButtonBar() {
    baseStyle = "x-button-bar";
    enableLayout = true;
  }

  /**
   * Adds a button to the bar. Fires the <i>BeforeAdd</i> event before
   * inserting, then fires the <i>Add</i> event after the widget has been
   * inserted.
   * 
   * @param button the button to be added
   * @return true if the button was added
   */
  @Override
  public boolean add(Button button) {
    return insert(button, getItemCount());
  }

  /**
   * Returns the bar's horizontal alignment.
   * 
   * @return the buttonAlign the alignment
   */
  public HorizontalAlignment getButtonAlign() {
    return buttonAlign;
  }

  /**
   * Returns the button with the specified button id.
   * 
   * @param buttonId the button id
   * @return the button or <code>null</code> if no match
   */
  public Button getButtonById(String buttonId) {
    return super.getItemByItemId(buttonId);
  }

  /**
   * Returns the last button that was selected.
   * 
   * @return the last button or <codee>null</code>
   */
  public Button getButtonPressed() {
    return buttonPressed;
  }

  /**
   * @return the buttonWidth
   */
  public int getButtonWidth() {
    return buttonWidth;
  }

  /**
   * Returns the cell spacing.
   * 
   * @return the cell spacing
   */
  public int getCellSpacing() {
    return cellSpacing;
  }

  /**
   * Inserts a button at the specified location. Fires the <i>BeforeAdd</i>
   * event before inserting, then fires the <i>Add</i> event after the widget
   * has been inserted.
   * 
   * @param button the button to be inserted
   * @param index the insert location
   * @return true if the button was added
   */
  @Override
  public boolean insert(Button button, int index) {
    TableData data = new TableData();
    data.setStyle("paddingRight: 4px;");
    if (button instanceof FillButton) {
      data.setWidth("100%");
    }
    ComponentHelper.setLayoutData(button, data);

    if (!(button instanceof ButtonAdapter)) {
      button.setMinWidth(buttonWidth);
    }

    boolean added = super.insert(button, index);
    if (added) {
      button.addListener(Events.BeforeSelect, listener);
      button.addListener(Events.Select, listener);
    }
    return added;
  }

  /**
   * Removes a button from the bar.
   * 
   * @param button the button to be removed
   */
  public boolean remove(Button button) {
    boolean removed = super.remove(button);
    if (removed) {
      button.removeListener(Events.Select, listener);
      button.removeListener(Events.BeforeSelect, listener);
    }
    return removed;
  }

  /**
   * Sets the bar's horizontal alignment.
   * 
   * @param buttonAlign the alignment
   */
  public void setButtonAlign(HorizontalAlignment buttonAlign) {
    assertPreRender();
    this.buttonAlign = buttonAlign;
  }

  /**
   * @param buttonWidth the buttonWidth to set
   */
  public void setButtonWidth(int buttonWidth) {
    assertPreRender();
    this.buttonWidth = buttonWidth;
  }

  /**
   * Sets the cell spacing (pre-render).
   * 
   * @param cellSpacing the cell spacing
   */
  public void setCellSpacing(int cellSpacing) {
    this.cellSpacing = cellSpacing;
  }

  @Override
  protected ContainerEvent createContainerEvent(Button item) {
    return new ButtonBarEvent(this, item);
  }

  @Override
  protected El getLayoutTarget() {
    return inner;
  }

  protected void onButtonPressed(ButtonEvent be) {
    Button btn = be.button;
    buttonPressed = btn;
    fireEvent(Events.Select, new ButtonBarEvent(this, btn));
  }

  @Override
  protected void onDisable() {
    super.onDisable();
    for (int i = 0; i < getItemCount(); i++) {
      getItem(i).disable();
    }
  }

  @Override
  protected void onEnable() {
    super.onEnable();
    for (int i = 0; i < getItemCount(); i++) {
      getItem(i).enable();
    }
  }

  @Override
  protected void onRender(Element target, int index) {
    super.onRender(target, index);

    String align = "left";
    if (buttonAlign != null) {
      align = buttonAlign.name().toLowerCase();
    }

    StringBuffer sb = new StringBuffer();
    sb.append("<div class=x-panel-btn-ct>");
    sb.append("<table width=100% cellpadding=100 cellspacing=0><tr><td class=inner-cell align="
        + align + "></td></tr></table>");

    setElement(XDOM.create(sb.toString()), target, index);

    inner = el().selectNode(".inner-cell");

    TableElement tbl = el().selectNode("table").dom.cast();
    if (cellSpacing != -1) {
      tbl.setCellSpacing(cellSpacing);
    }

    TableRowLayout layout = new TableRowLayout();
    setLayout(layout);
    layout();
  }

}
