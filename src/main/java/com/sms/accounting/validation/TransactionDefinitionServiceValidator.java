package com.sms.accounting.validation;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sms.accounting.dto.transactiondefinition.SaveDTO;
import com.sms.accounting.entity.Account;
import com.sms.accounting.entity.TransactionDefinition;
import com.sms.common.validation.ValidationException;
import com.sms.common.validation.ValidationResult;

@Component
public class TransactionDefinitionServiceValidator {
    @Autowired
    private String dateTimeRegex;

    public void validateSave(SaveDTO saveDTO) throws ValidationException {
        ValidationResult validationResult = new ValidationResult();

        if(saveDTO.getCostHeaderId() == null) {
            validationResult.addError("costHeaderId", "Cost header ID is mandatory.");
        }

        if(saveDTO.getFromAccountType() == null) {
            validationResult.addError("transactionFrom", "Transaction from is mandatory.");
        } else if(Arrays.binarySearch(Account.Type.values(), saveDTO.getFromAccountType()) < 0) {
            validationResult.addError("transactionFrom", "Transaction from must be valid.");
        }

        if(saveDTO.getToAccountType() == null) {
            validationResult.addError("transactionTo", "Transaction to is mandatory.");
        } else if(Arrays.binarySearch(Account.Type.values(), saveDTO.getToAccountType()) < 0) {
            validationResult.addError("transactionTo", "Transaction to must be valid.");
        }

        if(saveDTO.getInterval() == null) {
            validationResult.addError("interval", "Interval is mandatory.");
        } else if(Arrays.binarySearch(TransactionDefinition.Interval.values(), saveDTO.getInterval()) < 0) {
            validationResult.addError("interval", "Interval must be valid.");
        }

        if(saveDTO.getApplicableFrom() == null) {
            validationResult.addError("frpm", "From is mandatory.");
        } else if(!saveDTO.getApplicableFrom().matches(dateTimeRegex)) {
            validationResult.addError("from", "From must be valid.");
        }

        if(saveDTO.getHasParticulars() == null) {
            validationResult.addError("hasParticulars", "Has particulars is mandatory.");
        } else if(saveDTO.getHasParticulars()) {
            if(saveDTO.getParticulars().size() == 0) {
                validationResult.addError("particulars", "Particulars are mandatory.");
            } else {
                boolean particularsValidated = true;

                for(SaveDTO.Particular particularDTO : saveDTO.getParticulars()) {
                    if(particularDTO.getAmount() == null || particularDTO.getAmount() <= 0) {
                        validationResult.addError("particularAmount", "Particular amount must be valid.");

                        particularsValidated = false;
                    }

                    if(!particularsValidated) {
                        break;
                    }
                }
            }
        } else {
            if(saveDTO.getAmount() == null) {
                validationResult.addError("amount", "Amount is mandatory.");
            } else if(saveDTO.getAmount() <= 0) {
                validationResult.addError("amount", "Amount must be greater than 0.");
            }
        }

        if(validationResult.hasErrors()) {
            throw new ValidationException("Bad Input.", validationResult);
        }
    }
}
