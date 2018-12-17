package com.accesshouse.kostikovo;
import java.util.ArrayList;


import com.accesshouse.kostikovo.R;


import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;



public class TelephonyM extends Activity{
	String telephony;
static	String in="";EditText ET;
	ViewGroup fin;String	f="";
	static String outStr;
static PostListener postListener;
static TelephonyM aa;
public static void onFinal(){
	if(aa!=null)
	aa.finish();
}
public static void onList(PostListener poster){
	postListener=poster;
	if(postListener!=null)postListener.messageReceived(outStr);
	
}
ArrayList<String> aaq;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		aa=this;
		telephony=getIntent().getStringExtra("telephony");
		 aaq = PermissionStart.OpenHelper.getReadText();
		
		if(aaq.size()!=0)
			if(aaq.get(2).length()>2)finish();
	
		
		ReciveSMS.bindListener(new SmsListener() {
			
			@Override
			public void messageReceived(String number, String messageText) {
				// TODO Auto-generated method stub
				if(number!=null){
					Log.e("", String.valueOf(number + "  " +messageText));
					if(messageText.contains(String.valueOf(	num)) ){
						
						in=number;
						Intent intent = new Intent();
						 intent.putExtra("telephony", in); 
						setResult(RESULT_OK, intent);
						Toast.makeText(TelephonyM.this, " Успешно ", Toast.LENGTH_LONG).show();

	PermissionStart.OpenHelper.insertAndSend(in, PermissionStart.mTelephonyMgr.getSimSerialNumber());
							finish();
					}
				}
			}
		});
		
		setContentView(R.layout.telephone);
		fin = (ViewGroup) findViewById(R.id.ProgressBar);
		ET=(EditText) findViewById(R.id.phone_number);
	}

@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}

public void OkR(View v){
	
	
		in="+7"+ET.getText().toString();
	gogo();
	
}
	
EditText view;
	private void numinput() {
		// TODO Auto-generated method stub
		
			
		
				 view=new EditText(getApplicationContext());
				 view.setInputType(InputType.TYPE_CLASS_NUMBER);
				 view.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)});
				 AlertDialog.Builder alert= new AlertDialog.Builder(TelephonyM.this);
				
	                alert.setTitle("Введите код смс");
	               
					alert.setView(view);
	                alert.setNegativeButton("Оk", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int whichButton) {
	                    	
	                  if(  view.getText().toString().equals(""+num)){
	                	 
							 Intent intent=new Intent();
							intent.putExtra("telephony", in); 
							setResult(RESULT_OK, intent);         		
	                    		
	                    	}
	                  else {
	                	  outStr=view.getText().toString();
							onList(postListener);	
                  	}
	                 finish();
	                TelephonyM.this.finish();
	                    }
 
	                });

	             alert.show();			
	
			}
static	int num;
	
	private  void gogo(){
		
		fin.post(new Runnable() {
			
			@Override
			public void run() {
		fin.setVisibility(View.VISIBLE);
		
	}
});


		num=	(int) (Math.random()*100000);
		
				// TODO Auto-generated method stub
				SmsManager sms = SmsManager.getDefault();	
				PermissionStart.statusSMS=true;
				sms.sendTextMessage(in, null, String.valueOf(	num), null, null);	
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {

						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
			fin.post(new Runnable() {
							
							@Override
							public void run() {
							
							numinput();
							}});  
						
					}
				}).start();

		
	}
	

void endProgressBar(){
	fin.getHandler().post(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			findViewById(R.id.ProgressBar).setVisibility(View.GONE);			
		}
	});
	
}


 interface PostListener {
	public void messageReceived(String messageText);
}
}