package com.sms.common.service.impl;

import org.springframework.stereotype.Service;

import com.sms.accounting.entity.Account;
import com.sms.accounting.entity.TransactionDefinition;
import com.sms.common.model.StringKeyMap;
import com.sms.common.service.CommonService;
import com.sms.user.entity.Access;

@Service
public class CommonServiceImpl implements CommonService {
    @Override
    public StringKeyMap getEnums() {
        StringKeyMap map = new StringKeyMap();

        map.put("roles", Access.Role.values());
        map.put("accountTypes", Account.Type.values());
        map.put("intervals", TransactionDefinition.Interval.values());

        return map;
    }
}
