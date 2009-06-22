/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.explorer.client;

import com.extjs.gxt.samples.client.ExampleService;
import com.extjs.gxt.samples.client.ExampleServiceAsync;
import com.extjs.gxt.samples.client.Examples;
import com.extjs.gxt.samples.client.FileService;
import com.extjs.gxt.samples.client.FileServiceAsync;
import com.extjs.gxt.samples.client.examples.model.Entry;
import com.extjs.gxt.samples.explorer.client.mvc.AppController;
import com.extjs.gxt.samples.explorer.client.mvc.ContentController;
import com.extjs.gxt.samples.explorer.client.mvc.NavigationController;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class Explorer implements EntryPoint {

  public static final String MODEL = "model";
  
  private Dispatcher dispatcher;
  private ExplorerModel model;

  public void onModuleLoad() {
    ExampleServiceAsync service = (ExampleServiceAsync) GWT.create(ExampleService.class);
    ServiceDefTarget endpoint = (ServiceDefTarget) service;
    String moduleRelativeURL = GWT.getModuleBaseURL() + "service";
    endpoint.setServiceEntryPoint(moduleRelativeURL);
    Registry.register(Examples.SERVICE, service);

    FileServiceAsync fileservice = (FileServiceAsync) GWT.create(FileService.class);
    endpoint = (ServiceDefTarget) fileservice;
    moduleRelativeURL = GWT.getModuleBaseURL() + "fileservice";
    endpoint.setServiceEntryPoint(moduleRelativeURL);
    Registry.register(Examples.FILE_SERVICE, fileservice);

    model = new ExplorerModel();
    Registry.register(MODEL, model);

    dispatcher = Dispatcher.get();
    dispatcher.addController(new AppController());
    dispatcher.addController(new NavigationController());
    dispatcher.addController(new ContentController());
    dispatcher.dispatch(AppEvents.Init);

    String hash = Window.Location.getHash();

    showPage(model.findEntry("overview"));

    if (!"".equals(hash)) {
      hash = hash.substring(1);
      Entry entry = model.findEntry(hash);
      if (entry != null) {
        showPage(entry);
      }
    }
  }

  public static void showPage(Entry entry) {
    AppEvent<Entry> appEvent = new AppEvent<Entry>(AppEvents.ShowPage, entry);
    appEvent.historyEvent = true;
    appEvent.token = entry.getId();
    Dispatcher.forwardEvent(appEvent);
  }

}
