package eu.MasterZangetsu.GiGglePics;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.androidtitlan.ac.easymail.EasyMail;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import eu.MasterZangetsu.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class IdCheck extends Activity implements OnClickListener {

	String Input;

	String volunteerName;
	String volunteerImage;
	String volunteerPosition;
	String volunteerAuthorization;
	String volunteerVenue;
	String volunteerBio;

	String venueId;

	TextView nameOutput;
	TextView detailsOutput;
	TextView bioOutput;

	ImageView mugShot;

	EditText employeeIdInput;
	EditText venueIdInput;

	Button enter;
	Button Articles;
	Button About;
	Button SoundCloud;

	LinearLayout infoPane;
	LinearLayout idDetails;
	ProgressBar progressBar1;

	TextView errorText;

	String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
			"X", "Y", "Z", " " };

	String convertedString = "";
	Boolean developerMode;
	
	EasyMail mail = new EasyMail("master.zangetsu@googlemail.com", "test");

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.id_check_activity);

		Articles = (Button) findViewById(R.id.button1);
		About = (Button) findViewById(R.id.button2);
		SoundCloud = (Button) findViewById(R.id.button3);
		enter = (Button) findViewById(R.id.enterbtn);

		nameOutput = (TextView) findViewById(R.id.textView1);
		detailsOutput = (TextView) findViewById(R.id.textView2);
		bioOutput = (TextView) findViewById(R.id.textView3);

		mugShot = (ImageView) findViewById(R.id.imageView2);

		employeeIdInput = (EditText) findViewById(R.id.employeeidinput);
		venueIdInput = (EditText) findViewById(R.id.venueidinput);

		errorText = (TextView) findViewById(R.id.errorText);

		errorText.setGravity(Gravity.LEFT | Gravity.CENTER_HORIZONTAL);

		errorText
				.setText("GiGgle Pics Employee ID check allows venues, bands and promoters to verify a GiGgle Pics employee"
						+ "\n \n"
						+ "By entering the ID found on their pass you can verify that the employee is attending on official GiGgle Pics business"
						+ "\n \n"
						+ "Please note that an internet connection is required for the verification process");

		infoPane = (LinearLayout) findViewById(R.id.linearlayout);
		idDetails = (LinearLayout) findViewById(R.id.idDetails);
		progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);

		infoPane.setVisibility(View.GONE);
		progressBar1.setVisibility(View.GONE);

		enter.setOnClickListener((android.view.View.OnClickListener) this);
		Articles.setOnClickListener((android.view.View.OnClickListener) this);
		About.setOnClickListener((android.view.View.OnClickListener) this);
		SoundCloud.setOnClickListener((android.view.View.OnClickListener) this);
		
		loadPrefs();

	}
	
	private void readWebpage(String ID) {
		DownloadWebPageTask task = new DownloadWebPageTask();

		String Website = "http://gigglepics.co.uk";
		//String Website = "http://masterzangetsu.eu";

		task.execute(new String[] { Website + "/volunteer/" + ID });

	}
	
	private void savePrefs(String key, boolean value) {

		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putBoolean(key, value);
		edit.commit();

	}
	
	private void loadPrefs() {
		
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		
		developerMode = sp.getBoolean("DEV_MODE", false);
	}
	
	private void devMode() {
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Activate Developer Mode");
		
		String option1 = "";
		String option2 = "";
		
		final EditText input = new EditText(this);
		if (developerMode) {
			
			alert.setMessage("Developer Mode: Enabled");
			
			option1 = "Ok";
			option2 = "Disable";
			
		} else {
			
			alert.setMessage("Developer Mode: Disabled" + "\n" + "Enter developer password to enable");
			alert.setView(input);
			
			option1 = "Enable";
			option2 = "Ok";
			
		}

		// Set an EditText view to get user input 
		

		alert.setPositiveButton(option1, new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  Editable value = input.getText();
		  
		  if (value.toString().equals("developermodereallyisntthatinteresting")) {
			  
			  savePrefs("DEV_MODE", true);
			  developerMode = true;
			  Toast.makeText(IdCheck.this, "Developer Mode: " + developerMode, Toast.LENGTH_SHORT).show();
		  }	else if (value.toString().equals("")) {
			  
		  } else {
			  Toast.makeText(IdCheck.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
		  }
		  
		  }
		});

		alert.setNegativeButton(option2, new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
			  
			  savePrefs("DEV_MODE", false);
			  developerMode = false;
			  Toast.makeText(IdCheck.this, "Developer Mode: " + developerMode, Toast.LENGTH_SHORT).show();
			  dialog.cancel();
		  }
		});

		alert.show();

		loadPrefs();
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.button1) {

			Intent intent = new Intent(IdCheck.this, MainActivity.class);
			startActivity(intent);

		} else if (v.getId() == R.id.button2) {

			Intent intent = new Intent(IdCheck.this, About.class);
			startActivity(intent);

		} else if (v.getId() == R.id.button3) {

			Intent intent = new Intent(IdCheck.this, SoundCloud.class);
			startActivity(intent);

		} else if (v.getId() == R.id.enterbtn) {

			String fileName = employeeIdInput.getText().toString()
					.toUpperCase(Locale.UK)
					+ ".txt";
			venueId = venueIdInput.getText().toString().toUpperCase(Locale.UK);

			idDetails.setVisibility(View.GONE);

			readWebpage(fileName);

		}

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_id, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.id_check:
			Intent intent = new Intent(IdCheck.this, IdCheck.class);
			startActivity(intent);
			return true;
		case R.id.id_dev_mode:
			devMode();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
			
			
			
			/*
			
			Mail m = new Mail("master.zangetsu@googlemail.com", "Rhino5ramram."); 
			 
		      String[] toArr = {"rmuldoon@sbe-ltd.co.uk", "master.zangetsu@googlemail.com"}; 
		      m.setTo(toArr); 
		      m.setFrom("wooo@wooo.com"); 
		      m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device."); 
		      m.setBody("Email body."); 
			
		      try {
		   
		          if(m.send()) {
		            //Toast.makeText(IdCheck.this, "Email was sent successfully.", Toast.LENGTH_LONG).show(); 
		          } else {
		            //Toast.makeText(IdCheck.this, "Email was not sent.", Toast.LENGTH_LONG).show(); 
		          } 
		        } catch(Exception e) { 
		          //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show(); 
		          Log.e("IdCheck", "Could not send email", e); 
		        }
		        */
 
			return response;
		}

		protected void onPreExecute() {

			progressBar1.setVisibility(View.VISIBLE);
			errorText.setVisibility(View.GONE);

		}

		@Override
		protected void onPostExecute(String result) {

			Calendar c = Calendar.getInstance();

			int mins = c.get(Calendar.MINUTE);
			int hour = c.get(Calendar.HOUR);

			int day = c.get(Calendar.DAY_OF_MONTH);
			int month = c.get(Calendar.MONTH) + 1;
			int year = c.get(Calendar.YEAR);

			String date = day + "/" + month + "/" + year;

			Input = result.toString();

			bioOutput.setText(Input);

			if (Input.length() <= 1) {

				Toast.makeText(IdCheck.this,
						"Error: check internet connection", Toast.LENGTH_SHORT)
						.show();

				progressBar1.setVisibility(View.GONE);
				errorText.setVisibility(View.VISIBLE);
				errorText.setGravity(Gravity.CENTER_VERTICAL
						| Gravity.CENTER_HORIZONTAL);

				errorText
						.setText("GiGgle Pics employee ID not found, please ensure an active Internet connection");

				idDetails.setVisibility(View.VISIBLE);

			} else {

				int fileChecker = 0;
				String fileError = "Settings constructors missing: ";

				String initialiserText = "<!-- GiGgle Pics Application Settings File -->";

				boolean initialiser = Input.contains(initialiserText);
				int initialiserLocation = Input.indexOf(initialiserText);

				int nameLocation = Input.indexOf("name");
				int urlLocation = Input.indexOf("image");
				int positionLocation = Input.indexOf("position");
				int authorizationLocation = Input.indexOf("authorization");
				int venueLocation = Input.indexOf("venue");
				int bioLocation = Input.indexOf("bio");

				int[] test = { nameLocation, urlLocation, positionLocation,
						authorizationLocation, venueLocation, bioLocation };

				String[] constructors = { "name", "url", "position",
						"authorization", "venue", "bio" };

				if (initialiser == false) {

					fileError = fileError + "Initialiser";

				} else if (initialiserLocation != 0) {

					fileError = "Initialiser in incorrect location";

				} else {

					for (int i = 0; i < test.length; i++) {
						if (test[i] > 0) {

							fileChecker++;
						} else {
							fileError = fileError + constructors[i] + " ";
						}
					}
				}

				if (fileChecker >= test.length) {

					volunteerName = Input.substring(nameLocation + 5,
							urlLocation);
					volunteerImage = Input.substring(urlLocation + 6,
							positionLocation).replace(" ", "");
					volunteerPosition = Input.substring(positionLocation + 9,
							authorizationLocation);
					volunteerAuthorization = Input.substring(
							authorizationLocation + 14, venueLocation).replace(
							" ", "");
					volunteerVenue = Input
							.substring(venueLocation + 6, bioLocation)
							.toString().replace(" ", "").replace("_", " ");
					volunteerBio = Input
							.substring(bioLocation + 4, Input.length())
							.replace("</br>", "\n").replace("'", "'");

					for (int i = 0; i < volunteerVenue.length(); i++) {

						int code = Arrays.asList(alphabet).indexOf(
								volunteerVenue.substring(i, i + 1).toUpperCase(
										Locale.UK));
						convertedString = convertedString + code;

					}

					String passwordToCompare = convertedString;

					while (passwordToCompare.length() > 4) {

						for (int i = 0; i < passwordToCompare.length(); i++) {

							String firstChar = "" + passwordToCompare.charAt(i);
							passwordToCompare = passwordToCompare.replace(
									firstChar, "");

						}
					}

					if (passwordToCompare.length() < 4) {

						int length = 4 - passwordToCompare.length();
						int start = convertedString.length() / 2;

						String charToAdd = convertedString.substring(start,
								start + length);
						passwordToCompare = charToAdd + passwordToCompare;
					}

					nameOutput.setText(volunteerName);
					detailsOutput.setText("Position: " + volunteerPosition);

					if ((volunteerAuthorization.equals(date))
							&& (passwordToCompare.equals(venueId))) {

						detailsOutput.append("\n" + "\n" + "Authorised"
								+ " for " + volunteerVenue);

					} else {
						detailsOutput.append("\n" + "Unauthorised");
					}

					if (developerMode) {
						
						bioOutput.setText("\n" + volunteerBio + "\n" + "\n" + "Developer Info: " + "\n" + "Venue Code: " + passwordToCompare);
						
					} else {
						
						bioOutput.setText("\n" + volunteerBio);
						
					}

					UrlImageViewHelper.setUrlDrawable(mugShot, volunteerImage,
							R.drawable.logo);
					
					sendEmail();

					infoPane.setVisibility(View.VISIBLE);
					progressBar1.setVisibility(View.GONE);
					errorText.setVisibility(View.GONE);
				      

				} else {

					progressBar1.setVisibility(View.GONE);
					errorText.setVisibility(View.VISIBLE);
					errorText.setGravity(Gravity.CENTER_VERTICAL
							| Gravity.CENTER_HORIZONTAL);

					errorText.setText("GiGgle Pics settings file error." + "\n"
							+ fileError);

					idDetails.setVisibility(View.VISIBLE);

				}					
			}

		}

		private void sendEmail() {
			
			
			
			String[] directionsToSend = {"nrikediaz@androidtitlan.org", "nrikediaz@gmail.com"}; 
		       mail.setTo(directionsToSend);
		       mail.setFrom("nrikediaz@androidtitlan.com"); 
		       mail.setSubject("This is a test."); 
		       mail.setBody("This is an email sent using my Mail JavaMail wrapper from an Android device.");
		       
		       try { 
	                  if(mail.send()) { 
	        //Say sent
	                  } else {
	        //Say not sent 
	                  } 
	                } catch(Exception e) { 
	        //Print StackTrace
	                }  
			
		}
	}
}
