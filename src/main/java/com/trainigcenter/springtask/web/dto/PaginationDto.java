package com.trainigcenter.springtask.web.dto;

import java.util.List;

public class PaginationDto<T> {

    private int maxPage;
    private List<T> objects;

}
