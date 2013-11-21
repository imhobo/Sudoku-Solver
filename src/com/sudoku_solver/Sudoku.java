package com.sudoku_solver;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.os.AsyncTask;
import android.view.View;

public class Sudoku extends View 
{
	// One Tile Dimensions
	int width;
	int height;
	
	int row,col;
	
	int sud[][]= new int[9][9]; // Array to store the entered values
	boolean was[][]=new boolean[9][9]; //Keeps track of zero entries
	
	boolean solved=false; //Tells whether the puzzle is solved or not
	
	
	float X=-1,Y=-1; //Touch Coordinates
	
	Paint dark = new Paint();
	Paint non_sel = new Paint();
	Paint selected = new Paint();
	Paint light = new Paint();
	Paint numbers = new Paint(Paint.ANTI_ALIAS_FLAG);
	Paint solve = new Paint(Paint.ANTI_ALIAS_FLAG);
		
	Bitmap button=BitmapFactory.decodeResource(getResources(),R.drawable.solve);
	Bitmap button_sel=BitmapFactory.decodeResource(getResources(),R.drawable.solve_sel);
	
	AlertDialog alertDialog;
	
	String value="";

	Launcher launch;
	Solve object;
	
	Canvas canvas;

	public Sudoku(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		this.launch=(Launcher)context;
		
		initialize();
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		this.canvas=canvas;
		
		//Screen Dimensions
		int Width=canvas.getWidth();
		int Height=(int)(0.9f*canvas.getHeight());
		
		//Single Tile Dimensions
		width=Width/9;
		height=Height/9;
		
		
		canvas.drawColor(Color.WHITE); // Background
		
		// Scaling Button Bitmaps
		
		button=Bitmap.createScaledBitmap(button, Width, height, true);
		button_sel=Bitmap.createScaledBitmap(button_sel,Width, height, true);
		
		// Colors for grid lines
		
	    dark.setColor(getResources().getColor(R.color.puzzle_dark));	    
        light.setColor(getResources().getColor(R.color.puzzle_light));
        
        //Colors for tiles
        
        non_sel.setColor(getResources().getColor(R.color.puzzle_non_sel));
        selected.setColor(getResources().getColor(R.color.puzzle_sel));
        
        //Colors and other properties for numbers to be printed
        
        numbers.setColor(getResources().getColor(R.color.puzzle_number));
        numbers.setStyle(Style.FILL);
        numbers.setTextSize(0.5f*height);
        numbers.setTextScaleX(width / height);
        numbers.setTextAlign(Paint.Align.CENTER);
        
        // Draw grid lines
        
        for (int i = 0; i <= 9; i++)
        {
            canvas.drawLine(0, i * height, Width, i * height,light);
            canvas.drawLine(0, i * height + 1, Width, i * height+ 1, light);
            canvas.drawLine(i * width, 0, i * width, Height,light);
            canvas.drawLine(i * width + 1, 0, i * width + 1,Height, light);
        }
        
     // Draw the major grid lines
        for (int i = 0; i <=9; i+=3) 
        {
           canvas.drawLine(0, i * height, Width, i * height,dark);
           canvas.drawLine(0, i * height + 1, Width, i * height+ 1, dark);
           canvas.drawLine(i * width, 0, i * width, Height, dark);
           canvas.drawLine(i * width + 1, 0, i * width + 1, Height, dark);
        }
        
     // Draw the solve/reset button
        if(!solved)canvas.drawBitmap(button, 0, 9*height+2, solve);
        else canvas.drawBitmap(button_sel, 0, 9*height+2, solve);
        
        
          
     // Print the array on the screen
        
    	for(int i=0;i<9;i++)
	        for(int j=0;j<9;j++)
	        {
	        	if(sud[i][j]!=0)value=Integer.toString(sud[i][j]);
	        	else value="";
	        	
	        	if(!was[i][j])updateTile(i,j,non_sel);
	        	else updateTile(i,j,selected);
	        }
        
       
	}
	
