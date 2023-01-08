package com.example.backend.pathfinding;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinHeapTest {

    @Test
    void add_addNumber_One() {
        //Given
        MinHeap minHeap = new MinHeap();
        //When
        minHeap.add(13);
        List<Integer> actual = minHeap.getHeap();
        //Then
        Assertions.assertEquals(List.of(13), actual);
    }

    @Test
    void getParentIndex_ReturnZero() {
        //Given
        MinHeap minHeap = new MinHeap();
        //When
        minHeap.add(13);
        int actual = minHeap.getParentIndex(minHeap.getSize());
        //Then
        Assertions.assertEquals(0, actual);
    }



    @Test
    void add_addNumber_Two() {
        //Given
        MinHeap minHeap = new MinHeap();
        //When
        minHeap.add(13);
        minHeap.add(21);
        List<Integer> actual = minHeap.getHeap();
        //Then
        Assertions.assertEquals(List.of(13, 21), actual);
    }

    @Test
    void add_addNumber_Three() {
        //Given
        MinHeap minHeap = new MinHeap();
        //When
        minHeap.add(13);
        minHeap.add(21);
        minHeap.add(17);
        List<Integer> actual = minHeap.getHeap();
        //Then
        Assertions.assertEquals(List.of(13, 17, 21), actual);
    }

    @Test
    void add_addNumber_Four() {
        //Given
        MinHeap minHeap = new MinHeap();
        //When
        minHeap.add(13);
        minHeap.add(21);
        minHeap.add(17);
        minHeap.add(15);
        List<Integer> actual = minHeap.getHeap();
        //Then
        Assertions.assertEquals(List.of(13, 15, 21, 17), actual);
    }

    @Test
    void add_addNumber_Five() {
        //Given
        MinHeap minHeap = new MinHeap();
        //When
        minHeap.add(13);
        minHeap.add(21);
        minHeap.add(17);
        minHeap.add(15);
        minHeap.add(19);
        List<Integer> actual = minHeap.getHeap();
        //Then
        Assertions.assertEquals(List.of(13, 15, 19, 17, 21), actual);
    }

    @Test
    void add_addNumber_Six() {
        //Given
        MinHeap minHeap = new MinHeap();
        //When
        minHeap.add(13);
        minHeap.add(21);
        minHeap.add(17);
        minHeap.add(15);
        minHeap.add(19);
        minHeap.add(42);
        List<Integer> actual = minHeap.getHeap();
        //Then
        Assertions.assertEquals(List.of(13, 15, 19, 17, 21, 42), actual);
    }
}