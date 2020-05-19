package com.trainigcenter.springtask.controller;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.dto.ActorDto;
import com.trainigcenter.springtask.dto.Error;
import com.trainigcenter.springtask.exception.ForbiddenException;
import com.trainigcenter.springtask.exception.NotFoundException;
import com.trainigcenter.springtask.service.ActorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/actors")
public class ActorRestController {

    private static final Logger logger = LogManager.getLogger(ActorRestController.class);

    private final ActorService actorService;
    private final ModelMapper modelMapper;

    @Autowired
    public ActorRestController(ActorService actorService, ModelMapper modelMapper) {
        this.actorService = actorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public Set<ActorDto> getAll() {
        Set<Actor> allActors = actorService.getAll();
        Set<ActorDto> allActorsDto = allActors.stream()
                                              .map(this::convertToDto)
                                              .collect(Collectors.toSet());
        return allActorsDto;
    }

    @GetMapping("/{actorId}")
    public ActorDto getActorById(@PathVariable int actorId) {
        Actor actor = actorService.getActorById(actorId);
        notNullActor(actor);

        ActorDto actorDto = convertToDto(actor);
        return actorDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveActor(@Valid @RequestBody ActorDto actorDto) {
        actorDto.setId(null);

        Actor actor = convertFromDto(actorDto);
        actorService.addActor(actor);
    }

    @PutMapping("/{actorId}")
    public ActorDto updateGenre(@PathVariable("actorId") Integer actorId, @Valid @RequestBody ActorDto actorDto) {

        Actor actor = convertFromDto(actorDto);
        actor.setId(actorId);

        actor = actorService.updateActor(actor);
        notNullActor(actor);

        return convertToDto(actor);
    }

    @DeleteMapping("/{actorId}")
    public void deleteGenre(@PathVariable("actorId") Integer actorId) {

        Actor actor = actorService.getActorByIdWithMovies(actorId);
        notNullActor(actor);

        boolean isDeleted = actorService.deleteActor(actor);

        if (!isDeleted) {
            throw new ForbiddenException(actor.getName());
        }

    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private Error actorNotFound(NotFoundException e) {
        Error error = new Error(HttpStatus.NOT_FOUND.value(), e.getName());
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private Error handleBadInput(MethodArgumentNotValidException e) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), "Bad request. You should init actor name and year");
        return error;
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    private Error handleCanNotDeleted(ForbiddenException e) {
        Error error = new Error(HttpStatus.FORBIDDEN.value(), e.getName() + " take part in movie");
        return error;
    }

    private void notNullActor(Actor actor) {
        if (actor == null) {
            throw new NotFoundException("Actor is not exist");
        }
    }

    private ActorDto convertToDto(Actor actor) {
        return modelMapper.map(actor, ActorDto.class);
    }

    private Actor convertFromDto(ActorDto actorDto) {
        return modelMapper.map(actorDto, Actor.class);
    }

}
