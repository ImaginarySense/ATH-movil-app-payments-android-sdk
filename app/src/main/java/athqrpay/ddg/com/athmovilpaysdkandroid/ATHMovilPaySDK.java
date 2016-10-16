package athqrpay.ddg.com.athmovilpaysdkandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;


public class ATHMovilPaySDK {

    public void requestPayment(float amount, String phone_number, String reference_id, String url_callback, Activity activity) {

        //Generate intent and set mode to send so that ath demo app receives the info
        String url = "athmovil://requestPayment?amount=" + String.valueOf(amount) +
                "&phoneNumber=" + phone_number +
                "&referenceId=" + reference_id +
                "&urlCallback=" + url_callback;

        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            activity.startActivity(i);

            Toast toast = Toast.makeText(activity.getApplicationContext(), "Requesting payment...", Toast.LENGTH_LONG);
            toast.show();

        } catch (Exception e) {
            Toast toast = Toast.makeText(activity.getApplicationContext(), "ATH Movil not available!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public String verifyPayment(String transaction_receipt) {

        //Convert string to JSON object and catch any exceptions
        JSONObject jsonObject = new JSONObject();
        Response response = new Response() {
            @Override
            public int getStatus() {
                return 0;
            }

            @Override
            public StatusType getStatusInfo() {
                return null;
            }

            @Override
            public Object getEntity() {
                return null;
            }

            @Override
            public <T> T readEntity(Class<T> entityType) {
                return null;
            }

            @Override
            public <T> T readEntity(GenericType<T> entityType) {
                return null;
            }

            @Override
            public <T> T readEntity(Class<T> entityType, Annotation[] annotations) {
                return null;
            }

            @Override
            public <T> T readEntity(GenericType<T> entityType, Annotation[] annotations) {
                return null;
            }

            @Override
            public boolean hasEntity() {
                return false;
            }

            @Override
            public boolean bufferEntity() {
                return false;
            }

            @Override
            public void close() {

            }

            @Override
            public MediaType getMediaType() {
                return null;
            }

            @Override
            public Locale getLanguage() {
                return null;
            }

            @Override
            public int getLength() {
                return 0;
            }

            @Override
            public Set<String> getAllowedMethods() {
                return null;
            }

            @Override
            public Map<String, NewCookie> getCookies() {
                return null;
            }

            @Override
            public EntityTag getEntityTag() {
                return null;
            }

            @Override
            public Date getDate() {
                return null;
            }

            @Override
            public Date getLastModified() {
                return null;
            }

            @Override
            public URI getLocation() {
                return null;
            }

            @Override
            public Set<Link> getLinks() {
                return null;
            }

            @Override
            public boolean hasLink(String relation) {
                return false;
            }

            @Override
            public Link getLink(String relation) {
                return null;
            }

            @Override
            public Link.Builder getLinkBuilder(String relation) {
                return null;
            }

            @Override
            public MultivaluedMap<String, Object> getMetadata() {
                return null;
            }

            @Override
            public MultivaluedMap<String, String> getStringHeaders() {
                return null;
            }

            @Override
            public String getHeaderString(String name) {
                return null;
            }
        };
        try {
            jsonObject = new JSONObject(transaction_receipt);
            Client client = ClientBuilder.newClient();
            Entity payload = Entity.json("{  'token': " + "'" + jsonObject.getJSONObject("token") + "'," +
                    "  'referenceNumber': " + "'" + jsonObject.getJSONObject("referenceNumber") + "'}");
            response = client.target("http://athmapi.westus.cloudapp.azure.com/athm/verifyPaymentStatus")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(payload);

            Log.v("status", String.valueOf(response.getStatus()));
            Log.v("headers", String.valueOf(response.getHeaders()));
            Log.v("body:", response.readEntity(String.class));

        } catch (Exception e) {
            Log.e("ERROR!", "Exception generated!\n" + e);
        }

        return response.readEntity(String.class);

//        if(response.readEntity(String.class).contains("SUCCESS")) {
//            return "Transaction completed!";
//        } else {
//            return "Transaction failed!";
//        }


    }
}
