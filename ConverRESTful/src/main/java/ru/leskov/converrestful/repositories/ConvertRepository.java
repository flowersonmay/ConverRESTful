package ru.leskov.converrestful.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.leskov.converrestful.models.ConvertInformation;

@Repository
public interface ConvertRepository extends JpaRepository<ConvertInformation, Long> {
}
