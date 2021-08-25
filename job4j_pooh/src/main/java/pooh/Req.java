package pooh;

import java.text.MessageFormat;

public class Req {

    private final String method;
    private final String mode;
    private final String text;

    private Req(String method, String mode, String text) {
        this.method = method;
        this.mode = mode;
        this.text = text;
    }

    //test mode method need to be deleted after completing program
    public static void main(String[] args) {

        System.out.println(Req.of("POST /queue/weather -d 'temperature=18'").toString());
        System.out.println(Req.of("GET /queue/weather"));
    }

    public static Req of(String content) {
        String method = content.split(" ")[0];
        String mode = content.split( " ")[1];
        String text = "";

        if (method.equals("POST")) {
        text = content.split(" ")[3]; }

        return new Req(method, mode, text);
    }

    public String method() {
        return method;
    }

    public String mode() {
        return mode;
    }

    public String text() {
        return text;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Req'{'method=''{0}'', mode=''{1}'', text=''{2}'''}'",
                method, mode, text);
    }
}
