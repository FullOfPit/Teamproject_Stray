package com.example.backend.pathfinding;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PriorityQueue {

    private List<QueueNode> heap = new ArrayList<>();
    private int size = 0;

    //QueueNode = {Vertex vertex, double priority}

    private void add(QueueNode queueNode) {
        this.heap.add(queueNode);
        this.size++;
        this.bubbleUp();
    }

    private boolean isEmpty() {
        return this.size == 0;
    }

    public QueueNode popMin() {
        if (isEmpty()) {
            return null;
        }
        this.swap(0, this.size -1);
        QueueNode min = this.heap.remove(this.size - 1);
        this.size--;
        this.heapify();

        return min;
    }

    /*
    bubbleUp() {
        let current = this.size;
        while (current > 1 && this.heap[getParent(current)].priority > this.heap[current].priority) {
            this.swap(current, getParent(current));
            current = getParent(current);
        }
    }
     */

    public void bubbleUp() {
        int current = this.size - 1;

        while (current > 1
                && this.heap.get(getParentIndex(current)).getPriority()
                    > this.heap.get(current).getPriority()
        ) {
            this.swap(current, getParentIndex(current));
            current = getParentIndex(current);
        }
    }

    public void heapify() {
        int current = 1;
        int leftChildIndex = getLeftChildIndex(current);
        int rightChildIndex = getRightChildIndex(current);

        while (this.canSwap(current, leftChildIndex, rightChildIndex)) {
            if (this.exists(leftChildIndex) && this.exists(rightChildIndex)) {
                if (this.heap.get(leftChildIndex).getPriority() < this.heap.get(rightChildIndex).getPriority()) {
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
        QueueNode firstToSwap = this.heap.get(a);
        QueueNode secondToSwap = this.heap.get(b);
        this.heap.set(a, secondToSwap);
        this.heap.set(b, firstToSwap);
    }

    public boolean exists(int index) {
        return index < this.size;
    }

    public boolean canSwap(int current, int leftChildIndex, int rightChildIndex) {
        return
                this.exists(leftChildIndex)
                        && this.heap.get(current).getPriority() > this.heap.get(leftChildIndex).getPriority()
                        ||
                        this.exists(rightChildIndex)
                                && this.heap.get(current).getPriority() > this.heap.get(rightChildIndex).getPriority();

    }

    public Integer getParentIndex(int index) {
        return index / 2;
    }

    public Integer getLeftChildIndex(int index) {
        return index * 2;
    }
    public Integer getRightChildIndex(int index) {
        return index * 2 + 1;
    }

}
