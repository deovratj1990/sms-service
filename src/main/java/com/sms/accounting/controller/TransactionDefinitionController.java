package com.sms.accounting.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sms.accounting.dto.transactiondefinition.SaveDTO;
import com.sms.accounting.service.TransactionDefinitionService;
import com.sms.common.dto.ResponseDTO;
import com.sms.common.model.StringKeyMap;
import com.sms.common.validation.ValidationException;

@RestController
@RequestMapping("/accounting/transactionDefinitions")
@CrossOrigin(origins = "*")
public class TransactionDefinitionController {
    @Autowired
    private TransactionDefinitionService transactionDefinitionService;
    
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> readAll() {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
        	StringKeyMap data = new StringKeyMap();
        	
        	data.put("transactionDefinitions", transactionDefinitionService.readAll());
        	
            responseDTO.setCode(HttpStatus.CREATED.value());
            responseDTO.setMessage("Transaction definition created successfully.");
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
    
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> read(@PathVariable("id") Long id) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
        	StringKeyMap data = new StringKeyMap();
        	
        	data.put("transactionDefinition", transactionDefinitionService.read(id));
        	
            responseDTO.setCode(HttpStatus.CREATED.value());
            responseDTO.setMessage("Transaction definition created successfully.");
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

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> create(SaveDTO saveDTO) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
        	StringKeyMap data = new StringKeyMap();
        	
        	data.put("transactionDefinition", transactionDefinitionService.create(saveDTO));
        	
            responseDTO.setCode(HttpStatus.CREATED.value());
            responseDTO.setMessage("Transaction definition created successfully.");
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
        	
        	data.put("transactionDefinition", transactionDefinitionService.update(id, saveDTO));
        	
            responseDTO.setCode(HttpStatus.OK.value());
            responseDTO.setMessage("Transaction definition updated successfully.");
            responseDTO.setData(data);
        } catch(ValidationException ex) {
            responseDTO.setCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setMessage(ex.getMessage());
        } catch(EntityNotFoundException ex) {
            responseDTO.setCode(HttpStatus.NOT_FOUND.value());
            responseDTO.setMessage(ex.getMessage());
        } catch(Exception ex) {
            responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(ex.getMessage());
        }

        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
    
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseDTO> delete(@PathVariable("id") Long id) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
        	transactionDefinitionService.delete(id);
        	
            responseDTO.setCode(HttpStatus.OK.value());
            responseDTO.setMessage("Transaction definition deleted successfully.");
        } catch(ValidationException ex) {
            responseDTO.setCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setMessage(ex.getMessage());
        } catch(EntityNotFoundException ex) {
            responseDTO.setCode(HttpStatus.NOT_FOUND.value());
            responseDTO.setMessage(ex.getMessage());
        } catch(Exception ex) {
            responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(ex.getMessage());
        }

        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
