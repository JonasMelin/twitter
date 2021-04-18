package com.twitterstuff.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class BaseResponseDTO {

    public BaseResponseDTO(String message, int httpStatus){
        this.debugMessage = message;
        this.twitterStatus = httpStatus;
    }

    @NonNull
    @ApiModelProperty(example = "user not found")
    private String debugMessage = "";
    @NonNull
    @ApiModelProperty(example = "404")
    private int twitterStatus = HttpStatus.OK.value();
}
