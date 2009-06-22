/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.client.examples.layouts;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Padding;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowData;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayoutData;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;

public class HBoxLayoutExample extends LayoutContainer {
  private ContentPanel lcwest;
  private ContentPanel lccenter;

  private String button1Text = "Button 1";
  private String button2Text = "Button 2";
  private String button3Text = "Button 3";
  private String button4Text = "Button<br />Big 4";

  public HBoxLayoutExample() {
    setScrollMode(Scroll.AUTO);
    ContentPanel panel = new ContentPanel();
    panel.setHeading("HorizontalBox Example");
    panel.setSize(600, 500);
    panel.setLayout(new BorderLayout());

    lcwest = new ContentPanel();
    lcwest.setHeaderVisible(false);
    VBoxLayout westLayout = new VBoxLayout();
    westLayout.setPadding(new Padding(5));
    westLayout.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);
    lcwest.setLayout(westLayout);
    BorderLayoutData west = new BorderLayoutData(LayoutRegion.WEST, 150, 100, 250);
    west.setMargins(new Margins(5));
    west.setSplit(true);

    panel.add(lcwest, west);

    lccenter = new ContentPanel();
    lccenter.setHeaderVisible(false);
    lccenter.setLayout(new FitLayout());
    lccenter.add(new Html(
        "<p style=\"padding:10px;color:#556677;font-size:11px;\">Select a configuration on the left</p>"));
    BorderLayoutData center = new BorderLayoutData(LayoutRegion.CENTER);
    center.setMargins(new Margins(5));
    panel.add(lccenter, center);

    VBoxLayoutData vBoxData = new VBoxLayoutData(5, 5, 5, 5);
    vBoxData.setFlex(1);

