package com.geored.gui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.geored.rest.R;
import com.geored.rest.ServicioRestAutenticacion;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

public class FacebookActivity extends Activity {
	private TextView txtUserName;
	private ProgressBar pbLogin;
	private Button btnLogin;
	private Button btnLogout;

	public static final String TAG = "FACEBOOK";
	private Facebook mFacebook;
	public static final String APP_ID = "218019388328218";
	private AsyncFacebookRunner mAsyncRunner;
	private SharedPreferences sharedPrefs;
	private Context mContext;

	private TextView username;
	private ProgressBar pb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facebook);
		setConnection();

		txtUserName = (TextView) findViewById(R.id.textFacebook);
		pbLogin = (ProgressBar) findViewById(R.id.progressLogin);
		btnLogout = (Button) findViewById(R.id.buttonLogout);
		/*
		 * btnLogout.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { getLoguot(txtUserName,
		 * pbLogin); } });
		 */
		btnLogin = (Button) findViewById(R.id.buttonLogin);
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pbLogin.setVisibility(ProgressBar.VISIBLE);
				getID(txtUserName, pbLogin);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void setConnection() {
		mContext = this;
		mFacebook = new Facebook(APP_ID);
		mAsyncRunner = new AsyncFacebookRunner(mFacebook);
	}

	public void getID(TextView txtUserName, ProgressBar progbar) {
		username = txtUserName;
		pb = progbar;
		mFacebook.authorize(this, new LoginDialogListener());
	}

	private String loginFacebook(String accessToken) throws RestBlowUpException, UnauthorizedException {
		return ServicioRestAutenticacion.loginFacebook(accessToken);
	}

	private class LoginDialogListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {
			Log.d(TAG, "LoginONComplete");
			String token = mFacebook.getAccessToken();
			long token_expires = mFacebook.getAccessExpires();
			Log.d(TAG, "AccessToken: " + token);
			Log.d(TAG, "AccessExpires: " + token_expires);
			sharedPrefs = PreferenceManager
					.getDefaultSharedPreferences(mContext);
			sharedPrefs.edit().putLong("access_expires", token_expires)
					.commit();
			sharedPrefs.edit().putString("access_token", token).commit();

			RegistryAsyncTask task = new RegistryAsyncTask();
			task.execute(new String[] {token});
		}

		@Override
		public void onFacebookError(FacebookError e) {
			Log.d(TAG, "FacebookError: " + e.getMessage());
		}

		@Override
		public void onError(DialogError e) {
			Log.d(TAG, "Error: " + e.getMessage());
		}

		@Override
		public void onCancel() {
			Log.d(TAG, "OnCancel");
		}
	}

	private class RegistryAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			try {
				loginFacebook(params[0]);
			} catch (RestBlowUpException e) {
				e.printStackTrace();
				return "El servicio no responde";
			} catch (UnauthorizedException e) {
				e.printStackTrace();
				return "El usuario no esta autorizado";
			}
			return "Exito";
		}
	}
}
