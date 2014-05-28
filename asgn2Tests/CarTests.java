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

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Vehicles.Car;
import asgn2Vehicles.Vehicle;

/**
 * @author hogan
 *
 */
public class CarTests {
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
	 * Test method for {@link asgn2Vehicles.Car#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2Vehicles.Car#Car(java.lang.String, int, boolean)}.
	 * @throws VehicleException
	 * @author kyleannett 
	 */
	@Test
	public void testCar() throws VehicleException {
		Car newCar = new Car(genericId, genericTime, false);
		String checkId = newCar.getVehID();
		int checkTime = newCar.getArrivalTime();
		assertEquals(checkId, genericId);
		assertEquals(checkTime, genericTime);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Car#isSmall()}.
	 * @throws VehicleException 
	 * @author kyleannett
	 */
	@Test
	public void testIsSmall() throws VehicleException {
		Car newCar = new Car("1C", 100, true);
		boolean checkSmall = newCar.isSmall();
		assertTrue(checkSmall);
	}

	
	/*
	 * Confirm that the API spec has not been violated through the
	 * addition of public fields, constructors or methods that were
	 * not requested
	 */
	@Test
	public void NoExtraPublicMethods() {
		//Car Class implements Vehicle, adds isSmall() 
		final int NumVehicleClassMethods = Array.getLength(Vehicle.class.getMethods());
		final int NumCarClassMethods = Array.getLength(Car.class.getMethods());
		assertTrue("veh:"+NumVehicleClassMethods+":car:"+NumCarClassMethods,(NumVehicleClassMethods+1)==NumCarClassMethods);
	}
	
	@Test 
	public void NoExtraPublicFields() {
		//Same as Vehicle 
		final int NumVehicleClassFields = Array.getLength(Vehicle.class.getFields());
		final int NumCarClassFields = Array.getLength(Car.class.getFields());
		assertTrue("veh:"+NumVehicleClassFields+":car:"+NumCarClassFields,(NumVehicleClassFields)==NumCarClassFields);
	}
	
	@Test 
	public void NoExtraPublicConstructors() {
		//Same as Vehicle
		final int NumVehicleClassConstructors = Array.getLength(Vehicle.class.getConstructors());
		final int NumCarClassConstructors = Array.getLength(Car.class.getConstructors());
		assertTrue(":veh:"+NumVehicleClassConstructors+":car:"+NumCarClassConstructors,(NumVehicleClassConstructors)==NumCarClassConstructors);
	}
	

	
	

}
