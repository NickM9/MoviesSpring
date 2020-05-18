package com.trainigcenter.springtask.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.dto.ActorDto;
import com.trainigcenter.springtask.service.ActorService;

@RestController
@RequestMapping("/actors")
public class ActorRestController {
	
	private ActorService actorService;
	private ModelMapper modelMapper;
	
	@Autowired
	public ActorRestController(ActorService actorService, ModelMapper modelMapper) {
		this.actorService = actorService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping
    public Set<ActorDto> getAll() {
		Set<Actor> allActors = actorService.getAll();
		Set<ActorDto> allActorsDto = allActors.stream().map(this::convertoDto).collect(Collectors.toSet());
        return allActorsDto;
    }
	
	@GetMapping("/{actorId}")
	public ActorDto getActorById(@PathVariable int actorId) {
		Actor actor = actorService.getActorById(actorId);
		ActorDto actorDto = convertoDto(actor);
		return actorDto;
	}
	
	private ActorDto convertoDto(Actor actor) {
		return modelMapper.map(actor, ActorDto.class);
	}

}
