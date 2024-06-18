package roxor.games.roulette.controller.exception;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ErrorResp {
    private String type;
    private String title;
    private int status;
    private String detail;
    private String instance;
}
