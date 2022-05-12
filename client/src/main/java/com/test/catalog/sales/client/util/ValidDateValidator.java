package com.test.catalog.sales.client.util;

import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidDateValidator implements ConstraintValidator<ValidDate, String> {

    private static final String NOT_BLANK_MSG = "must not be blank";
    private Boolean isOptional;
    private String format;

    private static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            if (value != null) {
                date = sdf.parse(value);
                if (!value.equals(sdf.format(date).replace("+0000", "-0000"))) {
                    date = null;
                }
            }
        } catch (ParseException ex) {
        }
        return date != null;
    }

    @Override
    public void initialize(ValidDate validDate) {
        this.isOptional = validDate.optional();
        this.format = validDate.format();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (ObjectUtils.isEmpty(value)) {
            //disable existing violation message
            constraintValidatorContext.disableDefaultConstraintViolation();
            //build new violation message and add it
            constraintValidatorContext.buildConstraintViolationWithTemplate(NOT_BLANK_MSG).addConstraintViolation();
        }
        boolean validDate = isValidFormat(format, value);

        return isOptional ? (validDate || (ObjectUtils.isEmpty(value))) : validDate;
    }
}