package Validation.Custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TargetValidation.class)
public @interface TargetConstraint {
    String message() default "Target and type doesn't match";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
