package com.sudoku_solver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class Homepage extends Activity implements OnClickListener
{
	
	int width=0,height=0;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
      //Set up Click listeners for all the buttons
        View solver = findViewById(R.id.solver);
        solver.setOnClickListener(this);
        View instructions = findViewById(R.id.instructions);
        instructions.setOnClickListener(this);     
        View about = findViewById(R.id.about);
        about.setOnClickListener(this);
       
      //Transparency
        solver.getBackground().setAlpha(180);
        instructions.getBackground().setAlpha(180);
        about.getBackground().setAlpha(180);
        
        if (android.os.Build.VERSION.SDK_INT >= 13)
        {
        	Display display = getWindowManager().getDefaultDisplay();
        	Point size = new Point();
        	display.getSize(size);
        	width = size.x;
        	height = size.y;
        }
        
        else
        {
        	Display display = getWindowManager().getDefaultDisplay(); 
        	width = display.getWidth();  // deprecated
        	height = display.getHeight();  // deprecated
        }
        
        
        float ratio=pixelsToDp(this, 50f)/800; // Height ratio of the buttons
        
        //For top button
        float left=pixelsToDp(this, 30f)/480;
        float right=left;
        float top=pixelsToDp(this, 190f)/800;
        float bottom=pixelsToDp(this, 100f)/800;
        
        // Scaling the buttons
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,(int)(height*ratio));//Setting width,height
        params.setMargins((int)(width*left), (int)(height*top), (int)(width*right), (int)(height*bottom)); //substitute parameters for left, top, right, bottom
        solver.setLayoutParams(params);
        
        LinearLayout.LayoutParams params2=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,(int)(height*ratio));//Setting width,height
        params2.setMargins((int)(left*width), 0, (int)(right*width), (int)(left*height)); //substitute parameters for left, top, right, bottom
        instructions.setLayoutParams(params2);
        
        LinearLayout.LayoutParams params3=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,(int)(height*ratio));//Setting width,height
        params3.setMargins((int)(left*width), 0, (int)(right*width), 0); //substitute parameters for left, top, right, bottom
        about.setLayoutParams(params3);
        
        
	}
	
	public static float pixelsToDp(Context context, Float px) 
	{
	    float Density = context.getResources().getDisplayMetrics().density;
	    return px*Density;
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) 
	{
       switch (item.getItemId()) 
       {
	       case R.id.menu_settings:
	    	   AlertDialog alertDialog = new AlertDialog.Builder(this).create();
	   		   alertDialog.setTitle("Really???");
			   alertDialog.setMessage(getResources().getString(R.string.settings));
			   alertDialog.show();
	    	   
	          
	           break;
	       // More items go here (if any) ...
       }
       return false;
    }
    
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.solver:
				Intent i = new Intent(this,Launcher.class);
				startActivity(i);
				break;
			
			case R.id.instructions:
				AlertDialog alertDialog = new AlertDialog.Builder(this).create();
				alertDialog.setTitle("Instructions");
				alertDialog.setMessage(getResources().getString(R.string.instructions));
				alertDialog.show();
				break;
			case R.id.about:
				alertDialog = new AlertDialog.Builder(this).create();
				alertDialog.setTitle("About");
				alertDialog.setMessage(getResources().getString(R.string.about));
				alertDialog.show();
				break;
		
		}
		
	}

}
