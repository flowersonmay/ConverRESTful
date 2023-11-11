package ru.leskov.converrestful.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.leskov.converrestful.convert.ValueConverter;
import ru.leskov.converrestful.models.ConvertInformation;
import ru.leskov.converrestful.services.HistoryConvertService;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/convert")
public class ConvertController {

    private final HistoryConvertService historyConvertService;
    private final ValueConverter converter;


    @GetMapping()
    public ConvertResponse toConvert(
            @RequestParam("type") ConvertType type,
            @RequestParam("value") String value,
            Authentication authentication) {
        String result;
        if (type == ConvertType.StringToNumber) {
            result = String.valueOf(converter.stringToNumber(value));
        } else if (type == ConvertType.NumberToString) {
            result = converter.numberToString(Long.parseLong(value));
        } else {
            throw new RuntimeException("Wrong type");
        }
        String userName = authentication.getName();
        ConvertInformation convert = ConvertInformation.builder()
                .username(userName)
                .input(value)
                .output(result)
                .createdAt(new Date()).build();
        historyConvertService.save(convert);

        return new ConvertResponse(result);
    }
}
