package com.danifoldi.messagelib.messageprovider;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface MessageProvider<K> {

    @NotNull String getMessageBase(final @NotNull K key);
}
