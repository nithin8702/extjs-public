/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.explorer.client.pages;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.custom.ThemeSelector;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.menu.CheckMenuItem;
import com.extjs.gxt.ui.client.widget.menu.DateMenu;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.menu.SeparatorMenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.AdapterToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.SplitToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.TextToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToggleToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

public class ToolBarPage extends LayoutContainer implements EntryPoint {

  public void onModuleLoad() {
    RootPanel.get().add(this);
  }

  @Override
  protected void onRender(Element parent, int pos) {
    super.onRender(parent, pos);
    
    setLayout(new FlowLayout(10));

    ToolBar toolBar = new ToolBar();

    TextToolItem item1 = new TextToolItem("Button w/ Menu");
    item1.setIconStyle("icon-menu-show");
    
    Menu menu = new Menu();
    CheckMenuItem menuItem = new CheckMenuItem("I Like Cats");
    menuItem.setChecked(true);
    menu.add(menuItem);

    menuItem = new CheckMenuItem("I Like Dogs");
    menu.add(menuItem);
    item1.setMenu(menu);
    
    menu.add(new SeparatorMenuItem());
    
    MenuItem radios = new MenuItem("Radio Options");
    menu.add(radios);
    
    Menu radioMenu = new Menu();
    CheckMenuItem r = new CheckMenuItem("Blue Theme");
    r.setGroup("radios");
    r.setChecked(true);
    radioMenu.add(r);
    r = new CheckMenuItem("Gray Theme");
    r.setGroup("radios");
    radioMenu.add(r);
    r = new CheckMenuItem("Slate Theme");
    r.setGroup("radios");
    radioMenu.add(r);
    radios.setSubMenu(radioMenu);
    
    MenuItem date = new MenuItem("Choose a Date");
    date.setIconStyle("icon-calendar");
    menu.add(date);

    date.setSubMenu(new DateMenu());
    
    toolBar.add(item1);
    
    toolBar.add(new SeparatorToolItem());

    SplitToolItem splitItem = new SplitToolItem("Split Button");
    splitItem.setIconStyle("icon-list-items");

    menu = new Menu();
    menu.add(new MenuItem("<b>Bold</b>"));
    menu.add(new MenuItem("<i>Italic</i>"));
    menu.add(new MenuItem("<u>Underline</u>"));
    splitItem.setMenu(menu);

    toolBar.add(splitItem);

    toolBar.add(new SeparatorToolItem());

    ToggleToolItem toggle = new ToggleToolItem("Toggle");
    toolBar.add(toggle);
    
    toolBar.add(new SeparatorToolItem());
    toolBar.add(new FillToolItem());
    toolBar.add(new AdapterToolItem(new ThemeSelector()));
    
    ContentPanel panel = new ContentPanel();
    panel.setCollapsible(true);
    panel.setFrame(true);
    panel.setHeading("ToolBar & Menu Demo");
    panel.setLayout(new FitLayout());
    panel.setSize(450, 300);
    panel.setTopComponent(toolBar);
    
    LayoutContainer c = new LayoutContainer();
    c.setStyleAttribute("backgroundColor", "white");
    c.setBorders(true);
    panel.add(c);

    add(panel);
  }

}
