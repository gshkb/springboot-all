package cn.gshkb.ua;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

public class KafkaSend {

    public static void main(String[] args) {

        Properties kafkaPropertie = new Properties();
        //配置broker地址，配置多个容错
        kafkaPropertie.put("bootstrap.servers", "127.0.0.1:9092");
        kafkaPropertie.put("group.id", "test");
        //配置key-value允许使用参数化类型
        kafkaPropertie.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaPropertie.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer kafkaProducer = new KafkaProducer(kafkaPropertie);
        //创建消息对象，第一个为参数topic,第二个参数为key,第三个参数为value

        for (int i = 0; i < 99999; i++) {

            UMessage uMessage = new UMessage();
            uMessage.setUid(i + "");
            uMessage.setCreateTime("2020-09-09");
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("uv-topic2", "uv", JSON.toJSONString(uMessage));
            //异步发送消息。异常时打印异常信息或发送结果

            kafkaProducer.send(record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e != null) {
                        System.out.println(e.getMessage());
                    } else {
                        //  System.out.println("接收到返回结果：" + recordMetadata);
                    }
                }
            });
            //异步发送消息时必须要flush,否则发送不成功，不会执行回调函数
            kafkaProducer.flush();


        }
    }
}
