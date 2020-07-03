package cn.gshkb.demo;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

public class Suanzi {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        SingleOutputStreamOperator<Tuple2<String, Integer>> dataStream = env.socketTextStream("localhost", 9000)
                .flatMap(new Splitter())
                .keyBy(0)
                .timeWindow(Time.hours(10))
                .sum(1);

        dataStream.print();

        env.execute("Test");
    }

   public static class Splitter implements FlatMapFunction<String, Tuple2<String,Integer>>{

       @Override
       public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
           for (String word:s.split(" ")){
               collector.collect(new Tuple2<String,Integer>(word,1));
           }
       }
   }
}
