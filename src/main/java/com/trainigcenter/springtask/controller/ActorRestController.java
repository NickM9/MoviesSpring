package com.trainigcenter.springtask.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.dto.ActorDto;
import com.trainigcenter.springtask.dto.Error;
import com.trainigcenter.springtask.dto.GenreDto;
import com.trainigcenter.springtask.exception.NotFoundException;
import com.trainigcenter.springtask.service.ActorService;

@RestController
@RequestMapping("/actors")
public class ActorRestController {
	
	private static  final Logger logger = LogManager.getLogger(ActorRestController.class);
	
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
		Set<ActorDto> allActorsDto = allActors.stream().map(this::convertToDto).collect(Collectors.toSet());
        return allActorsDto;
    }
	
//	@GetMapping("/{actorId}")
//	public ActorDto getActorById(@PathVariable int actorId) {
//		Actor actor = actorService.getActorById(actorId);
//		ActorDto actorDto = convertoDto(actor);
//		return actorDto;
//	}
	
	@GetMapping("/{actorName}")
	public ActorDto getActorByName(@PathVariable String actorName) {
		actorName = mapActorName(actorName);

		Actor actor = actorService.getActorByName(actorName);
		
		if (actor == null) {
			throw new NotFoundException(actorName);
		}
		
		return convertToDto(actor);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void saveActor(@RequestBody ActorDto actorDto) {
		String actorName = actorDto.getName();
		
		actorName = mapActorName(actorName);
		actorDto.setName(actorName);
		
		Actor actor = convertFromDto(actorDto);
		actorService.addActor(actor);
	}
	
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	private Error actorNotFound(NotFoundException e){
		Error error = new Error(HttpStatus.NOT_FOUND.value(), e.getName() + " not found");
		return error;
	}
	
	private ActorDto convertToDto(Actor actor) {
		return modelMapper.map(actor, ActorDto.class);
	}
	
	private Actor convertFromDto(ActorDto actorDto) {
		return modelMapper.map(actorDto, Actor.class);
	}

	private String mapActorName(String actorName) {
		actorName = actorName.replaceAll("-", " ");
		return actorName;
	}
}
