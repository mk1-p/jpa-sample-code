package com.example.jpatestcode.mapping;

import com.example.jpatestcode.mapping.joined.*;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
//@Rollback(value = false)
@Slf4j
public class JoinedTest {

    private final JoinedProductRepository productRepository;
    private final JoinedGameRepository gameRepository;
    private final JoinedMusicRepository musicRepository;
    private final JoinedArtRepository artRepository;
    private final PriceRepository priceRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public JoinedTest(JoinedProductRepository productRepository, JoinedGameRepository gameRepository,
                      JoinedMusicRepository musicRepository, JoinedArtRepository artRepository,
                      PriceRepository priceRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.gameRepository = gameRepository;
        this.musicRepository = musicRepository;
        this.artRepository = artRepository;
        this.priceRepository = priceRepository;
        this.categoryRepository = categoryRepository;
    }


    @BeforeEach
    void initData() {
        // TODO Bulk Insert를 위해서는 JDBC Insert Or Query 이용해야할듯
        // Generate 방식을 Identity 로 하면 벌크 인서트 불가!
        // https://stackoverflow.com/questions/27697810/why-does-hibernate-disable-insert-batching-when-using-an-identity-identifier-gen
        log.info("[JOINED] START GAME INSERT!");

        List<JoinedGame> games = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {

            JoinedGame game = JoinedGame.builder()
                    .name("game-" + i)
                    .packageId(i)
                    .isLinux(true)
                    .isMac(false)
                    .isWindows(true)
                    .build();
            games.add(game);
        }
        gameRepository.saveAll(games);

        log.info("[JOINED] End GAME INSERT");

        log.info("[JOINED] START GAME - PRICE INSERT");

        for (JoinedGame game : games) {

            List<Price> prices = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                Price price = Price.builder()
                        .joinedProduct(game)
                        .cost(10000)
                        .build();
                prices.add(price);

            }
            priceRepository.saveAll(prices);

        }
        log.info("[JOINED] End GAME - PRICE INSERT");


        log.info("[JOINED] START ART INSERT!");

        List<JoinedArt> arts = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {

            JoinedArt art = JoinedArt.builder()
                    .name("art-" + i)
                    .packageId(i)
                    .painter("painter-"+i)
                    .build();
            arts.add(art);
        }
        artRepository.saveAll(arts);

        log.info("[JOINED] End ART INSERT");

        log.info("[JOINED] START ART - PRICE INSERT");

        for (JoinedArt art : arts) {

            List<Price> prices = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                Price price = Price.builder()
                        .joinedProduct(art)
                        .cost(10000)
                        .build();
                prices.add(price);

            }
            priceRepository.saveAll(prices);

        }

        log.info("[JOINED] End GAME - PRICE INSERT");


    }

    @Test
    @DisplayName("Product 에서 타입으로 조회가 가능한가?")
    void findProductByDType() {

    }

    @Test
    @DisplayName("Product 에서 PackageId로 여러 타입의 상품을 찾을 수 있는가?")
    void findProductByPackageId() {
        List<JoinedProduct> products = productRepository.findByPackageId(3);
        for (JoinedProduct product : products) {
            log.info(product.toString());
        }
    }

    @Test
    @DisplayName("특정 Game로 Id 를 찾을때")
    void findGameById(){
        JoinedGame joinedGame = gameRepository.findById(3L).get();
        log.info(joinedGame.toString());
    }



}
