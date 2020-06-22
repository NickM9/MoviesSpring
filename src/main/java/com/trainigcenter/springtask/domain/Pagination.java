package com.trainigcenter.springtask.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination<T> {

    private int page;
    private int maxPage;
    private int size;
    private List<T> objects;


}
