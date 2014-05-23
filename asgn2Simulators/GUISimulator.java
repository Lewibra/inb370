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

import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;

/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements Runnable {
	private CarPark carPark;
	private Simulator sim;
	
	private Log log;
	
	
	private JTextArea display;
	private JScrollPane scroll;
	private JPanel middlePanel;
	private JFrame frame;
	
	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUISimulator(String arg0) throws HeadlessException {
		super(arg0);
		middlePanel = new JPanel ();
	    middlePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Display Area" ) );

	    // create the middle panel components

	    display = new JTextArea(16,58);
	    
	    scroll = new JScrollPane(display );
	    scroll.setVerticalScrollBarPolicy(20);

	    //Add Textarea in to middle panel
	    middlePanel.add ( scroll );

	    // My code
	    frame = new JFrame ();
	    frame.add ( middlePanel );
	    frame.pack ();
	    frame.setLocationRelativeTo ( null );
	    frame.setVisible ( true );
	    
	    

	}
	
	public void updateTextBox(String string){

		display.append("\n" + string);
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		CarPark cp = new CarPark();
		Simulator s = null;
		Log l = null; 
		try {
			s = new Simulator();
			l = new Log();
		} catch (IOException | SimulationException e1) {
			e1.printStackTrace();
			System.exit(-1);
		}
		
		//TODO: Implement Argument Processing 
		
		//Run the simulation 
		SimulationRunner sr = new SimulationRunner(cp,s,l);
		try {
			sr.runSimulation();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} 

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GUISimulator textGui = new GUISimulator("text");
		textGui.run();

	}

}
