package com.accesshouse.kostikovo;

import com.accesshouse.kostikovo.R;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.provider.Settings;
public class ReciveSMS extends BroadcastReceiver{

	private static SmsListener mListener;
    @Override
    public void onReceive(Context context, Intent intent) {

    	if(PermissionStart.statusSMS)
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
        	PermissionStart.statusSMS=false;
            Bundle bundle = intent.getExtras(); 
            SmsMessage[] msgs = null;
            String number = null; 
            String text = null;
            if (bundle != null){

                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        number = msgs[i].getOriginatingAddress();
                  text = msgs[i].getMessageBody();
                  
                    }
                    mListener.messageReceived(number,text); 
                    abortBroadcast();
                 //   deleteSMS(context,number);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    	          intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    NotificationCompat.Builder builder =new NotificationCompat.Builder(context);
                    builder.setContentTitle("New Messge");
                    builder.setContentText("Messge");
                    builder.setSmallIcon(R.drawable.ic_launcher);
                    builder.setContentIntent(pendingIntent);
                    builder.setOngoing(true);

                    builder.build();

                }catch(Exception e){
        Log.w("Exception caught",e);
                }
            }
        }
    }
    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }

}