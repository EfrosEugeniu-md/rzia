package ru.list.sorfe.rzia.beans.consumer;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Embeddable
public class AggregatesOfConsumer {
    private final Integer numberOfAggregates;
    private final Integer currentOfAggregates;
}
