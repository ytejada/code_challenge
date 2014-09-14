package de.ytejada.rocketchallenge.content.adapter.provider;

import java.util.List;

import de.ytejada.rocketchallenge.content.adapter.ClothesItem;

/**
 * Listener callback which notifies when new content for a certain request has been retrieved.
 */
public interface ContentProviderListener {

    /**
     * Method called when new content has been retrieved.
     *
     * @param requestId
     * @param newContent
     */
    public void onNewContent(String requestId, List<ClothesItem> newContent);

    /**
     * Method called when any error occurs at the time of retrieving content.
     *
     * @param requestId Request Id which failed
     * @param message   Failure message.
     */
    public void onError(String requestId, String message);

}
