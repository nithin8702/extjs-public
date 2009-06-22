/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.widget.tree;

import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Style.Direction;
import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.core.Template;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.FxEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TreeEvent;
import com.extjs.gxt.ui.client.fx.FxConfig;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;

public class TreeItemUI {

  public String styleTreeOver = "my-tree-over";
  public String styleTreeJointOver = "my-tree-joint-over";
  public String styleTreeChecked = "my-tree-checked";
  public String styleTreeNotChecked = "my-tree-notchecked";
  public String styleTreeLoading = "my-tree-loading";
  public String styleTreeSelected = "my-tree-sel";
  public String styleTreeItem = "";
  public String classTreeOpen = "my-tree-open";
  public String classTreeClose = "my-tree-close";

  private static Template template;

  static {
    TreeItemTemplateFactory factory = GWT.create(TreeItemTemplateFactory.class);
    template = factory.createTemplate();
  }

  public static Template getTreeTemplate() {
    if (template == null) {
      TreeItemTemplateFactory factory = GWT.create(TreeItemTemplateFactory.class);
      template = factory.createTemplate();
    }
    return template;
  }

  protected String textStyle;
  protected int animDuration = 300;
  protected El containerEl;
  protected Element jointEl, jointDivEl;
  protected TreeItem item;
  protected El itemEl;
  protected Element indentEl;
  protected Element checkEl, checkDivEl;
  protected Element iconEl, iconDivEl;
  protected Element textEl, textSpanEl;

  public TreeItemUI(TreeItem item) {
    this.item = item;
  }

  public void afterCollapse() {
    item.tree.disableEvents(false);
    containerEl.dom.getStyle().setProperty("marginTop", "0");
    itemEl.removeStyleName(styleTreeJointOver);
    item.fireEvent(Events.Collapse, new TreeEvent(item.tree, item));
  }

  public void afterExpand() {
    item.tree.disableEvents(false);
    itemEl.removeStyleName(styleTreeJointOver);
    item.fireEvent(Events.Expand, new TreeEvent(item.tree, item));
  }

  public void animCollapse() {
    containerEl.slideOut(Direction.UP, new FxConfig(animDuration, new Listener<FxEvent>() {
      public void handleEvent(FxEvent fe) {
        afterCollapse();
      }
    }));
    item.tree.disableEvents(true);
  }

  public void animExpand() {
    containerEl.slideIn(Direction.DOWN, new FxConfig(animDuration, new Listener<FxEvent>() {
      public void handleEvent(FxEvent fe) {
        afterExpand();
      }
    }));
    item.tree.disableEvents(true);
  }

  public void collapse() {
    if (item.root) {
      return;
    }
    updateJointStyle();
    onIconStyleChange("");
    if (item.tree.getAnimate()) {
      animCollapse();
    } else {
      containerEl.setVisible(false);
      afterCollapse();
    }
  }

  public void expand() {
    if (item.root) {
      return;
    }
    updateJointStyle();
    if (item.getItemCount() == 0) {
      return;
    }
    onIconStyleChange("");
    if (item.tree.getAnimate()) {
      animExpand();
    } else {
      containerEl.setVisible(true);
      afterExpand();
    }
  }

  public Element getCheckEl() {
    return checkEl;
  }

  public int getIndent() {
    return (item.getDepth() - 1) * item.tree.getIndentWidth();
  }

  public Element getJointEl() {
    return jointEl;
  }

  public void handleEvent(TreeEvent te) {
    int type = te.getEventType();
    Element target = te.getTarget();
    switch (type) {
      case Event.ONCLICK:
      case Event.ONDBLCLICK:
        if (DOM.isOrHasChild(checkEl, target)) {
          te.stopEvent();
          item.setChecked(!item.isChecked());
        } else {
          handleClickEvent(te);
        }
        return;
      case Event.ONMOUSEOVER:
      case Event.ONMOUSEOUT:
        if (DOM.isOrHasChild(jointEl, target)) {
          handleJointEvent(te);
        } else if (DOM.isOrHasChild(iconEl, target) || DOM.isOrHasChild(textEl, target)
            || DOM.isOrHasChild(checkEl, target)) {
          handleMouseEvent(te);
        }
        break;
    }
  }

  public void onCheckChange(boolean checked) {
    String cls = checked ? styleTreeChecked : styleTreeNotChecked;
    checkDivEl.setClassName(cls);
    item.fireEvent(Events.CheckChange, new TreeEvent(item.tree, item));
  }

  public void onClick(TreeEvent te) {
    Element target = te.getTarget();
    if (target != null && te.within(jointEl)) {
      item.toggle();
    }
    te.cancelBubble();
  }

  public void onDoubleClick(ComponentEvent ce) {
    item.toggle();
    ce.cancelBubble();
  }

  public void onIconStyleChange(String style) {
    if (item.iconStyle != null) {
      fly(iconEl).setStyleAttribute("display", "");
      fly(iconDivEl).setIconStyle(item.iconStyle);
      return;
    }
    TreeStyle ts = item.tree.getStyle();
    if (!item.isLeaf()) {
      String s = "";
      if (item.isExpanded() && ts.getNodeOpenIconStyle() != null) {
        s = ts.getNodeOpenIconStyle();
      } else if (item.isExpanded() && ts.getNodeOpenIconStyle() != null) {
        s = ts.getNodeCloseIconStyle();
      } else if (!item.isExpanded()) {
        s = ts.getNodeCloseIconStyle();
      }
      fly(iconEl).setStyleAttribute("display", "");
      iconDivEl.setClassName(s);
    } else {
      fly(iconEl).setStyleAttribute("display", "");
      iconDivEl.setClassName(ts.getLeafIconStyle());
      return;
    }
  }

