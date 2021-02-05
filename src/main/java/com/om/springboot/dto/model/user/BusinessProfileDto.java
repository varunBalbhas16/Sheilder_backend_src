package com.om.springboot.dto.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessProfileDto {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 50)
    private String firstName;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 50)
    private String lastName;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 15)
    private String gender;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
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

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private Long regId;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 30)
    private String gsaNumber;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private Long employeeCount;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private int isAdmin;

}
