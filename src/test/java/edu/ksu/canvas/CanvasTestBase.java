package edu.ksu.canvas;

import edu.ksu.canvas.config.CommonTestConfig;
import edu.ksu.canvas.interfaces.CanvasMessenger;
import edu.ksu.canvas.net.FakeRestClient;
import edu.ksu.canvas.oauth.NonRefreshableOauthToken;
import edu.ksu.canvas.oauth.OauthToken;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CommonTestConfig.class})
@ActiveProfiles("dev")
public abstract class CanvasTestBase {
    public static final OauthToken SOME_OAUTH_TOKEN = new NonRefreshableOauthToken("token");;
    public static final int SOME_CONNECT_TIMEOUT = 1000;
    public static final int SOME_READ_TIMEOUT = 1000;
    public static final Integer DEFAULT_PAGINATION_PAGE_SIZE = null;
    @Autowired
    protected CanvasMessenger mockCanvasMessenger;
    @Autowired
    protected String baseUrl;
    @Autowired
    protected FakeRestClient fakeRestClient;
    protected Integer apiVersion = 1;
}
