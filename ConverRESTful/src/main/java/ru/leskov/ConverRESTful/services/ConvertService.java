package ru.leskov.ConverRESTful.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.leskov.ConverRESTful.models.ConvertInformation;
import ru.leskov.ConverRESTful.repositories.ConvertRepository;

@Service
public class ConvertService {
    private final ConvertRepository convertRepository;

    @Autowired
    public ConvertService(ConvertRepository convertRepository) {
        this.convertRepository = convertRepository;
    }

    @Transactional
    public void save(ConvertInformation convertInformation) {
        convertRepository.save(convertInformation);

    }
}
