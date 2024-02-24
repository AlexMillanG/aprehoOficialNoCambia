package mx.edu.utex.APREHO.controllers.RoomTypeControllers;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.services.ServicesRoomType.RoomTypeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin({"*"})
@RequestMapping("/api/roomType")
public class RoomTypeControllers {
    private final RoomTypeService service;
}
