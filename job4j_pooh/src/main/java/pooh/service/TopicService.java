package pooh.service;

import pooh.Req;
import pooh.Resp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> > userMap = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        if (req.mode().split("/")[1].equals("topic")) {
            String name = req.mode().split("/")[2];


            queue.get(name).add(req.text());
            if (req.method().equals("POST")) {
                return post(req);
            }
            if (req.method().equals("GET")) {
                return get(req);
            }
        }
        return new Resp("BadRequest",
                400);
    }

    private Resp get(Req req) {
        String name = req.mode().split("/")[2];
        String id = req.mode().split("/")[3];
        userMap.putIfAbsent(id, queue);
        return new Resp(queue.get(name).poll(), 200);
    }

    private Resp post(Req req) {
        String name = req.mode().split("/")[2];
        queue.putIfAbsent(name, new ConcurrentLinkedQueue<>());
        return new Resp("OK", 200);
    }
}
