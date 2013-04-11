package net.enib.myboule;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;

public class ScreenAccelerator extends View {

	
	private int xBoule;
	private int yBoule;
	private int diameter;
	private ShapeDrawable mDrawable;
	private boolean validClick = false;
	private ScaleGestureDetector mScaleGestureDectector;
	private SensorManager mSensorManager;
	private SensorEventListener mSensorListener;
	private static int pinchZoom = 4;
	
	public ScreenAccelerator(Context context) {
		super(context);
		xBoule = 100;
		yBoule = 100;
		diameter = 100;
		
		mDrawable = new ShapeDrawable(new OvalShape());
		mDrawable.getPaint().setColor(0xFFFFFFFF);
		mScaleGestureDectector = new ScaleGestureDetector(this.getContext(), new GestureListener(this));
		
		mSensorListener = new Accelerometre(this);
		// mon objet mSensorManager de type  SensorManager qui prend la main
        mSensorManager = (SensorManager)this.getContext().getSystemService(Context.SENSOR_SERVICE); 
        //Sensor. TYPE_ACCELEROMETER defini le type de capteur
        //SensorManager.SENSOR_DELAY_NORMAL le delai  de rafraichissement des informations
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
        SensorManager.SENSOR_DELAY_NORMAL);
	}
		
    	protected void onDraw(Canvas canvas) {
 	       super.onDraw(canvas);
 	       canvas.drawColor(Color.BLACK);
 	       verifyXY();
 	       verifyDiameter();
 	       mDrawable.setBounds(xBoule, yBoule, xBoule+diameter, yBoule+diameter);
 	       mDrawable.draw(canvas);
 	    }
 	

 	public boolean onTouchEvent(MotionEvent event) {
 		mScaleGestureDectector.onTouchEvent(event);
 		int xTouch = (int)event.getX();
 		int yTouch = (int)event.getY();
 		if (event.getActionMasked() == android.view.MotionEvent.ACTION_DOWN &&
 				( xTouch>= xBoule && xTouch<= xBoule+diameter) && 
 				(yTouch >= yBoule && yTouch<= yBoule+diameter)){
 			validClick = true;
 		}
 		else if (event.getActionMasked() == android.view.MotionEvent.ACTION_MOVE && validClick){	
 			setXYTouch( (int) event.getX(), (int) event.getY());
 		}
 		else if (event.getActionMasked() == android.view.MotionEvent.ACTION_UP){
 			validClick = false;
 		}
 	    return true;
 	}
 	
 	public void increaseDiameter(){
 		diameter+=pinchZoom;
 		xBoule-=pinchZoom/2;
 		yBoule-=pinchZoom/2;
 		this.invalidate();
 	}
 	
 	public void decreaseDiameter(){
 		diameter-=pinchZoom;
 		xBoule+=pinchZoom/2;
 		yBoule+=pinchZoom/2;
 		//verifyXY();
 		this.invalidate();
 	}
 	
 	public void setXYTouch(int l, int d){
 		xBoule = l-diameter/2;
 		yBoule = d-diameter/2;
 		this.invalidate();
 	}
 	
 	public void setXYAccelerometre(int l, int d){
 		xBoule+=l;
 		yBoule-=d;
 		this.invalidate();
 	}
 	
 	private void verifyXY(){
 		if(xBoule<0){
 			xBoule = 0;
 		}
 		if(yBoule<0){
 			yBoule = 0;
 		}
 		if(this.getWidth() < xBoule+diameter){
 			xBoule = this.getWidth()-diameter;
 		}
        if(this.getHeight() < yBoule+diameter){
 			yBoule = this.getHeight()-diameter;
 		}
 	}
 	
 	private void verifyDiameter(){
 		if (diameter > this.getWidth() || diameter > this.getHeight()){
 			diameter = java.lang.Math.min(this.getWidth(), this.getHeight());
 		}else if (diameter < 4){
 			diameter = 4;
 		}
 	}

	public int getxBoule() {
		return xBoule;
	}

	public int getyBoule() {
		return yBoule;
	}

	public boolean isValidClick() {
		return validClick;
	}



}
