package com.git.support.invoker.amq.impl;

import java.util.HashMap;
import java.util.UUID;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConverter;

import com.git.support.invoker.common.impl.AbstractDestinationRouteKey;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.SerialDataObject;
import com.git.support.sdo.inf.IDataObject;

class ConnectionInfo {
	private ConnectionFactory connectionFactory = null;
	private Connection connection = null;
	private Session session = null;
	private Destination reqQueue = null;
	private Destination rspQueue = null;
	private String msgSelector = null;
	private long mesgExpirationMillis = 0;

	public Connection createConnection() throws Exception {
		if (connectionFactory == null) {
			throw new RuntimeException("ConnectionFactory is null");
		}
		connection = connectionFactory.createConnection();
		connection.start();
		return connection;
	}
	
	public Session createSession() throws Exception {
		if (connection == null) {
			throw new RuntimeException("Connection is null");
		}
		session = connection.createSession(
				false, Session.AUTO_ACKNOWLEDGE);
		return session;
	}
	
	public MessageProducer createProcuder() throws Exception {
		if (session == null) {
			throw new RuntimeException("Session is null");
		}
		MessageProducer producer = session.createProducer(reqQueue);
		producer.setTimeToLive(mesgExpirationMillis);
		return producer;
	}

	public MessageConsumer createConsumer() throws Exception {
		MessageConsumer consumer = null;
		if (session == null) {
			throw new RuntimeException("Session is null");
		}
		if (msgSelector != null)  {
			consumer = session.createConsumer(rspQueue, msgSelector);
		} else {
			consumer = session.createConsumer(rspQueue);
		}
		return consumer;
	}
	
	public void closeConnection(){
		try {
			if (session != null) {
				session.close();
				session = null;
			}
		} catch (Exception e) {			
		}
		try {
			if (connection != null) {
				connection.stop();
				connection.close();
				connection = null;
			}
		} catch (Exception e) {			
		}
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Destination getReqQueue() {
		return reqQueue;
	}

	public void setReqQueue(Destination reqQueue) {
		this.reqQueue = reqQueue;
	}

	public Destination getRspQueue() {
		return rspQueue;
	}

	public void setRspQueue(Destination rspQueue) {
		this.rspQueue = rspQueue;
	}

	public String getMsgSelector() {
		return msgSelector;
	}

	public void setMsgSelector(String msgSelector) {
		this.msgSelector = msgSelector;
	}

	public long getMesgExpirationMillis() {
		return mesgExpirationMillis;
	}

	public void setMesgExpirationMillis(long mesgExpirationMillis) {
		this.mesgExpirationMillis = mesgExpirationMillis;
	}

};

public class AmqResAdptInvoker extends AbstractDestinationRouteKey implements IResAdptInvoker { 	
	private final int DEFAULT_TIMEOUT = 5000;
	
	private HashMap<String, AmqDestination> destinations = null;
	private MessageConverter msgConverter = null;	
	
	Logger logger  =  LoggerFactory.getLogger(AmqResAdptInvoker.class);

