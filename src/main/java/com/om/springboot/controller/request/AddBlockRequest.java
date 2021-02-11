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
public class AddBlockRequest {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 50)
    private String blockName;


    private String blockNumber;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 20)
    private String adminMobileNumber;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 1, max = 50)
    private String facilityName;
}
