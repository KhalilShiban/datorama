package com.datorama.automation.General;


public class GeneralEnums{


	public enum RequestType
	{
		Delay,
		Status
	}
	public enum StatusCode
	{
		status200,status404,status500,status503;

		public String getValue() 
		{
			switch (this)
			{
			case status200:
				return "200";

			case status404:
				return "404";

			case status500:
				return "500";

			case status503:
				return "503";

			}
			return "Status code was not found";
		}
	}

}

