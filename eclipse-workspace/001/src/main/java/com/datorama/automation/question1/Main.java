package com.datorama.automation.question1;

import com.datorama.automation.General.Basics.RequestsSender;
import com.datorama.automation.General.requests.RequestSender;


public class Main
{
	public static RequestSender senders[];
	
	public static void main(String[] args)
	{
		/*
		 * Send a random number of requests to execute
		 */
		RequestsSender tm = new RequestsSender(1, 30);
		tm.randomNew();
	}
}
