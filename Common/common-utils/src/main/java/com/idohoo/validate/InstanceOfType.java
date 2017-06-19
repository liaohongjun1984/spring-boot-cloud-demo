package com.idohoo.validate;

import net.sf.oval.ConstraintTarget;
import net.sf.oval.configuration.annotation.Constraint;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER,ElementType.METHOD})
@Constraint(checkWith = InstanceOfTypeCheck.class)
public @interface InstanceOfType {

    ConstraintTarget[] appliesTo() default {ConstraintTarget.CONTAINER};

    String errorCode() default "net.sf.oval.constraint.InstanceOf int";

    String message() default "data is not int";

    Class<?> type();

}