package com.datorama.automation.question2;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.datorama.automation.General.conts.AppConsts;
import com.datorama.automation.General.requests.ApisUtils;
import com.datorama.automation.General.requests.AsyncRequests;

public class Main1 {

    public static void main(String[] args) {

        AsyncRequests asyncRequests = new AsyncRequests();
        List<String> urls = new ArrayList<String>();
        urls.add(AppConsts.PTSV2_HOST +  "/t/datoerror/post");
        urls.add(AppConsts.PTSV2_HOST + "/t/datotest1/post");
        urls.add(AppConsts.PTSV2_HOST + "/t/datotest2/post");
        urls.add(AppConsts.PTSV2_HOST + "/t/datotest3/post");
        urls.add(AppConsts.POSTMAN_ECHO_HOST + "/delay/10");
    	StringBuffer stories = new StringBuffer();
        urls.parallelStream().forEach(url -> {
            try {
            	HttpURLConnection response = ApisUtils.createCallable(url).call();
            	JSONObject responseJson= ApisUtils.handleApiResponse(response);
            	if(responseJson.has("story")) {
            		stories.append(responseJson.get("story"));
            	}	
            }
            catch (Exception e) {
                System.out.println(e.toString());
            }

        });
        System.out.println(stories.toString());
        asyncRequests.start(AppConsts.THREADS_NUMBER);
        asyncRequests.allStories = new StringBuffer();
        asyncRequests.addUrl(urls.get(0));
        asyncRequests.addUrl(urls.get(1));
        asyncRequests.addUrl(urls.get(2));
        asyncRequests.addUrl(urls.get(3));
        asyncRequests.addUrl(urls.get(4));
        asyncRequests.shutdown();
        System.out.println(asyncRequests.allStories.toString());

    }
}
