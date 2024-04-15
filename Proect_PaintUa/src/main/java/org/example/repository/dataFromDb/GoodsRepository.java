package org.example.repository.dataFromDb;

import org.example.entity.dataFromDb.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods,Long> {
}
