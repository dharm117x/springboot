package com.example.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public IndexController() {
		System.out.println("IndexController.IndexController()");
	}

	@GetMapping("/")
	public String showIndex() throws SQLException {
		List<Map<String,Object>> list = jdbcTemplate.queryForList("select * from employee");
		list.forEach(map->{
			map.forEach((key, value)->{
				System.out.println(key +"---"+ value);
			});
		});
		
		System.out.println("IndexController.showIndex()");
		return "index";
	}

}
