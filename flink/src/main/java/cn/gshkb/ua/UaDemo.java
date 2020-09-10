package cn.gshkb.ua;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.state.*;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.evictors.TimeEvictor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.ContinuousProcessingTimeTrigger;
import org.apache.flink.streaming.api.windowing.triggers.CountTrigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;
public class UaDemo {

    public static final DateTimeFormatter TIME_FORMAT_YYYY_MM_DD_HHMMSS = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public static void main(String[] args) throws Exception {

        Logger.getLogger("org").setLevel(Level.FINE);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        // 仅 Kafka 0.8 需要
        // properties.setProperty("zookeeper.connect", "localhost:2181");
        properties.setProperty("group.id", "test");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(5000);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>("uv-topic2", new SimpleStringSchema(), properties);


        DataStream<String> detailStream = env.addSource(consumer).name("uv-pv_log").disableChaining();
        //detailStream.print();
        DataStream<Tuple2<UMessage, Integer>> detail = detailStream.map(new MapFunction<String, Tuple2<UMessage, Integer>>() {
            @Override
            public Tuple2<UMessage, Integer> map(String value) throws Exception {
                try {
                    UMessage uMessage = com.alibaba.fastjson.JSON.parseObject(value, UMessage.class);
                    return Tuple2.of(uMessage, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return Tuple2.of(null, null);
            }
        }).filter(s -> s != null && s.f0 != null).assignTimestampsAndWatermarks(new AscendingTimestampExtractor<Tuple2<UMessage, Integer>>() {
            @Override
            public long extractAscendingTimestamp(Tuple2<UMessage, Integer> element) {
                LocalDate localDate = LocalDate.parse(element.f0.getCreateTime(), TIME_FORMAT_YYYY_MM_DD_HHMMSS);
                long timestamp = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
                return timestamp;
            }
        });
        // 测试一
        //testOne(detail);
        testTwo(detail);
        env.execute("UV、PV");
    }

    public static void testOne(DataStream<Tuple2<UMessage, Integer>> detail) {
        DataStream<Tuple2<String, Integer>> statsResult = detail.windowAll(TumblingEventTimeWindows.of(Time.days(1), Time.hours(-8)))
              // .trigger(ContinuousProcessingTimeTrigger.of(Time.seconds(10)))
                .trigger(CountTrigger.of(1))
                .process(new ProcessAllWindowFunction<Tuple2<UMessage, Integer>, Tuple2<String, Integer>, TimeWindow>() {
                    @Override
                    public void process(Context context, Iterable<Tuple2<UMessage, Integer>> elements, Collector<Tuple2<String, Integer>> out) throws Exception {
                        Set<String> uvNameSet = new HashSet<String>();
                        Integer pv = 0;
                        Iterator<Tuple2<UMessage, Integer>> mapIterator = elements.iterator();
                        while (mapIterator.hasNext()) {
                            pv += 1;
                            String uvName = mapIterator.next().f0.getUid();
                            uvNameSet.add(uvName);
                        }
                        out.collect(Tuple2.of("uv", uvNameSet.size()));
                        out.collect(Tuple2.of("pv", pv));
                    }
                });

        statsResult.print();

    }

    public static void testTwo(DataStream<Tuple2<UMessage, Integer>> detail) {

        //写法二
        DataStream<Tuple3<String, String, Integer>> statsResult = detail.keyBy(new KeySelector<Tuple2<UMessage, Integer>, String>() {
            @Override
            public String getKey(Tuple2<UMessage, Integer> value) throws Exception {
                return value.f0.getUid();
            }
        }).window(TumblingEventTimeWindows.of(Time.days(1), Time.hours(-8)))
      //  }).window(TumblingEventTimeWindows.of(Time.minutes(1)))
//               .trigger(ContinuousProcessingTimeTrigger.of(Time.seconds(10)))
                .trigger(CountTrigger.of(1))
                .evictor(TimeEvictor.of(Time.seconds(0), true))
                .process(new ProcessWindowFunction<Tuple2<UMessage, Integer>, Tuple3<String, String, Integer>, String, TimeWindow>() {
                    private transient MapState<String, String> uvCountState;
                    private transient ValueState<Integer> pvCountState;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        super.open(parameters);
                        StateTtlConfig ttlConfig = StateTtlConfig.newBuilder(org.apache.flink.api.common.time.Time.minutes(60 * 6))
                                .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
                                .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired).build();
                        MapStateDescriptor<String, String> uvDescriptor = new MapStateDescriptor<String, String>("uv_count", String.class, String.class);
                        ValueStateDescriptor<Integer> pvDescriptor = new ValueStateDescriptor<Integer>("pv_count", Integer.class);
                        uvDescriptor.enableTimeToLive(ttlConfig);
                        pvDescriptor.enableTimeToLive(ttlConfig);
                        uvCountState = getRuntimeContext().getMapState(uvDescriptor);
                        pvCountState = getRuntimeContext().getState(pvDescriptor);
                    }

                    @Override
                    public void process(String key, Context context, Iterable<Tuple2<UMessage, Integer>> elements, Collector<Tuple3<String, String, Integer>> out) throws Exception {
                        Integer pv = 0;
                        Iterator<Tuple2<UMessage, Integer>> mapIterator = elements.iterator();
                        while (mapIterator.hasNext()) {
                            pv += 1;
                            UMessage uMessage = mapIterator.next().f0;
                            String uvName = uMessage.getUid();
                            uvCountState.put(uvName, null);
                        }
                        Integer uv = 0;
                        Iterator<String> uvIterator = uvCountState.keys().iterator();
                        while (uvIterator.hasNext()) {
                            uvIterator.next();
                            uv += 1;
                        }
                        Integer originPv = pvCountState.value();
                        if (originPv == null) {
                            pvCountState.update(pv);
                        } else {
                            pvCountState.update(originPv + pv);
                        }
                        out.collect(Tuple3.of(key, "uv", uv));
                        out.collect(Tuple3.of(key, "pv", pvCountState.value()));
                    }
                });
        statsResult.print();
    }

