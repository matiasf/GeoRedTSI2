package com.geored.gcm;

import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMBroadcastReceiver;

public class GCMIntentService extends GCMBaseIntentService {

	@Override
	protected void onError(Context context, String errorId) {
		// TODO Auto-generated method stub		
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		// TODO Auto-generated method stub		
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		// TODO Auto-generated method stub		
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		// TODO Auto-generated method stub		
	}
	
}
