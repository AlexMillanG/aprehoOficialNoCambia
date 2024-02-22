package mx.edu.utex.APREHO.services.ServicesRoomType;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.roomTypeBean.RoomType;
import mx.edu.utex.APREHO.model.roomTypeBean.RoomTypeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class ServiciosRoomType {
    private RoomTypeRepository roomTypeRepository;

    public ResponseEntity<ApiResponse> saveRoomType(RoomType roomType){
        return null;
    }
}
