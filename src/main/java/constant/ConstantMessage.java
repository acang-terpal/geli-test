package constant;

import com.google.gson.JsonObject;

/**
 * Create by Hasan on 04/09/2019.
 */
public class ConstantMessage {

    public enum Pesan {
        getSuccess,
        getFailed,
        UNKNOWN;

        public JsonObject pesan() {
            JsonObject resp = new JsonObject();
            switch (this) {
                case getSuccess:
                    resp.addProperty("message", "Success");
                    resp.addProperty("rc", "00");
                    return resp;
                case getFailed:
                    resp.addProperty("message", "Failed");
                    resp.addProperty("rc", "01");
                    return resp;
                default:
                    throw new AssertionError("Unknown operations " + this);
            }
        }
    }
}
