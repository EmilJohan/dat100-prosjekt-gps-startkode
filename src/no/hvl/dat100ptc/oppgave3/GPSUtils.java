package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

import java.math.BigDecimal;
import java.math.RoundingMode;
public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;

		min = da[0];
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		
		return min;


	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

			
		double latitudes[] = new double[gpspoints.length];
		
		for(int i = 0; i < gpspoints.length; i++) {
			latitudes[i] = gpspoints[i].getLatitude();

		}
		
		return latitudes;
		
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {
		

        double longitudes[] = new double[gpspoints.length];
		
		for(int i = 0; i < gpspoints.length; i++) {
			longitudes[i] = gpspoints[i].getLongitude();
	    }
	    
	    return longitudes; 
	
	}
	

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1 = gpspoint1.getLatitude();
		double longitude1 = gpspoint1.getLongitude();
		double latitude2 = gpspoint2.getLatitude();
		double longitude2 = gpspoint2.getLongitude();
		
		double latDis = toRadians(latitude2 - latitude1);
		double lonDis = toRadians(longitude2 - longitude1);
		
		double a = (Math.pow(sin(latDis / 2),2)) + cos(toRadians(latitude1)) * cos(toRadians(latitude2)) * (Math.pow(sin(lonDis / 2),2));
		double c = 2 * atan2(sqrt(a), sqrt(1-a));
		d = R * c;
		return d;
		

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;
		
		double distance = distance(gpspoint1, gpspoint2);
		
		secs = (gpspoint2.getTime() - gpspoint1.getTime());
		
		speed = (distance / secs);
		
		return (speed*3.6);

	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";
		
		
		int S = secs % 60;
		int H = secs / 60;
		int M = H % 60;
		H = H / 60;
		
		String s = Integer.toString(S);
		String m = Integer.toString(M);
		String h = Integer.toString(H);
		
		if (S < 10) {
			 s = "0" + Integer.toString(S);
		}
		if (M < 10) {
			 m = "0" + Integer.toString(M);
		}
		if (H < 10) {
			 h = "0" + Integer.toString(H);
		}
		
		timestr = "  " + h + TIMESEP + m + TIMESEP + s;
		return timestr;
		

	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str;
		double rounded = Math.round(d*100.0)/100.0;
		str = ("      " + rounded);
		return str;
	}
}
