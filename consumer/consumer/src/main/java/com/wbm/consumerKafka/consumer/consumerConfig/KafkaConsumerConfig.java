package com.wbm.consumerKafka.consumer.consumerConfig;

import com.wbm.consumerKafka.consumer.DTO.CarDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;


    @Value(value = "${spring.kafka.group-id}")
    private String groupID;

    @Bean
    public ConsumerFactory<String, CarDTO> carConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);//Atente-se para essas classes serem do Kafka
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);//IDEM

        //Aqui retornamos um consumidor padrão, passamos como parâmetro para ele as configs, um objeto de deserealizador para chave e outro para o valor/mensagem
        //Atenção, veja sempre se a classe que vai ser da mensagem tem construtor, pois aqui vamos instanciar um objeto para deserializar
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(CarDTO.class, false));
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CarDTO> carKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CarDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(carConsumerFactory());

        return factory;
    }
}
