package com.sms.common.model;

import java.util.HashMap;
import java.util.List;

public class StringKeyMap extends HashMap<String, Object> {
    public List<StringKeyMap> getList(String key) {
        return (List<StringKeyMap>) this.get(key);
    }

    public StringKeyMap getMap(String key) {
        return (StringKeyMap) this.get(key);
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
