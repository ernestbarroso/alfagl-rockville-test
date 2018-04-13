package com.rockville.exercise.beans;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode
@Builder
public class MetaData {
    private String name;
    private String value;
}
