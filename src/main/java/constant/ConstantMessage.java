package constant;

import com.google.gson.JsonObject;

/**
 * Create by Hasan on 04/09/2019.
 */
public class ConstantMessage {

    public enum Message {
        getSuccess,
        getFailed,
        getFailedDeleteStillHasChildren,
        getNotFound,
        getOutOffStock,
        UNKNOWN;

        public JsonObject message() {
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
                case getNotFound:
                    resp.addProperty("message", "Data not Found");
                    resp.addProperty("rc", "02");
                    return resp;
                case getFailedDeleteStillHasChildren:
                    resp.addProperty("message", "Cannot delete Parent with existing Children");
                    resp.addProperty("rc", "03");
                    return resp;
                case getOutOffStock:
                    resp.addProperty("message", "Item out off stock");
                    resp.addProperty("rc", "04");
                    return resp;
                default:
                    throw new AssertionError("Unknown operations " + this);
            }
        }
    }
}
