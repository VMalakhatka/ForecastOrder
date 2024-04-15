package org.example.repository.dataFromDb;

import org.example.entity.dataFromDb.Assemble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssembleRepository extends JpaRepository<Assemble,Long> {
}
