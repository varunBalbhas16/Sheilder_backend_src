package com.om.springboot.dto.response.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.om.springboot.dto.model.user.CountryDto;
import com.om.springboot.dto.response.ApiResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryResponse extends ApiResponse {
    List<CountryDto> countries;

    public CountryResponse(Boolean success, String message) {
        super(success, message);
    }
}
