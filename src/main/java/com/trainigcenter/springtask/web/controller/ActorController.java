package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.service.ActorService;
import com.trainigcenter.springtask.web.dto.ActorDto;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/actors")
public class ActorController {

    private static final Logger logger = LogManager.getLogger(ActorController.class);

    private final ActorService actorService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<ActorDto> getAll() {
        List<Actor> allActors = actorService.getAll();
        return allActors.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ActorDto getActorById(@PathVariable Integer id) {
        Actor actor = actorService.getById(id).orElseThrow(() -> new NotFoundException("Actor id:" + id + " not found"));

        return convertToDto(actor);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActorDto saveActor(@Valid @RequestBody ActorDto actorDto) {
        Actor actor = actorService.create(convertFromDto(actorDto));
        return convertToDto(actor);
    }

    @PutMapping("/{id}")
    public ActorDto updateActor(@PathVariable("id") int id,
                                @Valid @RequestBody ActorDto actorDto) {

        Actor actor = convertFromDto(actorDto);
        return convertToDto(actorService.update(actor, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteActor(@PathVariable("id") Integer id) {
        Actor actor = actorService.getByIdWithMovies(id).orElseThrow(() -> new NotFoundException("Actor id:" + id + " not found"));
        actorService.delete(actor.getId());
    }

    private ActorDto convertToDto(Actor actor) {
        return modelMapper.map(actor, ActorDto.class);
    }

    private Actor convertFromDto(ActorDto actorDto) {
        return modelMapper.map(actorDto, Actor.class);
    }

}
