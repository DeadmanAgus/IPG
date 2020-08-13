package com.nuvve.iotecha.protocolgateway.controller;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Type;
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

import com.nuvve.iotecha.protocolgateway.dtos.EvseDto;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EVSEControllerTest {
	private Logger logger = LogManager.getLogger(EVSEControllerTest.class);

	private static final String EVSE_MAKER = "maker";
    private static final String EVSE_SERIAL_NUMBER = "serialNumber";
    private static final String EVSE_SECC_MAC = "00-11-22-33-44-55";
    private static final String EVSE_POWER_RATINGS = "powerRatings";

	private String URL;
	
	@LocalServerPort
	private int port;

	private WebSocketClient client;
	private WebSocketStompClient stompClient;
	
	@BeforeEach
	public void setup() {
		URL = "ws://localhost:" + this.port + "/iotecha-wss";
		
		client = new StandardWebSocketClient();
		stompClient = new WebSocketStompClient(client);
		
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
	}
	
	@Test
	public void createEVSE()  throws Exception{
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
							return EvseDto.class;
						}
	
						@Override
						public void handleFrame(StompHeaders headers, Object payload) {
							EvseDto msg = (EvseDto) payload;
							logger.info("Received : " + msg.getEvseSn() + " from : " + msg.getSeccMac());
	
							try {
								Assertions.assertEquals(EVSE_MAKER, msg.getEvseMake());
								Assertions.assertEquals(EVSE_SERIAL_NUMBER, msg.getEvseSn());
								Assertions.assertEquals(EVSE_SECC_MAC, msg.getSeccMac());
								Assertions.assertEquals(EVSE_POWER_RATINGS, msg.getPowerRatings());
							} catch (Throwable t) {
								failure.set(t);
							} finally {
								session.disconnect();
								latch.countDown();
							}
						}
					});
	
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
	
			Thread.sleep(5000); /* Wait before closing the session */
			
			if (failure.get() != null) { /* Check for failures on the internal class */
				throw new AssertionError("", failure.get());
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Test
	@SuppressWarnings("unchecked")
	public void getEVSEs()  throws Exception{
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
							List<EvseDto> msgList = (List<EvseDto>) payload;
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
						session.send("/app/evse/getAll", null);
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
