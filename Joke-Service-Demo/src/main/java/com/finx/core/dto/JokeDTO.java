package com.finx.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finx.core.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;


public class JokeDTO implements Serializable {
	private Logger LOGGER = Logger.getLogger(JokeDTO.class);
	
    @JsonProperty("categories")
    private List<String> categories = new ArrayList<String>();
    @JsonProperty("created_at")
    private String create_at;
    @JsonProperty("icon_url")
    private String icon_url;
    @JsonProperty("id")
    private String id;
    @JsonProperty("updated_at")
    private String update_at;
    @JsonProperty("url")
    private String url;
    @JsonProperty("value")
    private String value;

    public JokeDTO(){
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    /*
     * Check if the joke value full match keyword
    */
    public boolean isFullMatch(String keyword) {
    	LOGGER.info("keyword >>>" + keyword);
    	if(null != this.value ) {
	     String[] toArray = this.value.split(Constants.SPACE);
	    	for(String word : toArray) {
	    		if(word.equalsIgnoreCase(keyword)) {
	    			return true;
	    		}
	    	}
       }   
    	return false;
   }
}