	@Override
	public IDataObject invoke(IDataObject reqData, int timeOut) throws Exception {
		ConnectionInfo connectionInfo = new ConnectionInfo();
		IDataObject rspData = null;

		try {
			if (timeOut <= 0) {
				timeOut = DEFAULT_TIMEOUT;
			}

			final String routeKey = getRouteKey(reqData);
			AmqDestination destination = findDestination(routeKey);

			connectionInfo.setConnectionFactory(destination
					.getPooledConnectionFactory());
			connectionInfo.createConnection();
			connectionInfo.createSession();
			connectionInfo.setReqQueue(destination.getQueueRequest());
			connectionInfo.setRspQueue(destination.getQueueReply());
			connectionInfo.setMesgExpirationMillis(destination.getMesgExpirationMillis());

			String correlationId = UUID.randomUUID().toString();
			connectionInfo.setMsgSelector("JMSCorrelationID='" + correlationId + "'");
			
			Message reqMsg = msgConverter.toMessage(reqData, connectionInfo.getSession());
			reqMsg.setJMSCorrelationID(correlationId);
			reqMsg.setJMSReplyTo(connectionInfo.getRspQueue());
			reqMsg.setLongProperty("MesgExpirationMillis", connectionInfo.getMesgExpirationMillis());

			StringBuffer strBuf = new StringBuffer(1024);
			SerialDataObject ser = new SerialDataObject();

			strBuf.append("Client send:");
			strBuf.append(correlationId);
			strBuf.append(" TimeOut=");
			strBuf.append(timeOut);
			strBuf.append(" DataObject Content:\n");
			ser.serial2String(reqData, strBuf);
			logger.info(strBuf.toString());

			sendMsg(connectionInfo, reqMsg);
			
			Message rspMsg = receiveMsg(connectionInfo, timeOut);

            connectionInfo.closeConnection();

			if (rspMsg != null) {
				rspData = (IDataObject) msgConverter.fromMessage(rspMsg);
			} else {
				rspData = null;
			}

			strBuf.delete(0, strBuf.length());
			strBuf.append("Client recv:");
			strBuf.append(correlationId);
			strBuf.append(" DataObject Content:\n");
			if (rspData != null) {
				ser.serial2String(rspData, strBuf);
			} else {
				strBuf.append("null !!!");
				if (rspMsg == null) {
					strBuf.append("Receive Message is null also!!!");
				}
			}
			logger.info(strBuf.toString());

		} catch (Exception e) {
			connectionInfo.closeConnection();
			logger.error("AmqResAdptInvoker invoke adapter error:", e);
			throw e;
		}
		return rspData;
	}

	private void sendMsg(ConnectionInfo connectInfo, Message msg) throws Exception {
		MessageProducer producer = null;
		producer = connectInfo.createProcuder();
		try {
			producer.send(msg);
			producer.close();
			producer = null;
		} catch (JMSException e) {
			if (producer != null) {
				producer.close();
				producer = null;
			}
			throw e;
		}
	}
	
	private Message receiveMsg(ConnectionInfo connectInfo, long timeOut) throws Exception {
		Message rspMsg = null;		
		long deadline = 0;
		
		if (timeOut <= 0) {
			timeOut = DEFAULT_TIMEOUT;
		}
		
        deadline = System.currentTimeMillis() + timeOut;
        
        MessageConsumer consumer = null;
        consumer = connectInfo.createConsumer();
        
        logger.debug(connectInfo.getMsgSelector() + " begin receive Response Message.");
        
        while (true) {
        	try {
        		rspMsg = consumer.receive(timeOut);
        	} catch (JMSException e) {
        		logger.warn(connectInfo.getMsgSelector() + " receive Response Message have exception, reconnect and receive again when not timeout!", e); 
        		while (true) {
        			try {
        				consumer = null;
                		connectInfo.closeConnection();
		        		connectInfo.createConnection();
		        		connectInfo.createSession();
		        		consumer = connectInfo.createConsumer();
		        		break;
        			} catch (JMSException e1) {
        				timeOut = deadline - System.currentTimeMillis();
        				if (timeOut > 0) {
        					logger.warn(connectInfo.getMsgSelector() + " receive Response Message have exception, reconnect and receive again when not timeout! timeOut剩余:"+ timeOut, e1);
        				} else {
        					break;
        				}
        			}
        		}
        	}
        	
        	if (rspMsg != null) {
        		 logger.debug(connectInfo.getMsgSelector() + " has received Response Message. ID="+rspMsg.getJMSCorrelationID());
        		break;
        	} else {
               	timeOut = deadline - System.currentTimeMillis();
                if (timeOut > 0) {
                    logger.warn(connectInfo.getMsgSelector() + " receive Response Message is null, receive again。timeOut剩余:"+ timeOut);
                } else {
                	break;
                }
            }
        }
        
        if (consumer != null) {
        	try {
	        	consumer.close();
	        	consumer = null;
        	} catch (Exception e) {
        	}
        }
        
		return rspMsg;
	}
	
	public AmqDestination findDestination(String resourceClass) {
		if (destinations.containsKey(resourceClass) == false) {
			return null;
		}
		return destinations.get(resourceClass);
	}

	public void setDestinations(HashMap<String, AmqDestination> destinations) {
		this.destinations = destinations;
	}

	public void setMsgConverter(MessageConverter msgConverter) {
		this.msgConverter = msgConverter;
	}
}
