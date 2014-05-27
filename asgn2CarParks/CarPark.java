/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2CarParks 
 * 21/04/2014
 * 
 */
package asgn2CarParks;


import java.util.ArrayList;

import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;
import asgn2Simulators.Simulator;
import asgn2Vehicles.Car;
import asgn2Vehicles.MotorCycle;
import asgn2Vehicles.Vehicle;

/**
 * The CarPark class provides a range of facilities for working with a car park in support 
 * of the simulator. In particular, it maintains a collection of currently parked vehicles, 
 * a queue of vehicles wishing to enter the car park, and an historical list of vehicles which 
 * have left or were never able to gain entry. 
 * ddddddd
 * The class maintains a wide variety of constraints on small cars, normal cars and motorcycles 
 * and their access to the car park. See the method javadoc for details. 
 * 
 * The class relies heavily on the asgn2.Vehicle hierarchy, and provides a series of reports 
 * used by the logger. 
 * 
 * @author hogan
 *
 */
public class CarPark {
	
	private int allSpaces;
	private int maxCarSpaces;
	private int maxSmallCarSpaces;
	private int maxMotorCycleSpaces;
	private int maxQueueSize;
	private int numParkedNormal=0;
	private int numParkedSmall=0;
	private int numParkedCycle=0;
	
	//These methods are public for the graph
	public static int numDissatisfied = 0;
	public static int count = 0;
	
	
	
	private String status = "";
	
	private boolean parkIsFull = false;
	private boolean parkIsEmpty = true;
	private boolean queueIsFull = false;
	private boolean queueIsEmpty = true;
	
	private int carCount = 0;
	private int smallCarCount = 0;
	private int motorCycleCount = 0;
	
	
	private int carIdCount = 1;
	private int bikeIdCount = 1;

	private ArrayList<Vehicle> past = new ArrayList<Vehicle>();
	
	private String idString;
	
	private ArrayList<Vehicle> archivedVehicles;
	private ArrayList<Vehicle> archiveDissatisfiedCars;
	
	private ArrayList<Vehicle> queue;

	
	private ArrayList<Vehicle> smallCarArray;
	private ArrayList<Vehicle> carArray;
	private ArrayList<Vehicle> bikeArray;
	private ArrayList<ArrayList<Vehicle>> spaces;
	
	
	
	
	

	
	/**
	 * CarPark constructor sets the basic size parameters. 
	 * Uses default parameters
	 */
	public CarPark() {
		this(Constants.DEFAULT_MAX_CAR_SPACES,Constants.DEFAULT_MAX_SMALL_CAR_SPACES,
				Constants.DEFAULT_MAX_MOTORCYCLE_SPACES,Constants.DEFAULT_MAX_QUEUE_SIZE);
		//Setting up important variables
		maxCarSpaces = Constants.DEFAULT_MAX_CAR_SPACES - Constants.DEFAULT_MAX_SMALL_CAR_SPACES;
		maxSmallCarSpaces = Constants.DEFAULT_MAX_SMALL_CAR_SPACES;
		maxMotorCycleSpaces = Constants.DEFAULT_MAX_MOTORCYCLE_SPACES;
		maxQueueSize = Constants.DEFAULT_MAX_QUEUE_SIZE;
		allSpaces = maxCarSpaces + maxSmallCarSpaces + maxMotorCycleSpaces + maxQueueSize;
		
		//Creating our storage data types
		archivedVehicles = new ArrayList<Vehicle>();
		archiveDissatisfiedCars  = new ArrayList<Vehicle>();
		queue = new ArrayList<Vehicle>(maxQueueSize);
		
		smallCarArray = new ArrayList<Vehicle>();
		carArray = new ArrayList<Vehicle>();
		bikeArray = new ArrayList<Vehicle>();
		
		//Making it easier for when we remove cars from these lists
		spaces = new ArrayList<ArrayList<Vehicle>>();
		spaces.add(carArray);
		spaces.add(bikeArray);
		spaces.add(smallCarArray);


		

	}
	
