package com.nuvve.iotecha.protocolgateway.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.nuvve.iotecha.protocolgateway.domains.Evse;
import com.nuvve.iotecha.protocolgateway.dtos.EvseDto;
import com.nuvve.iotecha.protocolgateway.mappers.EvseMapper;
import com.nuvve.iotecha.protocolgateway.persistence.EvseRepository;

@SpringBootTest
public class EvseServiceTest {

	@Mock
	private EvseRepository evseRepository;

	@Mock
	private EvseMapper mapper;
	
	@InjectMocks
	private EvseService evseService;
	
	private Evse evse;
	private EvseDto evseDto;
	
	
	@BeforeEach
	public void setup()throws Exception {
		evse = getEVSE();
		evseDto = getEVSEDto();
		
		Mockito.when(evseRepository.save(evse)).thenReturn(evse);
		Mockito.when(mapper.transformDtoToDomain(evseDto)).thenReturn(evse);
		Mockito.when(mapper.transformDomainToDto(evse)).thenReturn(evseDto);
	}
	
	@Test
	public void testSave()throws Exception {
		try {
			EvseDto dto = evseService.createEvse(evseDto);
			
			Assertions.assertEquals(evseDto.getEvseMake(), dto.getEvseMake());
			Assertions.assertEquals(evseDto.getEvseSn(), dto.getEvseSn());
			Assertions.assertEquals(evseDto.getSeccMac(), dto.getSeccMac());
			Assertions.assertEquals(evseDto.getPowerRatings(), dto.getPowerRatings());
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private Evse getEVSE() {
		Evse domain = new Evse();
		
        domain.setEvseMake("Make");
        domain.setEvseSn("SN");
        domain.setSeccMac("00-11-22-33-44-55");
        domain.setPowerRatings("Power");
        
        return domain;
	}
	
	private EvseDto getEVSEDto() {
		 EvseDto dto = EvseDto.builder()
	                .evseId(1)
	                .evseMake("Make")
	                .evseSn("SN")
	                .seccMac("00-11-22-33-44-55")
	                .powerRatings("Power")
	                .build();
		 
	        return dto;
	}
}
