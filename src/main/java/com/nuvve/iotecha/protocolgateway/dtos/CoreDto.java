package com.nuvve.iotecha.protocolgateway.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class CoreDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String eventType;
    private int errorCode;
    private String description;
    
    @JsonProperty(access = Access.WRITE_ONLY)
    private LocalDateTime timestamp = LocalDateTime.now();
}
