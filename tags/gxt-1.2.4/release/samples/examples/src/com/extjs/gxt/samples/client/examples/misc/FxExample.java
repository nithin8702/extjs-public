/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.client.examples.misc;

import com.extjs.gxt.samples.resources.client.TestData;
import com.extjs.gxt.ui.client.Style.Direction;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.fx.FxConfig;
import com.extjs.gxt.ui.client.util.Rectangle;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;

public class FxExample extends LayoutContainer {

  private ContentPanel cp;

  public FxExample() {

    ButtonBar buttonBar = new ButtonBar();
    buttonBar.add(new Button("Slide In / Out", new SelectionListener<ComponentEvent>() {
      public void componentSelected(ComponentEvent ce) {
        if (cp.isVisible()) {
          cp.el().slideOut(Direction.UP, FxConfig.NONE);
        } else {
          cp.el().slideIn(Direction.DOWN, FxConfig.NONE);
        }
      }
    }));
    buttonBar.add(new Button("Fade In / Out", new SelectionListener<ComponentEvent>() {
      public void componentSelected(ComponentEvent ce) {
        cp.el().fadeToggle(FxConfig.NONE);
      }
    }));
    buttonBar.add(new Button("Move", new SelectionListener<ComponentEvent>() {
      public void componentSelected(ComponentEvent ce) {
        Rectangle rect = cp.el().getBounds();
        cp.el().setXY(rect.x + 50, rect.y + 50, FxConfig.NONE);
      }
    }));

    buttonBar.add(new Button("Blink", new SelectionListener<ComponentEvent>() {
      public void componentSelected(ComponentEvent ce) {
        cp.el().blink(FxConfig.NONE);
      }
    }));

    buttonBar.add(new Button("Reset", new SelectionListener<ComponentEvent>() {
      public void componentSelected(ComponentEvent ce) {
        cp.setPosition(10, 10);
      }
    }));

    buttonBar.setPosition(10, 10);

    cp = new ContentPanel();
    cp.setCollapsible(true);
    cp.setHeading("FX Demo");
    cp.setIconStyle("icon-text");
    cp.setBodyStyleName("pad-text");
    cp.addText(TestData.DUMMY_TEXT_SHORT);
    cp.setPosition(10, 10);
    cp.setWidth(200);

    add(buttonBar);
    add(new Html("<br>"));
    add(cp);
  }

}