	/**
	 * CarPark constructor sets the basic size parameters. 
	 * @param maxCarSpaces maximum number of spaces allocated to cars in the car park 
	 * @param maxSmallCarSpaces maximum number of spaces (a component of maxCarSpaces) 
	 * 						 restricted to small cars
	 * @param maxMotorCycleSpaces maximum number of spaces allocated to MotorCycles
	 * @param maxQueueSize maximum number of vehicles allowed to queue
	 * @author kyleannett
	 */
	public CarPark(int maxCarSpaces,int maxSmallCarSpaces, int maxMotorCycleSpaces, int maxQueueSize) {
		this.maxCarSpaces = maxCarSpaces - maxSmallCarSpaces;
		this.maxSmallCarSpaces = maxSmallCarSpaces;
		this.maxMotorCycleSpaces = maxMotorCycleSpaces;
		this.maxQueueSize = maxQueueSize;
		allSpaces = maxCarSpaces + maxSmallCarSpaces + maxMotorCycleSpaces + maxQueueSize;

		smallCarArray = new ArrayList<Vehicle>();
		carArray = new ArrayList<Vehicle>();
		bikeArray = new ArrayList<Vehicle>();
		queue = new ArrayList<Vehicle>();
		archivedVehicles = new ArrayList<Vehicle>();
		archiveDissatisfiedCars  = new ArrayList<Vehicle>();
		
		spaces = new ArrayList<ArrayList<Vehicle>>();
		spaces.add(carArray);
		spaces.add(bikeArray);
		spaces.add(smallCarArray);

	}

	/**
	 * Archives vehicles exiting the car park after a successful stay. Includes transition via 
	 * Vehicle.exitParkedState(). 
	 * @param time int holding time at which vehicle leaves
	 * @param force boolean forcing departure to clear car park 
	 * @throws VehicleException if vehicle to be archived is not in the correct state 
	 * @throws SimulationException if one or more departing vehicles are not in the car park when operation applied
	 * @author Lewis
	 */
	public void archiveDepartingVehicles(int time,boolean force) throws VehicleException, SimulationException {
		ArrayList<Vehicle> spacesClone = new ArrayList<Vehicle>();

		
		
		for (int i = 0; i < spaces.size(); i++){
				for (Vehicle v : spaces.get(i)){
					if (force){
						spacesClone.add(v);
					}else
					if (v.getDepartureTime() == time){
						spacesClone.add(v);
						archivedVehicles.add(v);
					}
					
				}
				for (Vehicle v : spacesClone){
					unparkVehicle(v, time);
					if (v instanceof Car) {
						if (((Car)v).isSmall()) {
							smallCarCount--;
						} else {
							carCount--;
						}
					} else {
						motorCycleCount--;
					}
				}
				spacesClone.clear();
	
		}
	}
	
		
	/**
	 * Method to archive new vehicles that don't get parked or queued and are turned 
	 * away
	 * @param v Vehicle to be archived
	 * @throws SimulationException if vehicle is currently queued or parked
	 * @author Lewis
	 */
	public void archiveNewVehicle(Vehicle v) throws SimulationException {
		if (v.isParked() == true || v.isQueued() == true){
			throw new SimulationException("Can't archive this vehicle");
		}
		archivedVehicles.add(v);
		numDissatisfied++;
	
	}
	
	/**
	 * Archive vehicles which have stayed in the queue too long 
	 * @param time int holding current simulation time 
	 * @throws VehicleException if one or more vehicles not in the correct state or if timing constraints are violated
	 * @author kyleannett, Lewis
	 */
	public void archiveQueueFailures(int time) throws VehicleException {
		
		for(int i = 0; i < queue.size(); i++){
			if((queue.get(i).isQueued() != true)){
				throw new VehicleException("vehicle in the wrong state");
			}
			else if(time < 0 || time > Constants.CLOSING_TIME){
				throw new VehicleException("vehicle has been in the queue for too long");
			}


			else{
				Vehicle v;
				v = queue.get(i);
				if((time - v.getArrivalTime()) > Constants.MAXIMUM_QUEUE_TIME){
					archiveDissatisfiedCars.add(v);
					archivedVehicles.add(v);
					numDissatisfied++;
					queue.remove(v);
					if (v instanceof Car) {
						if (((Car)v).isSmall()) {
							status += "|S:Q>A|";
						} else {
							status += "|C:Q>A|";
						}
					} else {
						status += "|M:Q>A|";
					}
				}
			
			}
		}
		
	}
	
