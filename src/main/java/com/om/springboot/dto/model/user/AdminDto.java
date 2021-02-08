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
public class AdminDto {

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 100)
    private String adminName;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 15)
    private String adminMobileNumber;
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 100)
    private String company;
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 50)
    private String adminEmailId;

    Long userId;

}
