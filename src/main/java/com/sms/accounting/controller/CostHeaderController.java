package com.sms.accounting.controller;

import com.sms.accounting.dto.costheader.SaveDTO;
import com.sms.accounting.service.CostHeaderService;
import com.sms.common.validation.ValidationException;
import com.sms.common.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounting/costheader")
@CrossOrigin(origins = "*")
public class CostHeaderController {
    @Autowired
    private CostHeaderService costHeaderService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> add(@RequestBody SaveDTO saveDTO) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            costHeaderService.createCostHeader(saveDTO);

            responseDTO.setCode(HttpStatus.OK.value());
            responseDTO.setMessage("Cost header saved successfully.");
        } catch(ValidationException ex) {
            responseDTO.setCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setMessage(ex.getMessage());
        } catch(Exception ex) {
            responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(ex.getMessage());
        }

        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseDTO> edit(@PathVariable("id") Long id, @RequestBody SaveDTO saveDTO) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            costHeaderService.updateCostHeader(id, saveDTO);

            responseDTO.setCode(HttpStatus.OK.value());
            responseDTO.setMessage("Cost header edited successfully.");
        } catch(ValidationException ex) {
            responseDTO.setCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setMessage(ex.getMessage());
        } catch(Exception ex) {
            responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(ex.getMessage());
        }

        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
