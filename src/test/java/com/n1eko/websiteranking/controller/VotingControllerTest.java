package com.n1eko.websiteranking.controller;

import com.n1eko.websiteranking.model.Website;
import com.n1eko.websiteranking.service.VoteService;
import com.n1eko.websiteranking.service.WebsiteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = VotingController.class)
@WithMockUser
@AutoConfigureMockMvc(addFilters = false)
class VotingControllerTest {

    Website mockWebsite = Website.builder().id(1L).name("WebsiteName").description("WebsiteDescription")
            .baseUrl("http://test.com/").fullUrl("http://test.com/path").category(null).build();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VoteService voteService;
    @MockBean
    private WebsiteService websiteService;

    @Test
    void vote_non_existent_website() throws Exception {
        Mockito.when(websiteService.findWebsiteById(Mockito.anyLong())).thenReturn(Optional.empty());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                        "/api/v1/vote").queryParam("websiteId", "843")
                .queryParam("voteType", "UPVOTE").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        verify(websiteService, atLeast(1)).findWebsiteById(Mockito.anyLong());
    }

    @Test
    void upvote() throws Exception {

        Mockito.when(websiteService.findWebsiteById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mockWebsite));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                        "/api/v1/vote").queryParam("websiteId", "843")
                .queryParam("voteType", "UPVOTE").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
        verify(websiteService, atLeast(1)).upvoteWebsite(Mockito.anyLong());
    }

    @Test
    void downvote() throws Exception {

        Mockito.when(websiteService.findWebsiteById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mockWebsite));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                        "/api/v1/vote").queryParam("websiteId", "843")
                .queryParam("voteType", "DOWNVOTE").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
        verify(websiteService, atLeast(1)).downvoteWebsite(Mockito.anyLong());
    }

    @Test
    void reachMaxVotesPerDay() throws Exception {
        Mockito.when(voteService.countVotesForIpWithinLast24Hours(Mockito.anyString())).thenReturn(6);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                        "/api/v1/vote").queryParam("websiteId", "843")
                .queryParam("voteType", "DOWNVOTE").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.TOO_MANY_REQUESTS.value(), response.getStatus());
        verify(voteService, atLeast(1)).countVotesForIpWithinLast24Hours(Mockito.anyString());
    }
}