  public void onLoadingChange(boolean loading) {
    if (loading) {
      item.addStyleName(styleTreeLoading);
    } else {
      item.removeStyleName(styleTreeLoading);
    }
  }

  public void onMouseOut(BaseEvent be) {

  }

  public void onOverChange(boolean over) {
    itemEl.setStyleName(styleTreeOver, over);
  }

  public void onSelectedChange(boolean selected) {
    if (item.isRendered()) {
      itemEl.setStyleName(styleTreeSelected, selected);
      if (!selected) {
        onOverChange(false);
      }
    }
  }

  public void onTextChange(String text) {
    if (!item.root) {
      textSpanEl.setInnerHTML(text);
    }
  }

  public void onTextStyleChange(String style) {
    if (textStyle != null) {
      fly(textSpanEl).removeStyleName(textStyle);
    }
    textStyle = style;
    if (style != null) {
      fly(textSpanEl).addStyleName(style);
    }
  }

  public void removeItem(TreeItem child) {
    containerEl.dom.removeChild(child.getElement());
    if (item.getItemCount() == 0) {
      boolean b = item.tree.getAnimate();
      item.tree.setAnimate(false);
      item.setExpanded(false);
      item.tree.setAnimate(b);
    }
  }

  public void refresh() {
    onIconStyleChange(null);
    updateJointStyle();
  }

  public void update() {
    fly(indentEl).setWidth(getIndent());
  }

  public void render(Element target, int index) {
    if (item.root) return;

    item.setElement(template.create());

    item.el().insertInto(target, index);
    itemEl = item.el().firstChild();
    itemEl.addStyleName(styleTreeItem);

    El el = item.el();

    Element td = el.selectNode("td:first-child").dom;
    indentEl = td.getFirstChildElement().cast();
    jointEl = td.getNextSiblingElement().cast();
    jointDivEl = jointEl.getFirstChild().cast();
    checkEl = jointEl.getNextSiblingElement().getNextSiblingElement().cast();
    checkDivEl = checkEl.getFirstChild().cast();
    iconEl = checkEl.getNextSibling().cast();
    iconDivEl = iconEl.getFirstChild().cast();
    textEl = iconEl.getNextSiblingElement().cast();
    textSpanEl = textEl.getFirstChildElement().cast();
    containerEl = el.getChild(1);
    containerEl.makePositionable();

    if (item.getItemStyleName() != null) {
      item.el().firstChild().addStyleName(item.getItemStyleName());
    }

    fly(checkEl).setVisible(item.tree.getCheckable());

    onTextChange(item.getText());
    onIconStyleChange(item.getIconStyle());

    if (item.getTextStyle() != null) {
      onTextStyleChange(item.getTextStyle());
    }

    if (item.isChecked()) {
      onCheckChange(true);
    }

    fly(indentEl).setWidth(getIndent());

    if (!GXT.isIE) {
      el.dom.setPropertyInt("tabIndex", 0);
    }

    updateJointStyle();
    onIconStyleChange(item.getIconStyle());
    item.disableTextSelection(true);
  }

  public void updateJointStyle() {
    if (item.root) {
      return;
    }

    TreeStyle style = item.tree.getStyle();

    if (!item.isLeaf()) {
      String s = null;
      boolean children = false;
      boolean binder = item.tree != null ? item.tree.getData("binder") != null : false;
      boolean loaded = item.getData("loaded") != null;
      if ((!binder) || (binder && !loaded) || (binder && item.hasChildren())) {
        children = true;
      }
      if (item.isExpanded()) {
        s = children ? (style.getJointOpenIconStyle() != null ? style.getJointOpenIconStyle() : classTreeOpen) : "";
      } else {
        s = children ? (style.getJointCloseIconStyle() != null ? style.getJointCloseIconStyle() : classTreeClose) : "";
      }
      String cls = s;
      jointDivEl.setClassName(cls);
      jointDivEl.getStyle().setProperty("visibility", "visible");
    } else {
      jointDivEl.getStyle().setProperty("visibility", "hidden");
    }

    if (item.tree.getCheckable()) {
      switch (item.tree.getCheckNodes()) {
        case BOTH:
          fly(checkEl).setVisible(true);
          break;
        case PARENT:
          fly(checkEl).setVisible(!item.isLeaf());
          break;
        case LEAF:
          fly(checkEl).setVisible(item.isLeaf());
          break;
      }
    }
  }

  protected El fly(Element elem) {
    return El.fly(elem);
  }

  protected void handleClickEvent(TreeEvent te) {
    TreeItem item = te.item;
    if (te.type == Event.ONCLICK) {
      Element target = te.getTarget();
      if (target != null && te.within(item.getUI().jointEl)) {
        item.toggle();
      }
      te.cancelBubble();
    } else if (te.type == Event.ONDBLCLICK) {
      item.toggle();
    }
  }

  protected void handleJointEvent(TreeEvent ce) {
    switch (ce.getEventType()) {
      case Event.ONMOUSEOVER:
        itemEl.addStyleName(styleTreeJointOver);
        break;
      case Event.ONMOUSEOUT:
        itemEl.removeStyleName(styleTreeJointOver);
        break;
    }
    ce.stopEvent();
  }

  protected void handleMouseEvent(TreeEvent ce) {
    int type = ce.getEventType();
    switch (type) {
      case Event.ONMOUSEOVER:
        onOverChange(true);
        break;
      case Event.ONMOUSEOUT:
        onOverChange(false);
        break;
    }
    ce.cancelBubble();
  }
}
