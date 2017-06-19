package com.idohoo.validate;

import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;
import net.sf.oval.exception.OValException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CIsNullCheck extends AbstractAnnotationCheck<CheckIsNull> {

    private Logger logger =LoggerFactory.getLogger(CIsNullCheck.class);

    private static final long serialVersionUID = 1L;

    public void configure(final CheckIsNull constraintAnnotation) {
       super.configure(constraintAnnotation);
    }

    public boolean isSatisfied(Object validatedObject, Object valueToValidate,
                               OValContext context, Validator validator) throws OValException {

       if(valueToValidate instanceof String) {

           logger.info("valueToValidate :{}",(String)valueToValidate);

          if((String)valueToValidate == null
                  || "".equals((String)valueToValidate)) {
              return false;
          }
       }
       return true;
    }

}