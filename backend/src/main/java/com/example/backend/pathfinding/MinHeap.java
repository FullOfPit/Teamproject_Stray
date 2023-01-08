package com.example.backend.pathfinding;
import java.util.ArrayList;
import java.util.List;

public class MinHeap {

    private List<Integer> heap = new ArrayList<>();
    private int size = 0;

    public void add(Integer value) {
        this.heap.add(value);
        this.size++;
        this.bubbleUp();
    }

    public Integer popMin() {
        if (this.size == 0) {
            return null;
        }
        this.swap(1, this.size);
        Integer min = this.heap.remove(this.size);
        this.size--;
        this.heapify();

        return min;
    }

    public void bubbleUp() {
        int current = this.size;

        while (current > 1 && this.heap.get(getParentIndex(current)) > this.heap.get(current)) {
            this.swap(this.size, getParentIndex(current));
            current = getParentIndex(current);
        }
    }

    public void heapify() {
        int current = 1;
        int leftChildIndex = getLeftChildIndex(current);
        int rightChildIndex = getRightChildIndex(current);

        while (this.canSwap(current, leftChildIndex, rightChildIndex)) {
            if (this.exists(leftChildIndex) && this.exists(rightChildIndex)) {
                if (this.heap.get(leftChildIndex) < this.heap.get(rightChildIndex)) {
                    this.swap(current, leftChildIndex);
                    current = leftChildIndex;
                } else {
                    this.swap(current, rightChildIndex);
                    current = rightChildIndex;
                }
                leftChildIndex = getLeftChildIndex(current);
                rightChildIndex = getRightChildIndex(current);
            }
        }
    }

    public void swap(int a, int b) {
        int firstToSwap = this.heap.get(a);
        int secondToSwap = this.heap.get(b);
        this.heap.set(a, secondToSwap);
        this.heap.set(b, firstToSwap);
    }

    public boolean exists(int index) {
        return index <= this.size;
    }

    public boolean canSwap(int current, int leftChildIndex, int rightChildIndex) {
        return
                this.exists(leftChildIndex)
                        && this.heap.get(current) > this.heap.get(leftChildIndex)
                ||
                this.exists(rightChildIndex)
                        && this.heap.get(current) > this.heap.get(rightChildIndex);

    }



    public Integer getParentIndex(int index) {
        return Math.floorDiv(index, 2);
    }
    public Integer getLeftChildIndex(int index) {
        return index * 2;
    }
    public Integer getRightChildIndex(int index) {
        return index * 2 + 1;
    }
}
