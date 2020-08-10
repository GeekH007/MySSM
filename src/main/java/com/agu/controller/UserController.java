package com.agu.controller;

import java.util.List;
import com.agu.domain.User;
//import com.agu.kafka.KafkaConsumerDemo;
//import com.agu.kafka.KafkaProducerDemo;
//import com.agu.manager.KafkaManager;
import com.agu.service.UserService;
//import com.agu.util.redis.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

//    @Resource(name = "kafkaProducerDemo")
//    KafkaProducerDemo producer;
//
//    @Resource(name = "kafkaConsumerDemo")
//    KafkaConsumerDemo consumer;
    @Resource
    private KafkaTemplate<Integer, String> kafkaTemplate;




    @RequestMapping("list")
    public String test1(Model model) throws InterruptedException {
        List<User> users = null;

        //users = (List<User>)redisTemplate.opsForValue().get("list");
        if(null == users || users.size() == 0){
            users = userService.findAllUsers();
            //redisTemplate.opsForValue().set("list", users);
            //System.out.println("写入缓存");
        }

        JSONObject body = new JSONObject();
        body.put("id","10");
        body.put("userName","cs");
        body.put("name","测试");
        body.put("age","20");
        //kafka
        //KafkaManager.getInstance().sendMessage(KafkaManager.TOPIC_LOGIN,body);
        //producer.sendMessage("测试123");
        kafkaTemplate.sendDefault("测试123");
        System.out.println("发送成功");
        LOGGER.info("123"+"发送成功1");
        //Thread.sleep(5000);
//        String msg = consumer.receive();
//        System.out.println("内容:"+msg);

        model.addAttribute("userList", users);
        return "users";
    }

    @RequestMapping("list2")
    public String test2(Model model) {
        List<User> users = null;
        String msg = "";
        //String msg = consumer.receive();
        //System.out.println("内容:"+msg);
        return msg;
    }

    @RequestMapping("webS")
    public String webS(Model model) {
        return "webS";
    }
}

















