package com.accesshouse.kostikovo;

public interface SmsListener {
	public void messageReceived(String number,String messageText);
}
