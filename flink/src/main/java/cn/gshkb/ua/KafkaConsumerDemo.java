package cn.gshkb.ua;

import com.google.common.collect.Lists;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Properties;

public class KafkaConsumerDemo {

    public static void main(String[] args) {

        Properties kafkaPropertie = new Properties();
        //配置broker地址，配置多个容错
        kafkaPropertie.setProperty("bootstrap.servers", "127.0.0.1:9092");
        kafkaPropertie.setProperty("group.id", "test");
        //配置key-value允许使用参数化类型
        kafkaPropertie.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaPropertie.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(kafkaPropertie);
        kafkaConsumer.subscribe(Lists.newArrayList("uv-topic1"));
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
               System.out.printf("offset = %d, key = %s, value = %s" + "\n", record.offset(), record.key(), record.value());
               // System.out.println(records.toString());
            }
        }


    }
}
