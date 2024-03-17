package mx.edu.utex.APREHO.services.ServicesReservations;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.people.People;
import mx.edu.utex.APREHO.model.people.PeopleRepository;
import mx.edu.utex.APREHO.model.reservations.ReservationsBean;
import mx.edu.utex.APREHO.model.reservations.ReservationsRepository;
import mx.edu.utex.APREHO.model.room.Room;
import mx.edu.utex.APREHO.model.room.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ReservationService {
    private final ReservationsRepository repository;
    private final PeopleRepository peopleRepository;
    private final RoomRepository roomRepository;

    public ResponseEntity<ApiResponse> saveReservations(ReservationsBean reservations){
        Optional<People> foundPerson = peopleRepository.findById(reservations.getPeople().getPeopleId());
        if (foundPerson.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,true,"no se encontró la persona asociada"), HttpStatus.NOT_FOUND);
        Optional<Room> foundRoom = roomRepository.findById(reservations.getRoom().getRoomId());
        if (foundRoom.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,true,"no se encontró la habitación asociada"), HttpStatus.NOT_FOUND);

        if (!reservations.validateDate(reservations.getCheckin()))
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true,"error no puedes hacer una reservación para antes de hoy"), HttpStatus.BAD_REQUEST);

        if (reservations.getCheckout().getDayOfMonth()<=reservations.getCheckin().getDayOfMonth())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true,"error, la fecha de fin tiene que ser mayor a un día"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new ApiResponse(repository.save(reservations),HttpStatus.OK,false,"Reservación guardada con exito"),HttpStatus.OK);
    }


}
