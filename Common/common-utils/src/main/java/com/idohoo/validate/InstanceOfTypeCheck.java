package com.idohoo.validate;

import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;
import net.sf.oval.exception.OValException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstanceOfTypeCheck extends AbstractAnnotationCheck<InstanceOfType> {

    private Logger logger =LoggerFactory.getLogger(InstanceOfTypeCheck.class);

    private static final long serialVersionUID = 1L;

    private Class<?> type;

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type=type;
        this.requireMessageVariablesRecreation();
    }

    public void configure(final InstanceOfType constraintAnnotation) {
        super.configure(constraintAnnotation);

        this.setType(constraintAnnotation.type());
    }

    public boolean isSatisfied(Object validatedObject, Object valueToValidate,
                               OValContext context, Validator validator) throws OValException {

        if(valueToValidate == null) {
            return true;
        }

        if(!type.isInstance(valueToValidate)) {
            return false;
        }

       return true;
    }

}