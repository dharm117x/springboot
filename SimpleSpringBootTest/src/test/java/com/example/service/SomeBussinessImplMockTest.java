package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SomeBussinessImplMockTest {

	@Mock
	DataService dataService;
	
	@InjectMocks
	SomeBussinessImpl someBussiness;
	
	@Test
	private void findgeretestData_test() {
		DataService mock = mock(DataService.class);
		when(mock.retriveAllData()).thenReturn(new int[] {1,2,99, 55});
		SomeBussinessImpl impl = new SomeBussinessImpl(mock);
		int result = impl.findGretestValue();
		assertEquals(99, result);
	}

	@Test
	private void findgeretestData_test1() {
		when(dataService.retriveAllData()).thenReturn(new int[] {55});
		int result = someBussiness.findGretestValue();
		assertEquals(55, result);
	}

}
