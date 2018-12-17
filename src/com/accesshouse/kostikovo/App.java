package com.accesshouse.kostikovo;


import android.app.Application;
import android.content.Context;
import android.content.Intent;


public class App extends Application{
static App Inst;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Inst=this;
Intent i = new Intent(this, RegistrationService.class);
       startService(i);
        i = new Intent(this, NotificationsListenerService.class);
        startService(i);
	}

Context getInstance(){
	return Inst;
}
}
