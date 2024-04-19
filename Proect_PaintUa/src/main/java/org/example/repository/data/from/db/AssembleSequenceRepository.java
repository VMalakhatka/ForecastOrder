package org.example.repository.data.from.db;

import org.example.entity.data.from.db.Assemble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssembleSequenceRepository extends JpaRepository<Assemble, Long> {

    @Query(value = "SELECT nextval('assemble_sequence')", nativeQuery = true)
    Long getNextSequenceValue();

    @Query(value = "SELECT nextval('assemble_sequence') FROM generate_series(1, :count)", nativeQuery = true)
    List<Long> getNextSequenceValues(@Param("count") int count);

}
