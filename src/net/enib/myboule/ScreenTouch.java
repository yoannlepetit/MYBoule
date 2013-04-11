package net.enib.myboule;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class ScreenTouch extends View {

	private int x;
	private int y;
	private int diameter;
	private ShapeDrawable mDrawable;
	private boolean validClick = false;
	private ScaleGestureDetector mScaleGestureDectector;
	private static int pinchZoom = 4;
	
	public ScreenTouch(Context context) {
		super(context);
		x = 100;
		y = 100;
		diameter = 100;
		
		mDrawable = new ShapeDrawable(new OvalShape());
		mDrawable.getPaint().setColor(0xFFFFFFFF);
		
		mScaleGestureDectector = new ScaleGestureDetector(context, new GestureListener(this));
	}
 
	protected void onDraw(Canvas canvas) {
	       super.onDraw(canvas);
	       canvas.drawColor(Color.BLACK);
	       verifyXY();
	       verifyDiameter();
	       mDrawable.setBounds(x, y, x+diameter, y+diameter);
	       mDrawable.draw(canvas);
	    }
	

	public boolean onTouchEvent(MotionEvent event) {
		mScaleGestureDectector.onTouchEvent(event);
		int xTouch = (int)event.getX();
		int yTouch = (int)event.getY();
		if (event.getActionMasked() == android.view.MotionEvent.ACTION_DOWN &&
				( xTouch>= x && xTouch<= x+diameter) && 
				(yTouch >= y && yTouch<= y+diameter)){
			validClick = true;
		}
		else if (event.getActionMasked() == android.view.MotionEvent.ACTION_MOVE && validClick){	
		       setXY( (int) event.getX(), (int) event.getY());
		       invalidate();
		}
		else if (event.getActionMasked() == android.view.MotionEvent.ACTION_UP){
			validClick = false;
		}
	    return true;
	}
	
	public void increaseDiameter(){
		diameter+=pinchZoom;
		x-=pinchZoom/2;
		y-=pinchZoom/2;
		//verifyXY();
		this.invalidate();
	}
	
	public void decreaseDiameter(){
		diameter-=pinchZoom;
		x+=pinchZoom/2;
		y+=pinchZoom/2;
		//verifyXY();
		this.invalidate();
	}
	
	public void setXY(int l, int d){
		x = l-diameter/2;
		y = d-diameter/2;
		//verifyXY();
	}
	
	private void verifyXY(){
		if(x<0){
			x = 0;
		}
		if(y<0){
			y = 0;
		}
		if(this.getWidth() < x+diameter){
			x = this.getWidth()-diameter;
		}
       if(this.getHeight() < y+diameter){
			y = this.getHeight()-diameter;
		}
	}
	
	private void verifyDiameter(){
		if (diameter > this.getWidth() || diameter > this.getHeight()){
			diameter = java.lang.Math.min(this.getWidth(), this.getHeight());
		}else if (diameter < 4){
			diameter = 4;
		}
	}
	
}
