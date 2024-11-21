package com.yndw.dvp.common.core.filter;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.EnumSet;

public class WebInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addListener(RequestContextListener.class);

        FilterRegistration.Dynamic XssfilterRegistration = servletContext.addFilter("XssSqlFilter",MyXssFilter.class);
        XssfilterRegistration.addMappingForUrlPatterns(EnumSet.of(
                DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false, "/*");

    }
}
