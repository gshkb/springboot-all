package cn.gshkb.kafka;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

public class KafkaProducer {

    public static void main(String[] args) {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        KeyedStream<String, Tuple> stringTupleKeyedStream = env.socketTextStream("127.0.0.1", 9000).keyBy(0);



//        FlinkKafkaProducer011<String> myProducer = new FlinkKafkaProducer011<String>(
//                "localhost:9092",            // broker list
//                "my-topic",                  // target topic
//                new SimpleStringSchema());   // serialization schema

// versions 0.10+ allow attaching the records' event timestamp when writing them to Kafka;
// this method is not available for earlier Kafka versions
        //myProducer.setWriteTimestampToKafka(true);

        //stream.addSink(myProducer);
    }
}
