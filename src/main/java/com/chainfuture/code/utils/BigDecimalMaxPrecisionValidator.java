package com.chainfuture.code.utils;
public class BigDecimalMaxPrecisionValidator{
/*import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

@Slf4j
public class BigDecimalMaxPrecisionValidator implements ConstraintValidator<BigDecimalMaxPrecision, BigDecimal> {

    private int value;
    private String message;

    @Override
    public void initialize(BigDecimalMaxPrecision constraintAnnotation) {
        this.value = constraintAnnotation.value();
        this.message = constraintAnnotation.message();

    }

    @Override
    public boolean isValid(BigDecimal bigDecimal, ConstraintValidatorContext context) {
        if (bigDecimal == null) {
            return true;
        }
        String bigDecimalStr = bigDecimal.toString();
        int indexOf = bigDecimalStr.indexOf(".");
        int doublePrecision;
        if (indexOf > 0) {
            doublePrecision = bigDecimalStr.length() - 1 -indexOf;
            if (doublePrecision > value) {
                log.warn("input bigDecimal value is:{}, precision is:{}, set max precision is:{}", bigDecimal, doublePrecision, value);
                return false;
            }
        }

        return true;
    }*/
}