	public void values(float x, float y)
	{
		X=x;
		Y=y;
		
		row=(int)(X/width);
    	col=(int)(Y/height);
	 	
		// If solve/reset is pressed
		if(col>8)
		{
			//Solve is pressed
			if(!solved)
			{ 
				object=new Solve();
				object.execute();   //Solving			
			}
			
			//Reset is pressed
			else
			{
				initialize();
				solved=false;
				invalidate();	
			}
						
			return;
		}
		
		if(!solved)launch.ShowKeypad();	//Displays Keypad
	}
	
	
	void inputValue(int num)
	{
		
		num=num%10; //So that clear becomes 0
	
		sud[row][col]=num;
		if(num!=0)was[row][col]=true;
		else was[row][col]=false;
    			
		invalidate(row*width+2,col*height+2,row*width+width,col*height+height);
		
	}
	
	void updateTile(int row,int col,Paint paint )
	{  
     // Draw the number in the center of the tile
        FontMetrics fm = numbers.getFontMetrics();
   
        float x = width / 2;
        float y = height / 2 - (fm.ascent + fm.descent) / 2;
        
    	canvas.drawRect(row*width+2,col*height+2,row*width+width,col*height+height,paint);
    	canvas.drawText(value, row * width + x , col * height + y , numbers);
     
	}
	
	boolean valid (int row,int col,int num)
	{
		for(int i=0;i<9;i++)
	    {
	        if(i==col)continue;
	        if(num==sud[row][i])return false;
	    }
	    for(int i=0;i<9;i++)
	    {
	        if(i==row)continue;
	        if(num==sud[i][col])return false;
	    }

	    int tr,tc;
	    tr=row/3;
	    tc=col/3;
	    
	    for(int i=tr*3;i<tr*3+3;i++)
	    {
	        for(int j=tc*3;j<tc*3+3;j++)
	        {
	            if(i==row&&j==col)
	                continue;

	            if(num==sud[i][j])
	                return false;
	        }
	    }

	    return true;
	}
	
	boolean sudoku()

	{
		boolean flag=false;
	    int start=0;
		
		for(int i=0;i<9;i++)
	       for(int j=0;j<9;j++)
	       {
	            if(was[i][j])
	            {
	                if(valid(i,j,sud[i][j]))continue;
	                
	                else return false;
	            }       
	            if(!flag)
	            {
	                start=i*9+j;
	                flag=true;
	            }
	       }
		
	    int last=0;
	    int row,col;
	    for(int r=0;r<81;)
	    {

	            boolean back_flag = true;
	            int num;

	            row = r/9;
	            col = r%9;

	            if(was[row][col])
	            {
	                if(last>r) r--;
	                else r++;
	                continue;
	            }

	            for(num = sud[row][col]+1; num < 10; num++)
	            {
	                if(valid(row,col,num))
	                {
	                    sud[row][col] = num;
	                    back_flag=false;
	                    break;
	                }
	                else if(num >= 9)
	                {
	                    sud[row][col] = 0;
	                }
	            }

	            if(num>9)
	                sud[row][col] = 0;

	            if(back_flag)
	            {
	                last=r;
	                r--;
	            }
	            else
	            {
	                last=r;
	                r++;
	            }
	            if(r<start)return false;
	     }
	    
	    return true;
	}

	void showDialog(AlertDialog alertDialog,String title,String message)
	{
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.show();
	}
	void initialize()
	{
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				{
					sud[i][j]=0;
					was[i][j]=false;
				}
	}

	public class Solve extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected void onPreExecute ()
		{
			//Create Waiting Dialog
			alertDialog = new AlertDialog.Builder(launch).create();	
			alertDialog.setCancelable(false);
			showDialog(alertDialog,"Please Wait...","Doing extreme calculations!!");
		}
		
		@Override
		protected Void doInBackground(Void... params) 
		{
			// TODO Auto-generated method stub
			solved=sudoku();      
			return null;
		}
		
		@Override
		protected void onPostExecute (Void result)
		{
			if(solved)alertDialog.dismiss();
			
			//Invalid Sudoku Puzzle
			else 
			{
				initialize();
				alertDialog.dismiss();
				
				alertDialog = new AlertDialog.Builder(launch).create();
				alertDialog.setButton("Oops..I'm sorry",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// here you can add functions
						}
						});
				showDialog(alertDialog,"Enter Carefully!!","Invalid Sudoku Puzzle");
			}
			
			invalidate();	
			
		}
		
	}

}