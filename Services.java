import com.google.maps.model.LatLng;

public class Services {
	
    public static double computeHeading(LatLng from, LatLng to) {
        double fromLat = Math.toRadians(from.lat);
        double fromLng = Math.toRadians(from.lng);
        double toLat = Math.toRadians(to.lat);
        double toLng = Math.toRadians(to.lng);
        double dLng = toLng - fromLng;
        double heading = Math.atan2(
                Math.sin(dLng) * Math.cos(toLat),
                Math.cos(fromLat) * Math.sin(toLat) - Math.sin(fromLat) * Math.cos(toLat) * Math.cos(dLng));
        return Math.toDegrees(heading);
    }
    
    public static LatLng computeOffset(LatLng from, double distance, double heading) {
        distance /= 6831;
        heading = Math.toRadians(heading);
        double fromLat = Math.toRadians(from.lat);
        double fromLng = Math.toRadians(from.lng);
        double cosDistance = Math.cos(distance);
        double sinDistance = Math.sin(distance);
        double sinFromLat = Math.sin(fromLat);
        double cosFromLat = Math.cos(fromLat);
        double sinLat = cosDistance * sinFromLat + sinDistance * cosFromLat * Math.cos(heading);
        double dLng = Math.atan2(
                sinDistance * cosFromLat * Math.sin(heading),
                cosDistance - sinFromLat * sinLat);
        return new LatLng(Math.toDegrees(Math.asin(sinLat)), Math.toDegrees(fromLng + dLng));
    }


}
