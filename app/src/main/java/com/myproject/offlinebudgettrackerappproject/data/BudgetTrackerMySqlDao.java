package com.myproject.offlinebudgettrackerappproject.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerUserDto;
import com.myproject.offlinebudgettrackerappproject.util.LoginCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BudgetTrackerMySqlDao {

    private Context context;

    public BudgetTrackerMySqlDao(Context context) {
        this.context = context.getApplicationContext();
    }

    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void login(BudgetTrackerUserDto budgetTrackerUserDto, LoginCallback callback) {
        try {
            Properties properties = new Properties();
            InputStream inputStream = context.getAssets().open("server_config.properties");
            properties.load(inputStream);
            String serverUrl = properties.getProperty("server_url");
            String phpLoginFile = properties.getProperty("login_php_file");
            String loginUrl = serverUrl + phpLoginFile;
            Log.d("login_url", loginUrl);

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST, loginUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (!response.isEmpty()) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int success = jsonObject.getInt("success");
                                    if (success == 1) {
                                        callback.onSuccess();
                                    } else {
                                        callback.onFailure("Login failed");
                                    }
                                } catch (JSONException e) {
                                    Log.e("JSONException", e.toString());
                                    callback.onFailure("JSON Error: " + e.getMessage());
                                }
                            } else {
                                callback.onFailure("Empty response");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String errorMessage = "Unable to send data: " + error.toString();
                            if (error.getCause() != null) {
                                errorMessage += " Cause: " + error.getCause().toString();
                            }
                            Log.e("VolleyError", errorMessage);
                            callback.onFailure(errorMessage);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", budgetTrackerUserDto.getEmail());
                    params.put("password", hashPassword(budgetTrackerUserDto.getPassword())); // Sending hashed password
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        } catch (IOException e) {
            e.printStackTrace();
            callback.onFailure("IOException: " + e.getMessage());
        }
    }
}
