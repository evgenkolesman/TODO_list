package pooh;


public class TopicService implements Service {


    @Override
    public Resp process(Req req) {
        if (req.method().equals("POST")) {
            return post(req);
        }
        if (req.method().equals("GET")) {
            return get(req);

        }
        return null;
    }

    private Resp get(Req req) {

        return null;
    }

    private Resp post(Req req) {
        return  null;
    }
}
