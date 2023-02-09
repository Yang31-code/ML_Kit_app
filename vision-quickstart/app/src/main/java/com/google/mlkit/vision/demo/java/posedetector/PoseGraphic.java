package com.google.mlkit.vision.demo.java.posedetector;

import static java.lang.Math.max;
import static java.lang.Math.min;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.google.mlkit.vision.common.PointF3D;
import com.google.mlkit.vision.demo.GraphicOverlay;
import com.google.mlkit.vision.demo.GraphicOverlay.Graphic;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseLandmark;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class PoseGraphic extends Graphic {

  private static final float DOT_RADIUS = 8.0f;
  private static final float IN_FRAME_LIKELIHOOD_TEXT_SIZE = 30.0f;
  private static final float STROKE_WIDTH = 10.0f;
  private static final float POSE_CLASSIFICATION_TEXT_SIZE = 60.0f;

  private final Pose pose;
  private final boolean showInFrameLikelihood;
  private final boolean visualizeZ;
  private final boolean rescaleZForVisualization;
  private float zMin = Float.MAX_VALUE;
  private float zMax = Float.MIN_VALUE;
  private final Paint classificationTextPaint;
  private final Paint leftPaint;
  private final Paint rightPaint;
  private final Paint whitePaint;

//  private static PoseOracle oracle = null;

  private final int TEXT_COLOR = Color.WHITE;
  private final float TEXT_SIZE = 60.0f;
  private final Paint textPaint;

  //start custom variables
  // TODO: Turn this list into a PoseOracle instance that has built-in detection functions
//  List<KeyFrame> points = new ArrayList<>();
  int gestureIndex = 0;
  boolean start = false;
  boolean finished = false;
  float foundElbowAngle = 0;
  //end custom variable

  PoseGraphic(GraphicOverlay overlay, Pose pose, boolean showInFrameLikelihood, boolean visualizeZ, boolean rescaleZForVisualization) {

    super(overlay);

    textPaint = new Paint();
    textPaint.setColor(TEXT_COLOR);
    textPaint.setTextSize(TEXT_SIZE);
    textPaint.setShadowLayer(5.0f, 0f, 0f, Color.BLACK);

    this.pose = pose;
    this.showInFrameLikelihood = showInFrameLikelihood;
    this.visualizeZ = visualizeZ;
    this.rescaleZForVisualization = rescaleZForVisualization;

    classificationTextPaint = new Paint();
    classificationTextPaint.setColor(Color.WHITE);
    classificationTextPaint.setTextSize(POSE_CLASSIFICATION_TEXT_SIZE);
    classificationTextPaint.setShadowLayer(5.0f, 0f, 0f, Color.BLACK);

    whitePaint = new Paint();
    whitePaint.setStrokeWidth(STROKE_WIDTH);
    whitePaint.setColor(Color.WHITE);
    whitePaint.setTextSize(IN_FRAME_LIKELIHOOD_TEXT_SIZE);
    leftPaint = new Paint();
    leftPaint.setStrokeWidth(STROKE_WIDTH);
    leftPaint.setColor(Color.GREEN);
    rightPaint = new Paint();
    rightPaint.setStrokeWidth(STROKE_WIDTH);
    rightPaint.setColor(Color.YELLOW);

    //start custom code
//    if (oracle == null) {
//      setupJSON();
//    }
//    SetupKeyFrames();
    //end custom code
  }

  @Override
  public void draw(Canvas canvas) {

    // To determine the body movement's accuracy
    // We can write a new class that reads in the JSON file, remembers the current key frame and the next key frame
    // It has a detectKeyFrame function that takes in all the landmarks as an input, and returns whether the body movement completed successfully.

    //gets the list of all body landmarks
    List<PoseLandmark> landmarks = pose.getAllPoseLandmarks();

    //doesn't draw if no landmarks
    if (landmarks.isEmpty())
      return;

    //draws the pose
    DrawAllPoints(canvas, landmarks);
    DrawAllLines(canvas);


    //start tracking code
//    Tracking(landmarks);
//    canvas.drawText("Right elbow angle:\n" + foundElbowAngle, 10, 500, textPaint);
    //end tracking code
  }

  void DrawAllPoints(Canvas canvas, List<PoseLandmark> landmarks) {

    // Draw all the points
    for (PoseLandmark landmark : landmarks) {
      if (landmark.getLandmarkType() > 10) {
        drawPoint(canvas, landmark, whitePaint);
      }
      if (visualizeZ && rescaleZForVisualization) {
        zMin = min(zMin, landmark.getPosition3D().getZ());
        zMax = max(zMax, landmark.getPosition3D().getZ());
      }
    }
  }
  void drawPoint(Canvas canvas, PoseLandmark landmark, Paint paint) {
    PointF3D point = landmark.getPosition3D();
    updatePaintColorByZValue(
            paint, canvas, visualizeZ, rescaleZForVisualization, point.getZ(), zMin, zMax);
    canvas.drawCircle(translateX(point.getX()), translateY(point.getY()), DOT_RADIUS, paint);
  }

  void DrawAllLines(Canvas canvas) {

    PoseLandmark leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER);
    PoseLandmark rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER);
    PoseLandmark leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW);
    PoseLandmark rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW);
    PoseLandmark leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST);
    PoseLandmark rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST);
    PoseLandmark leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP);
    PoseLandmark rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP);
    PoseLandmark leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE);
    PoseLandmark rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE);
    PoseLandmark leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE);
    PoseLandmark rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE);

    PoseLandmark leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY);
    PoseLandmark rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY);
    PoseLandmark leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX);
    PoseLandmark rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX);
    PoseLandmark leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB);
    PoseLandmark rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB);
    PoseLandmark leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL);
    PoseLandmark rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL);
    PoseLandmark leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX);
    PoseLandmark rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX);

    drawLine(canvas, leftShoulder, rightShoulder, whitePaint);
    drawLine(canvas, leftHip, rightHip, whitePaint);

    // Left body
    drawLine(canvas, leftShoulder, leftElbow, leftPaint);
    drawLine(canvas, leftElbow, leftWrist, leftPaint);
    drawLine(canvas, leftShoulder, leftHip, leftPaint);
    drawLine(canvas, leftHip, leftKnee, leftPaint);
    drawLine(canvas, leftKnee, leftAnkle, leftPaint);
    drawLine(canvas, leftWrist, leftThumb, leftPaint);
    drawLine(canvas, leftWrist, leftPinky, leftPaint);
    drawLine(canvas, leftWrist, leftIndex, leftPaint);
    drawLine(canvas, leftIndex, leftPinky, leftPaint);
    drawLine(canvas, leftAnkle, leftHeel, leftPaint);
    drawLine(canvas, leftHeel, leftFootIndex, leftPaint);

    // Right body
    drawLine(canvas, rightShoulder, rightElbow, rightPaint);
    drawLine(canvas, rightElbow, rightWrist, rightPaint);
    drawLine(canvas, rightShoulder, rightHip, rightPaint);
    drawLine(canvas, rightHip, rightKnee, rightPaint);
    drawLine(canvas, rightKnee, rightAnkle, rightPaint);
    drawLine(canvas, rightWrist, rightThumb, rightPaint);
    drawLine(canvas, rightWrist, rightPinky, rightPaint);
    drawLine(canvas, rightWrist, rightIndex, rightPaint);
    drawLine(canvas, rightIndex, rightPinky, rightPaint);
    drawLine(canvas, rightAnkle, rightHeel, rightPaint);
    drawLine(canvas, rightHeel, rightFootIndex, rightPaint);

  }
  void drawLine(Canvas canvas, PoseLandmark startLandmark, PoseLandmark endLandmark, Paint paint) {

    PointF3D start = startLandmark.getPosition3D();
    PointF3D end = endLandmark.getPosition3D();

    // Gets average z for the current body line
    float avgZInImagePixel = (start.getZ() + end.getZ()) / 2;
    updatePaintColorByZValue(
            paint, canvas, visualizeZ, rescaleZForVisualization, avgZInImagePixel, zMin, zMax);

    canvas.drawLine(
            translateX(start.getX()),
            translateY(start.getY()),
            translateX(end.getX()),
            translateY(end.getY()),
            paint);
  }

  void setupJSON() {
    String url = "https://api.npoint.io/7cc23e4c9216b56abf53";
    RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
    StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
        try {
          JSONObject obj = new JSONObject(response);
          System.out.println("JSON read");
          System.out.println(obj);
//          oracle = new PoseOracle(obj);
        } catch (JSONException e) {
          System.out.println(e);
        }
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        System.out.println(error);
      }
    });

    rq.add(sr);
  }

  //start custom functions

  //tracks indexes from the list of pose landmarks
