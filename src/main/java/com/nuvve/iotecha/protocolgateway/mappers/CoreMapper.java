package com.nuvve.iotecha.protocolgateway.mappers;

import java.util.List;

import com.nuvve.iotecha.protocolgateway.domains.CoreDomain;
import com.nuvve.iotecha.protocolgateway.exceptions.ProtocolGatewayException;

@SuppressWarnings("all")
public interface CoreMapper  <T extends Object, S extends CoreDomain>{

	/**
	 * Transforms from Domain to Dto
	 * 
	 * @param domain
	 * @return
	 * @throws ProtocolGatewayException
	 */
	public T transformDomainToDto(S domain) throws ProtocolGatewayException;
	
	/**
	 * Transforms from Domain to Dto
	 * 
	 * @param domain
	 * @return
	 * @throws ProtocolGatewayException
	 */
	public List<T> transformDomainToDto(List<S> domain) throws ProtocolGatewayException;
	
	/**
	 * Transforms from Dto to Domain
	 * 
	 * @param dto
	 * @return
	 * @throws ProtocolGatewayException
	 */
	public S transformDtoToDomain(T dto) throws ProtocolGatewayException;
	
	/**
	 * Transforms from Dto to Domain
	 * 
	 * @param dto
	 * @return
	 * @throws ProtocolGatewayException
	 */
	public List<S> transformDtoToDomain(List<T> dto) throws ProtocolGatewayException;
}
