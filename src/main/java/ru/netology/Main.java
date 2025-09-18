package ru.netology;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper; // Добавили импорт Wrapper
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws LifecycleException, IOException {
        final Tomcat tomcat = new Tomcat();
        final Path tempDir = Files.createTempDirectory("tomcat-temp");
        tomcat.setBaseDir(tempDir.toAbsolutePath().toString());

        final Connector connector = new Connector();
        connector.setPort(9999);
        tomcat.setConnector(connector);

        final Context context = tomcat.addContext("", tempDir.toAbsolutePath().toString());

        final AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.scan("ru.netology");
        applicationContext.refresh();

        final DispatcherServlet servlet = new DispatcherServlet(applicationContext);

        final Wrapper dispatcherServletWrapper = context.createWrapper();
        dispatcherServletWrapper.setName("main");
        dispatcherServletWrapper.setServlet(servlet);
        dispatcherServletWrapper.setLoadOnStartup(1);
        context.addChild(dispatcherServletWrapper);
        context.addServletMappingDecoded("/", "main");

        tomcat.start();
        tomcat.getServer().await();
    }
}