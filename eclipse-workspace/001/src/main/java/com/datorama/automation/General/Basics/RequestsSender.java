package com.datorama.automation.General.Basics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.datorama.automation.General.AppEnums.GeneralEnums;
import com.datorama.automation.General.AppEnums.GeneralEnums.RequestType;
import com.datorama.automation.General.requests.AsyncRequests;
/**
 * 
 * @author khalil
 *Extend the basic class that 
 */
public class RequestsSender{

	public int min;
	public int max;

	public RequestsSender(int min, int max)
	{
		this.min = min;
		this.max = max;
	}

	public void randomNew() {

		long startTime = -1;
		long stopTime = -1;

		try {

			List<String> urls = new ArrayList<String>();
			int numberOfRequests = getRandomNumberInRange(min, max);
			System.out.println("Number of requests : " + numberOfRequests);
			UrlHelper urlhelper = new UrlHelper();
			for (int i = 0; i < numberOfRequests; i++) {

				/*
				 * Checks the type of the request and adds the url to a list
				 */
				RequestType requestType = RequestType.values()[getRandomNumberInRange(0, 1)];
				String requested;
				if (requestType.equals(RequestType.Status)) {
					requested = GeneralEnums.StatusCode.values()[getRandomNumberInRange(0, 3)].getValue();

					urls.add(urlhelper.urlBuilder(requestType, requested));
				} else {
					requested = "" + getRandomNumberInRange(1, 10);
					urls.add(urlhelper.urlBuilder(requestType, requested));
				}
			}
			/*
			 * Sends all the url in thread pool
			 */
			startTime = System.currentTimeMillis();
			AsyncRequests asyncRequests = new AsyncRequests();
			asyncRequests.start(numberOfRequests);
			for (String url : urls) {
				asyncRequests.addUrl(url);
				System.out.println(url);
			}
			asyncRequests.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Start Time: " + startTime);
		stopTime = System.currentTimeMillis();
		System.out.println("Stop Time: " + stopTime);
		System.out.println("Total Time in seconds = " + (double) (stopTime - startTime) / 1000);
	}
	
	/**
	 * 
	 * @param min
	 * @param max
	 * @return randomized integer
	 */
	public static int getRandomNumberInRange(int min, int max)
	{

		Random r = new Random();
		return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
	}

}
