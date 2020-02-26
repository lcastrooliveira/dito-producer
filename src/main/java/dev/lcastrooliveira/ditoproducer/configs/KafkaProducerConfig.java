package dev.lcastrooliveira.ditoproducer.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dtos.EventDocumentDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.databind.DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Autowired
    public ObjectMapper mapper;

    class EventSerializer implements Serializer<EventDocumentDTO> {

        @Override
        public void configure(Map configs, boolean isKey) {}

        @Override
        public byte[] serialize(String arg0, EventDocumentDTO arg1) {
            byte[] bytesVal = null;
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.disable(ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            try {
                bytesVal = mapper.writeValueAsString(arg1).getBytes();
            }catch (Exception e) {
                e.printStackTrace();
            }
            return bytesVal;
        }

        @Override
        public void close() {}
    }

    @Bean
    public ProducerFactory<String, EventDocumentDTO> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        final JsonSerializer<EventDocumentDTO> serializer = new JsonSerializer<>(mapper);
        final DefaultKafkaProducerFactory<String, EventDocumentDTO> producerFactory = new DefaultKafkaProducerFactory<>(configProps);
        producerFactory.setValueSerializer(serializer);
        return producerFactory;
    }

    @Bean
    public KafkaTemplate<String, EventDocumentDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
