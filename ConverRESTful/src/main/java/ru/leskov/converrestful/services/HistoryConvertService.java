package ru.leskov.converrestful.services;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.leskov.converrestful.models.ConvertInformation;
import ru.leskov.converrestful.repositories.ConvertRepository;

@Service
@RequiredArgsConstructor
public class HistoryConvertService {

    private final ConvertRepository convertRepository;

    @Transactional
    public void save(ConvertInformation convertInformation) {
        convertRepository.save(convertInformation);

    }
}
