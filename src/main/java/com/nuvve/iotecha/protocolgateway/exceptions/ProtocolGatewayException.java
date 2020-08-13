package com.nuvve.iotecha.protocolgateway.exceptions;

public class ProtocolGatewayException extends Exception {
	 
	private static final long serialVersionUID = 1L;
	
	public ProtocolGatewayException() { 
		super(); 
	}
	
	public ProtocolGatewayException(String message) { 
		super(message); 
	}
	
	public ProtocolGatewayException(String message, Throwable cause) { 
		super(message, cause); 
	}
	
	public ProtocolGatewayException(Throwable cause) { 
		super(cause); 
	}
}
