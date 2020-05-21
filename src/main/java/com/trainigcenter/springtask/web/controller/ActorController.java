package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.service.ActorService;
import com.trainigcenter.springtask.web.dto.ActorDto;
import com.trainigcenter.springtask.web.dto.Error;
import com.trainigcenter.springtask.web.exception.ForbiddenException;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import com.trainigcenter.springtask.web.exception.WebException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/actors")
public class ActorController {

    private static final Logger logger = LogManager.getLogger(ActorController.class);

    private final ActorService actorService;
    private final ModelMapper modelMapper;

    @Autowired
    public ActorController(ActorService actorService, ModelMapper modelMapper) {
        this.actorService = actorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<ActorDto> getAll() {
        List<Actor> allActors = actorService.getAll();
        return allActors.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ActorDto getActorById(@PathVariable Integer id) {
        Actor actor = Optional.ofNullable(actorService.getById(id))
                              .orElseThrow(() -> new NotFoundException("Actor id:" + id + " not found"));

        return convertToDto(actor);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActorDto saveActor(@Valid @RequestBody ActorDto actorDto) {
        actorDto.setId(null);

        Actor actor = actorService.add(convertFromDto(actorDto));
        return convertToDto(actor);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ActorDto updateActor(@PathVariable("id") Integer id,
                                @Valid @RequestBody ActorDto actorDto) {

        Actor actor = convertFromDto(actorDto);
        actor.setId(id);

        actor = Optional.ofNullable(actorService.update(actor))
                        .orElseThrow(() -> new NotFoundException("Actor id:" + id + " not found"));

        return convertToDto(actor);
    }

    @DeleteMapping("/{id}")
    public void deleteActor(@PathVariable("id") Integer id) {

        Actor actor = Optional.ofNullable(actorService.getByIdWithMovies(id))
                              .orElseThrow(() -> new NotFoundException("Actor id:" + id + " not found"));

        boolean isDeleted = actorService.delete(actor);

        if (!isDeleted) {
            throw new ForbiddenException(actor.getName() + " is take part in movies");
        }

    }

    @ExceptionHandler({NotFoundException.class, ForbiddenException.class})
    private ResponseEntity<Error> exceptionHandler(WebException e) {
        Error error = new Error(e.getStatus().value(), e.getMessage());
        return new ResponseEntity<>(error, e.getStatus());
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private Error notValidArgumentHandler(Exception e) {
        return new Error(HttpStatus.BAD_REQUEST.value(), "You need to initialize all fields");
    }

    private ActorDto convertToDto(Actor actor) {
        return modelMapper.map(actor, ActorDto.class);
    }

    private Actor convertFromDto(ActorDto actorDto) {
        return modelMapper.map(actorDto, Actor.class);
    }

}
