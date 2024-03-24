package de.dennisguse.opentracks.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.dennisguse.opentracks.data.models.AltitudeGainLoss;
import de.dennisguse.opentracks.data.models.Chairlift;
import de.dennisguse.opentracks.data.models.TrackPoint;
public class SegmentSplits {
    public static Map<String, List<TrackPoint>> splitSegments(List<TrackPoint> trackPoints) {
        Map<String, List<TrackPoint>> segments = new HashMap<>();
        segments.put("Skiing", new ArrayList<>());
        segments.put("Chairlift", new ArrayList<>());

        AltitudeGainLoss altitudeGainLoss = new AltitudeGainLoss(0, 0);
        Chairlift chairlift = new Chairlift();

        for (int i = 1; i < trackPoints.size(); i++) {
            TrackPoint cPoint = trackPoints.get(i);

            boolean startNewSegment = altitudeGainLoss.shouldStartNewSegment(trackPoints, i) || chairlift.isUserRidingChairlift(trackPoints.subList(i - 1, i + 1));;

            if (startNewSegment) {

                if (AltitudeGainLoss.isSkiing()) {
                    (segments.get("Skiing")).add(cPoint);
                }
                else if (AltitudeGainLoss.isChairlift()) {
                    (segments.get("Chairlift")).add(cPoint);
                }
                else {
                    (segments.get("waiting")).add(cPoint);
                }
            }

//            if (altitudeGainLoss.shouldStartNewSegment(trackPoints, i)) {
//                if (!segments.get("Skiing").isEmpty() && altitudeGainLoss.isSkiing()) {
//                    segments.get("Skiing").add(cPoint);
//                }
//                else if (!segments.get("Chairlift").isEmpty() && altitudeGainLoss.isChairlift()) {
//                    segments.get("Chairlift").add(cPoint);
//                }
//            }
//            else {
//                if (altitudeGainLoss.isSkiing()) {
//                    segments.get("Skiing").add(cPoint);
//                }
//                else if (altitudeGainLoss.isChairlift()) {
//                    segments.get("Chairlift").add(cPoint);
//                }
//            }
        }

        return segments;
    }
}
