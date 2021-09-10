package pooh.service;

import org.apache.log4j.Logger;
import pooh.Req;
import pooh.Resp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * TopicService
 * Service with mode name "topic"
 * get`s the response and make the request
 * two method`s
 *
 * @version 1.0
 * @post - realization of post request
 * @get - realization of get request
 * @Kolesnikov Evgeniy
 */
public class TopicService implements Service {

    Logger logger = Logger.getLogger(TopicService.class);
    ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> userMap = new ConcurrentHashMap<>();

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
        try {
            if (req.text() != null) {
                queue.get(id).add(req.text());
            } else {
                queue.get(id).add("Новая тема");
            }
            userMap.putIfAbsent(id, queue);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Resp("Bad request",
                    400);
        }

        return new Resp(queue.get(name).poll(), 200);
    }

    private Resp post(Req req) {
        String name = req.mode().split("/")[2];
        try {
            queue.putIfAbsent(name, new ConcurrentLinkedQueue<>());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Resp("Bad request",
                    400);
        }
        return new Resp("OK", 200);
    }
}
