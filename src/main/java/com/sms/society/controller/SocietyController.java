package com.sms.society.controller;

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

import com.sms.common.dto.ResponseDTO;
import com.sms.common.model.StringKeyMap;
import com.sms.society.dto.society.RegisterDTO;
import com.sms.society.service.SocietyService;

@RestController
@RequestMapping(path = "/society")
@CrossOrigin(origins = "*")
public class SocietyController {
    @Autowired
    private SocietyService societyService;
    
    @RequestMapping(path = "/getById/{societyId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long societyId) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            StringKeyMap map = new StringKeyMap();

            map.put("society", societyService.getById(societyId));

            responseDTO.setData(map);

            responseDTO.setCode(HttpStatus.OK.value());
            responseDTO.setMessage("Success.");
        } catch(EntityNotFoundException ex) {
            responseDTO.setCode(HttpStatus.NO_CONTENT.value());
            responseDTO.setMessage(ex.getMessage());
        } catch(Exception ex) {
            responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(ex.getMessage());
        }

        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(path = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getAll() {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            StringKeyMap map = new StringKeyMap();

            map.put("societies", societyService.getAll());

            responseDTO.setData(map);

            responseDTO.setCode(HttpStatus.OK.value());
            responseDTO.setMessage("Success.");
        } catch(EntityNotFoundException ex) {
            responseDTO.setCode(HttpStatus.NO_CONTENT.value());
            responseDTO.setMessage(ex.getMessage());
        } catch(Exception ex) {
            responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(ex.getMessage());
        }

        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> registerSociety(@RequestBody RegisterDTO registerDTO) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            societyService.register(registerDTO);

            responseDTO.setCode(HttpStatus.OK.value());
            responseDTO.setMessage("Registration successful. Your account will be activated within 24-72 hours.");
        } catch(Exception ex) {
            responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(ex.getMessage());
        }

        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
