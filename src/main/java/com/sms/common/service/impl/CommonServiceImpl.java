package com.sms.common.service.impl;

import com.sms.accounting.entity.TransactionDefinition;
import com.sms.common.dto.MapDTO;
import com.sms.common.service.CommonService;
import com.sms.user.entity.Access;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {
    @Override
    public MapDTO getEnums() {
        MapDTO map = new MapDTO();

        map.put("roles", Access.Role.values());
        map.put("accountTypes", TransactionDefinition.AccountType.values());
        map.put("intervals", TransactionDefinition.Interval.values());

        return map;
    }
}
