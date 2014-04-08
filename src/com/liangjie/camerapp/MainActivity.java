package com.liangjie.camerapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
	private FrameLayout frame;
	private CameSufaceView cameSufaceView;
	private Button add, minus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		frame = (FrameLayout) findViewById(R.id.mainFrameLayout);
		cameSufaceView = new CameSufaceView(this);
		frame.addView(cameSufaceView);
		add = (Button) findViewById(R.id.add);
		minus = (Button) findViewById(R.id.jian);
		add.bringToFront();
		minus.bringToFront();
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cameSufaceView.setZoomAdd();
			}
		});
		minus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cameSufaceView.setZoomMinus();
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return false;

	}
}
