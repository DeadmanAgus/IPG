package com.nuvve.iotecha.protocolgateway.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuvve.iotecha.protocolgateway.domains.Evse;
import com.nuvve.iotecha.protocolgateway.domains.PlugUnplug;
import com.nuvve.iotecha.protocolgateway.dtos.EventType;
import com.nuvve.iotecha.protocolgateway.dtos.PlugUnplugDto;
import com.nuvve.iotecha.protocolgateway.exceptions.ProtocolGatewayException;
import com.nuvve.iotecha.protocolgateway.mappers.PlugUnplugMapper;
import com.nuvve.iotecha.protocolgateway.persistence.EVRepository;
import com.nuvve.iotecha.protocolgateway.persistence.EvseRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EVService {

	@Autowired
	RabbitMQService rabbitMQService;

	@Autowired
	EVRepository evRepository;

	@Autowired
	PlugUnplugMapper mapper;

	@Autowired
	EvseRepository evseRepository;

	/**
	 * Plugs the EV
	 * 
	 * @param plugUnplugDto
	 * @return
	 * @throws ProtocolGatewayException
	 */
	public PlugUnplugDto evPlugged(PlugUnplugDto plugUnplugDto) throws ProtocolGatewayException {
		Optional<Evse> evse;
		PlugUnplug plugUnplug = mapper.transformDtoToDomain(plugUnplugDto);
		evse = evseRepository.findById(plugUnplugDto.getEvse().getEvseId());

		if (!evse.isPresent()) {
			throw new ProtocolGatewayException("The selected EVSE does not exist.");
		}

		try {
			plugUnplug.setEvse(evse.get());
			plugUnplug.setEventType(EventType.PLUG_IN.name());
			
			plugUnplug = evRepository.save(plugUnplug);

			rabbitMQService.send(plugUnplugDto);
			plugUnplugDto = mapper.transformDomainToDto(plugUnplug);

			log.info("EV Plugged Correctly");
		} catch (Exception e) {
			throw new ProtocolGatewayException(e);
		}

		return plugUnplugDto;
	}

	/**
	 * Unplugs the EV
	 * 
	 * @param plugUnplugDto
	 * @return
	 * @throws ProtocolGatewayException
	 */
	public PlugUnplugDto evUnplugged(PlugUnplugDto plugUnplugDto) throws ProtocolGatewayException {
		Optional<Evse> evse;
		PlugUnplug plugUnplug = mapper.transformDtoToDomain(plugUnplugDto);
		evse = evseRepository.findById(plugUnplugDto.getEvse().getEvseId());

		if (!evse.isPresent()) {
			throw new ProtocolGatewayException("The selected EVSE does not exist.");
		}

		try {
			plugUnplug.setEvse(evse.get());
			plugUnplug.setEventType(EventType.UNPLUGGED.name());
			plugUnplug = evRepository.save(plugUnplug);

			rabbitMQService.send(plugUnplugDto);
			plugUnplugDto = mapper.transformDomainToDto(plugUnplug);

			log.info("EV Unplugged Correctly");
		} catch (Exception e) {
			throw new ProtocolGatewayException(e);
		}

		return plugUnplugDto;
	}
}
