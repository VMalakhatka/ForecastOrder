package org.example.repository.dataFromDb;

import org.example.entity.dataFromDb.GoodsMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsMoveRepository extends JpaRepository<GoodsMove,Long> {
}
