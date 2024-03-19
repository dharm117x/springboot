package com.example.service;

public class SomeBussinessImpl {

	DataService dataService;

	public SomeBussinessImpl(DataService dataService) {
		this.dataService = dataService;
	}


	public int findGretestValue() {
		int[] data = dataService.retriveAllData();
		int gv = 0;
		for (int value : data) {
			if(value> gv) {
				gv = value;
			}
		}
		
		return gv;
	}
	
}

