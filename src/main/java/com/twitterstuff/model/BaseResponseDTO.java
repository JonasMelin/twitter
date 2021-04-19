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

    @ApiModelProperty(example = "false")
    private boolean success = true;
    @NonNull
    @ApiModelProperty(example = "user not found")
    private String debugMessage = "";
    @NonNull
    @ApiModelProperty(example = "NOT_FOUND")
    private HttpStatus twitterStatus = HttpStatus.OK;
}
