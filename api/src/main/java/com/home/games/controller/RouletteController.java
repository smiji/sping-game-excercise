package com.home.games.controller;

import com.home.games.model.request.SpinDTO;
import com.home.games.model.response.SpinResultDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.home.games.service.RouletteService;
import com.home.games.controller.exception.BadBetException;


@RestController
@RequestMapping("/v1/roulette")
@AllArgsConstructor
public class RouletteController {

    private final Logger logger = LoggerFactory.getLogger(RouletteController.class);

    private final RouletteService rouletteService;

    @PostMapping("/spin")
    @ResponseStatus(HttpStatus.OK)
    public SpinResultDTO spin(@Valid @RequestBody SpinDTO spinDTO) {
        logger.info("SpinDTO: {}", spinDTO);

        SpinResultDTO spinResultDTO = null;
        try {
            spinResultDTO = rouletteService.spin(spinDTO);
        } catch (Exception e) {
            throw new BadBetException(e.getMessage());
        }

        logger.info("SpinResultDTO: {} ", spinResultDTO);

        return spinResultDTO;
    }
}
