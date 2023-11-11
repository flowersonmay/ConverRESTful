package ru.leskov.ConverRESTful.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.leskov.ConverRESTful.convert.ValueConverter;
import ru.leskov.ConverRESTful.models.ConvertInformation;
import ru.leskov.ConverRESTful.services.ConvertService;

import java.util.Date;

@RestController
@RequestMapping("/convert")
public class ConvertController {

    private final ConvertService convertService;

    @Autowired
    public ConvertController(ConvertService convertService) {
        this.convertService = convertService;
    }

    @GetMapping()
    public String toConvert(
            @RequestParam("type") String type,
            @RequestParam("value") String value) {
        String convertor = new ValueConverter(value, type).toConvert();
        if (convertor.split(" ")[0].equals("WRONG")) {
            String[] request = convertor.split(" ");
            StringBuilder message = new StringBuilder();
            for (int i = 1; i < request.length; i++) {
                message.append(request[i]);
            }
            return HttpStatus.BAD_REQUEST.getReasonPhrase() + " " + message;
        }

//        ConvertInformation convert = new ConvertInformation();
//        convert.setInput(value);
//        convert.setOutput(convertor);
//        convert.setCreatedAt(new Date());
        ConvertInformation convert = ConvertInformation.builder()
                .input(value)
                .output(convertor)
                .createdAt(new Date()).build();
        convertService.save(convert);

        return convert.getOutput() + " status: " + HttpStatus.OK.value();
    }
}
