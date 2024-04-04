package org.example.repository.data_from_db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class GoodsRepositoryTest {

    @Autowired
    GoodsRepository goodsRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        goodsRepository.deleteAll();
    }



}