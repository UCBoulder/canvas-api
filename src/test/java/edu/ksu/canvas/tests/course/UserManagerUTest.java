package edu.ksu.canvas.tests.course;

import edu.ksu.canvas.CanvasTestBase;
import edu.ksu.canvas.constants.CanvasConstants;
import edu.ksu.canvas.impl.UserImpl;
import edu.ksu.canvas.interfaces.UserWriter;
import edu.ksu.canvas.model.User;
import edu.ksu.canvas.net.FakeRestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserManagerUTest extends CanvasTestBase {
    @Autowired
    private FakeRestClient fakeRestClient;
    private UserWriter userWriter;

    @BeforeEach
    void setupData() {
        userWriter = new UserImpl(baseUrl, apiVersion, SOME_OAUTH_TOKEN, fakeRestClient, SOME_CONNECT_TIMEOUT,
                SOME_READ_TIMEOUT, DEFAULT_PAGINATION_PAGE_SIZE, false);
    }

    @Test
    void testCreateUser() throws IOException {
        User user = new User();
        user.setName("somestring4");
        user.setLoginId("somestring4");
        String url = baseUrl + "/api/v1/accounts/1/users";
        fakeRestClient.addSuccessResponse(url, "SampleJson/CreateUserResponse.json");
        Optional<User> response = userWriter.createUser(user);
        System.out.println(response.toString());
        assertEquals("somestring4",response.get().getName());
        assertNotNull(response.get().getId());
        assertEquals("https://instance.test.instructure.com/register/214b60f3e9c42873d323bd503f9afd4e", response.get().getConfirmationUrl());
    }

    @Test
    void testDeleteUser() throws IOException {
        //assertTrue(accountImpl.deleteUser(token, 78839));
    }

    @Test
    void testSisUserMasqueradeCreateUser() throws IOException {
        User user = new User();
        String userId = "899123456";
        user.setName("somestring4");
        user.setLoginId("somestring4");
        String url = baseUrl + "/api/v1/accounts/1/users?as_user_id=" + CanvasConstants.MASQUERADE_SIS_USER + ":" + userId;
        fakeRestClient.addSuccessResponse(url, "SampleJson/CreateUserResponse.json");
        Optional<User> response = userWriter.writeAsSisUser(userId).createUser(user);
        System.out.println(response.toString());
        assertEquals("somestring4",response.get().getName());
        assertNotNull(response.get().getId());
    }
    @Test
    void testCanvasUserMasqueradeCreateUser() throws IOException {
        User user = new User();
        String userId = "899123456";
        user.setName("somestring4");
        user.setLoginId("somestring4");
        String url = baseUrl + "/api/v1/accounts/1/users?as_user_id=" + userId;
        fakeRestClient.addSuccessResponse(url, "SampleJson/CreateUserResponse.json");
        Optional<User> response = userWriter.writeAsCanvasUser(userId).createUser(user);
        System.out.println(response.toString());
        assertEquals("somestring4",response.get().getName());
        assertNotNull(response.get().getId());
    }
}

