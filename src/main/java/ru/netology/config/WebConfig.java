package ru.netology.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig {

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(Gson gson) {
        final RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();

        final GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter(gson);

        final List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(gsonConverter);

        adapter.setMessageConverters(messageConverters);

        return adapter;
    }
}