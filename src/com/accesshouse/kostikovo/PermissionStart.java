package com.accesshouse.kostikovo;

import java.util.ArrayList;

import com.accesshouse.kostikovo.TelephonyM.PostListener;

import a.permmission6.AccessPermission;
import a.permmission6.OnPermmissionResult;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;



public class PermissionStart extends Activity{
	static boolean statusSMS=false;
	 int permissionCheck1=555;
	private boolean one=false;
static DBOpenHelper OpenHelper;
static	 PermissionStart startAc;	 static TelephonyManager mTelephonyMgr;
	 private static final int RESULT_TELEPHONY = 3400006;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		startAc=this;
	
		
			
		//	a.permmission6.AccessPermission.getPermmission(this);
			
			if(!AccessPermission.getPermmission(this)){
				
				AccessPermission.setOnPermmissionResult(new OnPermmissionResult() {
					
					@Override
					public void OnPermmission(boolean access, String[] permissions,
							int[] grantResults) {
						// TODO Auto-generated method stub	
		if(access)doinit();
					}
				});
			}else doinit();


	}
private void	doinit(){
	if(!one){one=true;
	mTelephonyMgr = (TelephonyManager) 
	        getSystemService(Context.TELEPHONY_SERVICE); 
OpenHelper =  new DBOpenHelper(this);
	if(ups())
		preinit();
	else init();
	}
}
	boolean ups(){
		ArrayList<String> aa = DBOpenHelper.getReadText();
		if(aa.size()>0)
			if(mTelephonyMgr.getSimSerialNumber()!=null)
			if(mTelephonyMgr.getSimSerialNumber().equals(aa.get(10))) return true;
		return false;		
	}
void preinit(){
	if(AllMenu.all!=null)AllMenu.all.finish();

	Intent in=new Intent(getApplicationContext(), AllMenu.class);
	
	if(getIntent().getExtras()!=null){
		in.putExtra("body", getIntent().getExtras().getString("gcm.notification.body"));
		in.putExtra("body2", getIntent().getExtras().getString("body2"));
		in.putExtra("body", getIntent().getExtras().getString("body1"));
	}else if(NotificationsListenerService.mainHeadeer!=null)
		if(NotificationsListenerService.mainHeadeer.split("!!!!").length>1){
		String[] qq = NotificationsListenerService.mainHeadeer.split("!!!!");
		in.putExtra("body2", qq[1]);
		in.putExtra("body", qq[0]);
	}
	startActivity(in);
	finish();
}
void init (){

	readPhone();
	 
	TelephonyM.onList(new PostListener() {
		
		@Override
		public void messageReceived(String messageText) {
			// TODO Auto-generated method stub
		if(messageText!=null)finishW(messageText);	
		}
	});;
	
}
	  @Override
	   public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

	       if(requestCode == permissionCheck1)
	    	  
	    	   readPhone();
	      

	}
	private void readPhone(){
		 String t = getMyPhoneNumber();
		
		 Intent start=new Intent(this,TelephonyM.class);
		 if(t!=null)if(TelephonyM.aa ==null)
	       if(t.length()<2)
	    	   startActivityForResult(start, RESULT_TELEPHONY);
	       else{
	    	  start.putExtra("telephony", t);
	   	 startActivityForResult(start, RESULT_TELEPHONY);
	       }else
		    	  finish();
			
		
	      
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {	
		finish();
	}
	private void finishW(String in){
  	   finish();
  	 TelephonyM.onFinal();
	}
	private String getMyPhoneNumber(){
		 String out=mTelephonyMgr.getLine1Number();
		if(out==null){
			Toast.makeText(getApplicationContext(), "SIM Error", Toast.LENGTH_SHORT).show();
	    	  finish();
		} 
		return out;
		}

}
