package com.test.catalog.sales.api.validator;

import org.springframework.util.ObjectUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validate that the annotated string is in YYYY-MM-DD Date format
 */

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidDate.ValidDateValidator.class)
public @interface ValidDate {

    String message() default "Date format value should be - ";
    String format() default "yyyy-MM-dd";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean optional() default false;
    class ValidDateValidator implements ConstraintValidator<ValidDate, String> {

        private static final String NOT_BLANK_MSG = "must not be blank";
        private Boolean isOptional;
        private String format;
        private String message;

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
            this.message = validDate.message();
            this.format = validDate.format();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
            boolean validDate = isValidFormat(format, value);
            constraintValidatorContext.disableDefaultConstraintViolation();
            if (ObjectUtils.isEmpty(value)) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(NOT_BLANK_MSG).addConstraintViolation();
            }else if (!validDate) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(message.concat(this.format))
                        .addConstraintViolation();
            }
            return isOptional ? (validDate || (ObjectUtils.isEmpty(value))) : validDate;
        }
    }
}