package com.om.springboot.controller.request;

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
public class AddEmployeeRequest {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 20)
    private String employeeId;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 50)
    private String name;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 10)
    private String countryCode;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 15)
    private String mobileNumber;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 50)
    private String emailId;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 15)
    private String adminMobileNumber;

}
