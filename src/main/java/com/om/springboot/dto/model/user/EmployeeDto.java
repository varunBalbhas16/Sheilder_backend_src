package com.om.springboot.dto.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDto {
    private String shielderId;
    @NotEmpty
    @Size(min = 1, max = 50)
    private String employeeId;
    @NotEmpty
    @Size(min = 1, max = 50)
    private String name;
    @NotEmpty
    @Size(min = 1, max = 20)
    private String mobileNumber;
    @NotEmpty
    @Size(min = 1, max = 50)
    private String emailId;
    @NotEmpty
    @Size(min = 1, max = 10)
    private String countryCode;
    @NotEmpty
    @Size(min = 1, max = 50)
    private String company;
    @NotNull
    private int statusId;

    private int otpVerified;


}
