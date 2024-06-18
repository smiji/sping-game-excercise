package roxor.games.roulette.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import roxor.games.roulette.RouletteApplication;
import roxor.games.roulette.controller.exception.ErrorResp;
import roxor.games.roulette.model.response.SpinResultDTO;
import roxor.games.roulette.util.Util;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(classes = RouletteApplication.class)
@SpringBootTest(classes = RouletteApplication.class)
@AutoConfigureMockMvc
class RouletteControllerTest {
    private static final String FILE_NAME_SINGLE_PLAYER = "/player/SinglePlayer.json";
    private static final String FILE_NAME_SINGLE_PLAYER_FORCED = "/player/SinglePlayer_forced_36.json";
    private static final String FILE_NAME_MULTIPLAYER = "/player/Multiplayer.json";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSpin_without_force() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/roulette/spin")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonFromFile(FILE_NAME_SINGLE_PLAYER));
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
        SpinResultDTO spinResultDTO = Util.deserialize(SpinResultDTO.class,mvcResult.getResponse().getContentAsString());
        assertThat(spinResultDTO, hasProperty("pocket"));
    }

    @Test
    void testSpin_without_force_multiplayer() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/roulette/spin")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonFromFile(FILE_NAME_MULTIPLAYER));
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
        SpinResultDTO spinResultDTO = Util.deserialize(SpinResultDTO.class,mvcResult.getResponse().getContentAsString());
        assertThat(spinResultDTO, hasProperty("pocket"));
    }


    // Test validation - forced
    @Test
    void testSpin_with_force() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/roulette/spin")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonFromFile(FILE_NAME_SINGLE_PLAYER_FORCED));
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testValidations() throws Exception{
        //player name  - empty
        String singlePlayerData = jsonFromFile(FILE_NAME_SINGLE_PLAYER);
        processValidationRequest(singlePlayerData.replace("Sam", ""));
        //betType  - empty
        processValidationRequest(singlePlayerData.replace("number", ""));
        //pocket to empty
        processValidationRequest(singlePlayerData.replace("4", ""));
        //pocket to -1
        processValidationRequest(singlePlayerData.replace("4", "-1"));
        //pocket to 37
        processValidationRequest(singlePlayerData.replace("4", "37"));
        //betAmount to empty
        processValidationRequest(singlePlayerData.replace("1.00", ""));
    }
    private void processValidationRequest(String jsonData) throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/roulette/spin")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonData);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        ErrorResp errorResp= Util.deserialize(ErrorResp.class, mvcResult.getResponse().getContentAsString());
        assertThat(errorResp,hasProperty("type",equalTo("about:blank")));
        assertThat(errorResp,hasProperty("title",equalTo("Bad Request")));
        assertThat(errorResp,hasProperty("status",equalTo(400)));
        assertThat(errorResp,hasProperty("detail",equalTo("Invalid request content")));
        assertThat(errorResp,hasProperty("instance",equalTo("/v1/roulette/spin")));
    }
    private String jsonFromFile(String fileName) throws IOException {
        return Util.readJsonFromFile(fileName);
    }
}