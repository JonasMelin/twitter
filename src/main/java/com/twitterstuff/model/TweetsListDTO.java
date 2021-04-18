package com.twitterstuff.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.LinkedList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class TweetsListDTO extends BaseResponseDTO{

    public TweetsListDTO(String message, int httpStatus){
        super(message, httpStatus);
    }

    @NonNull
    @ApiModelProperty(example = "[\"Such a lovely day\", \"I had eggs for lunch!\"]")
    private List<String> tweets = new LinkedList<>();
}
