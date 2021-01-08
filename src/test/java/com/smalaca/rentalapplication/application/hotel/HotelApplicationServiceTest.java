package com.smalaca.rentalapplication.application.hotel;

import com.smalaca.rentalapplication.domain.hotel.Hotel;
import com.smalaca.rentalapplication.domain.hotel.HotelRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static com.smalaca.rentalapplication.domain.hotel.HotelAssertion.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class HotelApplicationServiceTest {
    private static final String NAME = "Great hotel";
    private static final String STREET = "Unknown";
    private static final String POSTAL_CODE = "12-345";
    private static final String BUILDING_NUMBER = "13";
    private static final String CITY = "Somewhere";
    private static final String COUNTRY = "Nowhere";

    private final HotelRepository repository = mock(HotelRepository.class);
    private final HotelApplicationService service = new HotelApplicationService(repository);

    @Test
    void shouldCreateHotel() {
        ArgumentCaptor<Hotel> captor = ArgumentCaptor.forClass(Hotel.class);
        HotelDto hotelDto = new HotelDto(NAME, STREET, POSTAL_CODE, BUILDING_NUMBER, CITY, COUNTRY);

        service.add(hotelDto);

        then(repository).should().save(captor.capture());
        assertThat(captor.getValue())
                .hasNameEqualsTo(NAME)
                .hasAddressEqualsTo(STREET, POSTAL_CODE, BUILDING_NUMBER, CITY, COUNTRY);
    }
}