package Validation.Custom;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import Entities.Notification;

public class TargetValidation implements ConstraintValidator<TargetConstraint, Notification> {
    @Override
    public void initialize(TargetConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(Notification notification, ConstraintValidatorContext constraintValidatorContext) {
        String type = notification.getType();
        String target = notification.getTarget();

        if(type.equals("SMS"))
            return target.matches("^\\+?[0-9]{7,15}$");

        if(type.equals("MAIL"))
            return target.matches("^[A-Za-z0-9+_.-]+@(.+)$");

        return false;
    }
}