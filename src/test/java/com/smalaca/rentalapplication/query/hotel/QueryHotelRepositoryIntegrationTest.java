package com.smalaca.rentalapplication.query.hotel;

import com.smalaca.rentalapplication.domain.hotel.Hotel;
import com.smalaca.rentalapplication.domain.hotel.HotelRepository;
import com.smalaca.rentalapplication.infrastructure.persistence.jpa.hotel.SpringJpaHotelTestRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.smalaca.rentalapplication.domain.hotel.Hotel.Builder.hotel;
import static java.util.Arrays.asList;

@SpringBootTest
@Tag("IntegrationTest")
class QueryHotelRepositoryIntegrationTest {
    private static final String NAME_1 = "Great hotel";
    private static final String STREET_1 = "Florianska";
    private static final String POSTAL_CODE_1 = "12-345";
    private static final String BUILDING_NUMBER_1 = "1";
    private static final String CITY_1 = "Cracow";
    private static final String COUNTRY_1 = "Poland";
    private static final String NAME_2 = "Even greater hotel";
    private static final String STREET_2 = "Grodzka";
    private static final String POSTAL_CODE_2 = "54-321";
    private static final String BUILDING_NUMBER_2 = "13";
    private static final String CITY_2 = "Berlin";
    private static final String COUNTRY_2 = "Germany";

    @Autowired private QueryHotelRepository queryHotelRepository;
    @Autowired private HotelRepository hotelRepository;
    @Autowired private SpringJpaHotelTestRepository springJpaHotelTestRepository;
    private String hotelId1;
    private String hotelId2;

    @AfterEach
    void deleteHotels() {
        springJpaHotelTestRepository.deleteAll(asList(hotelId1, hotelId2));
    }

    @Test
    void shouldFindAllHotels() {
        Hotel hotel1 = hotel()
                .withName(NAME_1)
                .withStreet(STREET_1)
                .withPostalCode(POSTAL_CODE_1)
                .withBuildingNumber(BUILDING_NUMBER_1)
                .withCity(CITY_1)
                .withCountry(COUNTRY_1)
                .build();
        hotelId1 = hotelRepository.save(hotel1);
        Hotel hotel2 = hotel()
                .withName(NAME_2)
                .withStreet(STREET_2)
                .withPostalCode(POSTAL_CODE_2)
                .withBuildingNumber(BUILDING_NUMBER_2)
                .withCity(CITY_2)
                .withCountry(COUNTRY_2)
                .build();
        hotelId2 = hotelRepository.save(hotel2);

        Iterable<HotelReadModel> actual = queryHotelRepository.findAll();

        Assertions.assertThat(actual)
                .hasSize(2)
                .anySatisfy(hotelReadModel -> {
                    HotelReadModelAssertion.assertThat(hotelReadModel)
                            .hasIdEqualsTo(hotelId1)
                            .hasNameEqualsTo(NAME_1)
                            .hasAddressEqualsTo(STREET_1, POSTAL_CODE_1, BUILDING_NUMBER_1, CITY_1, COUNTRY_1);
                })
                .anySatisfy(hotelReadModel -> {
                    HotelReadModelAssertion.assertThat(hotelReadModel)
                            .hasIdEqualsTo(hotelId2)
                            .hasNameEqualsTo(NAME_2)
                            .hasAddressEqualsTo(STREET_2, POSTAL_CODE_2, BUILDING_NUMBER_2, CITY_2, COUNTRY_2);
                });
    }
}