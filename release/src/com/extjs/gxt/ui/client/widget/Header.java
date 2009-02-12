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

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.widget.button.IconButton;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * A custom component that supports an icon, text, and tool area. Used by {@link ContentPanel}.
 */
public class Header extends Component {

  protected IconButton iconBtn;

  private String textStyle;
  private El textEl;
  private List<Component> tools = new ArrayList<Component>();
  private HorizontalPanel widgetPanel;
  private String text, iconStyle;

  /**
   * Adds a tool.
   *
   * @param tool the tool to be inserted
   */
  public void addTool(Component tool) {
    insertTool(tool, getToolCount());
  }

  public IconButton getIcon() {
    if (iconBtn == null) {
      iconBtn = new IconButton();
    }
    return iconBtn;
  }

  /**
   * Returns the header's icon style.
   *
   * @return the icon style
   */
  public String getIconStyle() {
    return iconStyle;
  }

  /**
   * Returns the header's text.
   *
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * Returns the item's text style.
   *
   * @return the textStyle the text style
   */
  public String getTextStyle() {
    return textStyle;
  }

  /**
   * Returns the tool at the given index.
   *
   * @param index the index
   * @return the tool
   */
  public Component getTool(int index) {
    return tools.get(index);
  }

  /**
   * Returns the number of tool items.
   *
   * @return the count
   */
  public int getToolCount() {
    return tools.size();
  }

  /**
   * Inserts a tool.
   *
   * @param tool the tool to insert
   * @param index the insert location
   */
  public void insertTool(Component tool, int index) {
    tools.add(index, tool);
    if (rendered) {
      widgetPanel.insert((Widget) tool, index);
    }
  }

  /**
   * Removes the header's icon style.
   */
  public void removeIconStyle() {
    this.iconStyle = "";
    if (rendered) {
      textEl.removeStyleName("x-panel-icon");
      iconBtn.setVisible(false);
    }
  }

  /**
   * Removes a tool.
   *
   * @param tool the tool to remove
   */
  public void removeTool(Component tool) {
    tools.remove(tool);
    if (rendered) {
      widgetPanel.remove(tool);
    }
  }

  /**
   * Sets the header's icon style. The style name should match a CSS style that
   * specifies a background image using the following format:
   *
   * <pre><code>
   * .my-icon {
   *    background: url(images/icons/my-icon.png) no-repeat center left !important;
   * }
   * </code></pre>
   *
   * @param iconStyle the icon style
   */
  public void setIconStyle(String iconStyle) {
    this.iconStyle = iconStyle;
    if (rendered) {
      iconBtn.setVisible(true);
      iconBtn.changeStyle(iconStyle);
    }
  }

  /**
   * Sets the header's text.
   *
   * @param text the new text
   */
  public void setText(String text) {
    this.text = text;
    if (rendered) {
      textEl.dom.setInnerHTML(text);
    }
  }

  /**
   * Sets the style name added to the header's text element.
   *
   * @param textStyle the text style
   */
  public void setTextStyle(String textStyle) {
    this.textStyle = textStyle;
    if (rendered) {
      textEl.dom.setClassName(textStyle);
    }
  }

  @Override
  protected void doAttachChildren() {
    ComponentHelper.doAttach(iconBtn);
    ComponentHelper.doAttach(widgetPanel);
  }

  @Override
  protected void doDetachChildren() {
    ComponentHelper.doDetach(iconBtn);
    ComponentHelper.doDetach(widgetPanel);
  }

  @Override
  protected void onRender(Element target, int index) {
    super.onRender(target, index);
    setElement(DOM.createDiv(), target, index);
    addStyleName("x-small-editor");

    if (iconBtn == null) {
      iconBtn = new IconButton();
    }
    iconBtn.setVisible(false);
    iconBtn.addStyleName("x-panel-inline-icon");
    iconBtn.setStyleAttribute("cursor", "default");
    iconBtn.setStyleAttribute(GXT.isIE ? "styleFloat":"cssFloat", "left");
    iconBtn.render(el().dom);
    widgetPanel = new HorizontalPanel();
    widgetPanel.setStyleName("x-panel-toolbar");
    widgetPanel.setLayoutOnChange(true);
    widgetPanel.setStyleAttribute("float", "right");

    if (tools.size() > 0) {
      for (int i = 0; i < tools.size(); i++) {
        widgetPanel.add(tools.get(i));
      }
    }

    widgetPanel.render(getElement());

    textEl = new El(DOM.createSpan());
    getElement().appendChild(textEl.dom);

    if (textStyle != null) {
      setTextStyle(textStyle);
    }

    if (text != null) {
      setText(text);
    } else {
      setText("&#160;");
    }

    if (iconStyle != null) {
      setIconStyle(iconStyle);
    }
  }

}
