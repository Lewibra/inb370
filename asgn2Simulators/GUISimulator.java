/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Simulators 
 * 20/04/2014
 * 
 */
package asgn2Simulators;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;
import asgn2Simulators.Log;
import asgn2Simulators.Simulator;

/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements Runnable {
	private CarPark carPark;
	private Simulator sim;
	private Log log;
	
	private JTextField carParks;
	private JTextField smallCarParks;
	private JTextField motorCycleParks;
	private JTextField queueSize;
	
	private JTextField seed;
	private JTextField averageStay;
	private JTextField SD;
	private JTextField chanceOfCar;
	private JTextField chanceOfSmallCar;
	private JTextField chanceOfMc;


	
	private JLabel carParksLabel;
	private JLabel smallCarParksLabel;
	private JLabel motorCycleParksLabel;
	private JLabel queueSizeLabel;
	
	private JLabel seedLabel;
	private JLabel averageStayLabel;
	private JLabel SDLabel;
	private JLabel chanceOfCarLabel;
	private JLabel chanceOfSmallCarLabel;
	private JLabel chanceOfMcLabel;

	
	
	private JButton submit;
	private JTextArea display;
	
	int carParkValue;
	int smallCarParksValue;
	int motorCycleParksValue;
	int queueSizeValue;
	int seedValue;
	double averageStayValue;
	double SDValue;
	double chanceOfCarValue;
	double chanceOfSmallCarValue;
	double chanceOfMcValue;

	
	private JScrollPane scroll;
	private JPanel middlePanel;
	private JFrame frame;
	
	/**
	 * @param arg0
	 * @throws HeadlessException
	 * @throws IOException 
	 */
	public GUISimulator(String arg0) throws HeadlessException, IOException {
		super(arg0);

		middlePanel = new JPanel ();
	    middlePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Display Area" ) );

	    submit = new JButton("Submit");
	    
	    int defaultQueue = Constants.DEFAULT_MAX_QUEUE_SIZE;
	    
	    
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    
	    
	    
	    
	    queueSize = new JTextField(10);
	    System.out.print("Please enter a value for the queue size: ");
	    queueSize.setText(String.valueOf(br.readLine()));
	    queueSizeLabel = new JLabel("Queue Size");
	    
	    carParks = new JTextField(10);
	    System.out.print("Please enter a value for the max car spaces: ");
	    carParks.setText(String.valueOf(br.readLine()));
	    carParksLabel = new JLabel("Car Parks");
	    
	    smallCarParks = new JTextField(10);
	    System.out.print("Please enter a value for the max small car spaces: ");
	    smallCarParks.setText(String.valueOf(br.readLine()));
	    smallCarParksLabel = new JLabel("Small Car Parks");
	    
	    motorCycleParks = new JTextField(10);
	    System.out.print("Please enter a value for the max motor cycle spaces: ");
	    motorCycleParks.setText(String.valueOf(br.readLine()));
	    motorCycleParksLabel = new JLabel("Motorcycle Parks");
	    
	    //
	    seed = new JTextField(10);
	    System.out.print("Please enter a value for the seed: ");
	    seed.setText(String.valueOf(br.readLine()));
	    seedLabel = new JLabel("Seed");
	    
	    averageStay = new JTextField(10);
	    System.out.print("Please enter a value for the average stay: ");
	    averageStay.setText(String.valueOf(br.readLine()));
	    averageStayLabel = new JLabel("Average Stay");
	    
	    SD = new JTextField(10);
	    System.out.print("Please enter a value for the SD: ");
	    SD.setText(String.valueOf(br.readLine()));
	    SDLabel = new JLabel("SD");
	    
	    chanceOfCar = new JTextField(10);
	    System.out.print("Please enter a value for the car chance: ");
	    chanceOfCar.setText(String.valueOf(br.readLine()));
	    chanceOfCarLabel = new JLabel("Car Chance");
	    
	    
	    chanceOfSmallCar = new JTextField(10);
	    System.out.print("Please enter a value for the small car chance: ");
	    chanceOfSmallCar.setText(String.valueOf(br.readLine()));
	    chanceOfSmallCarLabel = new JLabel("Small Car Chance");
	    
	    chanceOfMc = new JTextField(10);
	    System.out.print("Please enter a value for the mc chance: ");
	    chanceOfMc.setText(String.valueOf(br.readLine()));
	    chanceOfMcLabel = new JLabel("Motor Cycle Chance");
	    
	    
	    
	    
	    // create the middle panel components

	    display = new JTextArea(16,58);
	    display.setSize(new Dimension(150, 500));
	    scroll = new JScrollPane(display );
	    scroll.setVerticalScrollBarPolicy(20);
	    
		if (carParks.getText().trim().length() == 0 || smallCarParks.getText().trim().length() == 0 
				|| motorCycleParks.getText().trim().length() == 0 || queueSize.getText().trim().length() == 0){
			updateTextBox("One or more fields was left blank, the simulation will\n"
					+ "begin with default parametres");
			motorCycleParks.setText(String.valueOf(Constants.DEFAULT_MAX_MOTORCYCLE_SPACES));
			smallCarParks.setText(String.valueOf(Constants.DEFAULT_MAX_SMALL_CAR_SPACES));
			carParks.setText(String.valueOf(Constants.DEFAULT_MAX_CAR_SPACES));
			queueSize.setText(String.valueOf(Constants.DEFAULT_MAX_QUEUE_SIZE));
			
			
		    seed.setText(String.valueOf(Constants.DEFAULT_SEED));
		    averageStay.setText(String.valueOf(Constants.DEFAULT_INTENDED_STAY_MEAN));
		    SD.setText(String.valueOf(Constants.DEFAULT_INTENDED_STAY_SD));
		    chanceOfCar.setText(String.valueOf(Constants.DEFAULT_CAR_PROB));
		    chanceOfSmallCar.setText(String.valueOf(Constants.DEFAULT_SMALL_CAR_PROB));
		    chanceOfMc.setText(String.valueOf(Constants.DEFAULT_MOTORCYCLE_PROB));
		}
	    //Action Listeners
	    
	    submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
					carParkValue = Integer.parseInt(carParks.getText());
					smallCarParksValue = Integer.parseInt(smallCarParks.getText());
					motorCycleParksValue = Integer.parseInt(motorCycleParks.getText());
					queueSizeValue= Integer.parseInt(queueSize.getText());

					seedValue = Integer.parseInt(seed.getText());
					averageStayValue = Double.parseDouble(averageStay.getText());
					SDValue = Double.parseDouble(SD.getText());
					chanceOfCarValue= Double.parseDouble(chanceOfCar.getText());
					chanceOfSmallCarValue = Double.parseDouble(chanceOfSmallCar.getText());
					chanceOfMcValue = Double.parseDouble(chanceOfMc.getText());

					
					display.setText("");
					
					setUp(carParkValue, smallCarParksValue, motorCycleParksValue, queueSizeValue, seedValue, averageStayValue,  SDValue,
							chanceOfCarValue,  chanceOfSmallCarValue,  chanceOfMcValue);
					
				
			}
	    	
	    });

	    middlePanel.setLayout(new BoxLayout(middlePanel,BoxLayout.PAGE_AXIS));
	    //Add Textarea in to middle panel
	    middlePanel.add(scroll);
	    middlePanel.add(carParksLabel);
	    middlePanel.add(carParks);
	    
	    middlePanel.add(smallCarParksLabel);
	    middlePanel.add(smallCarParks);
	    
	    
	    
	    middlePanel.add(motorCycleParksLabel);
	    middlePanel.add(motorCycleParks);
	    
	    middlePanel.add(queueSizeLabel);
	    middlePanel.add(queueSize);
	   
	    middlePanel.add(seedLabel);
	    middlePanel.add(seed);
	    
	    middlePanel.add(averageStayLabel);
	    middlePanel.add(averageStay);
	    
	    middlePanel.add(SDLabel); 
	    middlePanel.add(SD);
	    
	    middlePanel.add(chanceOfCarLabel);
	    middlePanel.add(chanceOfCar);
	    
	    middlePanel.add(chanceOfSmallCarLabel);
	    middlePanel.add(chanceOfSmallCar);
	    
	    middlePanel.add(chanceOfMcLabel);
	    middlePanel.add(chanceOfMc);
	    
	    middlePanel.add(submit);

	    

	    frame = new JFrame();
	    frame.setPreferredSize(new Dimension(700, 700));
	    frame.add ( middlePanel );
	    frame.pack ();
	    frame.setLocationRelativeTo ( null );
	    frame.setVisible ( true );
	    
	}
	
	private void updateTextBox(String string){

		display.append(string + "\n");
		
	}
	
	private void setUp(int carParks, int smallCarParks, int motorCycleParks,int queueSizeValue,
						int seed,double meanStay, double sdStay,
							double carProb, double smallCarProb, double mcProb){
		carPark = new CarPark(carParks,smallCarParks,motorCycleParks,queueSizeValue);
		sim = null;
		log = null; 
		try {
			sim = new Simulator(seed, meanStay,  sdStay, carProb,  smallCarProb,  mcProb);
			log = new Log();
		} catch (IOException | SimulationException e1) {
			e1.printStackTrace();
			System.exit(-1);
		}
		try {
			runSimulation();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} 
	}
	
	
	private void runSimulation() throws VehicleException, SimulationException, IOException {
		this.log.initialEntry(this.carPark,this.sim);

		display.append(carPark.initialState() + "\n");
		for (int time=0; time<=Constants.CLOSING_TIME; time++) {
			
			if (time == Constants.CLOSING_TIME){
				barChartSetup();
			}
			
			//queue elements exceed max waiting time
			if (!carPark.queueEmpty()) {
				carPark.archiveQueueFailures(time);
			}
			//vehicles whose time has expired
			if (!carPark.carParkEmpty()) {
				//force exit at closing time, otherwise normal
				boolean force = (time == Constants.CLOSING_TIME);
				carPark.archiveDepartingVehicles(time, force);
			}
			//attempt to clear the queue 
			if (!carPark.carParkFull()) {
				carPark.processQueue(time,this.sim);
			}
			// new vehicles from minute 1 until the last hour
			if (newVehiclesAllowed(time)) { 
				carPark.tryProcessNewVehicles(time,this.sim);
			}
			//Log progress 
			log.logEntry(time,this.carPark);
			display.append(carPark.getStatus(time));

			

		}
		log.finalise(this.carPark);

	}
	
	private boolean newVehiclesAllowed(int time) {
		boolean allowed = (time >=1);
		return allowed && (time <= (Constants.CLOSING_TIME - 60));
	}
	
    private void barChartSetup(){
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	dataset.setValue(carPark.numDissatisfied, "Cars", "Dissatisfied Customers");
    	dataset.setValue(carPark.count, "Cars", "Total Cars");

    	
    	JFreeChart chart = ChartFactory.createBarChart("Car Park", "Vehicle Type", "Cars", dataset, PlotOrientation.VERTICAL, false, true, true );
    	CategoryPlot p = chart.getCategoryPlot();
    	p.setRangeGridlinePaint(Color.black);
    	ChartFrame frame = new ChartFrame("Car Park Bar Chart", chart);
    	frame.setVisible(true);
    	frame.setSize(500, 400);
    
    }


	/**
	 * @param args
	 * @throws IOException 
	 * @throws HeadlessException 
	 */
	public static void main(String[] args) throws HeadlessException, IOException {

		
		GUISimulator textGui = new GUISimulator("text");

	}

	@Override
	public void run() {

		
	}

}
