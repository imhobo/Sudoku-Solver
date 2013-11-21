package com.sudoku_solver;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class Keypad extends Dialog
{

	Sudoku obj;
	public Keypad(Context context , Sudoku obj)
	{
		super(context);
		// TODO Auto-generated constructor stub
		this.obj=obj;
	}
	
	View keys[] = new View[10];
	
	@Override
	   protected void onCreate(Bundle savedInstanceState) 
	   {
	      super.onCreate(savedInstanceState);

	      setTitle("Keypad");
	      setContentView(R.layout.keypad);
	      findViews();	      
	      setListeners();
	   }
	
	 
	   private void findViews() 
	   {
	      keys[0] = findViewById(R.id.keypad_1);
	      keys[1] = findViewById(R.id.keypad_2);
	      keys[2] = findViewById(R.id.keypad_3);
	      keys[3] = findViewById(R.id.keypad_4);
	      keys[4] = findViewById(R.id.keypad_5);
	      keys[5] = findViewById(R.id.keypad_6);
	      keys[6] = findViewById(R.id.keypad_7);
	      keys[7] = findViewById(R.id.keypad_8);
	      keys[8] = findViewById(R.id.keypad_9);
	      keys[9] = findViewById(R.id.keypad_clear);
	   }
	   
	   private void setListeners() 
	   {
		      for (int i = 0; i < keys.length; i++) 
		      {
		         final int t = i + 1;
		         keys[i].setOnClickListener(new View.OnClickListener()
		         {
		            public void onClick(View v) 
		            {
		              obj.inputValue(t);
		              dismiss();
		            }
		         });
		      }
		    
	    }
	   
}
