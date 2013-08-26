package com.hzflk.camera.utils;

import android.util.Log;

public class VideoConverter {
	private static final String TAG = "flk_converter";
	
	public static final int CONVERT_SUCCESS = 0;
	public static final int CONVERT_FAILED = 1;
	
	static {
		System.loadLibrary("ffmpeg");
		System.loadLibrary("VideoConverter");
	}
	
	public static final void warmup() {
		//do nothing, for load library
	}
	
	public static final void test() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				int result = VideoConverter.convertVideoFormat("/sdcard/input.mp4", "/sdcard/output.mp4", 90, new VideoConverter.ConvertProgressListener() {
					@Override
					public void onProgressing(long current) {
						Log.d(TAG, "progress " + current);
					}
					
					@Override
					public void onError(int code) {
						Log.d(TAG, "convert error " + code);
					}
				});
				
				Log.d(TAG, "convert result " + result);				
			}
		}, "flk-converter");
		
		t.start();
	}
	
	private volatile static boolean isConverting = Boolean.FALSE;
	/**
	 * convert video(h.264/amr_nb) to video(h.264/aac)
	 * @param inputFile
	 * @param outputFile
	 * @param degrees rotate degree(0, 90, 180, 270, default 0)
	 * @param progressListener
	 * @return 0 on success,  failed otherwise
	 */
	public static int convertVideoFormat(String inputFile, String outputFile, int degrees, ConvertProgressListener progressListener) {
		if(isConverting) return 1;
		
		isConverting = Boolean.TRUE;
		int result = convertFile(inputFile, outputFile, degrees, progressListener);
		isConverting = Boolean.FALSE;
		return result;
	}
	
	/**
	 * 
	 * @param input
	 * @param output
	 * @return 0 if input can be converted, error code otherwise
	 */
	private static native int convertFile(String input, String output, int degrees, ConvertProgressListener progressListener);
	
	public static interface ConvertProgressListener {
		void onProgressing(long current);
		void onError(int code);
	}
}
