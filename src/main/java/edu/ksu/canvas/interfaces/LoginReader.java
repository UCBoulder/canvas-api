package edu.ksu.canvas.interfaces;

import edu.ksu.canvas.model.Login;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface LoginReader extends CanvasReader<Login, LoginReader> {
    /**
     * Retrieve a user's logins.
     * @param userId the id of the user to retrieve logins for
     * @return List of the user's logins
     * @throws IOException When there is an error communicating with Canvas
     */
    public List<Login> getLoginForUser(String userId) throws IOException, URISyntaxException, ParseException;
}
