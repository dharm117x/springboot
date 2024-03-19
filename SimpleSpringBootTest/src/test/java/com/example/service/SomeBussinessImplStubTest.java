package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SomeBussinessImplStubTest {

	@Test
	private void findgeretestData_test() {
		DataserviceStub stub = new DataserviceStub();
		SomeBussinessImpl impl = new SomeBussinessImpl(stub);
		int result = impl.findGretestValue();
		assertEquals(99, result);
	}
}


class DataserviceStub implements DataService{

	public int[] retriveAllData() {
		return new int[] {1,2,3,99};
	}
}