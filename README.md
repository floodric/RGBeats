RGBeats
=======

RGBeats is an audio take on our visual world.  This application takes camera photos, and creates an song to accompany the pictures.  As you take more pictures, the song builds itself out more and more, growing with the visual enviroment which you are creating.

This project is a result of TartanHacks 2013, and was created by Josh Antonson, Ajay Ravindran, Yaakov Lyubetsky, and Ryan Flood.  This was created in a 24-hour marathon programming session, and development is ongoing, but will not be released to github until completion.  

This application was designed for a 800x600 phone, so as to appeal to the least common denominator, as a result though, it looks skewed on larger devices. We are working on generalizing the design to make it cross-resolution 

We have chosen to do this for security purposes, as this is our first android application, we do not want to compromise any devices by allowing our application to be released in a public form while a security check has not been done.

(We realize most of the visitors to this repo should be smart enough to know to check the security first, but do a quick github search on __eval($_GET(...__  to see why we are so worried.  If you don't know why that's worrying, look it up)

Usage
----------

To use, simply download the source, compile the apk, and install it to your machine.

Once that has been completed, open up the application and click on one of the camera icons to take a picture.
You also can click on the gallery button in the top nav to pull a photo that you have already taken into the app

The song will then generate and start playing.  You can add, pause, or remove songs dynamically, with a maximum of three pictures/song layers 

All photos are saved to the users gallery, and accessible from outside the app
