package io.hhplus.concert.tokens;

import io.hhplus.concert.domain.tokens.business.service.TokensService;
import io.hhplus.concert.domain.tokens.presentation.controller.TokensController;
import io.hhplus.concert.domain.tokens.presentation.dto.response.TokensResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(TokensController.class)
public class TokensControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokensService tokensService;

    @Test
    @DisplayName("토큰 발급 테스트")
    public void issuance() throws Exception {

        TokensResponseDTO tokensResponseDTO = new TokensResponseDTO(1L, "token");

        // given
        when(tokensService.issueToken(1L)).thenReturn(tokensResponseDTO);

        // when & then
        MvcResult ret = mockMvc.perform(MockMvcRequestBuilders.get("/api/tokens/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String restult = ret.getResponse().getContentAsString();
        System.out.println("restult : " + restult);

    }

}
