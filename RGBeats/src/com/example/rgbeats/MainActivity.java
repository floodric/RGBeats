package com.example.rgbeats;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.ImageView;
import java.lang.Math;
import java.util.Random;

public class MainActivity extends Activity {
	public char[] colors=new char[3];
	boolean[] picture=new boolean[3];
	public int currp=0;
	public String newsongname;
	public boolean newsong=false;
	public AssetFileDescriptor afd;
	   public MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp=new MediaPlayer();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
			@Override
			public void onCompletion(MediaPlayer mp){
				//count++;
				//System.out.print(getString(count));
				if(newsong){
					mp.stop();
					mp.reset();
					//mp.release();
					try {
						afd=getAssets().openFd("Sounds/"+newsongname+".mp3");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						mp.prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//mp.start()
					
					newsong=false;
					mp.start();
				}
				else{
					mp.start();
				}
			}
		});
      //  colors[0]=colors[1]=colors[2]='\0';
    }
    
    
    public void takePicture(View view) {
    	Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    	int CAMERA_PIC_REQUEST = 1337;
    	startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	
		super.onActivityResult(requestCode, resultCode, data);
		/*int currp=0;
		if (!picture[0]) currp=0;
		else if (!picture[1]) currp=1;
		else if (!picture[2]) currp=2;*/
		if (currp>2) currp=2;
		int CAMERA_PIC_REQUEST = 1337;
		if (requestCode == CAMERA_PIC_REQUEST) {
		    Bitmap newPicture = (Bitmap) data.getExtras().get("data"); 
		    int[] pixels = new int[newPicture.getHeight()*newPicture.getWidth()];
            newPicture.getPixels(pixels, 0, newPicture.getWidth(), 0, 0, newPicture.getWidth(), newPicture.getHeight());
			int red; int blue; int green; int totalRed = 0; int totalGreen = 0; int totalBlue = 0;
            int height = newPicture.getHeight();
            int width = newPicture.getWidth();
			for (int i = 0; i < height*width; i++){
				red = (pixels[i] & 0xFF0000) >> 16;
            	green = (pixels[i] & 0xFF00) >> 8;
            	blue = (pixels[i] & 0xFF);
            	totalRed += red; 
            	totalBlue += blue; 
            	totalGreen += green;
			}
			totalRed = totalRed / (height*width);
			totalBlue = totalBlue / (height*width);
			totalGreen = totalGreen / (height*width);	
		    int difference1 = totalRed - totalGreen;
		    int difference2 = totalRed - totalBlue;
			if ((-20 < difference1 && difference1 < 20) && (-20 < difference2 && difference2 < 20)){
				Random rand = new Random();
				int randomNumber = rand.nextInt(3);
				if (randomNumber == 0) {
					//Toast.makeText(this, "Red:" + Integer.toString(totalRed), Toast.LENGTH_LONG).show();
					colors[currp]='R';
				} else if (randomNumber == 1){
					//Toast.makeText(this, "Blue:" + Integer.toString(totalBlue), Toast.LENGTH_LONG).show();
					colors[currp]='B';
				} else {
					//Toast.makeText(this, "Green:" + Integer.toString(totalGreen), Toast.LENGTH_LONG).show();
					colors[currp]='G';
					}
		    } else if (totalRed >= totalBlue && totalRed >= totalGreen){
               // Toast.makeText(this, "Red:" + Integer.toString(totalRed), Toast.LENGTH_LONG).show();
		    	colors[currp]='R';
            } else if (totalGreen >= totalBlue && totalGreen >= totalRed){
            	//Toast.makeText(this, "Blue:" + Integer.toString(totalBlue), Toast.LENGTH_LONG).show();
            	colors[currp]='B';
            } else {
            	//Toast.makeText(this, "Green:" + Integer.toString(totalGreen), Toast.LENGTH_LONG).show();
            	colors[currp]='G';
            }
			if (currp==0){
				ImageView pictureView;
		        pictureView = (ImageView) findViewById(R.id.pictureImage);
		        pictureView.setImageBitmap(newPicture);
		        picture[0] = true;
			} else if (currp==1){
				ImageView pictureView1;
		        pictureView1 = (ImageView) findViewById(R.id.pictureImage1);
		        pictureView1.setImageBitmap(newPicture);
		        picture[1] = true;
			} else  if (currp==2){
				ImageView pictureView2;
				pictureView2 = (ImageView) findViewById(R.id.pictureImage2);
				pictureView2.setImageBitmap(newPicture);
				picture[2] = true;
			}
			currp++;
		}
		playSound();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public int count = 0;
    
 
    
    public void playSound() {
    	//Toast.makeText(this, "Button Pressed", Toast.LENGTH_LONG).show();
    	colors[0]='G';
    	String filename=new String(colors,0,currp);
    	Toast.makeText(this,filename+" "+((Integer)(filename.length())).toString() ,Toast.LENGTH_LONG).show();	
    	if (mp.isPlaying())
    	{
    		newsongname=filename;
    		newsong=true;
    	}
    	else
    	{
    		try {
				afd=getAssets().openFd("Sounds/"+filename+".mp3");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				mp.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//mp.start()
			
			newsong=false;
			mp.start();
    	}
    	
    	
    	
		
		
		
    }
    
    
//    @Override
	
}

