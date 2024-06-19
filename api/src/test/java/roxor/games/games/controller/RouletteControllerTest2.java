package roxor.games.games.controller;

import com.home.games.RouletteApplication;
import com.home.games.model.response.SpinResultDTO;
import com.home.games.util.Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest (classes = RouletteApplication.class)
@AutoConfigureMockMvc
public class RouletteControllerTest2 {

    private static final String FILE_NAME_SINGLE_PLAYER_MULTI = "/player/SinglePlayerMultipocket.json";
    private static final String FILE_NAME_SINGLE_PLAYER_FORCED = "/player/SinglePlayer_forced_36.json";
    private static final String FILE_NAME_MULTIPLAYER_MULTI = "/player/Multiplayer.json";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testWithoutForce() throws Exception {
        String url = "/v1/roulette/spin";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                //Get the request json
                .content(jsonFromFile(FILE_NAME_SINGLE_PLAYER_MULTI))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
        SpinResultDTO spinResultDTO = Util.deserialize(SpinResultDTO.class,mvcResult.getResponse().getContentAsString());
        assertThat(spinResultDTO, hasProperty("pocket"));
    }

    @Test
    void testWithoutForce_multiplayer() throws Exception {
        String url = "/v1/roulette/spin";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                //Get the request json
                .content(jsonFromFile(FILE_NAME_MULTIPLAYER_MULTI))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
        SpinResultDTO spinResultDTO = Util.deserialize(SpinResultDTO.class,mvcResult.getResponse().getContentAsString());
        assertThat(spinResultDTO, hasProperty("pocket"));
    }

    private String jsonFromFile(String fileName) throws IOException {
        return Util.readJsonFromFile(fileName);
    }
}
