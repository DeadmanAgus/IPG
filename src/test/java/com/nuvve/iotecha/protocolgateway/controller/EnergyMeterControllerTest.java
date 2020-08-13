package com.nuvve.iotecha.protocolgateway.controller;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.nuvve.iotecha.protocolgateway.dtos.EnergyMeterDto;
import com.nuvve.iotecha.protocolgateway.dtos.EvseDto;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EnergyMeterControllerTest {
	private Logger logger = LogManager.getLogger(EnergyMeterControllerTest.class);

	private static final String EM_MAKER = "maker";
    private static final String EM_MODEL = "model";
    private static final String EM_SERIAL_NUMBER = "serialNumber";
	
    private static final String EVSE_MAKER = "maker";
    private static final String EVSE_SERIAL_NUMBER = "serialNumber";
    private static final String EVSE_SECC_MAC = "00-11-22-33-44-55";
    private static final String EVSE_POWER_RATINGS = "powerRatings";
    
	@LocalServerPort
	private int port;

	private String URL;
	
	private WebSocketClient client;
	private WebSocketStompClient stompClient;
	
	@BeforeEach
	public void setup() {
		URL = "ws://localhost:" + this.port + "/iotecha-wss";
		
		client = new StandardWebSocketClient();
		stompClient = new WebSocketStompClient(client);
		
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
	}
	
	private void enterEVSE() throws Exception {
		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<Throwable> failure = new AtomicReference<>();

		StompSessionHandler sessionHandler = new EVSEControllerSessionHandler(failure) {
			@Override
			public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
				logger.info("New session established : " + session.getSessionId());
				
				try {
					session.send("/app/evse/create", 
							EvseDto.builder().
								evseMake(EVSE_MAKER).
								evseSn(EVSE_SERIAL_NUMBER).
								seccMac(EVSE_SECC_MAC).
								powerRatings(EVSE_POWER_RATINGS).
								build());
					logger.info("Message sent to websocket server");
				} catch (Throwable t) {
					failure.set(t);
					latch.countDown();
				}
			}
		};
		stompClient.connect(URL, sessionHandler);
		Thread.sleep(2000); /* Wait before closing the session */
	}
	
	@Test
	public void createEnergyMeter() throws Exception{
		try {
			enterEVSE();
			
			final CountDownLatch latch = new CountDownLatch(1);
			final AtomicReference<Throwable> failure = new AtomicReference<>();
	
			StompSessionHandler sessionHandler = new EVSEControllerSessionHandler(failure) {
				@Override
				public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
					logger.info("New session established : " + session.getSessionId());
					session.subscribe("/topic/messages", new StompFrameHandler() {
	
						@Override
						public Type getPayloadType(StompHeaders headers) {
							return EnergyMeterDto.class;
						}
	
						@Override
						public void handleFrame(StompHeaders headers, Object payload) {
							EnergyMeterDto msg = (EnergyMeterDto) payload;
							logger.info("Received : " + msg.getMeterMake() + " from : " + msg.getMeterModel());
	
							try {
								Assertions.assertEquals(EM_MAKER, msg.getMeterMake());
								Assertions.assertEquals(EM_MODEL, msg.getMeterModel());
								Assertions.assertEquals(EM_SERIAL_NUMBER, msg.getMeterSn());
							} catch (Throwable t) {
								failure.set(t);
							} finally {
								session.disconnect();
								latch.countDown();
							}
						}
					});
	
					try {
						session.send("/app/energyMeter/create", 
								EnergyMeterDto.builder()
									.evse(EvseDto.builder().evseId(1).build())
									.meterId(0)
									.meterMake(EM_MAKER)
									.meterModel(EM_MODEL)
									.meterSn(EM_SERIAL_NUMBER)
									.build());
						logger.info("Message sent to websocket server");
					} catch (Throwable t) {
						failure.set(t);
						latch.countDown();
					}
				}
			};
			stompClient.connect(URL, sessionHandler);
	
			Thread.sleep(5000); /* Wait before closing the session */
			
			if (failure.get() != null) { /* Check for failures on the internal class */
				throw new AssertionError("", failure.get());
			}
			//new Scanner(System.in).nextLine(); // Don't close immediately.
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void getEnergyMeters() throws Exception{
		try {
			final CountDownLatch latch = new CountDownLatch(1);
			final AtomicReference<Throwable> failure = new AtomicReference<>();
	
			StompSessionHandler sessionHandler = new EVSEControllerSessionHandler(failure) {
				@Override
				public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
					logger.info("New session established : " + session.getSessionId());
					session.subscribe("/topic/messages", new StompFrameHandler() {
	
						@Override
						public Type getPayloadType(StompHeaders headers) {
							return List.class;
						}
	
						@Override
						public void handleFrame(StompHeaders headers, Object payload) {
							Collection<EnergyMeterDto> msgList = (Collection<EnergyMeterDto>) payload;
							logger.info("Received : " + msgList.size() + " elements.");
	
							try {
								Assertions.assertTrue(msgList.size() > 0);
							} catch (Throwable t) {
								failure.set(t);
							} finally {
								session.disconnect();
								latch.countDown();
							}
						}
					});
	
					try {
						session.send("/app/energyMeter/getAll", null);
						logger.info("Message sent to websocket server");
					} catch (Throwable t) {
						failure.set(t);
						latch.countDown();
					}
				}
			};
			
			stompClient.connect(URL, sessionHandler);
	
			Thread.sleep(5000); /* Wait before closing the session */
			
			if (failure.get() != null) { /* Check for failures on the internal class */
				throw new AssertionError("", failure.get());
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private static class EVSEControllerSessionHandler extends StompSessionHandlerAdapter {
		private final AtomicReference<Throwable> failure;

		public EVSEControllerSessionHandler(AtomicReference<Throwable> failure) {
			this.failure = failure;
		}

		@Override
		public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
				Throwable exception) {
			this.failure.set(exception);
		}

		@Override
		public void handleTransportError(StompSession session, Throwable ex) {
			this.failure.set(ex);
		}

		@Override
		public void handleFrame(StompHeaders headers, Object payload) {
			this.failure.set(new Exception(headers.toString()));
		}
	}
}
