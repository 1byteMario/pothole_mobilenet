package com.pothole_mobile.pothole_mobilenet;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.opencv.android.*;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Net;
import org.opencv.dnn.Dnn;
import org.opencv.imgproc.Imgproc;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class MainActivity extends AppCompatActivity implements CvCameraViewListener2 {

    static {
       if(!OpenCVLoader.initDebug()) {
           Log.d("ERROR", "Unable to load OpenCV");
        } else {
            Log.d("SUCCESS", "OpenCV loaded");
        }
   }

    private static String TAG = "MainActivity";
    JavaCameraView javaCameraView;
    Mat mRGBA;
    private static final int CAMERA_REQUEST_CODE = 100;
    int activeCam = CameraBridgeViewBase.CAMERA_ID_BACK;


    BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(MainActivity.this) {
        @Override
        public void onManagerConnected(int status)
        {
            if (status == BaseLoaderCallback.SUCCESS) {
                javaCameraView.enableView();
            } else {
                super.onManagerConnected(status);
            }
        }
    };

   // private CameraBridgeViewBase mOpenCvCameraView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        javaCameraView = (JavaCameraView) findViewById(R.id.CameraView);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permissions granted");
            initializeCamera(javaCameraView, activeCam);
        } else {
            Log.d(TAG, "Troubles");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
       /* mOpenCvCameraView = (CameraBridgeViewBase)findViewById(R.id.CameraView);
        mOpenCvCameraView.setVisibility(CameraBridgeViewBase.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode != CAMERA_REQUEST_CODE
                && grantResults[0] != PackageManager.PERMISSION_GRANTED)
                            Toast.makeText(this, "Camera Permission denied", Toast.LENGTH_LONG).show();
        else
               Toast.makeText(this, "Camera Permission granted", Toast.LENGTH_LONG).show();
               initializeCamera(javaCameraView, activeCam);
    }

    private void initializeCamera(JavaCameraView javaCameraView, int activeCamera){
        javaCameraView.setCameraPermissionGranted();
        javaCameraView.setCameraIndex(activeCamera);
        javaCameraView.setVisibility(CameraBridgeViewBase.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);
    }
    @Override
    public void onCameraViewStarted(int width, int height)
    {
        File prtofl = new File("C:\\Users\\mhine\\git-work\\pothole_mobilenet\\app\\MobileNetSSD_deploy.prototxt");
        File wghtsfl = new File("C:\\Users\\mhine\\git-work\\pothole_mobilenet\\app\\MobileNetSSD_deploy.caffemodel");
        String prototxt = prtofl.getPath();
        String caffe_weights = wghtsfl.getPath();

        try{
            net = Dnn.readNetFromCaffe(prototxt, caffe_weights);
            Log.i(TAG, "Network loaded successfully");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onCameraViewStopped()
    {
        mRGBA.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame)
    {
        // code for the back camera
        mRGBA = inputFrame.rgba();
        return mRGBA;
    }
    private Net net;

    private static final String[] classNames = {};

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (javaCameraView != null)
        {
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (javaCameraView != null)
        {
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (OpenCVLoader.initDebug())
        {
            Log.d(TAG, "OpenCV is Configured or Connected successfully.");
            baseLoaderCallback.onManagerConnected(BaseLoaderCallback.SUCCESS);
        }
        else
        {
            Log.d(TAG, "OpenCV not Working or Loaded.");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, baseLoaderCallback);
        }
    }
}
