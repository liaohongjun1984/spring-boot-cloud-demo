package com.idohoo.validate;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import java.util.List;

/**
 * Created by tanj on 05May2017.
 */
public final class XValidateUtil {

    private static final Validator validator = new Validator();

    public static List<ConstraintViolation> validateAll(Validable obj) {
        if (obj == null) return null;
        return validator.validate(obj);
    }

    public static String validateOne(Validable obj) {
        List<ConstraintViolation> result = validateAll(obj);
        String errMsg = null;
        if (result != null && result.size() > 0) {
            errMsg = result.get(0).getMessage();
        }
        return errMsg;
    }

    public static void disableProfile(String profileName) {
        validator.disableProfile(profileName);
    }

    public static void disableAllProfiles() {
        validator.disableAllProfiles();
    }

    public static void enableProfile(String profileName) {
        validator.enableProfile(profileName);
    }

    public static void enableAllProfiles() {
        validator.enableAllProfiles();
    }
}
