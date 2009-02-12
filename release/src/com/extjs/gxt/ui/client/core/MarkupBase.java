/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.core;

import com.extjs.gxt.ui.client.GXT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class MarkupBase implements Markup {

  static {
    GXT.init();
  }

  private Element rootElement;

  public String getHtml() {
    return html;
  }

  private String html;

  /**
   * for use by the generator only
   */
  public void init(String html, Element rootElement) {
    this.html = html;
    this.rootElement = rootElement;
  }

  public Element select(String selector) {
    assert rootElement != null : "rootElement is null";
    return DomQuery.selectNode(selector, rootElement);
  }

  public Element getRootElement() {
    return rootElement;
  }

  public static Element createRootElement(String html) {
    Element rootElement = DOM.createDiv();
    rootElement.setInnerHTML(html);
    if (rootElement.getFirstChild() != null) {
      rootElement = rootElement.getFirstChildElement().cast();
    }
    return rootElement;
  }

}
