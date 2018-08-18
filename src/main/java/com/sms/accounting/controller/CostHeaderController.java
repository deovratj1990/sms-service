package com.sms.accounting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sms.accounting.dto.costheader.SaveDTO;
import com.sms.accounting.service.CostHeaderService;
import com.sms.common.dto.ResponseDTO;
import com.sms.common.model.StringKeyMap;
import com.sms.common.validation.ValidationException;

@RestController
@RequestMapping("/accounting/costHeaders")
@CrossOrigin(origins = "*")
public class CostHeaderController {
    @Autowired
    private CostHeaderService costHeaderService;
    
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> readAll() {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
        	StringKeyMap data = new StringKeyMap();
        	
        	data.put("costHeaders", costHeaderService.readAll());
        	
            responseDTO.setCode(HttpStatus.OK.value());
            responseDTO.setMessage("Successful.");
            responseDTO.setData(data);
        } catch(Exception ex) {
            responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(ex.getMessage());
        }

        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
    
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> read(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
        	StringKeyMap data = new StringKeyMap();
        	
        	data.put("costHeader", costHeaderService.read(id));
        	
            responseDTO.setCode(HttpStatus.OK.value());
            responseDTO.setMessage("Successful.");
            responseDTO.setData(data);
        } catch(Exception ex) {
            responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(ex.getMessage());
        }

        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
    
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> create(@RequestBody SaveDTO saveDTO) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            StringKeyMap data = new StringKeyMap();
            
            data.put("costHeader", costHeaderService.create(saveDTO));

            responseDTO.setCode(HttpStatus.CREATED.value());
            responseDTO.setMessage("Cost header created successfully.");
            responseDTO.setData(data);
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
    public ResponseEntity<ResponseDTO> update(@PathVariable("id") Long id, @RequestBody SaveDTO saveDTO) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            StringKeyMap data = new StringKeyMap();
            
            data.put("costHeader", costHeaderService.update(id, saveDTO));

            responseDTO.setCode(HttpStatus.OK.value());
            responseDTO.setMessage("Cost header updated successfully.");
            responseDTO.setData(data);
        } catch(ValidationException ex) {
            responseDTO.setCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setMessage(ex.getMessage());
        } catch(Exception ex) {
            responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(ex.getMessage());
        }

        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
    
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
        	costHeaderService.delete(id);
        	
            responseDTO.setCode(HttpStatus.NO_CONTENT.value());
            responseDTO.setMessage("Successful.");
        } catch(Exception ex) {
            responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(ex.getMessage());
        }

        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
