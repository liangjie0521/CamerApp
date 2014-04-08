package com.liangjie.camerapp;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/***
 * 
 * @author liangjie
 * @version create time:2014-4-8下午1:59:09
 * @Email liangjie@witmob.com
 * @Description 调用摄像头
 */
public class CameSufaceView extends SurfaceView implements SurfaceHolder.Callback {
	private final static String TAG = "CameSufaceView";
	private SurfaceHolder holder;
	int value;
	private Camera camera;
	Camera.Parameters parameters;
	private int maxZoom;
	private Context context;

	public CameSufaceView(Context context) {
		super(context);
		this.context = context;
		// 通过SurfaceView获得SurfaceHolder对象
		holder = getHolder();
		// 为holder添加回调结构SurfaceHolder.Callback
		holder.addCallback(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		camera = Camera.open();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		camera.setDisplayOrientation(0);
		try {
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		parameters = camera.getParameters();

		// 处理预览图片长宽比,设置为当前手机屏幕宽高
		int w = PhoneUtil.getDisplayWidth(context);
		int h = PhoneUtil.getDisplayHeight(context);
		Log.e(TAG, "w>>>>>>" + w + ">>>>>>>>>>H>>>>>" + h);
		parameters.setPreviewSize(w, h);
		// 处理自动对焦参数
		List<String> focusModes = parameters.getSupportedFocusModes();
		String CAF_PICTURE = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE, CAF_VIDEO = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO, supportedMode = focusModes
				.contains(CAF_PICTURE) ? CAF_PICTURE : focusModes.contains(CAF_VIDEO) ? CAF_VIDEO : "";
		if (!supportedMode.equals("")) {
			parameters.setFocusMode(supportedMode);
		}
		maxZoom = parameters.getMaxZoom();// 获取最大焦距
		value = parameters.getMaxZoom() / 2;
		parameters.setZoom(value);
		camera.setParameters(parameters);// 设置相机参数
		camera.startPreview();
	}

	public void setZoomAdd() {
		value = value + 2;
		Log.e(TAG, ">>>>>>>>>>>>>>" + value);
		if (value > maxZoom) {
			return;
		}
		parameters.setZoom(value);
		camera.setParameters(parameters);// 设置相机参数

		camera.startPreview();
	}

	public void setZoomMinus() {
		value = value - 2;
		Log.e(TAG, ">>>>>>>>>>>>>>" + value);
		if (value < 0) {
			return;
		}
		parameters.setZoom(value);
		camera.setParameters(parameters);// 设置相机参数

		camera.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		camera.setPreviewCallback(null);
		camera.stopPreview();
		camera.release();
		camera = null;
	}
}
