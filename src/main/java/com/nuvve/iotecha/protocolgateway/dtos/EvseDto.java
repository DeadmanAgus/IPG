package com.nuvve.iotecha.protocolgateway.dtos;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description="EVSE Information")
public class EvseDto extends CoreDto {
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(notes="Id will be generated by Nuvve")
	private long evseId;
    private String evseMake;
    private String evseSn;
    private String seccMac;
    private String powerRatings;

    public EvseDto(){
        super();
    }

    @Builder
    public EvseDto(String eventType, int errorCode, String description, LocalDateTime timestamp, long evseId,
                   String evseMake, String evseSn, String seccMac, String powerRatings) {
        super(eventType, errorCode, description, timestamp);
        this.evseId = evseId;
        this.evseMake = evseMake;
        this.evseSn = evseSn;
        this.seccMac = seccMac;
        this.powerRatings = powerRatings;

    }

    public EvseDto setError(String error, int errorCode){
        return EvseDto.builder()
                .description(error)
                .errorCode(errorCode)
                .eventType(EventType.ERROR.name())
                .build();
    }
}