//  private void Tracking(List<PoseLandmark> poseLandmarks) {
//
//    //if the gesture tracking hasn't finished yet
//    if (!finished) {
//
//      //outputs what frame
////      textViewD.setText("Index: " + gestureIndex);
//
//      if (WithinAngle(poseLandmarks)) {
//        //checks if the angle fits the current gesture keyframe
//
//        //increases the keyframe
//        gestureIndex++;
//
//        //checks if the gesture is finished
//        if (gestureIndex >= points.size())
//          finished = true;
//      }
//    } else {
//
//      //outputs that the gesture is finished
//      start = false;
////      textViewD.setText("Finished!");
//    }
//  }

  // TODO: Put these functions into a separate Helper class
  //Uses pythagoras to get the distance between two points
//  double GetDistance(Point start, Point end) {
//    double xDistance2 = Math.pow(start.x - end.x, 2);
//    double yDistance2 = Math.pow(start.y - end.y, 2);
//    double distance = Math.pow(xDistance2 + yDistance2, 0.5);
//    return distance;
//  }
//
//  //returns the angle between three points (the middle point)
//  double GetAnglePoints(Point trackStart, Point trackMid, Point trackEnd) {
//    double aLength = GetDistance(trackStart, trackMid);
//    double bLength = GetDistance(trackMid, trackEnd);
//    double cLength = GetDistance(trackEnd, trackStart);
//    return GetAngleLengths(aLength, bLength, cLength);
//  }
//
//  //returns the angle between three lengths (angle opposite length c)
//  double GetAngleLengths(double a, double b, double c) {
//    double C_radian = Math.acos((Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2)) / (2 * a * b));
//    double C_degrees = Math.toDegrees(C_radian);
//    return C_degrees;
//  }
//
//  //checks if the angle of the keyframe indexes are within the target angle
//  boolean WithinAngle(List<PoseLandmark> landMarks) {
//
//    KeyFrame point = points.get(gestureIndex);
//    List<Integer> toTrack = point.toTrack;
//    double targetAngle = point.angle;
//    double leniency = point.leniency;
//
//    Point start = new Point(landMarks.get(toTrack.get(0)).getPosition().x, landMarks.get(toTrack.get(0)).getPosition().y);
//    Point mid = new Point(landMarks.get(toTrack.get(1)).getPosition().x, landMarks.get(toTrack.get(1)).getPosition().y);
//    Point end = new Point(landMarks.get(toTrack.get(2)).getPosition().x, landMarks.get(toTrack.get(2)).getPosition().y);
//
//    double elbowAngle = GetAnglePoints(start, mid, end);
//
//    foundElbowAngle = (float)elbowAngle;
//    return (elbowAngle > targetAngle - leniency && elbowAngle < targetAngle + leniency);
//  }

  //start detecting gesture
  public void startDetecting(View v) {
    start = true;
  }

  //resets the gesture
  public void detectGesture(View v) {
    finished = false;
    gestureIndex = 0;
  }
}
