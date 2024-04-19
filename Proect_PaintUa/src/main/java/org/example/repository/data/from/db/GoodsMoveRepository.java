package org.example.repository.data.from.db;

import org.example.entity.data.from.db.GoodsMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsMoveRepository extends JpaRepository<GoodsMove,Long> {
}
