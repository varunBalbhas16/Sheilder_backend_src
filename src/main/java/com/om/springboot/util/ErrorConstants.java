package com.om.springboot.util;

public enum ErrorConstants {
    E101("Already registered"),
    E102("Details not inserted");



    private String value;
ErrorConstants(String value){
    this.value=value;
}
}
