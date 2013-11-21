package com.sudoku_solver;


import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;



public class Launcher extends Activity implements OnTouchListener

{
	Sudoku obj;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        obj=new Sudoku(this);  
        obj.setOnTouchListener(this);
        setContentView(obj);
       
    }

	public boolean onTouch(View v, MotionEvent event) 
	{
	    
		if((event.getAction() == KeyEvent.ACTION_UP))
		{
			 obj.values(event.getX(),event.getY());
	          
		}
		
		
	    return true;
	   	   
	}
	
	void ShowKeypad()
	{
		Dialog v = new Keypad(this,obj);
        v.show();
	}
     
}
