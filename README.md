# pothole_mobilenet
The is a repository for Pothole MobileNet, a pothole detecting GPS application for Android.


## Goals

i. Setup Android application with OpenCV. Complete [tutorial](https://docs.opencv.org/master/d2/d58/tutorial_table_of_content_dnn.html) for running deep nueral networks in mobile applications, by running a caffe model with pretrained wieghts, provided in 
   OpenCV's Dnn module.

ii. Train custom weights with a data set consisting of pothole images.

iii. Impelement GPS and notifications features.

## Environment

<p> Windows 10 <br>
IntelliJ </p>

## Setup

IntelliJ with Android Studio is used for this project.
<p>
i.  Clone this repository.<br>
ii.  Vist [OpenCV](https://opencv.org/releases/) and download version 4.5.2 for Android. Unzip package in directory of choice.<br>
iii. Download and install OpenCV for Windows 10.<br>
iii. Open IntelliJ and select "Open". When prompted, navigate to directory containing recently clone project and double click.<br>
iv. If a physical Android device isn't present to have the app installed, open "AVD Manager" and select an Android device to simulate in emulator.<br>
v. Open "Project Structure" and select the add symbol (+), then select "Import Module". When promped, navigate to directory of unziped OpenCV-Android download. Navigate 
to ../opencv-452-adroid-sdk/OpenCV-android-sdk/sdk and double click. When prompted, rename the sdk to OpenCV452. Click on pothole_mobilenet/app and select Dependencies > (+) > Select OpenCV452 > OK.
</p>

## Building and Running

i. In cmd terminal navigate to project's directory and execute "gradlew clean build".
ii. To run as emulator, open cmd terminal and navigate to directory of OpenCV, intalled in windows: ../AppData/Local/Android/emulator. Type "emulator -list-avds" to view available emulators to run. To run project in emulator, execute "emulator -avd emulator-id".
iii. To run physical device, connect device to pc through usb. In IntelliJ, click "Device Explorer" to verify connection of device. Select device in "Run/Debug Configurations". Click Play button to launch the app.

## Status

This project has most of Goal-i complete. Currently investigating a bug that hinders ability to open .prototxt file. This could be an issue with IntelliJ.
