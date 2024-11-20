package com.example.ms_support;

import com.example.ms_support.models.SupportQuestion;
import com.example.ms_support.services.SupportService;
import com.example.ms_support.controllers.SupportController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SupportController.class)
public class SupportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupportService supportService;

    private SupportQuestion testQuestion;

    @BeforeEach
    void setUp() {
        testQuestion = new SupportQuestion();
        testQuestion.setQuestionId(1L);
        testQuestion.setUserId(101L);
        testQuestion.setSubject("Test Subject");
        testQuestion.setDescription("Test Description");
        testQuestion.setStatus("open");
    }

    @Test
    void testCreateSupportQuestion() throws Exception {
        Mockito.when(supportService.createSupportQuestion(any(SupportQuestion.class)))
                .thenReturn(testQuestion);

        String jsonRequest = """
            {
                "userId": 101,
                "subject": "Test Subject",
                "description": "Test Description",
                "status": "open"
            }
            """;

        mockMvc.perform(post("/support/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionId").value(1))
                .andExpect(jsonPath("$.userId").value(101))
                .andExpect(jsonPath("$.subject").value("Test Subject"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.status").value("open"));
    }

    @Test
    void testGetSupportQuestionById() throws Exception {
        Mockito.when(supportService.getSupportQuestionById(1))
                .thenReturn(Optional.of(testQuestion));

        mockMvc.perform(get("/support/questions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionId").value(1))
                .andExpect(jsonPath("$.userId").value(101))
                .andExpect(jsonPath("$.subject").value("Test Subject"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.status").value("open"));
    }

    @Test
    void testGetSupportQuestionById_NotFound() throws Exception {
        Mockito.when(supportService.getSupportQuestionById(99))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/support/questions/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetOpenSupportQuestions() throws Exception {
        Mockito.when(supportService.getOpenSupportQuestions())
                .thenReturn(Arrays.asList(testQuestion));

        mockMvc.perform(get("/support/questions/open"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].questionId").value(1))
                .andExpect(jsonPath("$[0].userId").value(101))
                .andExpect(jsonPath("$[0].subject").value("Test Subject"))
                .andExpect(jsonPath("$[0].description").value("Test Description"))
                .andExpect(jsonPath("$[0].status").value("open"));
    }

    @Test
    void testCloseSupportQuestion() throws Exception {
        testQuestion.setStatus("closed");
        Mockito.when(supportService.closeSupportQuestion(1))
                .thenReturn(testQuestion);

        mockMvc.perform(put("/support/questions/1/close"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionId").value(1))
                .andExpect(jsonPath("$.status").value("closed"));
    }
}
