/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.client.examples.forms;

import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.DataField;
import com.extjs.gxt.ui.client.data.JsonReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelType;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.ScriptTagProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.ComboBox;

public class AdvancedComboBoxExample extends LayoutContainer {

  public AdvancedComboBoxExample() {
    String url = "http://extjs.com/forum/topics-remote.php";
    ScriptTagProxy<Object, PagingLoadResult<ModelData>> proxy = new ScriptTagProxy<Object, PagingLoadResult<ModelData>>(
        url);

    ModelType type = new ModelType();
    type.root = "topics";
    type.totalName = "totalCount";
    type.addField("title", "topic_title");
    type.addField("topicId", "topic_id");
    type.addField("author", "author");
    type.addField("excerpt", "post_text");

    DataField date = new DataField("lastPost", "post_time");
    date.type = Date.class;
    date.format = "timestamp";
    type.addField(date);

    JsonReader<PagingLoadConfig> reader = new JsonReader<PagingLoadConfig>(type) {
      @Override
      protected ListLoadResult newLoadResult(PagingLoadConfig loadConfig, List<ModelData> models) {
        PagingLoadResult result = new BasePagingLoadResult(models, loadConfig.getOffset(),
            loadConfig.getLimit());
        return result;
      }
    };

    PagingLoader loader = new BasePagingLoader(proxy, reader);

    ListStore<ModelData> store = new ListStore<ModelData>(loader);

    ComboBox<ModelData> combo = new ComboBox<ModelData>();
    combo.setWidth(580);
    combo.setDisplayField("title");
    combo.setItemSelector("div.search-item");
    combo.setTemplate(getTemplate());
    combo.setStore(store);
    combo.setHideTrigger(true);
    combo.setPageSize(10);

    VerticalPanel vp = new VerticalPanel();
    vp.setSpacing(10);

    vp.addText("<span class='text'><b>Combo with Templates and Ajax</b><br>This is a more advanced example that shows how you can combine paging, XTemplate and a remote data store to create a 'live search' feature.</span>");
    vp.add(combo);

    add(vp);
  }

  private native String getTemplate() /*-{
   return [
     '<tpl for="."><div class="search-item">',
     '<h3><span>{lastPost:date("MM/dd/y")}<br />by {author}</span>{title}</h3>',
     '{excerpt}',
     '</div></tpl>'
   ].join("");
   }-*/;
}
