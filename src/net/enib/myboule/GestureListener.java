package net.enib.myboule;

import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;

public class GestureListener extends SimpleOnScaleGestureListener {

	
	private View screen;
	

	public GestureListener(View screen){
		this.screen = screen;		
	}
	
		 
	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		if (detector.getPreviousSpan() > detector.getCurrentSpan()){
			((ScreenAccelerator)screen).decreaseDiameter();
		}else if (detector.getPreviousSpan() < detector.getCurrentSpan()){
			((ScreenAccelerator)screen).increaseDiameter();
		}
		return true;
	}
 
}
	

