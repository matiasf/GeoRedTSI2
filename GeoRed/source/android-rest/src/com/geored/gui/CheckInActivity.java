package com.geored.gui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.geored.rest.R;
import com.geored.rest.ServicioRestImagenes;
import com.geored.rest.ServicioRestSitiosInteres;
import com.geored.rest.data.CheckIn;
import com.geored.rest.data.Imagen;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

public class CheckInActivity extends GenericActivity {

	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri fileUri;

	@Override
	protected void loadVista() {
		setContentView(R.layout.activity_checkin);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String value = extras.getString("sitioDinteres_id");
			TextView txtView = (TextView) findViewById(R.id.textView2);
			txtView.setText(value);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Nothing TODO
	}

	private static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.
		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}
		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} 
		else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		} 
		else {
			return null;
		}
		return mediaFile;
	}

	public void showSacarFoto(View clickedButton) {
		// create Intent to take a picture and return control to the calling
		// application
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to
															// save the image
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
															// name
		// start the image capture Intent
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	public void showCheckin(View clickedButton) {
		TextView txtView = (TextView) findViewById(R.id.textView2);
		EditText editText = (EditText) findViewById(R.id.editText1);
		showToast(" path file= " + fileUri.getPath());
		CheckInAsyncTask task = new CheckInAsyncTask();
		task.execute(new String[] { txtView.getText().toString(), editText.getText().toString(), fileUri.getPath() });
	}

	private class CheckInAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... idSitioInteres) {
			try {
				FileInputStream input;
				ByteArrayOutputStream output = null;
				try {
					input = new FileInputStream(idSitioInteres[2]);
					byte[] buffer = new byte[1024];
					output = new ByteArrayOutputStream();
					while (input.read(buffer) > 0) {
						output.write(buffer);
					}
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
				if (output != null) {
					Imagen imagen = ServicioRestImagenes.subirImagen(output.toByteArray());
					CheckIn checkin = new CheckIn();
					checkin.setComentario(idSitioInteres[1]);
					checkin.setIdImagen(imagen.getId());
					ServicioRestSitiosInteres.hacerCheckIn(idSitioInteres[0], checkin);
					return imagen.getId().toString();
				}
			} 
			catch (RestBlowUpException e) {
				e.printStackTrace();
				return null;
			} 
			catch (NotFoundException e) {
				e.printStackTrace();
				return null;
			} 
			catch (UnauthorizedException e) {
				e.printStackTrace();
				return null;
			}
			return "Exito";
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				goToActivity(NotificacionesActivity.class);
				showToast("image id=" + result);
			} 
			else {
				showToast("error");
			}
		}
	}
}
