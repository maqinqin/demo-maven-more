package com.git.support.invoker.amq.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.git.support.sdo.impl.DataObject;

public class ObjectMessageConverter implements MessageConverter {

	Logger logger = LoggerFactory.getLogger(ObjectMessageConverter.class);

	@Override
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		Object object = null;
		
		if (message == null) {
			return null;
		}

		if (message instanceof ObjectMessage) {
			Object  msgContent = ((ObjectMessage) message).getObject();	
			object = (DataObject)(msgContent);
		} else {
			logger.info("message is not instanceof ObjectMessage"+message.getClass().getName());
		}
		
		return object;
	}

	@Override
	public Message toMessage(Object object, Session session) throws JMSException,
			MessageConversionException {
		ObjectMessage objMsg = session.createObjectMessage();		
		objMsg.setObject((Serializable)object);
		
		return objMsg;
	}

}
