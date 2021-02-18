package com.danifoldi.messagelib.messageprovider;

@FunctionalInterface
public interface MessageProvider<I> {

    String getMessageBase(I id);
}
