package ru.leskov.ConverRESTful.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.leskov.ConverRESTful.models.ConvertInformation;

@Repository
public interface ConvertRepository extends JpaRepository<ConvertInformation, Long> {
}
