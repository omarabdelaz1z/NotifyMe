package Validation.Global;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

public interface EntityValidator<T> {
    Set<ConstraintViolation<T>> validate(T entity);
    String containsViolations(Set<ConstraintViolation <T>> violations);
}
