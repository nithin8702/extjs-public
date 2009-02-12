/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.widget.layout;

import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.util.Size;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Container;
import com.extjs.gxt.ui.client.widget.Layout;

/**
 * This is the layout style of choice for creating structural layouts in a
 * multi-column format where the width of each column can be specified as a
 * percentage or fixed width, but the height is allowed to vary based on the
 * content.
 * <p>
 * ColumnLayout supports a ColumnData layout object. The layout will use the
 * width (if pixels) or columnWidth (if percent) of each panel during layout to
 * determine how to size each panel. If width or columnWidth is not specified
 * for a given panel, its width will default to the panel's width (or auto).
 * <p>
 * Code snippet:
 * 
 * <code><pre>
   LayoutContainer container = new LayoutContainer();
   container.setLayout(new ColumnLayout());
   container.add(new Button("100px"), new ColumnData(100));
   container.add(new Button("30%"), new ColumnData(.3));
   container.add(new Button("50px"), new ColumnData(50));
 * </pre></code>
 * 
 * @see ColumnData
 */
public class ColumnLayout extends Layout {

  protected El innerCt;
  protected boolean adjustForScroll = false;

  public ColumnLayout() {
    setExtraStyle("x-column");
  }

  /**
   * Returns true if adjust for scroll is enabled.
   * 
   * @return the adjust for scroll state
   */
  public boolean isAdjustForScroll() {
    return adjustForScroll;
  }

  /**
   * True to adjust the container width calculations to account for the scroll
   * bar (defaults to false).
   * 
   * @param adjustForScroll the adjust for scroll state
   */
  public void setAdjustForScroll(boolean adjustForScroll) {
    this.adjustForScroll = adjustForScroll;
  }

  @Override
  protected void onLayout(Container container, El target) {
    if (innerCt == null) {
      container.addStyleName("x-column-layout-ct");
      innerCt = target.createChild("<div class='x-column-inner'></div>");
      innerCt.createChild("<div class='x-clear'></div>");
    }

    renderAll(container, innerCt);

    Size size = target.getSize(true);

    int w = size.width - (adjustForScroll ? 19 : 0);
    int pw = w;

    int count = container.getItemCount();

    // some columns can be percentages while others are fixed
    // so we need to make 2 passes
    for (int i = 0; i < count; i++) {
      Component c = container.getItem(i);
      ColumnData data = (ColumnData) getLayoutData(c);

      if (data.getWidth() > 1) {
        pw -= data.getWidth();
      }
    }

    pw = pw < 0 ? 0 : pw;

    for (int i = 0; i < count; i++) {
      Component c = container.getItem(i);
      ColumnData data = (ColumnData) getLayoutData(c);
      if (data.getWidth() <= 1) {
        setSize(c, (int) (data.getWidth() * pw), -1);
      } else {
        setSize(c, (int) data.getWidth(), -1);
      }
    }
  }

}
