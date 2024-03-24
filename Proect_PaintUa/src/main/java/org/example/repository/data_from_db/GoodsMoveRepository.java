package org.example.repository.data_from_db;

import org.example.entity.data_from_db.GoodsMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsMoveRepository extends JpaRepository<GoodsMove,Long> {
}
