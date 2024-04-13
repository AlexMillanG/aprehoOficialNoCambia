package mx.edu.utex.APREHO.controllers.ReservationsControllers.Dto;

import jakarta.persistence.Column;
import lombok.Data;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.people.People;
import mx.edu.utex.APREHO.model.reservations.ReservationsBean;
import mx.edu.utex.APREHO.model.room.Room;

import java.time.LocalDate;
@Data
public class DtoReservations {
    private Long reservationId;
    private LocalDate checkin;
    private LocalDate checkout;
    private  Double discountQuantity;
    private  Boolean discount;
    private People person;
    private Room room;
    private Hotel hotel;

    public ReservationsBean toEntity(){
        return new ReservationsBean(reservationId,checkin,checkout,discountQuantity,discount,person,room,hotel);
    }
}
