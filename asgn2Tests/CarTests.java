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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.VehicleException;
import asgn2Vehicles.Car;

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
	 * @throws VehicleException 
	 */
	@Test
	public void testToString() throws VehicleException {
		Car newCar = new Car(genericId, genericTime, true);
		String parkStr = newCar.toString();
		assertEquals(parkStr, "Vehicle vehID: 1C"
				+ "\nArrival Time: 100"
				+ "\nVehicle was not queued"
				+ "\nVehicle was not parked"
				+ "\nCustomer was not satisfied"
				+ "\nCar can use small car parking space");
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
	/**
	 * Test method for checking exception gets thrown in constructor
	 * @throws VehicleException
	 * @author kyle annett
	 */
	@Test (expected = VehicleException.class)
	public void testCatchZeroConstructor() throws VehicleException{
		Car newCar = new Car("1C", 0, true);
	}
	
	/**
	 * Test method for checking exception gets thrown in constructor
	 * @throws VehicleException
	 * @author kyle annett
	 */
	@Test (expected = VehicleException.class)
	public void testCatchLessZeroConstructor() throws VehicleException{
		Car newCar = new Car("1C", -20, true);
	}

}
