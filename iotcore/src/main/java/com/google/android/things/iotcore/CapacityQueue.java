// Copyright 2018 Google LLC.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.android.things.iotcore;

import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

/** Queue implementation with limited capacity. */
class CapacityQueue<E> extends AbstractQueue<E> {
    private static final String TAG = CapacityQueue.class.getSimpleName();
    
    private static final Logger Log = Logger.getLogger(TAG);

    /**
     * A CapacityQueue's drop policy determines how CapacityQueue handles attempts to enqueue new
     * elements after the queue is at maximum capacity.
     *
     * <p>DROP_POLICY_TAIL means that elements enqueued in a full CapacityQueue are rejected until
     * older elements are dequeued.
     *
     * <p>DROP_POLICY_HEAD means that when an element is enqueued in a full CapacityQueue, the
     * oldest element, the element at the head of the queue, is discarded and the new element is
     * added to the back of the queue.
     */
    public enum DropPolicy{
    	DROP_POLICY_HEAD, 
    	DROP_POLICY_TAIL
    };

    private final Deque<E> mDeque;
    private final int mMaxCapacity;
    private final DropPolicy mDropPolicy;

    CapacityQueue(int maxCapacity, DropPolicy dropPolicy) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Queue capacity must be greater than 0");
        }
        if (!dropPolicy.equals(DropPolicy.DROP_POLICY_HEAD) && !dropPolicy.equals(DropPolicy.DROP_POLICY_TAIL)) {
            throw new IllegalArgumentException(
                    "Queue drop policy must be DROP_POLICY_HEAD or DROP_POLICY_TAIL");
        }

        mMaxCapacity = maxCapacity;
        mDropPolicy = dropPolicy;
        mDeque = new ArrayDeque<>();
    }

    @Override
    public int size() {
        return mDeque.size();
    }

    @Override
    public boolean offer(E e) {
        if (mDropPolicy.equals(DropPolicy.DROP_POLICY_TAIL)) {
            return mDeque.size() < mMaxCapacity && mDeque.offerLast(e);
        }

        // DROP_POLICY_HEAD
        if (mDeque.size() >= mMaxCapacity) {
            Log.fine("Dropping from head");
            mDeque.removeFirst();
        }
        return mDeque.offerLast(e);
    }

    @Override
    public E poll() {
        return mDeque.pollFirst();
    }

    @Override
    public E peek() {
        return mDeque.peekFirst();
    }

    @Override
    @Nonnull
    public Iterator<E> iterator() {
        return mDeque.iterator();
    }
}
