package org.jetlang.core;


/**
 * User: mrettig
 * Date: Aug 29, 2009
 */
public class EventBuffer extends MessageBuffer<Runnable> implements EventReader {
    public void add(Runnable runnable) {
        super.add(runnable);
    }
}
