package mx.edu.utex.APREHO.controllers.RoomTypeControllers;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.controllers.RoomTypeControllers.Dto.DtoRoomType;
import mx.edu.utex.APREHO.model.roomTypeBean.RoomType;
import mx.edu.utex.APREHO.services.ServicesRoomType.RoomTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin({"*"})
@RequestMapping("/api/roomType")
public class RoomTypeControllers {
    private final RoomTypeService service;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getRoomType(@PathVariable Long id){
        return service.getRoomTypeByHotel(id);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveRoomType(@RequestBody DtoRoomType dtoRoomType){

        return service.saveRoomType(dtoRoomType.toEntity());
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateRoomType(@RequestBody DtoRoomType dtoRoomType){
        return service.updateRoomType(dtoRoomType.toEntityUpdate());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteRoomType(@PathVariable Long id){
        return service.deleteRoomType(id);
    }
}
