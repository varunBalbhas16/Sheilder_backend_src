package com.om.springboot.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessProfileRequest {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 50)
    private String firstName;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 50)
    private String lastName;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 15)
    private String gender;

    private Date dob;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 45)
    private String country;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 20)
    private String mobileNumber;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 50)
    private String emailId;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 30)
    private String company;

    @NotNull
    private Long regId;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 30)
    private String gsaNumber;

    @NotNull
    private Long employeeCount;

    @NotNull
    private int isAdmin;

    private String anotherAdminName;

    private String anotherAdminMobCountryCode;

    private String anotherAdminMobileNumber;

    private String anotherAdminEmailId;

}
