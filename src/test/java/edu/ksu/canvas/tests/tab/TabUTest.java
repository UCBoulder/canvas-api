package edu.ksu.canvas.tests.tab;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import edu.ksu.canvas.CanvasTestBase;
import edu.ksu.canvas.impl.TabImpl;
import edu.ksu.canvas.interfaces.TabWriter;
import edu.ksu.canvas.model.Tab;
import edu.ksu.canvas.net.FakeRestClient;
import edu.ksu.canvas.requestOptions.UpdateCourseTabOptions;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TabUTest extends CanvasTestBase {

    @Autowired
    private FakeRestClient fakeRestClient;

    private TabWriter tabWriter;

    @BeforeEach
    void setUp() {
        tabWriter = new TabImpl(baseUrl, apiVersion, SOME_OAUTH_TOKEN, fakeRestClient, SOME_CONNECT_TIMEOUT,
                SOME_READ_TIMEOUT, DEFAULT_PAGINATION_PAGE_SIZE, false);
    }

    @Test
    void testUpdateCourseTab() throws IOException, URISyntaxException, ParseException {
        String url = baseUrl + "/api/v1/courses/1234/tabs/files";
        fakeRestClient.addSuccessResponse(url, "SampleJson/tab/UpdateTabSuccess.json");
        UpdateCourseTabOptions options = new UpdateCourseTabOptions("1234", "files");
        options.hidden(true);
        Optional<Tab> tab = tabWriter.updateCourseTab(options);

        assertTrue(tab.isPresent());
        assertEquals("files", tab.get().getId());
        assertTrue(tab.get().isHidden());
        assertEquals("admins", tab.get().getVisibility());
        assertEquals("Files", tab.get().getLabel());
    }
}
