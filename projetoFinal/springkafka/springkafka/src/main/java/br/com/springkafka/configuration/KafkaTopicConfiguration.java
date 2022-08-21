package br.com.springkafka.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfiguration {

    private final String topic;

    public KafkaTopicConfiguration(@Value("${topic.name}") String topic) {
        this.topic = topic;
    }

    /**
     * Usamos a lib do Kafka admin para trazer um objeto do tipo NewTopic que é do Kafk admin e criamos um tópico
     * @return
     */
    @Bean
    public NewTopic createTopic() {
        return new NewTopic(topic, 1, (short) 1);
    }
}
