package edu.ksu.canvas.tests.assignment;

import com.google.gson.JsonSyntaxException;
import edu.ksu.canvas.CanvasTestBase;
import edu.ksu.canvas.constants.CanvasConstants;
import edu.ksu.canvas.impl.AssignmentImpl;
import edu.ksu.canvas.interfaces.AssignmentReader;
import edu.ksu.canvas.model.assignment.Assignment;
import edu.ksu.canvas.net.FakeRestClient;
import edu.ksu.canvas.net.Response;
import edu.ksu.canvas.requestOptions.GetSingleAssignmentOptions;
import edu.ksu.canvas.requestOptions.ListCourseAssignmentsOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssignmentRetrieverUTest extends CanvasTestBase {
    @Autowired
    private FakeRestClient fakeRestClient;
    private AssignmentReader assignmentReader;

    @BeforeEach
    void setupData() {
        assignmentReader = new AssignmentImpl(baseUrl, apiVersion, SOME_OAUTH_TOKEN, fakeRestClient, SOME_CONNECT_TIMEOUT,
                SOME_READ_TIMEOUT, DEFAULT_PAGINATION_PAGE_SIZE, false);
    }


    @Test
    void testListCourseAssignments() throws Exception {
        String someCourseId = "123456";
        Assignment assignment1 = new Assignment();
        assignment1.setId(1L);
        Assignment assignment2 = new Assignment();
        assignment2.setId(2L);
        Response notErroredResponse = new Response();
        notErroredResponse.setErrorHappened(false);
        notErroredResponse.setResponseCode(200);
        String url = baseUrl + "/api/v1/courses/" + someCourseId + "/assignments";
        fakeRestClient.addSuccessResponse(url, "SampleJson/assignment/AssignmentList.json");

        List<Assignment> assignments = assignmentReader.listCourseAssignments(new ListCourseAssignmentsOptions(someCourseId));
        assertEquals(2, assignments.size());
        assertTrue(assignments.stream().map(Assignment::getName).filter("Assignment1"::equals).findFirst().isPresent());
        assertTrue(assignments.stream().map(Assignment::getName).filter("Assignment2"::equals).findFirst().isPresent());
    }

    @Test
    void testListAssignments_responseInvalid() throws Exception {
        String someCourseId = "123456";
        Response erroredResponse = new Response();
        erroredResponse.setResponseCode(401);
        String url =  baseUrl + "/api/v1/courses/" + someCourseId + "/assignments";
        fakeRestClient.addSuccessResponse(url, "InvalidJson.json");
        assertThrows(JsonSyntaxException.class, () -> {
            assignmentReader.listCourseAssignments(new ListCourseAssignmentsOptions(someCourseId));
        });

    }

    @Test
    void testRetrieveAssignment() throws Exception {
        String someCourseId = "1234";
        Long someAssignmentId = 123L;
        String url = baseUrl + "/api/v1/courses/" + someCourseId + "/assignments/" + someAssignmentId;
        fakeRestClient.addSuccessResponse(url, "SampleJson/assignment/MinimalAssignment.json");
        Optional<Assignment> assignment = assignmentReader.getSingleAssignment(new GetSingleAssignmentOptions(someCourseId, someAssignmentId));
        assertTrue(assignment.isPresent());
        assertEquals("Assignment1", assignment.map(Assignment::getName).orElse(""));
    }

    @Test
    void testSisUserMasqueradeListCourseAssignments() throws Exception {
        String someUserId = "899123456";
        String someCourseId = "123456";
        Assignment assignment1 = new Assignment();
        assignment1.setId(1L);
        Assignment assignment2 = new Assignment();
        assignment2.setId(2L);
        Response notErroredResponse = new Response();
        notErroredResponse.setErrorHappened(false);
        notErroredResponse.setResponseCode(200);
        String url = baseUrl + "/api/v1/courses/" + someCourseId + "/assignments?as_user_id=" + CanvasConstants.MASQUERADE_SIS_USER + ":" + someUserId;
        fakeRestClient.addSuccessResponse(url, "SampleJson/assignment/AssignmentList.json");

        List<Assignment> assignments = assignmentReader.readAsSisUser(someUserId).listCourseAssignments(new ListCourseAssignmentsOptions(someCourseId));
        assertEquals(2, assignments.size());
        assertTrue(assignments.stream().map(Assignment::getName).filter("Assignment1"::equals).findFirst().isPresent());
        assertTrue(assignments.stream().map(Assignment::getName).filter("Assignment2"::equals).findFirst().isPresent());
    }

    @Test
    void testSisUserMasqueradeListAssignments_responseInvalid() throws Exception {
        String someUserId = "899123456";
        String someCourseId = "123456";
        Response erroredResponse = new Response();
        erroredResponse.setResponseCode(401);
        String url =  baseUrl + "/api/v1/courses/" + someCourseId + "/assignments?as_user_id=" + CanvasConstants.MASQUERADE_SIS_USER + ":" + someUserId;
        fakeRestClient.addSuccessResponse(url, "InvalidJson.json");
        assertThrows(JsonSyntaxException.class, () -> {
            assignmentReader.readAsSisUser(someUserId).listCourseAssignments(new ListCourseAssignmentsOptions(someCourseId));
        });
    }

    @Test
    void testSisUserMasqueradingRetriveAssignment() throws Exception{
        String someUserId = "8991123123";
        String someCourseId = "1234";
        Long someAssignmentId = 123L;
        String url = baseUrl + "/api/v1/courses/" + someCourseId + "/assignments/" + someAssignmentId + "?as_user_id=" + CanvasConstants.MASQUERADE_SIS_USER + ":" + someUserId;
        fakeRestClient.addSuccessResponse(url, "SampleJson/assignment/MinimalAssignment.json");
        Optional<Assignment> assignment = assignmentReader.readAsSisUser(someUserId).getSingleAssignment(new GetSingleAssignmentOptions(someCourseId, someAssignmentId));
        assertTrue(assignment.isPresent());
        assertEquals("Assignment1", assignment.map(Assignment::getName).orElse(""));
    }

    @Test
    void testCanvasUserMasqueradeListCourseAssignments() throws Exception {
        String someUserId = "899123456";
        String someCourseId = "123456";
        Assignment assignment1 = new Assignment();
        assignment1.setId(1L);
        Assignment assignment2 = new Assignment();
        assignment2.setId(2L);
        Response notErroredResponse = new Response();
        notErroredResponse.setErrorHappened(false);
        notErroredResponse.setResponseCode(200);
        String url = baseUrl + "/api/v1/courses/" + someCourseId + "/assignments?as_user_id=" + someUserId;
        fakeRestClient.addSuccessResponse(url, "SampleJson/assignment/AssignmentList.json");

        List<Assignment> assignments = assignmentReader.readAsCanvasUser(someUserId).listCourseAssignments(new ListCourseAssignmentsOptions(someCourseId));
        assertEquals(2, assignments.size());
        assertTrue(assignments.stream().map(Assignment::getName).filter("Assignment1"::equals).findFirst().isPresent());
        assertTrue(assignments.stream().map(Assignment::getName).filter("Assignment2"::equals).findFirst().isPresent());
    }

    @Test
    void testCanvasUserMasqueradeListAssignments_responseInvalid() throws Exception {
        String someUserId = "899123456";
        String someCourseId = "123456";
        Response erroredResponse = new Response();
        erroredResponse.setResponseCode(401);
        String url =  baseUrl + "/api/v1/courses/" + someCourseId + "/assignments?as_user_id=" + someUserId;

        fakeRestClient.addSuccessResponse(url, "InvalidJson.json");
        assertThrows(JsonSyntaxException.class, () -> {
            assignmentReader.readAsCanvasUser(someUserId).listCourseAssignments(new ListCourseAssignmentsOptions(someCourseId));
        });
    }

    @Test
    void testCanvasUserMasqueradingRetriveAssignment() throws Exception{
        String someUserId = "8991123123";
        String someCourseId = "1234";
        Long someAssignmentId = 123L;
        String url = baseUrl + "/api/v1/courses/" + someCourseId + "/assignments/" + someAssignmentId + "?as_user_id=" + someUserId;
        fakeRestClient.addSuccessResponse(url, "SampleJson/assignment/MinimalAssignment.json");
        Optional<Assignment> assignment = assignmentReader.readAsCanvasUser(someUserId).getSingleAssignment(new GetSingleAssignmentOptions(someCourseId, someAssignmentId));
        assertTrue(assignment.isPresent());
        assertEquals("Assignment1", assignment.map(Assignment::getName).orElse(""));
    }


}
