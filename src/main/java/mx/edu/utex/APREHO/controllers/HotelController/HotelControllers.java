package mx.edu.utex.APREHO.controllers.HotelController;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.controllers.HotelController.Dto.DtoHotel;
import mx.edu.utex.APREHO.controllers.UserControllers.Dto.DtoUser;
import mx.edu.utex.APREHO.model.user.User;
import mx.edu.utex.APREHO.services.ServicesHotel.HotelsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/hotel")
@CrossOrigin({"*"})
@AllArgsConstructor
public class HotelControllers {
    private final HotelsService service;
//obtiene todos lo registros de hotel
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAll(){
        return service.getAll();
    }

//obtiene todos lo registros de hotel filtrados por ciudad SE OBTIENE POR URL

    @GetMapping("/{city}")
    public ResponseEntity<ApiResponse>getByCity(@PathVariable String city){
        return service.getByCity(city);
    }
//obtiene todos lo registros de hotel filtrados por ciudad SE OBTIENE POR BODY
    @GetMapping("/getByCity")
    public ResponseEntity<ApiResponse>getByCityBody(@RequestBody DtoHotel hotel){
        return service.getByCity(hotel.getCity());
    }

    //elimina un registro por id se obtiene por url
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse>delete(@PathVariable Long id){
        return service.deleteHotel(id);

    }
    //elimina un registro por id se obtiene por body
    @DeleteMapping("/deleteBody")
    public ResponseEntity<ApiResponse>deleteBody(@RequestBody DtoHotel dtoHotel){
        return service.deleteHotel(dtoHotel.getHotelId());
    }

    //encuentra un registro por id se obtiene por url

    @GetMapping("/findOne/{id}")
    public ResponseEntity<ApiResponse> findOne(@PathVariable Long id) {
        return service.findOneHotel(id);
    }
    //encuentra un registro por id se obtiene por body
    @GetMapping("/findOneBody")
    public ResponseEntity<ApiResponse> findOneBody(@RequestBody DtoHotel dtoHotel){
        return service.findOneHotel(dtoHotel.getHotelId());
    }

    //guarda un hotel, obtiene los datos por formularios
    @PostMapping("/saveHotelWithImages")
    public ResponseEntity<ApiResponse> saveWithImages(@RequestParam("images") Set<MultipartFile> files, //lista de imágenes
                                                      @RequestParam("hotelName") String hotelName,
                                                      @RequestParam("email") String email,
                                                      @RequestParam("address") String address,
                                                      @RequestParam("phone") String phone,
                                                      @RequestParam("city") String city,
                                                      @RequestParam("userId") Long userId,
                                                      @RequestParam("description") String description) throws IOException {
        //valida que se envíen imágenes
        if (files.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se han subido imágenes"), HttpStatus.BAD_REQUEST);
        //valida que no sean más de 3 imágenes
        if (files.size()>3)
        return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se pueden subir más de 3 imagenes"), HttpStatus.BAD_REQUEST);
        //manda los datos al método
        return service.saveWithImage(files, hotelName, address, email, phone, city, userId, description);
    }


    @PostMapping("/updateHotelWithImages")
    public ResponseEntity<ApiResponse> updateWithImages(
            @RequestParam("id") Long id,
            @RequestParam("images") Set<MultipartFile> files,
            @RequestParam("hotelName") String hotelName,
            @RequestParam("email") String email,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone,
            @RequestParam("city") String city,
            @RequestParam("userId") Long userId,
            @RequestParam("description") String description,
            @RequestParam("imagesId") List<Long> imagesId) throws IOException {
        if (files.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se han subido imágenes"), HttpStatus.BAD_REQUEST);
        if (files.size()>3)
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se pueden subir más de 3 imágenes"), HttpStatus.BAD_REQUEST);
        return service.updateWithImage(id, files, hotelName, address, email, phone, city, userId, description, imagesId);
    }







    @GetMapping("/findByUserBody")
    public ResponseEntity<ApiResponse> findByUserBody(@RequestBody DtoUser dtoUser){
        Long id = dtoUser.getUserId();
        return service.findHotelsByUser(id);
    }

    @GetMapping("/findByUser/{id}")
    public ResponseEntity<ApiResponse> findByUser(@PathVariable Long id){
        return service.findHotelsByUser(id);
    }


    @GetMapping("/getCities")
    public  ResponseEntity<ApiResponse> getCities(){
        return service.getCities();
    }



}
