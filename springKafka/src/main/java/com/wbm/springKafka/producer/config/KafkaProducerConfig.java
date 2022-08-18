package com.wbm.springKafka.producer.config;

import com.wbm.springKafka.controller.CarDTO;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${topic.name}")
    private String topic;

    /**
     * Através do KafkaClient esse Bean consegue criar um tópico com 3 partições e fator de replicação=1
     *
     * @return
     */
    @Bean
    public NewTopic criaTopico() {
        return new NewTopic(topic, 3, (short) 1);
    }

    /**
     * Método que cria um producer padrão de kafka e nele criamos um Map chave valor de configurações do Kafka producer
     *
     * @return
     */
    @Bean
    public ProducerFactory<String, CarDTO> carProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, CarDTO> kafkaTemplate() {
        return new KafkaTemplate<>(carProducerFactory());
    }

}
