package cn.gshkb.kafka;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

public class KafkaConsumer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        // 仅 Kafka 0.8 需要
        // properties.setProperty("zookeeper.connect", "localhost:2181");
        properties.setProperty("group.id", "test");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(5000);
        DataStream<String> stream = env
                .addSource(new FlinkKafkaConsumer<>("topic", new SimpleStringSchema(), properties));
    }
}
