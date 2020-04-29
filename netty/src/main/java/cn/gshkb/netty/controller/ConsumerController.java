package cn.gshkb.netty.controller;

import cn.gshkb.netty.pord.NettyClient;
import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ConsumerController {

    @Autowired
    private NettyClient nettyClient;

    @GetMapping("/send")
    public String send() {
        /*MessageBase.Message message = new MessageBase.Message()
                .toBuilder().setCmd(MessageBase.Message.CommandType.NORMAL)
                .setContent("hello server")
                .setRequestId(UUID.randomUUID().toString()).build();
        nettyClient.sendMsg(message);*/
        return "send ok";
    }

}
