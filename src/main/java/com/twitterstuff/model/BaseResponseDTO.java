package com.twitterstuff.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class BaseResponseDTO {

    public BaseResponseDTO(String message, int httpStatus) {
        this.success = false;
        this.debugMessage = message;
        this.twitterStatus = HttpStatus.valueOf(httpStatus);
    }

    @ApiModelProperty(example = "true")
    private boolean success = true;
    @NonNull
    @ApiModelProperty(example = "Everyhing works great!")
    private String debugMessage = "";
    @NonNull
    @ApiModelProperty(example = "OK")
    private HttpStatus twitterStatus = HttpStatus.OK;
}
