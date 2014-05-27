/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Tests 
 * 29/04/2014
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Simulators.Log;
import asgn2Simulators.Simulator;
import asgn2Vehicles.*;

/**
 * @author hogan
 *
 */
public class CarParkTests {
	private String genericId = "1C";
	private int genericTime = 100;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#archiveDepartingVehicles(int, boolean)}.
	 * @throws SimulationException 
	 * @throws VehicleException 
	 * @author Kyle Annett
	 */
	@Test
	public void testArchiveDepartingVehicles() throws VehicleException, SimulationException {
		CarPark testInstance = new CarPark();
		Car newCar = new Car(genericId, genericTime, false);
		testInstance.parkVehicle(newCar, genericTime, 200);
		assertFalse(testInstance.carParkEmpty());
		
		testInstance.archiveDepartingVehicles(200, true);
		assertTrue(testInstance.carParkEmpty());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#archiveNewVehicle(asgn2Vehicles.Vehicle)}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 * @author kyle
	 */
	@Test
	public void testArchiveNewVehicle() throws VehicleException, SimulationException {
		CarPark testInstance = new CarPark();
		Car newCar = new Car(genericId, genericTime, false);
		testInstance.archiveNewVehicle(newCar);
		assertFalse(newCar.wasParked());
		assertFalse(newCar.wasQueued());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#archiveQueueFailures(int)}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 * @author kyle
	 */
	@Test
	public void testArchiveQueueFailures() throws VehicleException, SimulationException {
		CarPark testInstance = new CarPark(1,1,1,4);
		Car newCar = new Car(genericId, genericTime, false);
		Car newCar2 = new Car(genericId, genericTime, false);
		Car newCar3 = new Car(genericId, genericTime, false);
		Car newCar4 = new Car(genericId, genericTime, false);
		testInstance.enterQueue(newCar);
		testInstance.enterQueue(newCar2);
		testInstance.enterQueue(newCar3);
		testInstance.enterQueue(newCar4);
		testInstance.archiveQueueFailures(genericTime);
		assertTrue(newCar.wasQueued());
		assertFalse(newCar.wasParked());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#carParkEmpty()}.
	 * @author Lewis
	 */
	@Test
	public void testCarParkEmpty() {
		CarPark testInstance = new CarPark();
		assertTrue(testInstance.carParkEmpty());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#carParkFull()}.
	 * @author Kyle
	 * @throws VehicleException 
	 * @throws SimulationException 
	 */
	@Test
	public void testCarParkFull() throws VehicleException, SimulationException {
		CarPark testInstance = new CarPark( 3,1, 1, 1);
		Car newCar = new Car(genericId, genericTime, false);
		Car newCar2 = new Car(genericId, genericTime, false);
		Car newSmallCar = new Car(genericId, genericTime, true);
		Car newSmallCar2 = new Car(genericId, genericTime, true);
		MotorCycle newBike = new MotorCycle(genericId, genericTime);
		MotorCycle newBike2 = new MotorCycle(genericId, genericTime);
		testInstance.parkVehicle(newCar, genericTime, 60);
		testInstance.parkVehicle(newSmallCar, genericTime, 60);
		testInstance.parkVehicle(newBike, genericTime, 60);
		testInstance.parkVehicle(newCar2, genericTime, 60);
		testInstance.enterQueue(newBike2);
		testInstance.parkVehicle(newSmallCar2, genericTime, 60);
		assertTrue(testInstance.carParkFull());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#enterQueue(asgn2Vehicles.Vehicle)}.
	 * @author Lewis
	 * @throws VehicleException 
	 * @throws SimulationException 
	 */
	@Test
	public void testEnterQueue() throws VehicleException, SimulationException {
		CarPark testInstance = new CarPark( 1,1, 1, 1);
		Car newCar = new Car(genericId, genericTime, false);
		testInstance.enterQueue(newCar);
		assertTrue(newCar.isQueued());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#exitQueue(asgn2Vehicles.Vehicle, int)}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 * @author Kyle Annett
	 */
	@Test
	public void testExitQueue() throws VehicleException, SimulationException {
		CarPark testInstance = new CarPark( 1,1, 1, 1);
		Car newCar = new Car(genericId, genericTime, false);
		testInstance.enterQueue(newCar);
		testInstance.exitQueue(newCar, 120);
		assertTrue(newCar.wasQueued());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#finalState()}.
	 * @author kyle
	 */
	@Test
	public void testFinalState() {
		CarPark testInstance = new CarPark( 1,1, 1, 5);
		String finalStr = testInstance.finalState();
		assertEquals(finalStr, "Vehicles Processed: count:1, logged: 0"
				+ "\nVehicle Record: "
				+ "\n"
				+ "\n");
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getNumCars()}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 * @author kyle
	 */
	@Test
	public void testGetNumCars() throws SimulationException, VehicleException {
		CarPark testInstance = new CarPark( 10,5, 5, 5);
		Car newCar = new Car(genericId, genericTime, false);
		Car newCar2 = new Car(genericId, genericTime, false);
		Car newCar3 = new Car(genericId, genericTime, false);
		testInstance.parkVehicle(newCar, genericTime, 160);
		testInstance.parkVehicle(newCar2, 101, 160);
		testInstance.parkVehicle(newCar3, 102, 160);
		int count = testInstance.getNumCars();
		assertEquals(count, 3);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getNumMotorCycles()}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 * @author kyle
	 */
	@Test
	public void testGetNumMotorCycles() throws SimulationException, VehicleException {
		CarPark testInstance = new CarPark( 5,5, 5, 5);
		MotorCycle newBike = new MotorCycle(genericId, genericTime);
		MotorCycle newBike2 = new MotorCycle(genericId, genericTime);
		MotorCycle newBike3 = new MotorCycle(genericId, genericTime);
		testInstance.parkVehicle(newBike, genericTime, 160);
		testInstance.parkVehicle(newBike2, 101, 160);
		testInstance.parkVehicle(newBike3, 102, 160);
		int count = testInstance.getNumMotorCycles();
		assertEquals(count, 3);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getNumSmallCars()}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 * @author kyle
	 */
	@Test
	public void testGetNumSmallCars() throws SimulationException, VehicleException {
		CarPark testInstance = new CarPark( 5,5, 5, 5);
		Car newCar = new Car(genericId, genericTime, true);
		Car newCar2 = new Car(genericId, genericTime, true);
		Car newCar3 = new Car(genericId, genericTime, true);
		testInstance.parkVehicle(newCar, genericTime, 160);
		testInstance.parkVehicle(newCar2, 101, 160);
		testInstance.parkVehicle(newCar3, 102, 160);
		int count = testInstance.getNumSmallCars();
		assertEquals(count, 3);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getStatus(int)}.
	 * @author kyle
	 */
	@Test
	public void testGetStatus() {
		CarPark testInstance = new CarPark( 1,1, 1, 5);
		String statStr = testInstance.getStatus(genericTime);
		assertEquals(statStr, "100::1::P:0::C:0::S:0::M:0::D:2::A:0::Q:0"
				+ "\n");
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#initialState()}.
	 * @author kyle
	 */
	@Test
	public void testInitialState() {
		CarPark testInstance = new CarPark( 1,1, 1, 5);
		String testIn = testInstance.initialState();
		assertEquals(testIn, "CarPark [maxCarSpaces: 1 maxSmallCarSpaces: 1 maxMotorCycleSpaces: 1 maxQueueSize: 5]");
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#numVehiclesInQueue()}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 * @author Kyle Annett
	 */
	@Test
	public void testNumVehiclesInQueue() throws SimulationException, VehicleException {
		CarPark testInstance = new CarPark( 1,1, 1, 5);
		Car newCar = new Car(genericId, genericTime, false);
		Car newCar2 = new Car(genericId, genericTime, false);
		Car newCar3 = new Car(genericId, genericTime, false);
		testInstance.enterQueue(newCar);
		testInstance.enterQueue(newCar2);
		testInstance.enterQueue(newCar3);
		assertEquals(testInstance.numVehiclesInQueue(), 3);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#parkVehicle(asgn2Vehicles.Vehicle, int, int)}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 * @author Kyle Annett
	 */
	@Test
	public void testParkVehicle() throws VehicleException, SimulationException {
		CarPark testInstance = new CarPark( 1,1, 1, 1);
		Car newCar = new Car(genericId, genericTime, false);
		testInstance.parkVehicle(newCar, genericTime, 60);
		assertTrue(newCar.isParked());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#processQueue(int, asgn2Simulators.Simulator)}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 * @author kyle
	 */
	@Test
	public void testProcessQueue() throws VehicleException, SimulationException {
		CarPark cp = new CarPark(100, 30, 30, 10);
		Simulator s = null;
		Log l = null; 
		try {
			s = new Simulator(100,300.0, 100.0,
					0.75, 0.2, 0.1);
			l = new Log();
		} catch (IOException | SimulationException e1) {
			e1.printStackTrace();
			System.exit(-1);
		}
		Car newCar = new Car(genericId, genericTime, false);
		Car newCar2 = new Car(genericId, 110, false);
		cp.enterQueue(newCar);
		cp.enterQueue(newCar2);
		cp.processQueue(126, s);
		assertTrue(newCar.wasQueued());
		assertFalse(newCar.isParked());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#queueEmpty()}.
	 * @author Kyle Annett
	 */
	@Test
	public void testQueueEmpty() {
		CarPark testInstance = new CarPark();
		assertTrue(testInstance.queueEmpty());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#queueFull()}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 * @author Kyle Annett
	 */
	@Test
	public void testQueueFull() throws VehicleException, SimulationException {
		CarPark testInstance = new CarPark( 1,1, 1, 1);
		Car newCar = new Car(genericId, genericTime, false);
		testInstance.enterQueue(newCar);
		assertTrue(testInstance.queueFull());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#spacesAvailable(asgn2Vehicles.Vehicle)}.
	 * @throws VehicleException 
	 * @author Kyle Annett
	 */
	@Test
	public void testSpacesAvailable() throws VehicleException {
		CarPark testInstance = new CarPark(2, 1, 1, 10);
		Car newCar = new Car(genericId, genericTime, false);
		Car newSmallCar = new Car(genericId, genericTime, true);
		MotorCycle newBike = new MotorCycle(genericId, genericTime);
		boolean numSpace = testInstance.spacesAvailable(newCar);
		boolean numSpaceSmall = testInstance.spacesAvailable(newSmallCar);
		boolean numSpaceBike = testInstance.spacesAvailable(newBike);
		assertTrue(numSpace);
		assertTrue(numSpaceSmall);
		assertTrue(numSpaceBike);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#toString()}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 * @author kyle
	 */
	@Test
	public void testToString() throws SimulationException, VehicleException {
		CarPark testInstance = new CarPark( 2,1, 1, 1);
		Car newCar = new Car(genericId, genericTime, false);
		testInstance.parkVehicle(newCar, genericTime, 60);
		String parkStr = testInstance.toString();
		assertEquals(parkStr,"CarPark [count: 0 numCars: 1 numSmallCars: 0 numMotorCycles: 0 queue: 0 numDissatisfied: 1 past: 0]");
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#tryProcessNewVehicles(int, asgn2Simulators.Simulator)}.
	 * @throws SimulationException 
	 * @throws VehicleException 
	 * @author kyle
	 */
	@Test
	public void testTryProcessNewVehicles() throws VehicleException, SimulationException {
		boolean checkCreate = false;
		CarPark cp = new CarPark(100, 30, 30, 10);
		Simulator s = null;
		Log l = null; 
		try {
			s = new Simulator(100,300.0, 100.0,
					0.75, 0.2, 0.1);
			l = new Log();
		} catch (IOException | SimulationException e1) {
			e1.printStackTrace();
			System.exit(-1);
		}
		cp.tryProcessNewVehicles(genericTime, s);
		if(cp.getNumCars() == 1||cp.getNumMotorCycles() == 1){
			checkCreate = true;
		}
		assertTrue(checkCreate);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#unparkVehicle(asgn2Vehicles.Vehicle, int)}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 * @author Kyle Annett
	 */
	@Test
	public void testUnparkVehicle() throws SimulationException, VehicleException {
		CarPark testInstance = new CarPark( 1,1, 1, 1);
		Car newCar = new Car(genericId, genericTime, false);
		testInstance.parkVehicle(newCar, genericTime, 60);
		testInstance.unparkVehicle(newCar, 160);
		assertTrue(newCar.wasParked());
	}

}
