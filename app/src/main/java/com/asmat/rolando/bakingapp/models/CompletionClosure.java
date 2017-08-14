package com.asmat.rolando.bakingapp.models;

/**
 * Created by rolandoasmat on 8/12/17.
 */


public interface CompletionClosure <T> {
    public void onComplete(T response);
}
