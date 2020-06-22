package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.domain.exception.NotFoundException;
import com.trainigcenter.springtask.service.ActorService;
import com.trainigcenter.springtask.web.dto.ActorDto;
import com.trainigcenter.springtask.web.dto.mapper.ActorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping("/actors")
public class ActorController {

    private final ActorService actorService;
    private final ActorMapper mapper;

    @GetMapping
    public List<ActorDto> getAll() {
        return actorService.getAll()
                           .stream()
                           .map(actor -> mapper.toDto(actor))
                           .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ActorDto getById(@PathVariable Integer id) {
        return mapper.toDto(actorService.getById(id).orElseThrow(() -> new NotFoundException("Actor id:" + id + " not found")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActorDto save(@Valid @RequestBody ActorDto actorDto) {
        actorDto.setId(null);
        Actor actor = actorService.save(mapper.fromDto(actorDto));
        return mapper.toDto(actor);
    }

    @PutMapping("/{id}")
    public ActorDto update(@PathVariable("id") int id,
                           @Valid @RequestBody ActorDto actorDto) {
        actorDto.setId(id);
        Actor actor = actorService.save(mapper.fromDto(actorDto));
        return mapper.toDto(actor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        actorService.delete(id);
    }
}
