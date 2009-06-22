/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.event;

import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.ToolItem;

/**
 * ToolBar event type.
 * 
 * @see ToolBar
 */
public class ToolBarEvent extends ContainerEvent<ToolBar, ToolItem> {

  public ToolBarEvent(ToolBar toolBar, ToolItem item) {
    super(toolBar, item);
  }

  public ToolBarEvent(ToolBar toolBar) {
    super(toolBar);
  }

}