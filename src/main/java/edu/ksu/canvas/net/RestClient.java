package edu.ksu.canvas.net;

import edu.ksu.canvas.exception.InvalidOauthTokenException;
import edu.ksu.canvas.oauth.OauthToken;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

public interface RestClient {
    Response sendApiGet(@NotNull OauthToken token, @NotNull String url, int connectTimeout, int readTimeout) throws IOException, URISyntaxException, ParseException;
    Response sendJsonPost(@NotNull OauthToken token, @NotNull String url, String json, int connectTimeout, int readTimeout) throws IOException, URISyntaxException, ParseException;
    Response sendJsonPut(@NotNull OauthToken token, @NotNull String url, String json, int connectTimeout, int readTimeout) throws IOException, URISyntaxException, ParseException;
    Response sendApiPost(@NotNull OauthToken token, @NotNull String url, Map<String, List<String>> postParameters, int connectTimeout, int readTimeout) throws InvalidOauthTokenException, IOException, URISyntaxException, ParseException;
    Response sendApiPostFile(@NotNull OauthToken token, @NotNull String url, Map<String, List<String>> postParameters, String fileParameter, String filePath, InputStream is, int connectTimeout, int readTimeout) throws InvalidOauthTokenException, IOException, URISyntaxException, ParseException;
    Response sendApiDelete(@NotNull OauthToken token, @NotNull String url, Map<String, List<String>> deleteParameters, int connectTimeout, int readTimeout) throws InvalidOauthTokenException, IOException, URISyntaxException, ParseException;
    Response sendApiPut(@NotNull OauthToken token, @NotNull String url, Map<String, List<String>> putParameters, int connectTimeout, int readTimeout) throws InvalidOauthTokenException, IOException, URISyntaxException, ParseException;

    String sendUpload(String uploadUrl, Map<String, List<String>> params, InputStream in, String filename, int connectTimeout, int readTimeout) throws IOException, URISyntaxException, ParseException;
}
