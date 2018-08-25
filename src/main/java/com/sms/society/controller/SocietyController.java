package com.sms.society.controller;

import java.util.Set;

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

import com.sms.common.InvalidEntityException;
import com.sms.common.dto.ResponseDTO;
import com.sms.common.model.StringKeyMap;
import com.sms.common.model.Token;
import com.sms.common.service.SecurityService;
import com.sms.common.validation.ValidationException;
import com.sms.society.dto.society.RegisterDTO;
import com.sms.society.service.SocietyService;

@RestController
@RequestMapping(path = "/societies")
@CrossOrigin(origins = "*")
public class SocietyController {
	@Autowired
    private SecurityService securityService;
	
    @Autowired
    private SocietyService societyService;
    
    @RequestMapping(path = "/{societyId}", method = RequestMethod.GET)
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

    @RequestMapping(path = "", method = RequestMethod.GET)
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

    @RequestMapping(path = "", method = RequestMethod.POST)
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
    
    @RequestMapping(path = "/getRegistrations", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getRegistrations() {
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			Token token = securityService.getRequestToken();
			
			Set<StringKeyMap> registrations = societyService.getRegistrationsBySocietyId(token.getSocietyId());

			responseDTO.setCode(HttpStatus.OK.value());

			responseDTO.setMessage("Success");

			responseDTO.addData("rooms", registrations);
		} catch(ValidationException ex) {
			responseDTO.setCode(HttpStatus.BAD_REQUEST.value());

			responseDTO.setMessage(ex.getMessage());

			responseDTO.setData(ex.getValidationResult().getMessages());
		} catch(EntityNotFoundException ex) {
			responseDTO.setCode(HttpStatus.UNAUTHORIZED.value());

			responseDTO.setMessage(ex.getMessage());
		} catch(InvalidEntityException ex) {
			responseDTO.setCode(HttpStatus.FORBIDDEN.value());

			responseDTO.setMessage(ex.getMessage());
		} catch(Exception ex) {
			responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

			responseDTO.setMessage(ex.getMessage());
		}

		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
    
    @RequestMapping(path = "/activateRegistration/{residentRoomId}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO> activateRegistration(@PathVariable Long residentRoomId) {
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			societyService.activateRegistration(residentRoomId);

			responseDTO.setCode(HttpStatus.OK.value());

			responseDTO.setMessage("Registration activated successfully!");
		} catch(ValidationException ex) {
			responseDTO.setCode(HttpStatus.BAD_REQUEST.value());

			responseDTO.setMessage(ex.getMessage());

			responseDTO.setData(ex.getValidationResult().getMessages());
		} catch(EntityNotFoundException ex) {
			responseDTO.setCode(HttpStatus.NOT_FOUND.value());

			responseDTO.setMessage(ex.getMessage());
		} catch(InvalidEntityException ex) {
			responseDTO.setCode(HttpStatus.FORBIDDEN.value());

			responseDTO.setMessage(ex.getMessage());
		} catch(Exception ex) {
			responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

			responseDTO.setMessage(ex.getMessage());
		}

		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
    
    @RequestMapping(path = "/deleteRegistration/{residentRoomId}", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseDTO> deleteRegistration(@PathVariable Long residentRoomId) {
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			societyService.deleteRegistration(residentRoomId);

			responseDTO.setCode(HttpStatus.OK.value());

			responseDTO.setMessage("Registration deleted successfully!");
		} catch(ValidationException ex) {
			responseDTO.setCode(HttpStatus.BAD_REQUEST.value());

			responseDTO.setMessage(ex.getMessage());

			responseDTO.setData(ex.getValidationResult().getMessages());
		} catch(EntityNotFoundException ex) {
			responseDTO.setCode(HttpStatus.NOT_FOUND.value());

			responseDTO.setMessage(ex.getMessage());
		} catch(InvalidEntityException ex) {
			responseDTO.setCode(HttpStatus.FORBIDDEN.value());

			responseDTO.setMessage(ex.getMessage());
		} catch(Exception ex) {
			responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

			responseDTO.setMessage(ex.getMessage());
		}

		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
}
