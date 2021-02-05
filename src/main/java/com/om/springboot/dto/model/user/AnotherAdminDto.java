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

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnotherAdminDto {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 50)
    private String anotherAdminName;

    @NotEmpty
    private String anotherAdminMobCountryCode;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 20)
    private String anotherAdminMobileNumber;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 50)
    private String anotherAdminEmailId;

}
