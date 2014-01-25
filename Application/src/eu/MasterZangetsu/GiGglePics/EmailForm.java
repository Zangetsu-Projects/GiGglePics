package eu.MasterZangetsu.GiGglePics;

import eu.MasterZangetsu.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

public class EmailForm extends Activity implements OnClickListener {

	RadioGroup Subject;

	RadioButton liveSubmit;
	RadioButton albumSubmit;
	RadioButton becomeInvolved;
	
	String CheckedSubjectButton = null;

	Button About;
	Button Soundcloud;
	
	Button sendEmail;

	TimePicker timePicker;
	DatePicker datePicker;
	
	ScrollView FormHolder;

	LinearLayout LiveGigForm;
	EditText LiveBandName;
	EditText LiveGenre;
	EditText LiveWebsite;
	EditText LiveLocation;
	
	
	LinearLayout AlbumForm;
	EditText AlbumBandName;
	EditText AlbumGenre;
	EditText AlbumWebsite;
	EditText AlbumDownloadLocation;
	
	
	LinearLayout InvolvedForm;
	EditText VolunteerName;
	EditText VolunteerGenre;
	EditText VolunteerAbout;
	EditText VolunteerExample;
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emailform_activity);
		
		About = (Button) findViewById(R.id.button1);
		Soundcloud = (Button) findViewById(R.id.button2);

		Subject = (RadioGroup) findViewById(R.id.subject);
		liveSubmit = (RadioButton) findViewById(R.id.livesubmit);
		albumSubmit = (RadioButton) findViewById(R.id.albumsubmit);
		becomeInvolved = (RadioButton) findViewById(R.id.becomeinvolved);

		sendEmail = (Button) findViewById(R.id.sendEmail);
		
		FormHolder = (ScrollView) findViewById(R.id.scrollView1);

		//Live gig details
		LiveGigForm = (LinearLayout) findViewById(R.id.liveGigForm);
		LiveBandName = (EditText) findViewById(R.id.liveNameInput);
		LiveGenre = (EditText) findViewById(R.id.liveGenreInput);
		LiveWebsite = (EditText) findViewById(R.id.liveWebsiteInput);
		
		timePicker = (TimePicker) findViewById(R.id.timePicker);
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		
		LiveLocation = (EditText) findViewById(R.id.liveLocationInput);

		timePicker.setIs24HourView(true);
		
		//Album details
		AlbumForm = (LinearLayout) findViewById(R.id.albumForm);
		AlbumBandName = (EditText) findViewById(R.id.albumNameInput);
		AlbumGenre = (EditText) findViewById(R.id.albumGenreInput);
		AlbumWebsite = (EditText) findViewById(R.id.albumWebsiteInput);
		AlbumDownloadLocation = (EditText) findViewById(R.id.albumDownloadLocation);
		
		//volunteer details
		InvolvedForm = (LinearLayout) findViewById(R.id.involvedForm);
		VolunteerName = (EditText) findViewById(R.id.volunteerNameInput);
		VolunteerGenre = (EditText) findViewById(R.id.volunteerGenreInput);
		VolunteerAbout = (EditText) findViewById(R.id.volunteerAboutInput);
		VolunteerExample = (EditText) findViewById(R.id.volunteerExampleInput);
		
		FormHolder.setVisibility(View.INVISIBLE);

		LiveGigForm.setVisibility(View.INVISIBLE);
		AlbumForm.setVisibility(View.INVISIBLE);
		InvolvedForm.setVisibility(View.INVISIBLE);

		About.setOnClickListener((android.view.View.OnClickListener) this);
		Soundcloud.setOnClickListener((android.view.View.OnClickListener) this);
		sendEmail.setOnClickListener((android.view.View.OnClickListener) this);

		Subject.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				FormHolder.setVisibility(View.VISIBLE);

				if (checkedId == R.id.livesubmit) {

					LiveGigForm.setVisibility(View.VISIBLE);
					AlbumForm.setVisibility(View.GONE);
					InvolvedForm.setVisibility(View.GONE);
					
					CheckedSubjectButton = "livesubmit";
					
				} else if (checkedId == R.id.albumsubmit) {
					
					LiveGigForm.setVisibility(View.GONE);
					AlbumForm.setVisibility(View.VISIBLE);
					InvolvedForm.setVisibility(View.GONE);
					
					CheckedSubjectButton = "albumsubmit";
					
				} else if (checkedId == R.id.becomeinvolved) {
					
					LiveGigForm.setVisibility(View.GONE);
					AlbumForm.setVisibility(View.GONE);
					InvolvedForm.setVisibility(View.VISIBLE);
					
					CheckedSubjectButton = "becomeinvolved";
					
				}
			}
		});

	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.sendEmail) {
			
			String Email = "gigglepicsreviews@gmail.com";
			//String Email = "master.zangetsu@googlemail.com";
			
			String EmailSubject = null;
			String EmailBody = null;
			
			if (CheckedSubjectButton == "livesubmit" && LiveBandName.getText() != null && LiveGenre.getText() != null && LiveWebsite.getText() != null && LiveLocation.getText() != null) {
				
				String timeDate = timePicker.getCurrentHour() + " : "
						+ timePicker.getCurrentMinute() + " on "
						+ datePicker.getDayOfMonth() + " / " + datePicker.getMonth()+ " / " + datePicker.getYear();
				
				EmailSubject = "Submittion for Live Review (APP)";
				
				EmailBody = "Bandname: " + LiveBandName.getText() + "\n \n"
						+ "Genre: " + LiveGenre.getText() + "\n \n" + "Location: "
						+ LiveLocation.getText() + "\n \n" + "Time and Date: " + timeDate + "\n \n" + "Website: "
						+ LiveWebsite.getText();
				
			} else if (CheckedSubjectButton == "albumsubmit" && AlbumBandName.getText() != null && AlbumGenre.getText() != null && AlbumWebsite.getText() != null && AlbumDownloadLocation.getText() != null) {
				
				EmailSubject = "Submittion for Album/Ep Review (APP)";
				EmailBody = "Bandname: " + AlbumBandName.getText() + "\n \n"
						+ "Genre: " + AlbumGenre.getText() + "\n \n" +  "Website: "
						+ AlbumWebsite.getText() + "\n \n"+ "Location: "
						+ AlbumDownloadLocation.getText();
				
			} else if (CheckedSubjectButton == "becomeinvolved" && VolunteerName.getText() != null && VolunteerGenre.getText() != null && VolunteerAbout.getText() != null && VolunteerExample.getText() != null) {
				
				EmailSubject = "Volunteer Request (APP)";
				EmailBody = "Name: " + VolunteerName.getText() + "\n \n"
						+ "Prefered Genre: " + VolunteerGenre.getText() + "\n \n" +  "Bio: "
						+ VolunteerAbout.getText() + "\n \n"+ "Previous Work: "
						+ VolunteerExample.getText();
				
			} else {
				Toast.makeText(EmailForm.this, "Please select a subject and fill in the requested details", Toast.LENGTH_LONG).show();
			}

			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("message/rfc822");
			i.putExtra(Intent.EXTRA_EMAIL,
					new String[] { Email });
			i.putExtra(Intent.EXTRA_SUBJECT, EmailSubject);
			i.putExtra(Intent.EXTRA_TEXT, EmailBody);
			try {
				startActivity(Intent.createChooser(i, "Send mail..."));
			} catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(EmailForm.this,
						"There are no email clients installed.",
						Toast.LENGTH_SHORT).show();
			}

		} else if (v.getId() == R.id.button1) {
			
			Intent intent = new Intent(EmailForm.this, SoundCloud.class);
			startActivity(intent);
			
		} else if (v.getId() == R.id.button2) {
			
			Intent intent = new Intent(EmailForm.this, SoundCloud.class);
			startActivity(intent);
			
		}

	}
}
