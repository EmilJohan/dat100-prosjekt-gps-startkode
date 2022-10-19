package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon)); 

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {
	
		
		
        double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
	    double ystep = MAPYSIZE / (Math.abs(maxlat - minlat)); 

		return ystep;
	}

	public void showRouteMap(int ybase) {
		
		int i = 0;
		int x = 0;
		int y = 0;
		int h = 0;
		int z = 0;
		double[] latitudes = GPSUtils.getLatitudes(gpspoints);
		double[] longitudes = GPSUtils.getLongitudes(gpspoints);
		
		for (i = 1; i < gpspoints.length-1; i++) {
			y += (int)Math.round((latitudes[i] - latitudes[i+1])* ystep());
			x += (int)Math.round((longitudes[i] - longitudes[i+1]) * xstep());
			if (i < 195){
			z += (int)Math.round((latitudes[i+1] - latitudes[i+2])* ystep());
			h += (int)Math.round((longitudes[i+1] - longitudes[i+2]) * xstep());
			}
			else {
				setColor(0,0,255);
				fillCircle(40 + (x*-1), -ybase + 1000 + y, 10);
			}
			
			int pos1 = (-ybase+1000+y);
			int pos2 = (40 + (x*-1));
			int pos3 = (-ybase+1000+z);
			int pos4 = (40 + (h*-1));
			drawLine(pos2,pos1,pos4,pos3);
			fillCircle(40 + (x*-1), -ybase + 1000 + y, 3); 

		}
		
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(250,0,0);
		setFont("Courier",12);
		setBackground(255,255,255);
		
	    int totalTime = gpscomputer.totalTime();
		double totalDistance = gpscomputer.totalDistance();
		double totalElevation = gpscomputer.totalElevation();
		double maxSpeed = gpscomputer.maxSpeed();
		double averageSpeed = gpscomputer.averageSpeed();
		double totalKcal = gpscomputer.totalKcal(80);
		
		String stime =      (" Total Time     :" + GPSUtils.formatTime(totalTime));
		String sdistance =  (" Total Distance :" + GPSUtils.formatDouble(totalDistance) + " km");
		String selevation = (" Total elevation:" + GPSUtils.formatDouble(totalElevation) + " m");
		String sspeed =     (" Max Speed      :" + GPSUtils.formatDouble(maxSpeed)+ " km/h");
		String saspeed =    (" Average Speed  :" + GPSUtils.formatDouble(averageSpeed) + " km/h");
		String senergy =    (" Energy         :" + GPSUtils.formatDouble(totalKcal) + " kcal");
		
		drawString(stime, 0, 30);
		drawString(sdistance, 0, 45);
		drawString(selevation, 0, 60);
		drawString(sspeed, 0, 75);
		drawString(saspeed, 0, 90);
		drawString(senergy, 0, 105);
		
	}

}