	/**
	 * Simple status showing whether carPark is empty
	 * @return true if car park empty, false otherwise
	 * @author Lewis, Kyle
	 */
	public boolean carParkEmpty() {
		if (getNumCars() + getNumMotorCycles() == 0 && queueEmpty()){
			parkIsEmpty = true;
		}else{
			parkIsEmpty = false;
		}
		return parkIsEmpty;
	}
	
	/**
	 * Simple status showing whether carPark is full
	 * @return true if car park full, false otherwise
	 * @author Lewis, Kyle
	 */
	public boolean carParkFull() {
		if (getNumCars() + getNumMotorCycles() + queue.size() >= allSpaces){
			parkIsFull = true;
		}else{
			parkIsFull = false;
		}
		return parkIsFull;
	}
	
	/**
	 * Method to add vehicle successfully to the queue
	 * Precondition is a test that spaces are available
	 * Includes transition through Vehicle.enterQueuedState 
	 * @param v Vehicle to be added 
	 * @throws SimulationException if queue is full  
	 * @throws VehicleException if vehicle not in the correct state 
	 * @author Lewis
	 */
	public void enterQueue(Vehicle v) throws SimulationException, VehicleException {
		if (queue.size() >= maxQueueSize){
			throw new SimulationException("Queue is full");
		}
		if (v.isQueued()){
			throw new SimulationException("wrong state");
		}
		
		queue.add(v);
		v.enterQueuedState();

	}
	
	
	/**
	 * Method to remove vehicle from the queue after which it will be parked or 
	 * removed altogether. Includes transition through Vehicle.exitQueuedState.  
	 * @param v Vehicle to be removed from the queue 
	 * @param exitTime int time at which vehicle exits queue
	 * @throws SimulationException if vehicle is not in queue 
	 * @throws VehicleException if the vehicle is in an incorrect state or timing 
	 * constraints are violated
	 */
	public void exitQueue(Vehicle v,int exitTime) throws SimulationException, VehicleException {
		if(v.isQueued() != true){
			throw new VehicleException("vehicle not in the queue");
		}
		else if(exitTime < 0 || exitTime> Constants.CLOSING_TIME){
			throw new VehicleException("time constraints are off");
		}
		v.exitQueuedState(exitTime);
		queue.remove(v);
		//archiveDissatisfiedCars.add(v);

	}
	
	/**
	 * State dump intended for use in logging the final state of the carpark
	 * All spaces and queue positions should be empty and so we dump the archive
	 * @return String containing dump of final carpark state 
	 */
	public String finalState() {
		String str = "Vehicles Processed: count:" + 
				this.count + ", logged: " + this.archivedVehicles.size() 
				+ "\nVehicle Record: \n";
		for (Vehicle v : this.archivedVehicles) {
			str += v.toString() + "\n\n";
		}
		return str + "\n";
	}
	
	/**
	 * Simple getter for number of cars in the car park 
	 * @return number of cars in car park, including small cars
	 */
	public int getNumCars() {
		return carCount + smallCarCount;
	}
	
	/**
	 * Simple getter for number of motorcycles in the car park 
	 * @return number of MotorCycles in car park, including those occupying 
	 * 			a small car space
	 */
	public int getNumMotorCycles() {
		return motorCycleCount;
	}
	
	/**
	 * Simple getter for number of small cars in the car park 
	 * @return number of small cars in car park, including those 
	 * 		   not occupying a small car space. 
	 */
	public int getNumSmallCars() {
		return smallCarCount;
	}
	
