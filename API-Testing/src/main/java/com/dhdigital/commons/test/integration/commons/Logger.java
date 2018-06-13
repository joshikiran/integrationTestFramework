package com.dhdigital.commons.test.integration.commons;

import org.slf4j.LoggerFactory;

public class Logger {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

	public void log(Global global, String message, Object... objs) {
		log(global, null, message, objs);
	}

	public void log(Global global, TestCase tc, String message, Object... objs) {
		boolean logLevel = false;
		try {
			logLevel = getLoggingLevel(global, tc);
			if (logLevel) {
				logger.info(message, objs);
			} else {
				logger.debug(message, objs);
			}
		} catch (Exception e) {
			logger.error("Exception while logging the message with details {}", e);
		}
	}

	private boolean getLoggingLevel(Global global, TestCase tc) {
		boolean logLevel = false;
		try {
			logLevel = global.isLoggingEnabled();
			if (!logLevel && null != tc)
				logLevel = tc.isLoggingEnabled();
		} catch (Exception e) {
			logger.error("Exception while getting log level for config object with details ", e);
		}
		return logLevel;
	}

}
