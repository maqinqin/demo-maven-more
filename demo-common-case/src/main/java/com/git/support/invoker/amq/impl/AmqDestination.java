package com.git.support.invoker.amq.impl;

import javax.jms.ConnectionFactory;

import org.apache.activemq.command.ActiveMQQueue;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class AmqDestination {
	private final PeriodFormatter periodFormatter = 
			new PeriodFormatterBuilder()
			    .appendDays()
			    .appendSuffix("d")	
			    .appendSeparator(":")
			    .appendHours()
			    .appendSuffix("h")
			    .appendSeparator(":")
			    .appendMinutes()
			    .appendSuffix("m")
			    .appendSeparator(":")
			    .appendSeconds()
			    .appendSuffix("s")
			    .toFormatter();
	
	private ActiveMQQueue  queueRequest;
	private ActiveMQQueue  queueReply;
	private String mesgExpiration;
	private int mesgExpirationMillis;
	private ConnectionFactory connectionFactory;
	
	public ActiveMQQueue getQueueRequest() {
		return queueRequest;
	}
	
	public void setQueueRequest(ActiveMQQueue queue) {
		this.queueRequest = queue;
	}

	public ActiveMQQueue getQueueReply() {
		return queueReply;
	}

	public void setQueueReply(ActiveMQQueue queueReply) {
		this.queueReply = queueReply;
	}
	
	public String getMesgExpiration() {
		return mesgExpiration;
	}
	
	public void setMesgExpiration(String mesgExpiration) {
		this.mesgExpiration = mesgExpiration;
		this.mesgExpirationMillis = periodFormatter.parsePeriod(mesgExpiration)
				.toStandardSeconds().getSeconds()*1000;
	}
	
	public int getMesgExpirationMillis() {
		return mesgExpirationMillis;
	}
	
	public void setMesgExpirationMillis(int mesgExpirationMillis ) {
		this.mesgExpirationMillis = mesgExpirationMillis;
		this.mesgExpiration = periodFormatter.print(Period.millis(mesgExpirationMillis));
	}
	
	public ConnectionFactory getPooledConnectionFactory() {
		return connectionFactory;
	}
	
	public void setConnectionFactory(
			ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
}
