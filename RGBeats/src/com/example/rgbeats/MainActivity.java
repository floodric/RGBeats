package com.example.rgbeats;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.Log;
import android.widget.ImageView;
import java.util.Random;

public class MainActivity extends Activity {
	public char[] colors=new char[3];
	boolean[] picture=new boolean[3];
	public int currp=0;
	public String newsongname;
	public boolean newsong=false;
	public AssetFileDescriptor afd;
	public MediaPlayer mp;
	public SoundPool sp;
	public boolean spactive=false;
	public int[] streamids = new int[3];
	//public File sdpath = getExternalStorageDirectory();
	AssetManager am;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // am=new AssetManager();
        am=this.getAssets();
        mp=new MediaPlayer();
        sp=new SoundPool(5,AudioManager.STREAM_MUSIC,0);
        //afd = new AssetFileDescriptor();
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
        sp.setOnLoadCompleteListener(new OnLoadCompleteListener(){
        	public void onLoadComplete(SoundPool sp, int sampleid, int status){
        		if(status==0){
        			sp.play(sampleid, (float)1, (float)1, currp-1, -1, 1);
        		}
        	}
        });
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
		try {
			playSoundPool();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
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
   
    public void playpause(){
    	if(mp.isPlaying()){
    		mp.pause();
    	}
    	else{
    		assert(!mp.isPlaying());
    		mp.start();
    	}
    }
    
    public void killtrack(int i){
    	assert(0<=i && i<=2);
    	//TODO switch file
    	if(i==0){
    		colors=new char[3];
    		currp=0;
    		picture = new  boolean[3];
    		mp.stop();mp.reset();
    		//DEAL WITH PICTURES TODO
    	}else if(i==1){
    		if(colors[i]=='R'){
    			colors[i]='1';
    		}else if(colors[i]=='G'){
    			colors[i]='2';
    		}else if(colors[i]=='B'){
    			colors[i]='3';
    		}
    		currp=1;
    		// DEAL WITH PICTURES
    	}else{
    		colors[i]=0;
    		currp=2;
    		//DEAL WITH PICTURES
    	}
    }
    
    //TODO get phone volume
    public void playSoundPool() throws IOException {
    	//AssetManager am=this.getAssets();
        //Toast.makeText(this, "Button Pressed", Toast.LENGTH_LONG).show();
        colors[0]='G';
        String filename=new String(colors,0,currp);
        Toast.makeText(this,filename+" "+((Integer)(filename.length())).toString() ,Toast.LENGTH_LONG).show();	
        if (spactive){
        	Toast.makeText(this,filename+" "+((Integer)(filename.length())).toString() ,Toast.LENGTH_LONG).show();
        	streamids[currp-1]=sp.load("./assets/Sounds/"+filename+".mp3", currp-1);
		//	sp.play(streamids[currp-1], (float)1, (float)1, currp-1, -1, 1);
        }
        else{
        	streamids[currp-1]=sp.load("./assets/Sounds/"+filename+".mp3", currp-1);
			//-sp.play(streamids[currp-1], (float)1, (float)1, currp-1, -1, 1);
			spactive=!spactive;
        }
    }
	
    public void playpausepool(){
    	if(spactive){
    		sp.autoPause();
    		spactive=!spactive;
    	}
    	else{
    		sp.autoResume();
    		spactive=!spactive;
    	}
    }
    
    public void killpooltrack(int i){
    	assert(0<=i && i<=2);
    	//TODO switch file
    	if(i==0){
    		colors=new char[3];
    		currp=0;
    		picture = new  boolean[3];
    		mp.stop();mp.reset();
    		//DEAL WITH PICTURES TODO
    	}else if(i==1){
    		if(colors[i]=='R'){
    			colors[i]='1';
    		}else if(colors[i]=='G'){
    			colors[i]='2';
    		}else if(colors[i]=='B'){
    			colors[i]='3';
    		}
    		currp=1;
    		// DEAL WITH PICTURES
    	}else{
    		colors[i]=0;
    		currp=2;
    		//DEAL WITH PICTURES
    	}
    	spactive=!spactive;
    }
    
}

