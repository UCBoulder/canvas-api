package edu.ksu.canvas.interfaces;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import edu.ksu.canvas.model.Conversation;
import edu.ksu.canvas.requestOptions.GetSingleConversationOptions;
import org.apache.hc.core5.http.ParseException;

public interface ConversationReader extends CanvasReader<Conversation, ConversationReader> {

    /**
     * Get a single conversation from Canvas
     * @param options API options for the conversation call
     * @return The requested conversation, if it exists
     * @throws IOException if there is an error communicating with Canvas
     */
    public Optional<Conversation> getSingleConversation(GetSingleConversationOptions options) throws IOException, URISyntaxException, ParseException;
}
