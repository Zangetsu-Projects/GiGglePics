package eu.MasterZangetsu.GiGglePics;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import eu.MasterZangetsu.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SoundCloud extends Activity implements OnClickListener{

	int testposition;
	int number;

	Button Articles;
	Button About;
	Button Download;
	
	ProgressBar progressBar;
	
	ImageView soundcloudImage;
	
	TextView imageText;
	

	String Input;

	String enabled;
	
	Spinner mixupSelector;
	
	Intent soundcloudWebLink = new Intent(android.content.Intent.ACTION_VIEW);

	
	String[] soundcloudDates = { 
			"Error", 
			"Error", 
			"Error",
			"Error",
			"Error",
			"Error"
			};

	String[] soundcloudLinks = { 
			"Link1",
			"Link2",
			"Link3",
			"Link4",
			"Link5",
			"Link6" };
	
	
	List<String> SpinnerArray;
	
	ArrayAdapter<String> adapter;
    

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.soundcloud);

		Articles = (Button) findViewById(R.id.button1);
		About = (Button) findViewById(R.id.button2);
		Download = (Button) findViewById(R.id.button3);
		
		Download.setVisibility(View.INVISIBLE);
		
		mixupSelector = (Spinner) findViewById(R.id.spinner1);
		mixupSelector.setVisibility(View.INVISIBLE);
		
		soundcloudImage = (ImageView) findViewById(R.id.imageView);
		imageText = (TextView) findViewById(R.id.myImageViewText);
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		
		progressBar.setVisibility(View.INVISIBLE);
		imageText.setVisibility(View.INVISIBLE);
		
		Articles.setOnClickListener((android.view.View.OnClickListener) this);
		About.setOnClickListener((android.view.View.OnClickListener) this);
		Download.setOnClickListener((android.view.View.OnClickListener) this);
		
		SpinnerArray =  new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		readWebpage();
		
		mixupSelector.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				
				int i = mixupSelector.getSelectedItemPosition();
				
				soundcloudWebLink.setData(Uri.parse(soundcloudLinks[i]));
				imageText.setText(soundcloudDates[i]);
				
		    	//Toast.makeText(getApplicationContext(), soundcloudLinks[i] + "", Toast.LENGTH_SHORT).show();
								
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});		
	}
	
	  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void createNotification(View view) {
		  
			// prepare intent which is triggered if the
			// notification is selected

			Intent intent = new Intent(this, SoundCloud.class);
			PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

			// build notification
			// the addAction re-use the same intent to keep the example short
			
			Notification n  = new Notification.Builder(this)
			        .setContentTitle("New Monthly Mixup for {month}")
			        .setContentText("Subject")
			        .setSmallIcon(R.drawable.logo)
			        .setContentIntent(pIntent)
			        .setAutoCancel(true)
			        .addAction(R.drawable.facebookicon, "Call", pIntent)
			        .addAction(R.drawable.twittericon, "And more", pIntent).build();
			    
			  
			NotificationManager notificationManager = 
			  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

			notificationManager.notify(0, n);
			 
		  
	  }
	
	public void onWindowFocusChanged(boolean hasFocus){

	    int finalHeight = soundcloudImage.getHeight() / 8;
	    int finalWidth = soundcloudImage.getWidth() / 4;
		
		//Toast.makeText(getApplicationContext(), finalHeight + "", Toast.LENGTH_SHORT).show();
	    
	    RelativeLayout.LayoutParams progressParams = (RelativeLayout.LayoutParams)progressBar.getLayoutParams();

	    progressParams.setMargins(0, 0, 0, finalHeight);

	    progressBar.setLayoutParams(progressParams);

		//progressBar.setVisibility(View.VISIBLE);

	}
	
	@Override
	public void onClick(View v) {
		
		 if (v.getId() == R.id.button1) {
			 
			 Intent intent = new Intent(SoundCloud.this, MainActivity.class);
				startActivity(intent);
			 
		 } else if (v.getId() == R.id.button2) {
			 
			 Intent intent = new Intent(SoundCloud.this, About.class);
				startActivity(intent);
			 
		 } else if (v.getId() == R.id.button3) {
			 
			 int i = mixupSelector.getSelectedItemPosition();
			
				 
				 //Toast.makeText(getApplicationContext(),"" + soundcloudLinks[i], Toast.LENGTH_SHORT).show();
				  startActivity(soundcloudWebLink);

		 }
	}


	private class DownloadWebPageTask extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... urls) {
			
			publishProgress(5);
			
			String response = "";
			for (String url : urls) {
				DefaultHttpClient client = new DefaultHttpClient();
				publishProgress(10);
				HttpGet httpGet = new HttpGet(url);
				publishProgress(15);
				try {
					HttpResponse execute = client.execute(httpGet);
					publishProgress(20);
					InputStream content = execute.getEntity().getContent();
					publishProgress(25);

					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));
					publishProgress(30);
					String s = "";
					publishProgress(35);
					while ((s = buffer.readLine()) != null) {
						
						response += s;
					}
					publishProgress(40);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			publishProgress(45);
			return response;
		}
		
		protected void onProgressUpdate(final Integer... value) {
			
					progressBar.setProgress(value[0]);
		
	}

		@Override
		protected void onPostExecute(String result) {

			publishProgress(100);
			Input = result.toString();
			
			savePrefs("CURRENT_SETTINGS_FILE", Input);
			//progressBar.setVisibility(View.GONE);
			
			if (Input.length() <= 1) {
				
				SpinnerArray.add("Error: check internet connection");
				
				mixupSelector.setAdapter(adapter);
				
			} else {
				
			int enabledLocation = Input.indexOf("enabled");
			
			int name1Location = Input.indexOf("name1");
			int downloadLink1Location = Input.indexOf("url1");
			
			int name2Location = Input.indexOf("name2");
			int downloadLink2Location = Input.indexOf("url2");
			
			int name3Location = Input.indexOf("name3");
			int downloadLink3Location = Input.indexOf("url3");
			
			int name4Location = Input.indexOf("name4");
			int downloadLink4Location = Input.indexOf("url4");
			
			int name5Location = Input.indexOf("name5");
			int downloadLink5Location = Input.indexOf("url5");
			
			int name6Location = Input.indexOf("name6");
			int downloadLink6Location = Input.indexOf("url6");

			enabled = Input.substring(enabledLocation + 8, name1Location).replace(" ", "").toString();
			enabled = enabled.replace("_", " ");
			
			soundcloudDates[0] = Input.substring(name1Location+6, downloadLink1Location).replace(" ", "").replace("_", " ");
			soundcloudLinks[0] = Input.substring(downloadLink1Location+5, name2Location).replace(" ", "");
			
			soundcloudDates[1] = Input.substring(name2Location+6, downloadLink2Location).replace(" ", "").replace("_", " ");
			soundcloudLinks[1] = Input.substring(downloadLink2Location+5, name3Location).replace(" ", "");
			
			soundcloudDates[2] = Input.substring(name3Location+6, downloadLink3Location).replace(" ", "").replace("_", " ");
			soundcloudLinks[2] = Input.substring(downloadLink3Location+5, name4Location).replace(" ", "");
			
			soundcloudDates[3] = Input.substring(name4Location+6, downloadLink4Location).replace(" ", "").replace("_", " ");
			soundcloudLinks[3] = Input.substring(downloadLink4Location+5, name5Location).replace(" ", "");
			
			soundcloudDates[4] = Input.substring(name5Location+6, downloadLink5Location).replace(" ", "").replace("_", " ");
			soundcloudLinks[4] = Input.substring(downloadLink5Location+5, name6Location).replace(" ", "");
			
			soundcloudDates[5] = Input.substring(name6Location+6, downloadLink6Location).replace(" ", "").replace("_", " ");
			soundcloudLinks[5] = Input.substring(downloadLink6Location+5, Input.length()).replace(" ", "");
			
			
			int x = 0;
			while (x <= soundcloudDates.length -1) {
				
				SpinnerArray.add(soundcloudDates[x]);
				x++;
			}
		   
		    mixupSelector.setAdapter(adapter);
		    mixupSelector.setVisibility(View.VISIBLE);
		    Download.setVisibility(View.VISIBLE);
			 
			}
		}
	}
	
	private void savePrefs(String key, String value) {

		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();

	}

	public void readWebpage() {
		DownloadWebPageTask task = new DownloadWebPageTask();
		task.execute(new String[] {"http://gigglepics.co.uk/gigglepicsApplicationSettings.txt"});

	}
}