	/**
	 * Method used to provide the current status of the car park. 
	 * Uses private status String set whenever a transition occurs. 
	 * Example follows (using high probability for car creation). At time 262, 
	 * we have 276 vehicles existing, 91 in car park (P), 84 cars in car park (C), 
	 * of which 14 are small (S), 7 MotorCycles in car park (M), 48 dissatisfied (D),
	 * 176 archived (A), queue of size 9 (CCCCCCCCC), and on this iteration we have 
	 * seen: car C go from Parked (P) to Archived (A), C go from queued (Q) to Parked (P),
	 * and small car S arrive (new N) and go straight into the car park<br>
	 * 262::276::P:91::C:84::S:14::M:7::D:48::A:176::Q:9CCCCCCCCC|C:P>A||C:Q>P||S:N>P|
	 * @return String containing current state 
	 */
	public String getStatus(int time) {
		String str = time +"::"
		+ count + "::"		
		+ "P:" + (getNumCars() + getNumMotorCycles()) + "::"
		+ "C:" + getNumCars() + "::S:" + getNumSmallCars()
		+ "::M:" + getNumMotorCycles()
		+ "::D:" + numDissatisfied
		+ "::A:" + archivedVehicles.size()
		+ "::Q:" + numVehiclesInQueue(); 
		for (Vehicle v : this.queue) {
			if (v instanceof Car) {
				if (((Car)v).isSmall()) {
					str += "S";
				} else {
					str += "C";
				}
			} else {
				str += "M";
			}
		}
		str += this.status;
		this.status="";
		return str+"\n";
	}
	

	/**
	 * State dump intended for use in logging the initial state of the carpark.
	 * Mainly concerned with parameters. 
	 * @return String containing dump of initial carpark state 
	 */
	public String initialState() {
		return "CarPark [maxCarSpaces: " + (maxCarSpaces + maxSmallCarSpaces)
				+ " maxSmallCarSpaces: " + this.maxSmallCarSpaces 
				+ " maxMotorCycleSpaces: " + this.maxMotorCycleSpaces 
				+ " maxQueueSize: " + this.maxQueueSize + "]";
	}

	/**
	 * Simple status showing number of vehicles in the queue 
	 * @return number of vehicles in the queue
	 */
	public int numVehiclesInQueue() {
		return queue.size();
	}
	
	/**
	 * Method to add vehicle successfully to the car park store. 
	 * Precondition is a test that spaces are available. 
	 * Includes transition via Vehicle.enterParkedState.
	 * @param v Vehicle to be added 
	 * @param time int holding current simulation time
	 * @param intendedDuration int holding intended duration of stay 
	 * @throws SimulationException if no suitable spaces are available for parking 
	 * @throws VehicleException if vehicle not in the correct state or timing constraints are violated
	 * @author Lewis
	 */
	public void parkVehicle(Vehicle v, int time, int intendedDuration) throws SimulationException, VehicleException {
		if ((numParkedNormal + numParkedSmall + numParkedCycle) > (maxCarSpaces + maxSmallCarSpaces + maxMotorCycleSpaces)){
			throw new SimulationException("Car park is full!");
		}
		if (v.isParked()||v.wasParked()||v.getArrivalTime() > Constants.CLOSING_TIME){
			throw new VehicleException("vehicle exception thrown");
		}
		
		if (v instanceof Car){
			if (((Car) v).isSmall()){
				smallCarCount++;
				if (smallCarArray.size() < maxSmallCarSpaces){
					smallCarArray.add(v);
				}else if (carArray.size() < maxCarSpaces){
					carArray.add(v);
				}
			}else {
				carCount++;
				if (carArray.size() < maxCarSpaces){
					carArray.add(v);
				}
				
			}
		}else if (v instanceof MotorCycle) {
			motorCycleCount++;
			if (bikeArray.size() < maxMotorCycleSpaces){
				bikeArray.add(v);
			} else if (smallCarArray.size() < maxSmallCarSpaces) {
				 smallCarArray.add(v);
			}
		}
		v.enterParkedState(time, intendedDuration);
		
	}

