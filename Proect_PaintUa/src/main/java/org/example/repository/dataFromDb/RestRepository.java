package org.example.repository.dataFromDb;

import org.example.entity.dataFromDb.Rest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestRepository extends JpaRepository<Rest,Long> {
}
