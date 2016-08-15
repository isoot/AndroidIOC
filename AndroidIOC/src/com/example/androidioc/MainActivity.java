package com.example.androidioc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

@ContentView(R.layout.activity_main)
public class MainActivity extends Activity {

	@ViewInJect(R.id.button1)
	private Button button1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewInJectUtils.inJect(this);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "你再点一个试试",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

}
