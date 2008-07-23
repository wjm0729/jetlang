package com.jetlang.channels;

import com.jetlang.core.Callback;
import com.jetlang.core.ICommandQueue;

import java.util.ArrayList;

/**
 * User: mrettig
 * Date: Jul 22, 2008
 * Time: 3:57:21 PM
 */
public class Channel<T> {

    //TODO optimize threading
    private final ArrayList<Callback<T>> _subscribers = new ArrayList<Callback<T>>();

    public boolean publish(T s) {
        boolean published = false;
        synchronized (_subscribers) {
            for (Callback<T> callback : _subscribers) {
                callback.onMessage(s);
                published = true;
            }
        }
        return published;
    }

    public void subscribe(final ICommandQueue queue, final Callback<T> onReceive) {
        synchronized (_subscribers) {
            Callback<T> callbackOnQueue = new Callback<T>() {
                public void onMessage(final T message) {
                    final Runnable toExecute = new Runnable() {
                        public void run() {
                            onReceive.onMessage(message);
                        }
                    };
                    queue.queue(toExecute);
                }
            };
            _subscribers.add(callbackOnQueue);
        }
    }

    public void clearSubscribers() {
        synchronized (_subscribers) {
            _subscribers.clear();
        }
    }
}
