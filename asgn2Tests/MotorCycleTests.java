/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Tests 
 * 22/04/2014
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import java.lang.reflect.Array;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.VehicleException;
import asgn2Vehicles.MotorCycle;
import asgn2Vehicles.Vehicle;

/**
 * @author hogan
 *
 */
public class MotorCycleTests {
	private String genericId = "1C";
	private int parkingTime = 20;
	private int genericArrivalTime = 100;
	private int genericDepartQueue = 120;
	private int hour = 60;
	private int departTime = 160;

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
	 * Test method for {@link asgn2Vehicles.MotorCycle#MotorCycle(java.lang.String, int)}.
	 * @throws VehicleException 
	 */
	@Test
	public void testMotorCycle() throws VehicleException {
		MotorCycle newBike = new MotorCycle(genericId, genericArrivalTime);
		String bikeId = newBike.getVehID();
		int bikeTime = newBike.getArrivalTime();
		assertEquals(bikeId,genericId);
		assertEquals(bikeTime,genericArrivalTime);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#Vehicle(java.lang.String, int)}.
	 * @throws
	 */
	@Test
	public void testVehicle() throws VehicleException {
		Vehicle testVeh = new MotorCycle(genericId, genericArrivalTime);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getVehID()}.
	 * @throws VehicleException 
	 * @author kyleannett
	 */
	@Test
	public void testGetVehID() throws VehicleException {
		MotorCycle newBike = new MotorCycle(genericId, genericArrivalTime);
		String bikeId = newBike.getVehID();
		assertEquals(bikeId,genericId);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getArrivalTime()}.
	 * @throws VehicleException 
	 * @author kyleannett
	 */
	@Test
	public void testGetArrivalTime() throws VehicleException {
		MotorCycle newBike = new MotorCycle(genericId, genericArrivalTime);
		int bikeTime = newBike.getArrivalTime();
		assertEquals(bikeTime,genericArrivalTime);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#enterQueuedState()}.
	 * @throws VehicleException 
	 * @author kyleannett
	 */
	@Test
	public void testEnterQueuedState() throws VehicleException {
		MotorCycle newBike = new MotorCycle(genericId, genericArrivalTime);
		newBike.enterQueuedState();
		boolean checkState = newBike.isQueued();
		assertEquals(checkState, true);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#exitQueuedState(int)}.
	 * @throws VehicleException 
	 * @author kyleannett
	 */
	@Test
	public void testExitQueuedState() throws VehicleException {
		MotorCycle newBike = new MotorCycle(genericId, genericArrivalTime);
		newBike.enterQueuedState();
		newBike.exitQueuedState(genericDepartQueue); 
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#enterParkedState(int, int)}.
	 * @throws VehicleException 
	 * @author kyleannett
	 */
	@Test
	public void testEnterParkedState() throws VehicleException {
		MotorCycle newBike = new MotorCycle(genericId, genericArrivalTime);
		newBike.enterParkedState(genericArrivalTime, hour);
		
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#exitParkedState(int)}.
	 * @throws VehicleException 
	 * @author kyleannett
	 */
	@Test
	public void testExitParkedStateInt() throws VehicleException {
		MotorCycle newBike = new MotorCycle(genericId, genericArrivalTime);
		newBike.enterParkedState(genericArrivalTime, hour);
		newBike.exitParkedState(departTime);
		int duration = genericArrivalTime + hour;
		assertEquals(departTime, duration);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#exitParkedState()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testExitParkedState() throws VehicleException {
		//unsure of what this tests	
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#isParked()}.
	 * @throws VehicleException 
	 * @author kyleannett
	 */
	@Test
	public void testIsParked() throws VehicleException {
		MotorCycle newBike = new MotorCycle(genericId, genericArrivalTime);
		newBike.enterParkedState(genericArrivalTime, hour);
		boolean checkParked = newBike.isParked();
		assertEquals(checkParked, true);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#isQueued()}.
	 * @throws VehicleException 
	 * @author kyleannett
	 */
	@Test
	public void testIsQueued() throws VehicleException {
		MotorCycle newBike = new MotorCycle(genericId, genericArrivalTime);
		newBike.enterQueuedState();
		boolean checkState = newBike.isQueued();
		assertEquals(checkState, true);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getParkingTime()}.
	 * @throws VehicleException 
	 * @author kyleannett
	 */
	@Test
	public void testGetParkingTime() throws VehicleException {
		MotorCycle newBike = new MotorCycle(genericId, genericArrivalTime);
		newBike.enterParkedState(genericArrivalTime, hour);
		int parkTime = newBike.getParkingTime();
		assertEquals(parkTime, genericArrivalTime);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getDepartureTime()}.
	 * @throws VehicleException 
	 * @author kyleannett
	 */
	@Test
	public void testGetDepartureTime() throws VehicleException {
		MotorCycle newBike = new MotorCycle(genericId, genericArrivalTime);
		newBike.enterParkedState(genericArrivalTime, hour);
		newBike.exitParkedState(departTime);
		int intededDepart = genericArrivalTime + hour;
		int getDepartTime = newBike.getDepartureTime();
		assertEquals(intededDepart, getDepartTime);		
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#wasQueued()}.
	 * @throws VehicleException 
	 * @author kyleannett
	 */
	@Test
	public void testWasQueued() throws VehicleException {
		MotorCycle newBike = new MotorCycle(genericId, genericArrivalTime);
		newBike.enterQueuedState();
		newBike.exitQueuedState(genericDepartQueue);
		boolean checkWasQueued = newBike.wasQueued();
		assertEquals(checkWasQueued, true);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#wasParked()}.
	 * @throws VehicleException 
	 * @author kyleannett
	 */
	@Test
	public void testWasParked() throws VehicleException {
		MotorCycle newBike = new MotorCycle(genericId, genericArrivalTime);
		newBike.enterParkedState(genericArrivalTime, hour);
		newBike.exitParkedState(departTime);
		boolean checkWasParked = newBike.wasParked();
		assertEquals(checkWasParked, true);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#isSatisfied()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testIsSatisfied() throws VehicleException {
		MotorCycle newBike = new MotorCycle(genericId, genericArrivalTime);
		newBike.enterParkedState(parkingTime, hour);

		boolean checkSatisfied = newBike.isSatisfied();
		assertTrue(checkSatisfied);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#toString()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testToString() throws VehicleException {
		MotorCycle newBike = new MotorCycle(genericId, genericArrivalTime);
		nkewBike.toString();
		//comeback
	}

	
	
	/* MotorCycleTEsts */
	/*
	 * Confirm that the API spec has not been violated through the
	 * addition of public fields, constructors or methods that were
	 * not requested
	 */
	@Test
	public void NoExtraPublicMethods() {
		//MotorCycle Class implements Vehicle
		final int NumVehicleClassMethods = Array.getLength(Vehicle.class.getMethods());
		final int NumMotorCycleClassMethods = Array.getLength(MotorCycle.class.getMethods());
		assertTrue("veh:"+NumVehicleClassMethods+":MotorCycle:"+NumMotorCycleClassMethods,(NumVehicleClassMethods)==NumMotorCycleClassMethods);
	}
	
	@Test 
	public void NoExtraPublicFields() {
		//Same as Vehicle 
		final int NumVehicleClassFields = Array.getLength(Vehicle.class.getFields());
		final int NumMotorCycleClassFields = Array.getLength(MotorCycle.class.getFields());
		assertTrue("veh:"+NumVehicleClassFields+":MotorCycle:"+NumMotorCycleClassFields,(NumVehicleClassFields)==NumMotorCycleClassFields);
	}
	
	@Test 
	public void NoExtraPublicConstructors() {
		//Same as Vehicle
		final int NumVehicleClassConstructors = Array.getLength(Vehicle.class.getConstructors());
		final int NumMotorCycleClassConstructors = Array.getLength(MotorCycle.class.getConstructors());
		assertTrue(":veh:"+NumVehicleClassConstructors+":mc:"+NumMotorCycleClassConstructors,(NumVehicleClassConstructors)==NumMotorCycleClassConstructors);
	}
}
