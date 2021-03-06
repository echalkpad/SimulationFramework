package org.smartcampus.simulation.framework.simulator;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.Timestamp;

import org.json.JSONObject;
import org.smartcampus.simulation.framework.messages.CountRequestsPlusOne;
import org.smartcampus.simulation.framework.messages.CountResponsesPlusOne;
import org.smartcampus.simulation.framework.messages.SendValue;

/**
 * @inheritDoc This class allow to send a request HTTP
 */
public class DataSender extends DataMaker {

    public DataSender(final String output) {
        super(output);
    }

    @Override
    /**
     * @inheritDoc
     */
    public void onReceive(final Object o) throws Exception {
        if (o instanceof SendValue) {
            SendValue sendValue = (SendValue) o;
            sendData(sendValue);
        }
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
    }

    private void sendData(SendValue sendValue) throws Exception{
        JSONObject obj = new JSONObject();
        obj.put("n", sendValue.getName());
        obj.put("v", sendValue.getValue());
        obj.put("t", String.valueOf(sendValue.getTime() / 1000));

        this.log.debug(sendValue.getName() + " - " + (System.currentTimeMillis() - sendValue.getTime()));

        URL url = new URL(this.output);
        HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
        httpconn.setRequestMethod("POST");

        httpconn.setDoOutput(true);
        httpconn.setAllowUserInteraction(false);
        httpconn.setRequestProperty("charset", "utf-8");
        httpconn.setRequestProperty("Content-Length",
                "" + Integer.toString(obj.toString().getBytes().length));
        httpconn.setRequestProperty("Content-Type", "application/json");

        // We don't want to wait for the response. If you want the responses, comment the following line
        httpconn.setReadTimeout(10);

        httpconn.connect();
        DataOutputStream wr = new DataOutputStream(httpconn.getOutputStream());
        wr.writeBytes(obj.toString());
        wr.flush();
        wr.close();

        this.getSender().tell(new CountRequestsPlusOne(), this.getSelf());

        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    httpconn.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            if (httpconn.getResponseCode() != 201) {
                this.log.debug("BAD ------------------" + httpconn.getResponseMessage());
            }
            else {
                this.getSender().tell(new CountResponsesPlusOne(), this.getSelf());
            }
        }catch (SocketTimeoutException e){
        }

        httpconn.disconnect();

    }
}
