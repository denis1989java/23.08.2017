package ru.mail.denis.service.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Denis Monich on 27.08.2017.
 */
public class ViewDTO implements Serializable {
    private Map <String,Object> viewMap;

    public ViewDTO() {
    }

    public Map<String, Object> getViewMap() {
        return viewMap;
    }

    public void setViewMap(Map<String, Object> viewMap) {
        this.viewMap = viewMap;
    }
}
