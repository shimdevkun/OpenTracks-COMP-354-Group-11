package de.dennisguse.opentracks.data.models;

import java.time.Duration;
import java.util.List;

public class Chairlift {
    private String name;
    private int number;
    private double averageSpeed;
    private String liftType;

    // Constructor
    public Chairlift(String name, int number, double averageSpeed, String liftType) {
        this.name = name;
        this.number = number;
        this.averageSpeed = averageSpeed;
        this.liftType = liftType;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public String getLiftType() {
        return liftType;
    }

    public void setLiftType(String liftType) {
        this.liftType = liftType;
    }

    // Method to determine if the user is riding the chairlift
    public boolean isUserRidingChairlift(List<TrackPoint> trackPoints) {
        if (trackPoints.size() < 2) {
            return false; //Not enough data
        }

        // Thresholds to determine movement of chairlift (you can adjust these as needed)
        double altitudeChangeThreshold = 2.0;
        double speedThreshold = 2;
        double timeThreshold = 1;

        // Get the first and last track points
        TrackPoint firstPoint = trackPoints.get(0);
        TrackPoint lastPoint = trackPoints.get(trackPoints.size() - 1);
        // Check if altitude change is within threshold
        double altitudeChange;

        // Check if altitude change is within threshold
        altitudeChange = Math.abs(firstPoint.getAltitude().toM() - lastPoint.getAltitude().toM());

        if (altitudeChange > altitudeChangeThreshold) {

            return false; //exceed and likely not on chairlift
        }

        // Calculate total distance
        double totalDistance = calculateTotalDistance(trackPoints);

        // Calculate total time
        double totalTime = calculateTotalTime(trackPoints);

        double time = calculateTime(trackPoints);

        // Calculate average speed
        double averageSpeed = totalDistance / totalTime;

        // Check if average speed is below a certain threshold
        if (averageSpeed > speedThreshold) {
            if (averageSpeed < 2 || averageSpeed > 6) //meters/seconds
            return false; //averege speed too slow/fast for a chairlift
        }
        else {
            return true;
        }

        if (totalTime > timeThreshold){
            if (time > 1 && time < 10){
                return true;
            }
            else
                return false;

        }

        return false;
    }



    // Helper method to calculate total distance covered
    private double calculateTotalDistance(List<TrackPoint> trackPoints) {


        double totalDistance = 0.0; //km
        for (int i = 1; i< trackPoints.size(); i++){
            TrackPoint pPoint = trackPoints.get(i - 1);
            TrackPoint cPoint = trackPoints.get(i);
            Distance distance = pPoint.distanceToPrevious(cPoint);
            totalDistance += distance.toKM();
        }
        return totalDistance;
    }

    private double calculateTime(List<TrackPoint> trackPoints) {

        return 0;
    }

    private double calculateTotalTime(List<TrackPoint> trackPoints) {
        double totalTime = 0; //max time for chairlift

        for (int i = 1; i < trackPoints.size(); i++) {
            TrackPoint pPoint = trackPoints.get(i - 1);
            TrackPoint cPoint = trackPoints.get(i);
            // Assuming each track point represents the end of one chairlift ride and the start of the next
            Duration rideDuration = Duration.between(pPoint.getTime(), cPoint.getTime());
            double rideDurationMinutes = rideDuration.toMinutes(); // Convert duration to minutes
            totalTime += rideDurationMinutes; // Add the ride duration to total time
        }
        return totalTime;
    }


}
