package com.trainigcenter.springtask.domain;

import lombok.Data;

import java.util.List;

@Data
public class Pagination<T> {

    private int localPage;
    private int maxPage;
    private int size;
    private List<T> objects;


}
