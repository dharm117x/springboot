package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.data.UserData;
import com.example.service.WebClinetService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/flux")
public class HomeController {

	@Autowired
	WebClinetService service;
	
	@GetMapping("/ping/{name}")
	public Mono<String> getName(@PathVariable String name) {

		return Mono.just("Name:::->> " + new UserData(1L, name));
	}

	@GetMapping("/user")
	public Mono<UserData> getUser() {
		Mono<UserData> mono = service.getWebClient().get().uri("/user").exchangeToMono(f->f.bodyToMono(UserData.class));
		return mono;
	}

	
	@GetMapping("/users")
	public Flux<List<UserData>> getUsersall() {
		Flux<List<UserData>> flux = service.getWebClient().get().uri("/users").exchangeToFlux(f->f.bodyToFlux(List.class));
		return flux;
	}


}
