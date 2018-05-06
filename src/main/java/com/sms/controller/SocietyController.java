package com.sms.controller;

import com.sms.EntityNotFoundException;
import com.sms.dto.MapDTO;
import com.sms.dto.ResponseDTO;
import com.sms.service.SocietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/society")
@CrossOrigin(origins = "*")
public class SocietyController {
    @Autowired
    private SocietyService societyService;

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getSocieties(@RequestParam(name = "society", required = false) Long societyId, @RequestParam(name = "locality", required = false) Long localityId, @RequestParam(name = "pincode", required = false) Long pincodeId, @RequestParam(name = "area", required = false) Long areaId, @RequestParam(name = "city", required = false) Long cityId, @RequestParam(name = "state", required = false) Long stateId, @RequestParam(name = "country", required = false) Long countryId) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            if(societyId != null) {
                responseDTO.setData(societyService.getSocietyById(societyId));
            } else if(localityId != null) {
            } else if(pincodeId != null) {
            } else if(areaId != null) {
            } else if(cityId != null) {
            } else if(stateId != null) {
            } else if(countryId != null) {
            } else {
                MapDTO map = new MapDTO();

                map.put("societies", societyService.getAllSocieties());

                responseDTO.setData(map);
            }

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
}
