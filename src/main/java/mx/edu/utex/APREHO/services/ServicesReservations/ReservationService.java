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

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ReservationService {
    private final ReservationsRepository repository;
    private final PeopleRepository peopleRepository;
    private final RoomRepository roomRepository;


    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> saveReservations(ReservationsBean reservations){

        // Verifica que la persona que esté reservando exista
        Optional<People> foundPerson = peopleRepository.findById(reservations.getPeople().getPeopleId());
        if (foundPerson.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,true,"No se encontró la persona asociada"), HttpStatus.NOT_FOUND);

        // Verifica que la habitación que esté reservando exista
        Optional<Room> foundRoom = roomRepository.findById(reservations.getRoom().getRoomId());
        if (foundRoom.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,true,"No se encontró la habitación asociada"), HttpStatus.NOT_FOUND);

        // Verifica que no se puedan hacer reservaciones para antes de hoy
        if (!reservations.validateDate(reservations.getCheckin()))
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true,"Error: no puedes hacer una reservación para antes de hoy"), HttpStatus.BAD_REQUEST);

        // Verifica que la fecha de salida sea de al menos un día
        if (reservations.getCheckout().getDayOfMonth() <= reservations.getCheckin().getDayOfMonth())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true,"Error: la fecha de fin tiene que ser mayor a un día"), HttpStatus.BAD_REQUEST);

        // Busca reservaciones existentes para la misma habitación
        List<ReservationsBean> foundReservation = repository.findByRoom_RoomId(reservations.getRoom().getRoomId());

        // Verifica si hay reservaciones existentes para la misma habitación
        if (!foundReservation.isEmpty()) {
            // Itera sobre las reservaciones encontradas y verifica si se enciman
            for (ReservationsBean existingReservation : foundReservation) {
                LocalDate existingStart = existingReservation.getCheckin();
                LocalDate existingEnd = existingReservation.getCheckout();

                // Verifica si se enciman las fechas entre las fechas
                if (!(existingEnd.isBefore(reservations.getCheckin()) || existingStart.isAfter(reservations.getCheckout()))) {
                    // Si se enciman las fecha, responde un mensaje de advertencia
                    return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true,"Error: la nueva reserva se encima con una reservacion del "+ existingStart +" al " + existingEnd+" prueba con otra fecha"), HttpStatus.BAD_REQUEST);
                }
            }
        }

        return new ResponseEntity<>(new ApiResponse(repository.save(reservations), HttpStatus.OK, false,"Reservación guardada con éxito"), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> deleteReservation(Long id){
        Optional<ReservationsBean> foundReservations = repository.deleteByReservationId(id);

        if (foundReservations.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,true,"no se encontró la reservación"), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(new ApiResponse(repository.deleteByReservationId(id),false,"eliminado correctamente"),HttpStatus.OK);
    }



}