	/**
	 * Silently process elements in the queue, whether empty or not. If possible, add them to the car park. 
	 * Includes transition via exitQueuedState where appropriate
	 * Block when we reach the first element that can't be parked. 
	 * @param time int holding current simulation time 
	 * @throws SimulationException if no suitable spaces available when parking attempted
	 * @throws VehicleException if state is incorrect, or timing constraints are violated
	 * @author Lewis
	 */
	public void processQueue(int time, Simulator sim) throws VehicleException, SimulationException {
	
		archiveQueueFailures(time);
		
		final int tempQueueSize = queue.size();
		Vehicle v;
		outerloop:
		for (int i = 0; i < tempQueueSize; i++){

			v = queue.get(0);
			if (!v.isQueued()){
				throw new VehicleException("the vehicle is in the wrong state");
			}
			if (time < 0 || v.getArrivalTime() > Constants.CLOSING_TIME){
				throw new VehicleException("Timing constraints are violated!");
			}
			
			if (spacesAvailable(v)){	
				if (!spacesAvailable(v)){
					throw new SimulationException("There are no suitable spaces to park the first in queue");
				}
				exitQueue(v, time);
				parkVehicle(v, time, sim.setDuration());
				
				if (v instanceof Car){
					if (((Car)v).isSmall()){
						status += "|S:Q>P|";
					}else{
						status += "|C:Q>P|";
					}
				}else{
					status += "|M:Q>P|";
				}
			}else{
				break outerloop;
			}
		}
	}

	/**
	 * Simple status showing whether queue is empty
	 * @return true if queue empty, false otherwise
	 */
	public boolean queueEmpty() {
		if (queue.size() == 0){
			queueIsEmpty = true;
		}else{
			queueIsEmpty = false;
		}
		return queueIsEmpty;
	}

	/**
	 * Simple status showing whether queue is full
	 * @return true if queue full, false otherwise
	 */
	public boolean queueFull() {
		if (queue.size() >= maxQueueSize){
			queueIsFull = true;
		}else{
			queueIsFull = false;
		}
		return queueIsFull;
	}
	