    public void testThree(DataStream<Tuple2<UMessage, Integer>> detail) {

        DataStream<Tuple3<String, String, Integer>> statsResult = detail.keyBy(new KeySelector<Tuple2<UMessage, Integer>, String>() {
            @Override
            public String getKey(Tuple2<UMessage, Integer> value) throws Exception {
                return value.f0.getUid();
            }
        }).window(TumblingEventTimeWindows.of(Time.days(1), Time.hours(-8)))
//               .trigger(ContinuousProcessingTimeTrigger.of(Time.seconds(10)))
                .trigger(CountTrigger.of(1))
                .evictor(TimeEvictor.of(Time.seconds(0), true))
                .process(new ProcessWindowFunction<Tuple2<UMessage, Integer>, Tuple3<String, String, Integer>, String, TimeWindow>() {


                    private transient ValueState<BloomFilter<String>> boomFilterState;
                    private transient ValueState<Integer> uvCountState;
                    private transient ValueState<Integer> pvCountState;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        super.open(parameters);
                        StateTtlConfig ttlConfig = StateTtlConfig.newBuilder(org.apache.flink.api.common.time.Time.minutes(60 * 6))
                                .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
                                .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired).build();
                        ValueStateDescriptor<BloomFilter<String>> boomFilterDescriptor
                                = new ValueStateDescriptor<BloomFilter<String>>("boom_filter", TypeInformation.of(new TypeHint<BloomFilter<String>>() {
                        }));
                        ValueStateDescriptor<Integer> pvDescriptor = new ValueStateDescriptor<Integer>("pv_count", Integer.class);
                        ValueStateDescriptor<Integer> uvDescriptor = new ValueStateDescriptor<Integer>("uv_count", Integer.class);
                        boomFilterDescriptor.enableTimeToLive(ttlConfig);
                        pvDescriptor.enableTimeToLive(ttlConfig);
                        uvDescriptor.enableTimeToLive(ttlConfig);
                        boomFilterState = getRuntimeContext().getState(boomFilterDescriptor);
                        pvCountState = getRuntimeContext().getState(pvDescriptor);
                        uvCountState = getRuntimeContext().getState(uvDescriptor);
                    }

                    @Override
                    public void process(String key, Context context, Iterable<Tuple2<UMessage, Integer>> elements
                            , Collector<Tuple3<String, String, Integer>> out) throws Exception {
                        Integer uv = uvCountState.value();
                        Integer pv = pvCountState.value();
                        BloomFilter<String> bloomFilter = boomFilterState.value();
                        if (bloomFilter == null) {
                            bloomFilter = BloomFilter.create(Funnels.unencodedCharsFunnel(), 10 * 1000 * 1000L);
                            uv = 0;
                            pv = 0;
                        }

                        Iterator<Tuple2<UMessage, Integer>> mapIterator = elements.iterator();
                        while (mapIterator.hasNext()) {
                            pv += 1;
                            UMessage uMessage = mapIterator.next().f0;
                            String uid = uMessage.getUid();
                            if (!bloomFilter.mightContain(uid)) {
                                bloomFilter.put(uid); //不包含就添加进去
                                uv += 1;
                            }
                        }

                        boomFilterState.update(bloomFilter);
                        uvCountState.update(uv);
                        pvCountState.update(pv);

                        out.collect(Tuple3.of(key, "uv", uv));
                        out.collect(Tuple3.of(key, "pv", pv));
                    }
                });
        statsResult.print();
    }

}
