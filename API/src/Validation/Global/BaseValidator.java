package Validation.Global;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.Validation;
import java.util.Set;

public class BaseValidator<T> implements EntityValidator <T>{
    protected final Validator validatorUnit;

    public BaseValidator(){
        this.validatorUnit = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public Set<ConstraintViolation<T>> validate(T entity) {
        return validatorUnit.validate(entity);
    }

    @Override
    public String containsViolations(Set<ConstraintViolation<T>> constraintViolations) {
        if(!constraintViolations.isEmpty()) {
            StringBuilder violationMessage = new StringBuilder();
            for(ConstraintViolation<T> violation : constraintViolations) {
                violationMessage.append(violation.getPropertyPath()).append(" : ").append(violation.getMessage()).append("\n");
            }

            return violationMessage.toString();
        }
        return null;
    }
}
