package com.om.springboot.util;

public enum ErrorConstants {
    E101("Already registered"),
    E102("Details not inserted in DB"),
    E103("You seems to be already admin for the organisation"),
    E104("Resend otp count exceeded"),
    E105("Company already registered"),
    E106("Otp hasn't been generated for that particular mobile number"),
    E107("Otp mismatched.Enter the correct otp"),
    E108("User is not Logged In.Please Login and Proceed"),
    E109("Please Register your Business and Proceed");


    private String value;

    ErrorConstants(String value) {
        this.value = value;
    }
}
