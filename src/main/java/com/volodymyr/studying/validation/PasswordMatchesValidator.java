package com.volodymyr.studying.validation;

import com.volodymyr.studying.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, User> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
