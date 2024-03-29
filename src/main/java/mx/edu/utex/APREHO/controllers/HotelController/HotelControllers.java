package mx.edu.utex.APREHO.controllers.HotelController;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.controllers.HotelController.Dto.DtoHotel;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.images.Images;
import mx.edu.utex.APREHO.model.user.User;
import mx.edu.utex.APREHO.services.ServicesHotel.HotelsService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/api/hotel")
@CrossOrigin({"*"})
@AllArgsConstructor
public class HotelControllers {
    private final HotelsService service;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse>save(@RequestBody DtoHotel hotel){
        System.out.println(hotel.getCity());
        String city = hotel.getCity();
        // Verificar si la ciudad es nula o está vacía
        if (city == null || city.isEmpty()) {
            return  new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "La ciudad no puede estar vacía"),HttpStatus.BAD_REQUEST);
        }
        // Verificar si la ciudad tiene espacios al principio o al final
        if (!city.equals(city.trim())) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "La ciudad no puede tener espacios al principio ni al final"),HttpStatus.BAD_REQUEST);
        }
        return  service.saveHotel(hotel.toEntity());
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAll(){
        return service.getAll();
    }
    @GetMapping("/{city}")
    public ResponseEntity<ApiResponse>getByCity(@PathVariable String city){
        return service.getByCity(city);
    }

    @GetMapping("/getByCity")
    public ResponseEntity<ApiResponse>getByCityBody(@RequestBody DtoHotel hotel){
        System.err.println("el drip"+hotel.getCity());
        return service.getByCity(hotel.getCity());
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse>update(@RequestBody DtoHotel hotel){
        return  service.updateHotel(hotel.toEntityUpdate());
    }
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<ApiResponse>delete(@PathVariable String email){
        return service.deleteHotel(email);
    }

    @GetMapping("/findOne/{id}")
    public ResponseEntity<ApiResponse> findOne(@PathVariable Long id) {
        return service.findOneHotel(id);
    }


    @PostMapping("/saveHotelWithImages") // Cambio en el nombre del endpoint para reflejar la opción de múltiples imágenes
    public ResponseEntity<ApiResponse> saveWithImages(@RequestParam("images") Set<MultipartFile> files,
                                                      @RequestParam("hotelName") String hotelName,
                                                      @RequestParam("email") String email,
                                                      @RequestParam("address") String address,
                                                      @RequestParam("phone") String phone,
                                                      @RequestParam("city") String city,
                                                      @RequestParam("userId") Long userId,
                                                      @RequestParam("description") String description) throws IOException {
        if (files.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se han subido imágenes"), HttpStatus.BAD_REQUEST);
        if (files.size()>3)
        return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se pueden subir más de 3 imagenes"), HttpStatus.BAD_REQUEST);
        return service.saveWithImage(files, hotelName, address, email, phone, city, userId, description);
    }



    @GetMapping("/findByUser")
    public ResponseEntity<ApiResponse> findByUser(@RequestBody User user){
        return service.findHotelsByUser(user);

    }

    // no
    @GetMapping("/imagen/{id}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable Long id) {
        ResponseEntity<ApiResponse> responseEntity = service.getHotelAndImage();
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            ApiResponse apiResponse = responseEntity.getBody();
            Set<Hotel> hoteles = (Set<Hotel>) apiResponse.getData();
            for (Hotel hotel : hoteles) {
                for (Images imagen : hotel.getImages()) {
                    if (imagen.getImagesId().equals(id)) {
                        return ResponseEntity.ok()
                                .contentType(MediaType.IMAGE_JPEG)
                                .body(new ByteArrayResource(imagen.getImage()));
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/getCities")
    public  ResponseEntity<ApiResponse> getCities(){
        return service.getCities();
    }



}
