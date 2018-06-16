package com.sms.accounting.controller;

import com.sms.accounting.dto.transactiondefinition.SaveDTO;
import com.sms.accounting.service.TransactionDefinitionService;
import com.sms.common.dto.ResponseDTO;
import com.sms.common.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/accounting/transactiondef")
@CrossOrigin(origins = "*")
public class TransactionDefinitionController {
    @Autowired
    private TransactionDefinitionService transactionDefinitionService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> create(SaveDTO saveDTO) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            transactionDefinitionService.createTransactionDefinition(saveDTO);

            responseDTO.setCode(HttpStatus.CREATED.value());
            responseDTO.setMessage("Transaction definition created successfully.");
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
            transactionDefinitionService.updateTransactionDefinition(id, saveDTO);

            responseDTO.setCode(HttpStatus.OK.value());
            responseDTO.setMessage("Transaction definition updated successfully.");
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
