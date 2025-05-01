package client;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vsu.rogachev.client.codeforces.CodeforcesClient;
import ru.vsu.rogachev.client.codeforces.dto.Problems;
import ru.vsu.rogachev.client.codeforces.dto.Submission;
import ru.vsu.rogachev.client.codeforces.dto.CodeforcesUser;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class CodeforcesClientTest {

    private CodeforcesClient codeforcesClient;

    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        codeforcesClient = new CodeforcesClient(webClient);
    }

    @AfterEach
    void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    private static final String userHandle = "egor4444ik";
    private static final String userEmail = "egorchik.rog@yandex.ru";
    private static final Integer userRating = 1634;
    private static final String userInfoResponse =
            "{\n" +
                    "    \"status\": \"OK\",\n" +
                    "    \"result\": [\n" +
                    "        {\n" +
                    "            \"lastName\": \"Rogachev\",\n" +
                    "            \"country\": \"Russia\",\n" +
                    "            \"lastOnlineTimeSeconds\": 1735697663,\n" +
                    "            \"city\": \"Voronezh\",\n" +
                    "            \"rating\": " + userRating + ",\n" +
                    "            \"friendOfCount\": 29,\n" +
                    "            \"titlePhoto\": \"https://userpic.codeforces.org/2461028/title/2bf1f6f99c980c03.jpg\",\n" +
                    "            \"handle\": \"" + userHandle + "\",\n" + // Кавычки для строки
                    "            \"avatar\": \"https://userpic.codeforces.org/2461028/avatar/d61b3969ab863e18.jpg\",\n" +
                    "            \"firstName\": \"Egor\",\n" +
                    "            \"contribution\": 0,\n" +
                    "            \"organization\": \"Voronezh State University\",\n" +
                    "            \"rank\": \"expert\",\n" +
                    "            \"maxRating\": " + userRating + ",\n" +
                    "            \"registrationTimeSeconds\": 1645285201,\n" +
                    "            \"email\": \"" + userEmail + "\",\n" + // Кавычки для строки
                    "            \"maxRank\": \"expert\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
    private static final String userSubmissionsResponse =
            "{\n" +
                    "    \"status\": \"OK\",\n" +
                    "    \"result\": [\n" +
                    "        {\n" +
                    "            \"id\": 298292898,\n" +
                    "            \"contestId\": 2043,\n" +
                    "            \"creationTimeSeconds\": 1735057671,\n" +
                    "            \"relativeTimeSeconds\": 6771,\n" +
                    "            \"problem\": {\n" +
                    "                \"contestId\": 2043,\n" +
                    "                \"index\": \"E\",\n" +
                    "                \"name\": \"Matrix Transformation\",\n" +
                    "                \"type\": \"PROGRAMMING\",\n" +
                    "                \"tags\": [\n" +
                    "                    \"bitmasks\",\n" +
                    "                    \"brute force\",\n" +
                    "                    \"data structures\",\n" +
                    "                    \"dfs and similar\",\n" +
                    "                    \"graphs\",\n" +
                    "                    \"greedy\",\n" +
                    "                    \"implementation\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"author\": {\n" +
                    "                \"contestId\": 2043,\n" +
                    "                \"members\": [\n" +
                    "                    {\n" +
                    "                        \"handle\": \"egor4444ik\"\n" +
                    "                    }\n" +
                    "                ],\n" +
                    "                \"participantType\": \"CONTESTANT\",\n" +
                    "                \"ghost\": false,\n" +
                    "                \"startTimeSeconds\": 1735050900\n" +
                    "            },\n" +
                    "            \"programmingLanguage\": \"C++20 (GCC 13-64)\",\n" +
                    "            \"verdict\": \"WRONG_ANSWER\",\n" +
                    "            \"testset\": \"TESTS\",\n" +
                    "            \"passedTestCount\": 1,\n" +
                    "            \"timeConsumedMillis\": 171,\n" +
                    "            \"memoryConsumedBytes\": 0\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": 298273943,\n" +
                    "            \"contestId\": 2043,\n" +
                    "            \"creationTimeSeconds\": 1735056120,\n" +
                    "            \"relativeTimeSeconds\": 5220,\n" +
                    "            \"problem\": {\n" +
                    "                \"contestId\": 2043,\n" +
                    "                \"index\": \"D\",\n" +
                    "                \"name\": \"Problem about GCD\",\n" +
                    "                \"type\": \"PROGRAMMING\",\n" +
                    "                \"tags\": [\n" +
                    "                    \"brute force\",\n" +
                    "                    \"flows\",\n" +
                    "                    \"math\",\n" +
                    "                    \"number theory\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"author\": {\n" +
                    "                \"contestId\": 2043,\n" +
                    "                \"members\": [\n" +
                    "                    {\n" +
                    "                        \"handle\": \"egor4444ik\"\n" +
                    "                    }\n" +
                    "                ],\n" +
                    "                \"participantType\": \"CONTESTANT\",\n" +
                    "                \"ghost\": false,\n" +
                    "                \"startTimeSeconds\": 1735050900\n" +
                    "            },\n" +
                    "            \"programmingLanguage\": \"C++20 (GCC 13-64)\",\n" +
                    "            \"verdict\": \"OK\",\n" +
                    "            \"testset\": \"TESTS\",\n" +
                    "            \"passedTestCount\": 27,\n" +
                    "            \"timeConsumedMillis\": 155,\n" +
                    "            \"memoryConsumedBytes\": 102400\n" +
                    "        }" +
                    "    ]\n" +
                    "}";
    private static final String problemSetResponse =
            "{\n" +
                    "    \"status\": \"OK\",\n" +
                    "    \"result\": {\n" +
                    "        \"problems\": [\n" +
                    "            {\n" +
                    "                \"contestId\": 2053,\n" +
                    "                \"index\": \"I2\",\n" +
                    "                \"name\": \"Affectionate Arrays (Hard Version)\",\n" +
                    "                \"type\": \"PROGRAMMING\",\n" +
                    "                \"points\": 2000.0,\n" +
                    "                \"tags\": [\n" +
                    "                    \"data structures\",\n" +
                    "                    \"dp\",\n" +
                    "                    \"graphs\",\n" +
                    "                    \"greedy\",\n" +
                    "                    \"math\",\n" +
                    "                    \"shortest paths\",\n" +
                    "                    \"two pointers\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"contestId\": 2053,\n" +
                    "                \"index\": \"I1\",\n" +
                    "                \"name\": \"Affectionate Arrays (Easy Version)\",\n" +
                    "                \"type\": \"PROGRAMMING\",\n" +
                    "                \"points\": 3000.0,\n" +
                    "                \"tags\": [\n" +
                    "                    \"data structures\",\n" +
                    "                    \"dp\",\n" +
                    "                    \"greedy\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"contestId\": 2053,\n" +
                    "                \"index\": \"H\",\n" +
                    "                \"name\": \"Delicate Anti-monotonous Operations\",\n" +
                    "                \"type\": \"PROGRAMMING\",\n" +
                    "                \"points\": 4500.0,\n" +
                    "                \"tags\": [\n" +
                    "                    \"constructive algorithms\",\n" +
                    "                    \"implementation\"\n" +
                    "                ]\n" +
                    "            }" +
                    "        ]\n" +
                    "    }\n" +
                    "}";

    @Test
    public void positive_getUserInfo() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(userInfoResponse)
                        .addHeader("Content-Type", "application/json")
        );

        Optional<CodeforcesUser> user = codeforcesClient.getUserInfo(userHandle);

        assertTrue(user.isPresent());
        assertEquals(userHandle, user.get().getHandle());
        assertEquals(userEmail, user.get().getEmail());
        assertEquals(userRating, user.get().getRating());

        var recordedRequest = mockWebServer.takeRequest();
        assertEquals("/user.info?handles=" + userHandle, recordedRequest.getPath());
        assertEquals("GET", recordedRequest.getMethod());
    }

    @Test
    void positive_getUserSubmissions() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(userSubmissionsResponse)
                        .addHeader("Content-Type", "application/json")
        );

        List<Submission> submissions = codeforcesClient.getUserSubmissions(userHandle);

        assertThat(submissions).isNotEmpty();

        var recordedRequest = mockWebServer.takeRequest();
        assertEquals("/user.status?handle=" + userHandle, recordedRequest.getPath());
        assertEquals("GET", recordedRequest.getMethod());
    }

    @Test
    void positive_getProblemSet() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(problemSetResponse)
                        .addHeader("Content-Type", "application/json")
        );

        Optional<Problems> problems = codeforcesClient.getProblemSet();

        assertThat(problems).isNotEmpty();

        var recordedRequest = mockWebServer.takeRequest();
        assertEquals("/problemset.problems", recordedRequest.getPath());
        assertEquals("GET", recordedRequest.getMethod());
    }
}