    lcwest.add(createToggleButton("Spaced", new Listener<ButtonEvent>() {

      public void handleEvent(ButtonEvent ce) {
        if (!ce.<ToggleButton> getComponent().isPressed()) {
          return;
        }

        LayoutContainer c = new LayoutContainer();
        HBoxLayout layout = new HBoxLayout();
        layout.setPadding(new Padding(5));
        layout.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);
        c.setLayout(layout);

        c.add(new Button(button1Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        HBoxLayoutData flex = new HBoxLayoutData(new Margins(0, 5, 0, 0));
        flex.setFlex(1);
        c.add(new Text(), flex);
        c.add(new Button(button2Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Button(button3Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Button(button4Text), new HBoxLayoutData(new Margins(0)));

        addToCenter(c);
      }

    }), vBoxData);

    lcwest.add(createToggleButton("Multi-Spaced", new Listener<ButtonEvent>() {

      public void handleEvent(ButtonEvent ce) {
        if (!ce.<ToggleButton> getComponent().isPressed()) {
          return;
        }
        LayoutContainer c = new LayoutContainer();
        HBoxLayout layout = new HBoxLayout();
        layout.setPadding(new Padding(5));
        layout.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);
        c.setLayout(layout);

        c.add(new Button(button1Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        HBoxLayoutData flex = new HBoxLayoutData(new Margins(0, 5, 0, 0));
        flex.setFlex(1);
        c.add(new Text(), flex);
        c.add(new Button(button2Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Text(), flex);
        c.add(new Button(button3Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Text(), flex);
        c.add(new Button(button4Text), new HBoxLayoutData(new Margins(0)));

        addToCenter(c);
      }

    }), vBoxData);

    lcwest.add(createToggleButton("Align: top", new Listener<ButtonEvent>() {

      public void handleEvent(ButtonEvent ce) {
        if (!ce.<ToggleButton> getComponent().isPressed()) {
          return;
        }
        LayoutContainer c = new LayoutContainer();
        HBoxLayout layout = new HBoxLayout();
        layout.setPadding(new Padding(5));
        layout.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);
        c.setLayout(layout);

        c.add(new Button(button1Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Button(button2Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Button(button3Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Button(button4Text), new HBoxLayoutData(new Margins(0)));

        addToCenter(c);
      }

    }), vBoxData);

    lcwest.add(createToggleButton("Align: middle", new Listener<ButtonEvent>() {

      public void handleEvent(ButtonEvent ce) {
        if (!ce.<ToggleButton> getComponent().isPressed()) {
          return;
        }
        LayoutContainer c = new LayoutContainer();
        HBoxLayout layout = new HBoxLayout();
        layout.setPadding(new Padding(5));
        layout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
        c.setLayout(layout);

        c.add(new Button(button1Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Button(button2Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Button(button3Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Button(button4Text), new HBoxLayoutData(new Margins(0)));

        addToCenter(c);
      }

    }), vBoxData);

    lcwest.add(createToggleButton("Align: bottom", new Listener<ButtonEvent>() {

      public void handleEvent(ButtonEvent ce) {
        if (!ce.<ToggleButton> getComponent().isPressed()) {
          return;
        }
        LayoutContainer c = new LayoutContainer();
        HBoxLayout layout = new HBoxLayout();
        layout.setPadding(new Padding(5));
        layout.setHBoxLayoutAlign(HBoxLayoutAlign.BOTTOM);
        c.setLayout(layout);

        c.add(new Button(button1Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Button(button2Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Button(button3Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Button(button4Text), new HBoxLayoutData(new Margins(0)));

        addToCenter(c);
      }

    }), vBoxData);

    lcwest.add(createToggleButton("Align: stretch", new Listener<ButtonEvent>() {

      public void handleEvent(ButtonEvent ce) {
        if (!ce.<ToggleButton> getComponent().isPressed()) {
          return;
        }
        LayoutContainer c = new LayoutContainer();
        HBoxLayout layout = new HBoxLayout();
        layout.setPadding(new Padding(5));
        layout.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCH);
        c.setLayout(layout);

        c.add(new Button(button1Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Button(button2Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Button(button3Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Button(button4Text), new HBoxLayoutData(new Margins(0)));

        addToCenter(c);
      }

    }), vBoxData);

    lcwest.add(createToggleButton("Align: stretchmax", new Listener<ButtonEvent>() {

      public void handleEvent(ButtonEvent ce) {
        if (!ce.<ToggleButton> getComponent().isPressed()) {
          return;
        }
        LayoutContainer c = new LayoutContainer();
        HBoxLayout layout = new HBoxLayout();
        layout.setPadding(new Padding(5));
        layout.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCHMAX);
        c.setLayout(layout);

        c.add(new Button(button1Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Button(button2Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Button(button3Text), new HBoxLayoutData(new Margins(0, 5, 0, 0)));
        c.add(new Button(button4Text), new HBoxLayoutData(new Margins(0)));

        addToCenter(c);
      }

    }), vBoxData);

    lcwest.add(createToggleButton("Flex: All even", new Listener<ButtonEvent>() {

      public void handleEvent(ButtonEvent ce) {
        if (!ce.<ToggleButton> getComponent().isPressed()) {
          return;
        }
        LayoutContainer c = new LayoutContainer();
        HBoxLayout layout = new HBoxLayout();
        layout.setPadding(new Padding(5));
        layout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
        c.setLayout(layout);

        HBoxLayoutData flex = new HBoxLayoutData(new Margins(0, 5, 0, 0));
        flex.setFlex(1);
        c.add(new Button(button1Text), flex);
        c.add(new Button(button2Text), flex);
        c.add(new Button(button3Text), flex);

        HBoxLayoutData flex2 = new HBoxLayoutData(new Margins(0));
        flex2.setFlex(1);
        c.add(new Button(button4Text), flex2);

        addToCenter(c);
      }

    }), vBoxData);

    lcwest.add(createToggleButton("Flex: ratio", new Listener<ButtonEvent>() {

      public void handleEvent(ButtonEvent ce) {
        if (!ce.<ToggleButton> getComponent().isPressed()) {
          return;
        }
        LayoutContainer c = new LayoutContainer();
        HBoxLayout layout = new HBoxLayout();
        layout.setPadding(new Padding(5));
        layout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
        c.setLayout(layout);

        HBoxLayoutData flex = new HBoxLayoutData(new Margins(0, 5, 0, 0));
        flex.setFlex(1);
        c.add(new Button(button1Text), flex);
        c.add(new Button(button2Text), flex);
        c.add(new Button(button3Text), flex);

        HBoxLayoutData flex2 = new HBoxLayoutData(new Margins(0));
        flex2.setFlex(3);
        c.add(new Button(button4Text), flex2);

        addToCenter(c);
      }

    }), vBoxData);

    lcwest.add(createToggleButton("Flex + Stretch", new Listener<ButtonEvent>() {

      public void handleEvent(ButtonEvent ce) {
        if (!ce.<ToggleButton> getComponent().isPressed()) {
          return;
        }
        LayoutContainer c = new LayoutContainer();
        HBoxLayout layout = new HBoxLayout();
        layout.setPadding(new Padding(5));
        layout.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCH);
        c.setLayout(layout);

        HBoxLayoutData flex = new HBoxLayoutData(new Margins(0, 5, 0, 0));
        flex.setFlex(1);
        c.add(new Button(button1Text), flex);
        c.add(new Button(button2Text), flex);
        c.add(new Button(button3Text), flex);

        HBoxLayoutData flex2 = new HBoxLayoutData(new Margins(0));
        flex2.setFlex(3);
        c.add(new Button(button4Text), flex2);

        addToCenter(c);
      }

    }), vBoxData);

    lcwest.add(createToggleButton("Pack: start", new Listener<ButtonEvent>() {

      public void handleEvent(ButtonEvent ce) {
        if (!ce.<ToggleButton> getComponent().isPressed()) {
          return;
        }
        LayoutContainer c = new LayoutContainer();
        HBoxLayout layout = new HBoxLayout();
        layout.setPadding(new Padding(5));
        layout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
        layout.setPack(BoxLayoutPack.START);
        c.setLayout(layout);

        HBoxLayoutData layoutData = new HBoxLayoutData(new Margins(0, 5, 0, 0));
        c.add(new Button(button1Text), layoutData);
        c.add(new Button(button2Text), layoutData);
        c.add(new Button(button3Text), layoutData);

        HBoxLayoutData layoutData2 = new HBoxLayoutData(new Margins(0));
        c.add(new Button(button4Text), layoutData2);

        addToCenter(c);
      }

    }), vBoxData);

    lcwest.add(createToggleButton("Pack: center", new Listener<ButtonEvent>() {

      public void handleEvent(ButtonEvent ce) {
        if (!ce.<ToggleButton> getComponent().isPressed()) {
          return;
        }
        LayoutContainer c = new LayoutContainer();
        HBoxLayout layout = new HBoxLayout();
        layout.setPadding(new Padding(5));
        layout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
        layout.setPack(BoxLayoutPack.CENTER);
        c.setLayout(layout);

        HBoxLayoutData layoutData = new HBoxLayoutData(new Margins(0, 5, 0, 0));
        c.add(new Button(button1Text), layoutData);
        c.add(new Button(button2Text), layoutData);
        c.add(new Button(button3Text), layoutData);

        HBoxLayoutData layoutData2 = new HBoxLayoutData(new Margins(0));
        c.add(new Button(button4Text), layoutData2);

        addToCenter(c);
      }

    }), vBoxData);

    lcwest.add(createToggleButton("Pack: end", new Listener<ButtonEvent>() {

      public void handleEvent(ButtonEvent ce) {
        if (!ce.<ToggleButton> getComponent().isPressed()) {
          return;
        }
        LayoutContainer c = new LayoutContainer();
        HBoxLayout layout = new HBoxLayout();
        layout.setPadding(new Padding(5));
        layout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
        layout.setPack(BoxLayoutPack.END);
        c.setLayout(layout);

        HBoxLayoutData layoutData = new HBoxLayoutData(new Margins(0, 5, 0, 0));
        c.add(new Button(button1Text), layoutData);
        c.add(new Button(button2Text), layoutData);
        c.add(new Button(button3Text), layoutData);

        HBoxLayoutData layoutData2 = new HBoxLayoutData(new Margins(0));
        c.add(new Button(button4Text), layoutData2);

        addToCenter(c);
      }

    }), vBoxData);

    add(panel, new FlowData(10));

  }

  private void addToCenter(LayoutContainer c) {
    lccenter.removeAll();
    lccenter.add(c);
    lccenter.layout();
  }

  private ToggleButton createToggleButton(String name, Listener<ButtonEvent> l) {
    ToggleButton button = new ToggleButton(name);
    button.setToggleGroup("vboxlayoutbuttons");
    button.addListener(Events.Toggle, l);
    button.setAllowDepress(false);
    return button;
  }
}
