package eu.MasterZangetsu.GiGglePics;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import eu.MasterZangetsu.R;

public class MainActivity extends FragmentActivity {
	
	Button About;
	Button SoundCloud;
	Button Refresh;
	
	PendingIntent pi;
    BroadcastReceiver br;
    AlarmManager am;
    
    final static private long ONE_MIN = 1000 * 60;
    final static private long ONE_HOURS = ONE_MIN * 60;
    //final static private long SIX_HOURS = ONE_MIN * 15;
    
    private static final int NOTIFY_ME_ID=1337;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		About = (Button) findViewById(R.id.button1);
		SoundCloud = (Button) findViewById(R.id.button2);
		Refresh = (Button) findViewById(R.id.button3);
		
		Alarmsetup();
		
		am.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + ONE_HOURS, pi );
		
		About.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(MainActivity.this, About.class);
				startActivity(intent);
				
			}
		});
		
		SoundCloud.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {		
				
				Intent intent = new Intent(MainActivity.this, SoundCloud.class);
				startActivity(intent);
				
			}
		});
		
		Refresh.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				addRssFragment();
				
			}
		});

		if (savedInstanceState == null) {
			addRssFragment();
		}
	}

	private void Alarmsetup() {
		
		 br = new BroadcastReceiver() {
             @Override
             public void onReceive(Context c, Intent i) {
                    
                    DownloadWebPageTask task = new DownloadWebPageTask();
            		task.execute(new String[] {"http://gigglepics.co.uk/gigglepicsApplicationSettings.txt"});
            		
            		am.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + ONE_HOURS, pi );
                    }
             };
      registerReceiver(br, new IntentFilter("com.authorwjf.wakeywakey") );
      pi = PendingIntent.getBroadcast( this, 0, new Intent("com.authorwjf.wakeywakey"), 0 );
      am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
		
	}
	
	  @SuppressWarnings("deprecation")
	public void createNotification(String outcome) {
		  
		  final NotificationManager mgr=(NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
		  Notification note=new Notification(R.drawable.logo,"GiGgle Pics: Monthly Mixup", System.currentTimeMillis());

		  // This pending intent will open after notification click
		  PendingIntent i=PendingIntent.getActivity(this, 0,new Intent(this, SoundCloud.class),0);
		  note.setLatestEventInfo(this, "GiGgle Pics: Monthly Mixup",outcome, i);

		  //After uncomment this line you will see number of notification arrived
		  //note.number=2;
		  mgr.notify(NOTIFY_ME_ID, note);
			 
		  
	  }
	
	private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			for (String url : urls) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				try {
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();

					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));
					String s = "";
					while ((s = buffer.readLine()) != null) {
						response += s;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {

			String Input;
			String oldInput;
			
			Input = result.toString();
			
			oldInput = currentSavedSettings();
			
			if (Input.equals(oldInput) && !oldInput.equals(null)){
				
				//createNotification("No new monthly mixup available yet.");
				
			} else {
				
				savePrefs("CURRENT_SETTINGS_FILE", Input);
				
				createNotification("New monthly mixup available.");
				
			}	
		}

		private String currentSavedSettings() {
			
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
			
			String result = sp.getString("CURRENT_SETTINGS_FILE", null);
			
			return result;
		}
	}

	private void addRssFragment() {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		RssFragment fragment = new RssFragment();
		transaction.add(R.id.fragment_container, fragment);
		transaction.commit();
	}
	
	private void savePrefs(String key, String value) {

		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(MainActivity.this);
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();

	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_beginings, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.id_check:
			
			Intent intent = new Intent(MainActivity.this, IdCheck.class);
			startActivity(intent);
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean("fragment_added", true);
	}
}