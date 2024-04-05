package mx.edu.utex.APREHO.controllers.RoomControllers;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.controllers.RoomControllers.Dto.DtoRoom;
import mx.edu.utex.APREHO.model.room.Room;
import mx.edu.utex.APREHO.services.ServicesRoom.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/api/room")
@CrossOrigin({"*"})
@AllArgsConstructor
public class RoomControllers {
    private final RoomService service;
    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody DtoRoom room){
        return service.saveRoom(room.toEntity());
    }



    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody DtoRoom room){
        return service.updateRoom(room.toEntityUpdate());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        System.err.println(id);
        return service.deleteRoom(id);
    }

    @GetMapping("/getByHotel/{id}")
    public ResponseEntity<ApiResponse> getByHotel(@PathVariable Long id){
        return  service.getByHotel(id);
    }
    //el get all es solo pruebas

    @GetMapping("/getByHotel/")
    public ResponseEntity<ApiResponse> getByHotelBody(@RequestBody DtoRoom dtoRoom){
        return  service.getByHotel(dtoRoom.getHotel().getHotelId());
    }
    //el get all es solo pruebas

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAll(){
        return service.getAll();
    }

    @GetMapping("/findOneRoom/{id}")
    public ResponseEntity<ApiResponse> findOneRoom(@PathVariable Long  id){
        return service.findOneRoom(id);
    }

    @GetMapping("findOneRoomBody/")
    public ResponseEntity<ApiResponse> findOneRoomBody(@RequestBody DtoRoom room){
        System.err.println(room.getRoomId());
        return service.findOneRoom(room.getRoomId());
    }

    @PostMapping("/saveWithImage")
    public ResponseEntity<ApiResponse> saveWithImage(@RequestParam("images") Set<MultipartFile> files,
                                                     @RequestParam("roomName") String roomName,
                                                     @RequestParam("status") String status,
                                                     @RequestParam("peopleQuantity") int peopleQuantity,
                                                     @RequestParam("description") String description,
                                                     @RequestParam("hotelId") Long hotelId,
                                                     @RequestParam("roomTypeId") Long roomTypeId) throws IOException {
        if (files.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se han subido imágenes"), HttpStatus.BAD_REQUEST);
        if (files.size()>3)
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se pueden subir más de 3 imagenes"), HttpStatus.BAD_REQUEST);
        return service.saveWithImage(files,roomName,status,peopleQuantity,description,hotelId,roomTypeId);
    }

    @GetMapping("/getByType")
    public ResponseEntity<ApiResponse> getByType(@RequestBody DtoRoom dtoRoom){
        return service.findByTypeAndHotel(dtoRoom.getType().getTypeName(),dtoRoom.getHotel().getHotelId());
    }
}
