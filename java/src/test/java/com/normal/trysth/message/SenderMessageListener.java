package com.normal.trysth.message;

import com.google.common.collect.Maps;

import javax.xml.ws.Endpoint;
import java.util.List;
import java.util.Map;

/**
 * @author hf
 * @date 18-8-14
 */
public class SenderMessageListener {
    Map<Integer,List<SendTemplate>> map = Maps.newHashMap();

    public void onHappen(int type, Object args) {
        List<SendTemplate> sendTemplates = map.get(type);
        for (SendTemplate sendTemplate : sendTemplates) {
            sendTemplate.send(args);
        }
    }
    //send to who
    static class EndPoint {

    }
    // send template
    static abstract class SendTemplate{
        abstract Endpoint endpoint();

        abstract String template();

        public abstract void send(Object args);
    }
    //send template impl

}
