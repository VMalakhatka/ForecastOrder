package org.example.repository.data_from_db;

import org.example.entity.data_from_db.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods,Long> {
}
