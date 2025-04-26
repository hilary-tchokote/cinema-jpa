package cinemajpa.util;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class JacksonConfig {

//    private final ObjectMapper mapper;
//
//    public JacksonConfig(ObjectMapper mapper) {
//        this.mapper = mapper;
//    }

    @Bean
    public SimpleModule lenientLocalDateModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDate.class, new LenientLocalDateDeserializer());
        //module.addDeserializer(Integer.class, new LenientIntegerDeserializer());
        return module;
    }

    @Bean
    public SimpleModule lenientIntegerModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Integer.class, new LenientIntegerDeserializer());
        return module;
    }

}
