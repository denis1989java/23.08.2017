package ru.mail.denis.service.modelDTO;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by user on 27.08.2017.
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
