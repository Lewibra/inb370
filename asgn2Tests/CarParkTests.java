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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
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
	 */
	@Test
	public void testArchiveDepartingVehicles() throws VehicleException, SimulationException {
		CarPark testInstance = new CarPark();
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#archiveNewVehicle(asgn2Vehicles.Vehicle)}.
	 */
	@Test
	public void testArchiveNewVehicle() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#archiveQueueFailures(int)}.
	 */
	@Test
	public void testArchiveQueueFailures() {
		fail("Not yet implemented"); // TODO
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
		CarPark testInstance = new CarPark( 1,1, 1, 1);
		Car newCar = new Car(genericId, genericTime, false);
		Car newSmallCar = new Car(genericId, genericTime, true);
		MotorCycle newBike = new MotorCycle(genericId, genericTime);
		testInstance.parkVehicle(newCar, genericTime, 60);
		testInstance.parkVehicle(newSmallCar, genericTime, 60);
		testInstance.parkVehicle(newBike, genericTime, 60);
		boolean checkFull = testInstance.carParkFull();
		assertTrue(checkFull);
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
	 */
	@Test
	public void testExitQueue() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#finalState()}.
	 */
	@Test
	public void testFinalState() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getNumCars()}.
	 */
	@Test
	public void testGetNumCars() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getNumMotorCycles()}.
	 */
	@Test
	public void testGetNumMotorCycles() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getNumSmallCars()}.
	 */
	@Test
	public void testGetNumSmallCars() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getStatus(int)}.
	 */
	@Test
	public void testGetStatus() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#initialState()}.
	 */
	@Test
	public void testInitialState() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#numVehiclesInQueue()}.
	 */
	@Test
	public void testNumVehiclesInQueue() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#parkVehicle(asgn2Vehicles.Vehicle, int, int)}.
	 */
	@Test
	public void testParkVehicle() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#processQueue(int, asgn2Simulators.Simulator)}.
	 */
	@Test
	public void testProcessQueue() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#queueEmpty()}.
	 */
	@Test
	public void testQueueEmpty() {
		CarPark testInstance = new CarPark();
		assertTrue(testInstance.queueEmpty());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#queueFull()}.
	 */
	@Test
	public void testQueueFull() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#spacesAvailable(asgn2Vehicles.Vehicle)}.
	 * @throws VehicleException 
	 */
	@Test
	public void testSpacesAvailable() throws VehicleException {
		CarPark testInstance = new CarPark( 1,1, 1, 1);
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
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#tryProcessNewVehicles(int, asgn2Simulators.Simulator)}.
	 */
	@Test
	public void testTryProcessNewVehicles() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#unparkVehicle(asgn2Vehicles.Vehicle, int)}.
	 */
	@Test
	public void testUnparkVehicle() {
		fail("Not yet implemented"); // TODO
	}

}
