package com.agu.manager;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * 生产者
 */
public class KafkaManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaManager.class);

    //private static final String BOOTSTRAP_SERVERS = Config.getMqConfig("kafka.bootstrap.servers");

    private static final String BOOTSTRAP_SERVERS = "127.0.0.1:9092";
    public static final String TOPIC_LOGIN = "Topic_login";
    public static final String TOPIC_OLD_API = "Topic_api_use";
    public static final String TOPIC_COURSE_VISIT = "Topic_course_visit";

    private boolean kafkaAvailable = true;

    private KafkaProducer<String, String> producer;

    private KafkaManager() {
        this.initManger();
    }

    private static class SingletonManager {
        private static final KafkaManager singletonObj = new KafkaManager();
    }

    public static KafkaManager getInstance() {
        return KafkaManager.SingletonManager.singletonObj;
    }

    private void initManger() {
        try {
            Properties producerProperties = new Properties();
            //设置接入点，请通过控制台获取对应 Topic 的接入点
            producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
            //消息队列 Kafka 消息的序列化方式
            producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            //请求的最长等待时间
            producerProperties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 30 * 1000);
            producerProperties.put("group.id", "0");
            this.producer = new KafkaProducer<String, String>(producerProperties);

        } catch (Exception ex) {
            //LOGGER.error("创建kafka失败",ex);
            ex.printStackTrace();
            System.out.println("创建kafka失败");
            this.kafkaAvailable = false;
        }
    }

    protected void finalize () {
        if (null != this.producer) {
            this.producer.close();
        }
    }

    public boolean sendMessage(String topic, JSONObject body) {
        if (false == this.kafkaAvailable) {
            return false;
        }
        boolean succeed = true;
        try {
            ProducerRecord<String, String> kafkaMessage =  new ProducerRecord<String, String>(topic, body.toJSONString());

            //发送消息，并获得一个 Future 对象
            Future<RecordMetadata> metadataFuture = this.producer.send(kafkaMessage);
            LOGGER.info("消息发送成功");
        } catch (Exception e) {
            LOGGER.error("发送kafka失败",e);
            succeed = false;
        }
        return succeed;
    }

}
