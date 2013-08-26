package com.hzflk.camera;

import com.hzflk.camera.utils.VideoConverter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestConvert extends Activity implements OnClickListener {
	private static final String TAG = "flk_converter";
	
	private Button testButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.test_convert);
		
		testButton = (Button) findViewById(R.id.test_button);
		testButton.setOnClickListener(this);
		
		VideoConverter.warmup();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.test_button:
			doTest();
			break;
		}
	}
	private void doTest() {
		VideoConverter.test();
	}
}
