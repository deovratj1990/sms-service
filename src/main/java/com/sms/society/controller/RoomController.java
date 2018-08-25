package com.sms.society.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sms.common.dto.ResponseDTO;
import com.sms.common.model.StringKeyMap;
import com.sms.common.validation.ValidationException;
import com.sms.society.service.RoomService;

@RestController
@RequestMapping(path = "/rooms")
@CrossOrigin(origins = "*")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @RequestMapping(path = "/{roomId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> read(@PathVariable Long roomId) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
    		responseDTO.setData(roomService.getById(roomId));
    		
    		responseDTO.setCode(HttpStatus.OK.value());
            responseDTO.setMessage("Success.");
        } catch(ValidationException ex) {
            responseDTO.setCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setMessage(ex.getMessage());
        } catch(EntityNotFoundException ex) {
            responseDTO.setCode(HttpStatus.NO_CONTENT.value());
            responseDTO.setMessage(ex.getMessage());
        } catch(Exception ex) {
            responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(ex.getMessage());
        }

        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
    
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> readAll(@RequestParam(required = false) Long societyId) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
        	StringKeyMap map = new StringKeyMap();

            if(societyId != null) {
            	map.put("rooms", roomService.getBySocietyId(societyId));
            } else {
            	map.put("rooms", roomService.getAll());
            }

            responseDTO.setData(map);

            responseDTO.setCode(HttpStatus.OK.value());
            responseDTO.setMessage("Success.");
        } catch(ValidationException ex) {
            responseDTO.setCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setMessage(ex.getMessage());
        } catch(EntityNotFoundException ex) {
            responseDTO.setCode(HttpStatus.NO_CONTENT.value());
            responseDTO.setMessage(ex.getMessage());
        } catch(Exception ex) {
            responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(ex.getMessage());
        }

        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
