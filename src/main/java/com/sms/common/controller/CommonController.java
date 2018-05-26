package com.sms.common.controller;

import com.sms.common.dto.ResponseDTO;
import com.sms.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private CommonService commonService;

    @RequestMapping(path = "/getEnums", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getEnums() {
        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setCode(HttpStatus.OK.value());
        responseDTO.setMessage("Success");
        responseDTO.setData(commonService.getEnums());

        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
