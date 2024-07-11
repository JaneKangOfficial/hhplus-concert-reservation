package io.hhplus.concert.points;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.concert.domain.concerts.presentation.dto.response.UsersResponseDTO;
import io.hhplus.concert.domain.points.business.entity.PointsType;
import io.hhplus.concert.domain.points.business.service.PointsService;
import io.hhplus.concert.domain.points.presentation.controller.PointsController;
import io.hhplus.concert.domain.points.presentation.dto.request.PointsRequestDTO;
import io.hhplus.concert.domain.points.presentation.dto.response.PointsResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PointsController.class)
public class PointsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PointsService pointsService;

    @Test
    @DisplayName("포인트 조회 테스트")
    public void balancesTest() throws Exception {
        // given
        Long userId = 1L;
        Long point = 10000L;
        UsersResponseDTO usersResponseDTO = new UsersResponseDTO(userId, point);
        when(pointsService.balances(userId)).thenReturn(usersResponseDTO);

        // when & then
        MvcResult ret = mockMvc.perform(MockMvcRequestBuilders.get("/api/points/balances/{userId}", userId)
//                        .param("userId", String.valueOf(userId)) // @RequestParam 일 경우 사용
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.point").value(point))
                .andDo(print())
                .andReturn();

        String restult = ret.getResponse().getContentAsString();
        System.out.println("restult : " + restult);
    }

    @Test
    @DisplayName("포인트 충전 테스트")
    public void chargePointTest() throws Exception {
        // given
        PointsRequestDTO pointsRequestDTO = new PointsRequestDTO(1L, 100L, PointsType.CHARGE);
        PointsResponseDTO pointsResponseDTO = new PointsResponseDTO(1000L);
        when(pointsService.chargePoint(pointsRequestDTO)).thenReturn(pointsResponseDTO);

        // when & then
        MvcResult ret = mockMvc.perform(MockMvcRequestBuilders.post("/api/points/charge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pointsRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(1000L))
                .andDo(print())
                .andReturn();

        String restult = ret.getResponse().getContentAsString();
        System.out.println("restult : " + restult);
    }
}
