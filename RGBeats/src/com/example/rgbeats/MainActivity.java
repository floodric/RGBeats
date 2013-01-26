package com.example.rgbeats;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class MainActivity extends Activity {

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
    
    public void takePicture(View view) {
    	Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    	int CAMERA_PIC_REQUEST = 1337;
    	startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
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

