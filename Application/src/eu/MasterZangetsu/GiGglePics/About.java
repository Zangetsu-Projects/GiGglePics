package eu.MasterZangetsu.GiGglePics;

import eu.MasterZangetsu.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class About extends Activity implements OnClickListener{
	
	Button Articles;
	Button SoundCloud;
	
	ImageView Facebook;
	ImageView Twitter;
	ImageView Soundcloud;
	Button Email;
	
	Intent InvolvementLink = new Intent(android.content.Intent.ACTION_VIEW);	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		Articles = (Button) findViewById(R.id.button1);
		SoundCloud = (Button) findViewById(R.id.button2);
		
		Facebook = (ImageView) findViewById(R.id.FacebookBtn);
		Twitter = (ImageView) findViewById(R.id.TwitterBtn);
		Soundcloud = (ImageView) findViewById(R.id.SoundcloudBtn);
		Email = (Button) findViewById(R.id.EmailBtn);
		
		Articles.setOnClickListener((android.view.View.OnClickListener) this);
		SoundCloud.setOnClickListener((android.view.View.OnClickListener) this);
		
		Facebook.setOnClickListener((android.view.View.OnClickListener) this);
		Twitter.setOnClickListener((android.view.View.OnClickListener) this);
		Soundcloud.setOnClickListener((android.view.View.OnClickListener) this);
		Email.setOnClickListener((android.view.View.OnClickListener) this);
	}


	@Override
	public void onClick(View v) {
		
		 if (v.getId() == R.id.button1) {
			 
			 Intent intent = new Intent(About.this, MainActivity.class);
				startActivity(intent);
			 
		 } else if (v.getId() == R.id.button2) {
			 
			 Intent intent = new Intent(About.this, SoundCloud.class);
				startActivity(intent);
			 
		 } else if (v.getId() == R.id.FacebookBtn) {
			 
			 InvolvementLink.setData(Uri.parse("http://www.facebook.com/GIGglepics"));
			 startActivity(InvolvementLink);
			 
		 } else if (v.getId() == R.id.TwitterBtn) {
			 
			 InvolvementLink.setData(Uri.parse("https://twitter.com/GIGglePics13"));
			 startActivity(InvolvementLink);
			 
		 } else if (v.getId() == R.id.SoundcloudBtn) {
			 
			 InvolvementLink.setData(Uri.parse("https://soundcloud.com/giggle-pics"));
			 startActivity(InvolvementLink);
			 
		 } else if (v.getId() == R.id.EmailBtn) {
			 
			 Intent intent = new Intent(About.this, EmailForm.class);
				startActivity(intent);
			 
		 }
		
	}
}
