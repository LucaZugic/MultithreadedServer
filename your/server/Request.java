// TODO: change paackage name to match yours
package your.server;

import java.io.Serializable;

public class Request implements Serializable {

    private static final long serialVersionUID = 1L;
    private String request;

    // TODO: If you decide to use Object streams, make sure to add your specific object requirmeents here

    public Request(String request) {
        this.request = request;
    }
    public String getRequest() {
        return request;
    }
}
