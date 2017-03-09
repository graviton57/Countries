package com.havrylyuk.countries.observer;

import java.util.Observable;

/**
 * Created by Igor Havrylyuk on 09.03.2017.
 */

public class ContentObserver extends Observable {

    private static ContentObserver instance = new ContentObserver();

    public static ContentObserver getInstance() {
        return instance;
    }

    private ContentObserver() {
    }

    public void notifyDataChange() {
        synchronized (this) {
            setChanged();
            notifyObservers();
        }
    }
}
