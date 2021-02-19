package com.danifoldi.messagelib.messageprovider;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface MessageProvider<I> {

    @NotNull String getMessageBase(final @NotNull I id);
}
