package org.example.repository.data.from.db;

import org.example.entity.data.from.db.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods,Long> {
}
