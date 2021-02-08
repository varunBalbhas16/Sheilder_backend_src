package com.om.springboot.controller.request.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateOtpRequest {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 15)
    private String mobileNumber;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 6)
    private String otp;
}
