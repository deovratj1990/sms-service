package com.sms.controller;

import com.sms.EntityNotFoundException;
import com.sms.ValidationException;
import com.sms.dto.MapDTO;
import com.sms.dto.ResponseDTO;
import com.sms.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/room")
@CrossOrigin(origins = "*")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getRooms(@RequestParam(name = "room", required = false) Long roomId, @RequestParam(name = "wing", required = false) Long wingId, @RequestParam(name = "society", required = false) Long societyId) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            if(roomId != null) {
                responseDTO.setData(roomService.getRoomByRoomId(roomId));
            } else if(wingId != null) {
            } else if(societyId != null) {
                MapDTO map = new MapDTO();

                map.put("rooms", roomService.getRoomsBySocietyId(societyId));

                responseDTO.setData(map);
            } else {
                throw new ValidationException("Bad Input.", null);
            }

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
