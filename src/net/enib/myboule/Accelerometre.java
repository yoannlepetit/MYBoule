package net.enib.myboule;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class Accelerometre implements SensorEventListener{
	
	private ScreenAccelerator screen;
	
	public Accelerometre(ScreenAccelerator view){
		screen = view;
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (!screen.isValidClick()){
			((ScreenAccelerator)screen).setXYAccelerometre(-(int) event.values[0], -(int) event.values[1]);
		}
	}
	
}
