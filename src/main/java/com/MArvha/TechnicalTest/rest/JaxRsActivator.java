package com.MArvha.TechnicalTest.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**Resources are served relative to the servlet path specified in the {@ApplicationPath} */
@ApplicationPath("/rest")
public class JaxRsActivator extends Application {}
