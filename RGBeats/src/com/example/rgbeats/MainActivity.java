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
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.util.Log;

public class MainActivity extends Activity {
	public char[] colors=new char[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public int count = 0;
    
    public void playSound(View view) {
    	Toast.makeText(this, "Button Pressed", Toast.LENGTH_LONG).show();
    	String filename=new String(colors);
    	filename="G";
    	MediaPlayer mp;
    	AssetFileDescriptor afd;
		try {
			
			afd = getAssets().openFd("Sounds/"+filename+".mp3");
			mp = new MediaPlayer();count=0;
			try {
				mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
				mp.prepare();
				mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
					@Override
					public void onCompletion(MediaPlayer mp){
						count++;
						//System.out.print(getString(count));
						
						if(count==3){
							mp.stop();
							mp.reset();
							mp.release();
						}
						else{
							mp.start();
						}
					}
				});
				mp.start();
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
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		int CAMERA_PIC_REQUEST = 1337;
		if (requestCode == CAMERA_PIC_REQUEST) {
		    Bitmap newPicture = (Bitmap) data.getExtras().get("data"); 
		    int[] pixels = new int[newPicture.getHeight()*newPicture.getWidth()];
            newPicture.getPixels(pixels, 0, newPicture.getWidth(), 0, 0, newPicture.getWidth(), newPicture.getHeight());
			Toast.makeText(this, "Height:" + Integer.toString(newPicture.getHeight()), Toast.LENGTH_LONG).show();
		}
	}
}

