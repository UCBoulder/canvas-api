package edu.ksu.canvas.tests.module;

import edu.ksu.canvas.CanvasTestBase;
import edu.ksu.canvas.impl.ModuleImpl;
import edu.ksu.canvas.interfaces.ModuleReader;
import edu.ksu.canvas.model.Module;
import edu.ksu.canvas.net.FakeRestClient;
import edu.ksu.canvas.requestOptions.ListModulesOptions;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ModuleListingTest extends CanvasTestBase {
    @Autowired
    private FakeRestClient fakeRestClient;
    private ModuleReader moduleReader;

    @BeforeEach
    void setupData() {
        moduleReader = new ModuleImpl(baseUrl, apiVersion, SOME_OAUTH_TOKEN, fakeRestClient, SOME_CONNECT_TIMEOUT,
                SOME_READ_TIMEOUT, DEFAULT_PAGINATION_PAGE_SIZE, false);
    }

    @Test
    void testListModulesInCourse() throws IOException, URISyntaxException, ParseException {
        Long courseId = 1092L;
        ListModulesOptions options = new ListModulesOptions(courseId).studentId("526007");
        String url = baseUrl + "/api/v1/courses/1092/modules?student_id=526007";
        fakeRestClient.addSuccessResponse(url, "SampleJson/Modules.json");
        List<Module> modules = moduleReader.getModulesInCourse(options);
        assertNotNull(modules);
        assertEquals(2, modules.size());
        assertEquals("Module One", modules.get(0).getName());
        assertEquals(new Date(1581379227000L), modules.get(0).getCompletedAt());
        assertNull(modules.get(1).getCompletedAt());
    }

}
