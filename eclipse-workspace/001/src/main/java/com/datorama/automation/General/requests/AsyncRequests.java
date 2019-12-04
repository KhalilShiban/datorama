package com.datorama.automation.General.requests;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
/**
 * 
 * @author khalil
 *This class handle the thread pool and manage the threaded-API-requests 
 */

public class AsyncRequests {

    private ExecutorService executor = Executors.newFixedThreadPool(5);
    public StringBuffer allStories = new StringBuffer();
    public ArrayList<Future<HttpURLConnection>> allRequests = new ArrayList<Future<HttpURLConnection>>();

    /*
     * start the executers
     */
    public void start(int threadNumber) {
        executor = Executors.newFixedThreadPool(threadNumber);
    }

    /*
     * Terminate the executers
     */
    public void shutdown() {
    	this.allRequests.forEach(request -> {
    		try {
				JSONObject response = ApisUtils.handleApiResponse(request.get());
				if(response.has("story")) {
					allStories.append(response.get("story"));
            	}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
    	});
    	
        executor.shutdown();
        try {
            if(!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        }
        catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /*
     * Adds requests to the executer
     */
    public void addUrl(String destinationUrl) {
        this.allRequests.add(executor.submit(ApisUtils.createCallable(destinationUrl)));
    }
}
