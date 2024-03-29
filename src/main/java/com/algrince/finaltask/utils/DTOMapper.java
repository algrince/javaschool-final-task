package com.algrince.finaltask.utils;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Maps properties between objects, object and class, list / page of
 * objects and classes
 */

@Component
@RequiredArgsConstructor
public class DTOMapper {
    private final ModelMapper modelMapper;

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .toList();
    }

    public <S, T> Page<T> mapPage(Page<S> source, Class<T> targetClass) {
        return source.map(element -> modelMapper.map(element, targetClass));
    }

    public <S, T> T mapClass(S sourceClass, Class<T> targetClass) {
        return modelMapper.map(sourceClass, targetClass);
    }


    public <S, T> void mapProperties (S sourceClass, T targetClass) {
        modelMapper.map(sourceClass, targetClass);
    }


}
