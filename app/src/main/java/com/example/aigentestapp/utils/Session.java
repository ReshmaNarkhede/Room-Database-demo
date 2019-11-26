package com.example.aigentestapp.utils;

import android.content.Context;

public class Session {

	private static SessionManagerPref manager;
	
	public Session(Context context)
	{
		if(manager==null)
		{
			manager=new SessionManagerPref(context);
		}
	}
	
	public static SessionManagerPref GetInstance(Context context)
	{
		if(manager==null)
			manager=new SessionManagerPref(context);
		return  manager;
	}

}