	/**
	 * Method determines, given a vehicle of a particular type, whether there are spaces available for that 
	 * type in the car park under the parking policy in the class header.  
	 * @param v Vehicle to be stored. 
	 * @return true if space available for v, false otherwise 
	 * @author kyle/lewis
	 */
	public boolean spacesAvailable(Vehicle v) {
		if (v instanceof Car){
			if(((Car)v).isSmall()){
				if(smallCarArray.size() < maxSmallCarSpaces){
					return true;
				}
				else if(carArray.size() < maxCarSpaces){
					return true;
				}
			}else{
				if (carArray.size() < maxCarSpaces){
					return true;
				}
			}
		}
		else if (v instanceof MotorCycle){
			if(bikeArray.size() < maxMotorCycleSpaces){
				return true;
			}else if(smallCarArray.size() < maxSmallCarSpaces){
				return true;
			}
		}
		return false;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CarPark [count: " + count
				+ " numCars: " + getNumCars()
				+ " numSmallCars: " + getNumSmallCars()
				+ " numMotorCycles: " + getNumMotorCycles()
				+ " queue: " + numVehiclesInQueue()
				+ " numDissatisfied: " + numDissatisfied
				+ " past: " + past.size() + "]";
	}

	/**
	 * Method to try to create new vehicles (one trial per vehicle type per time point) 
	 * and to then try to park or queue (or archive) any vehicles that are created 
	 * @param sim Simulation object controlling vehicle creation 
	 * @throws SimulationException if no suitable spaces available when operation attempted 
	 * @throws VehicleException if vehicle creation violates constraints 
	 * @author Lewis
	 */
	public void tryProcessNewVehicles(int time,Simulator sim) throws VehicleException, SimulationException {
		if(smallCarArray.size() + carArray.size() + bikeArray.size() + queue.size() > allSpaces){
			throw new SimulationException("Car Park is full");
		}


		if(sim.newCarTrial()){

			
			if (sim.smallCarTrial()){
				idString = "C" + carIdCount;
				Car smallCar = new Car(idString, time, true);
				count++;

				carIdCount++;

				if (queueFull() && !spacesAvailable(smallCar)){
					archiveNewVehicle(smallCar);
					status += "|S:N>A|";
					
				}else if (!spacesAvailable(smallCar)){
					enterQueue(smallCar);
					status += "|S:N>Q|";
					
				}else{

					parkVehicle(smallCar, time, sim.setDuration());
					status += "|S:N>P|";
				}			

			}else{
				idString = "C" + carIdCount;
				Car normalCar = new Car(idString, time, false);
				carIdCount++;
				count++;
				if (queueFull() && !spacesAvailable(normalCar)){
					archiveNewVehicle(normalCar);
					status += "|C:N>A|";
					
				}else if (!spacesAvailable(normalCar)){
					enterQueue(normalCar);
					status += "|C:N>Q|";
					
				}else{
	
					parkVehicle(normalCar, time, sim.setDuration());
					status += "|C:N>P|";
				}
			}
		}
			
		if (sim.motorCycleTrial()){

			idString = "MC" + carIdCount;
			MotorCycle motorCycle = new MotorCycle(idString, time);
			carIdCount++;
			count++;
			if (queueFull() && !spacesAvailable(motorCycle)){
				archiveNewVehicle(motorCycle);
				status += "|M:N>A|";
			}else if (!spacesAvailable(motorCycle)){
				enterQueue(motorCycle);
				status += "|M:N>Q|";
			}else{
				parkVehicle(motorCycle, time, sim.setDuration());
				status += "|M:N>P|";
			}
		
		}
		
		

	}
	
	

	/**
	 * Method to remove vehicle from the carpark. 
	 * For symmetry with parkVehicle, include transition via Vehicle.exitParkedState.  
	 * So vehicle should be in parked state prior to entry to this method. 
	 * @param v Vehicle to be removed from the car park 
	 * @throws VehicleException if Vehicle is not parked, is in a queue, or violates timing constraints 
	 * @throws SimulationException if vehicle is not in car park
	 */
	public void unparkVehicle(Vehicle v,int departureTime) throws VehicleException, SimulationException {
		if(v.isQueued()||!v.isParked()||v.getDepartureTime() < 0){
			throw new VehicleException("Invalid stuff Brah");
		}
		else if(v.isQueued()!=true && v.isParked()!=true){
			throw new SimulationException("vehicle does not exist brah");
		}
		
		v.exitParkedState(departureTime);
		
		if (v instanceof Car){
			//remove small cars
			if (((Car) v).isSmall()){
				if (smallCarArray.contains(v)){
					smallCarArray.remove(v);
				}else{
					carArray.remove(v);
				}
				status += "|S:P>A|";
				
			//remove cars
			}else{
				carArray.remove(v);
				status += "|C:P>A|";
			}
		//remove bikes
		}else{
			if (smallCarArray.contains(v)){
				smallCarArray.remove(v);
			}else{
				bikeArray.remove(v);
			}
			status += "|M:P>A|";
		}
	}
	
	/**
	 * Helper to set vehicle message for transitions 
	 * @param v Vehicle making a transition (uses S,C,M)
	 * @param source String holding starting state of vehicle (N,Q,P,A) 
	 * @param target String holding finishing state of vehicle (Q,P,A) 
	 * @return String containing transition in the form: |(S|C|M):(N|Q|P|A)>(Q|P|A)| 
	 */
	private String setVehicleMsg(Vehicle v,String source, String target) {
		String str="";
		if (v instanceof Car) {
			if (((Car)v).isSmall()) {
				str+="S";
			} else {
				str+="C";
			}
		} else {
			str += "M";
		}
		return "|"+str+":"+source+">"+target+"|";
	}
}
