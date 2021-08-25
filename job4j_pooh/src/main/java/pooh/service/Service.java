package pooh.service;

import pooh.Req;
import pooh.Resp;

public interface Service {
    Resp process(Req req);
}
