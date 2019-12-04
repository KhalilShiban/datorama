package com.datorama.automation.General.requests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.json.JSONObject;

import com.datorama.automation.General.AppEnums.GeneralEnums.StatusCode;

/**
 * 
 * @author khalil
 *Handle the callable api-requests
 *Handle the api-responses
 *JSON object handler
 */
public class ApisUtils {

	/*
	 * Calls the API-requests , then handle the cached tag from the header
	 * Returns callable object
	 */
    public static Callable<HttpURLConnection> createCallable(final String destinationUrl) {
        return new Callable<HttpURLConnection>() {

            public HttpURLConnection call() throws Exception {
                URL url = null;

                url = new URL(destinationUrl);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();

                //Todo add constant
                try {
                    if(CachedApisHelper.getInstance().etags.containsKey(destinationUrl)) {
                        String delayEtag = CachedApisHelper.getInstance().etags.get(destinationUrl);
                        c.setRequestProperty("If-None-Match", delayEtag);
                        c.setRequestProperty("Accept-Encoding", "utf-8");
                    }
                    c.setUseCaches(true);
                    c.setDefaultUseCaches(true);
                    c.setRequestMethod("GET");
                    c.setRequestProperty("Content-Type", "application/json");

                    //c.setDoOutput(true);
                    c.connect();
                    

                    Map<String,List<String>> headers = c.getHeaderFields();

                    if(headers.containsKey("ETag")) {
                        CachedApisHelper.getInstance().etags.put(destinationUrl, headers.get("ETag").get(0));
                    }
                    return c;
                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
                    String exceptionAsString = sw.toString();
                    System.out.println(exceptionAsString);
                }
                return c;
            }
        };
    }

    /**
     * This method return objects of JSON got the the response.
     * @param HTTP URL connection string
     * @return JSON object 
     */
    public static JSONObject handleApiResponse(HttpURLConnection c) {
    	JSONObject myResponse = new JSONObject();
    	try {
	    	if ( (StatusCode.status200.getNumericValue() <= c.getResponseCode() && c.getResponseCode() <= 299) || c.getResponseCode() == StatusCode.status304.getNumericValue()) {
	            BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
	            myResponse = new JSONObject(br.lines().collect(Collectors.joining()));
	
	        } else {
	            BufferedReader br = new BufferedReader(new InputStreamReader(c.getErrorStream()));
	            myResponse = new JSONObject(br.lines().collect(Collectors.joining()));
	        }
    	}
    	catch(Exception e) {
    		
    	}
    	return myResponse;
    }
}
