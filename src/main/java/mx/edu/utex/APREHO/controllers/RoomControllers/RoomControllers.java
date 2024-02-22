package mx.edu.utex.APREHO.controllers.RoomControllers;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.controllers.RoomControllers.Dto.DtoRoom;
import mx.edu.utex.APREHO.model.roomBean.Room;
import mx.edu.utex.APREHO.services.ServicesHotel.ServiciosHoteles;
import mx.edu.utex.APREHO.services.ServicesRoom.ServiciosRoom;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
@CrossOrigin({"*"})
@AllArgsConstructor
public class RoomControllers {
    private final ServiciosRoom service;
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
        return service.deleteRoom(id);
    }

    @GetMapping("/getByHotel/{id}")
    public ResponseEntity<ApiResponse> getByHotel(@PathVariable Long id){
        return  service.getByHotel(id);
    }
}
