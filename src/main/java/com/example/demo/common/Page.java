package com.example.demo.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class Page<T> implements org.springframework.data.domain.Page<T> {

    private final org.springframework.data.domain.Page<T> source;

    @JsonView(View.Public.class)
    @Override
    public int getTotalPages() {
        return source.getTotalPages();
    }

    @JsonView(View.Public.class)
    @Override
    public long getTotalElements() {
        return source.getTotalElements();
    }

    @JsonView(View.Public.class)
    @JsonProperty("page")
    @Override
    public int getNumber() {
        return source.getNumber();
    }

    @JsonView(View.Public.class)
    @Override
    public int getSize() {
        return source.getSize();
    }

    @JsonView(View.Public.class)
    @Override
    public int getNumberOfElements() {
        return source.getNumberOfElements();
    }

    @JsonView(View.Public.class)
    @JsonProperty("data")
    @Override
    public List<T> getContent() {
        return source.getContent();
    }

    @Override
    public boolean hasContent() {
        return source.hasContent();
    }

    @Override
    public Sort getSort() {
        return source.getSort();
    }

    @JsonView(View.Public.class)
    @Override
    public boolean isFirst() {
        return source.isFirst();
    }

    @JsonView(View.Public.class)
    @Override
    public boolean isLast() {
        return source.isLast();
    }

    @Override
    public boolean hasNext() {
        return source.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return source.hasPrevious();
    }

    @Override
    public Pageable nextPageable() {
        return source.nextPageable();
    }

    @Override
    public Pageable previousPageable() {
        return source.previousPageable();
    }

    @Override
    public <U> org.springframework.data.domain.Page<U> map(Function<? super T, ? extends U> converter) {
        return new Page<>(source.map(converter));
    }

    @Override
    public Iterator<T> iterator() {
        return source.iterator();
    }
}
