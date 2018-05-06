package com.sms.common.dto;

import java.util.HashMap;
import java.util.List;

public class MapDTO extends HashMap<String, Object> {
    public List<MapDTO> getList(String key) {
        return (List<MapDTO>) this.get(key);
    }

    public MapDTO getMap(String key) {
        return (MapDTO) this.get(key);
    }

    public String getString(String key) {
        return (String) this.get(key);
    }

    public Long getLong(String key) {
        return (Long) this.get(key);
    }

    public Integer getInteger(String key) {
        return (Integer) get(key);
    }

    public Boolean getBoolean(String key) {
        return (Boolean) get(key);
    }
}
