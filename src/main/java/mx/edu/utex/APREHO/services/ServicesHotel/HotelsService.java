package mx.edu.utex.APREHO.services.ServicesHotel;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.hotel.HotelRepository;
import mx.edu.utex.APREHO.model.images.ImageRepository;
import mx.edu.utex.APREHO.model.images.Images;
import mx.edu.utex.APREHO.model.user.User;
import mx.edu.utex.APREHO.model.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class HotelsService {
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    /*
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> saveHotel(Hotel hotel) {
        Optional<Hotel> foundHotel = hotelRepository.findByEmail(hotel.getEmail());
        if (foundHotel.isPresent()) {
            //checa que el hotel sea único
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Error, ya se ha registrado un hotel con ese email"), HttpStatus.BAD_REQUEST);
        } else {
            //si es unico checa que este asociado a un usuario
            if (hotel.getUser() == null) {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Error, no se ha insertado un dueño del hotel"), HttpStatus.BAD_REQUEST);
            } else {
                for (User user : hotel.getUser()) {
                    Optional<User> foundUser = userRepository.findById(user.getUserId());
                    if (!foundUser.isPresent()) {
                        return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Error, no se ha encontrado el usuario asociado"), HttpStatus.BAD_REQUEST);
                    }
                }
                hotelRepository.saveAndFlush(hotel);
                for (User user : hotel.getUser()) {
                    System.err.println(user.getUserId());
                }
                return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Guardado correctamente"), HttpStatus.OK);
            }
        }
    }*/


    //trae todos los registros de hotel, junto con el estado de la petición
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> getAll() {
        return new ResponseEntity<>(new ApiResponse(hotelRepository.findAll(), HttpStatus.OK), HttpStatus.OK);
    }

    //muestra los hoteles por ciudad
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse>getByCity(String city){

        List<Hotel> foundCity = hotelRepository.findByCity(city);
        //revisa si encontró algo el metodo de arriba
        if (!foundCity.isEmpty())
            return new ResponseEntity<>(new ApiResponse(foundCity,HttpStatus.OK,false,"ciudad encontrada"),HttpStatus.OK);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true,"Error, ciudad no encontrada"),HttpStatus.BAD_REQUEST);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> updateHotel(Hotel hotel) {
        Optional<Hotel> foundHotel = hotelRepository.findByEmail(hotel.getEmail());
        if (!foundHotel.isPresent()) {
            //checa que el hotel sea único
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Error, hotel no encontrado"), HttpStatus.BAD_REQUEST);
        } else {
            //si es unico checa que este asociado a un usuario
            if (hotel.getUser() == null) {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Error, no se ha insertado un dueño del hotel"), HttpStatus.BAD_REQUEST);
            } else {
                for (User user : hotel.getUser()) {
                    Optional<User> foundUser = userRepository.findById(user.getUserId());
                    if (!foundUser.isPresent()) {
                        return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Error, no se ha encontrado el usuario asociado"), HttpStatus.BAD_REQUEST);
                    }
                }
                hotelRepository.saveAndFlush(hotel);
                for (User user : hotel.getUser()) {
                    System.err.println(user.getUserId());
                }
                return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Actualizado correctamente"), HttpStatus.OK);
            }
        }
    }
    //elimina un registro de hotel
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse>deleteHotel(Long id){
        Optional<Hotel> foundHotel = hotelRepository.findById(id);
        //valida que el hotel que se quiere eliminar exista
        if (foundHotel.isEmpty())
        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,true,"No se encontró el hotel"),HttpStatus.NOT_FOUND);
        hotelRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,false,"Hotel eliminado correctamente"),HttpStatus.OK);
    }
    //busca un hotel que coincida por id
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> findOneHotel(Long id){
        Optional<Hotel> foundHotel = hotelRepository.findById(id);
        //valida que el hotel exista
        if (foundHotel.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,true,"No se encontró el hotel"),HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(new ApiResponse(foundHotel.get(),HttpStatus.OK),HttpStatus.OK);
    }


    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> saveWithImage(Set<MultipartFile> files, String hotelName, String address, String email, String phone, String city, Long userId, String description) throws IOException {
        // Verificar si ya existe un hotel con el mismo correo electrónico
        Optional<Hotel> foundHotel = hotelRepository.findByEmail(email);
        if (foundHotel.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Error, ya se ha registrado un hotel con ese email"), HttpStatus.BAD_REQUEST);
        }

        // Verificar si se ha asignado un usuario al hotel y si este usuario existe en la base de datos
        Optional<User> foundUser = userRepository.findById(userId);
        if (!foundUser.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Error, no se ha encontrado el usuario asociado"), HttpStatus.BAD_REQUEST);
        }

        //declara una lista de imágenes llamada "images"
        Set<Images> images = new HashSet<>();

        //itera objetos MultipartFile
        //variable de interación "file", va a iterar la lista "files" que recibe el método
        for (MultipartFile file : files) {
            //hace un arreglo de bytes lamado imageData y le setea los variables del objeto
            //que esta iterando (variable de iteración), mediante el método getBytes
            byte[] imageData = file.getBytes();
            //instancia de imágen
            Images image = new Images();
            //le setea los datos obtenidos de image data
            image.setImage(imageData);
            //lo guarda en la base de datos
            imageRepository.saveAndFlush(image);
            //agrega la imagen a una lista
            images.add(image);
        }
        //instancia de usuario
        User user = new User();
        //le setea un id
        user.setUserId(userId);
        //debido a que la relación de usuario con Hotel es muchos a muchos
        //se crea una lista de usuarios
        Set<User> users = new HashSet<>();
        //y a esta lista de usuarios se le agrega el usuario creado previamente
        users.add(user);

        //instancia de hotel
        Hotel hotel = new Hotel();
        //se le setean los datos
        hotel.setHotelName(hotelName);
        hotel.setAddress(address);
        hotel.setEmail(email);
        hotel.setPhone(phone);
        hotel.setCity(city);
        //se setea el usuario (dueño del hotel)
        hotel.setUser(users);
        hotel.setDescription(description);
        hotel.setImages(images); // Relaciona las imágenes

        //se guarda en la base de datos, por medio del repositorio la entidad hotel
        hotelRepository.save(hotel);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Guardado correctamente"), HttpStatus.OK);
    }



    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> updateWithImage(Long id,Set<MultipartFile> files, String hotelName, String address, String email, String phone, String city, Long userId, String description, List<Long> imagesId) throws IOException{

        // Verificar si ya existe un hotel con el mismo correo electrónico
        Optional<Hotel> foundHotel = hotelRepository.findByEmail(email);


        // Verificar si se ha asignado un usuario al hotel y si este usuario existe en la base de datos
        Optional<User> foundUser = userRepository.findById(userId);
        if (!foundUser.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Error, no se ha encontrado el usuario asociado"), HttpStatus.BAD_REQUEST);
        }


        Set<Images> images = new HashSet<>();

        // Obtener un iterador para la lista de IDs de imágenes
        Iterator<Long> idIterator = imagesId.iterator();

        // Iterar sobre los archivos y asignar los IDs correspondientes
        for (MultipartFile file : files) {
            // Verificar si aún quedan IDs disponibles
            if (!idIterator.hasNext()) {
                // Manejar el caso en el que no hay suficientes IDs
                throw new IllegalArgumentException("No hay suficientes IDs de imágenes");
            }

            // Obtener el próximo ID de imagen disponible
            Long imageId = idIterator.next();

            // Obtener los datos del archivo
            byte[] imageData = file.getBytes();

            // Crear una instancia de Images y asignar el ID y los datos de la imagen
            Images image = new Images();
            image.setImagesId(imageId); // Asignar el ID de la imagen
            image.setImage(imageData);

            // Guardar la imagen en la base de datos
            imageRepository.saveAndFlush(image);

            // Agregar la imagen al conjunto de imágenes
            images.add(image);
        }


        User user = new User();
        user.setUserId(userId);
        Set<User> users = new HashSet<>();
        users.add(user);

        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelName);
        hotel.setAddress(address);
        hotel.setEmail(email);
        hotel.setPhone(phone);
        hotel.setCity(city);
        hotel.setUser(users);
        hotel.setDescription(description);
        hotel.setImages(images); // Relaciona las imágenes

        hotelRepository.save(hotel);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Guardado correctamente"), HttpStatus.OK);
    }

    //encontrar hoteles por usuario
    @Transactional(rollbackFor = {SQLException.class})
    //se espera un id
    public ResponseEntity<ApiResponse> findHotelsByUser(Long id){
        //se crea un objeto usuario
        User user = new User();
        //a este objeto se le setea la id obtenida
        user.setUserId(id);
        //crea una lista de hoteles que coincida con el usuario
    List<Hotel> foundUsersHotels = hotelRepository.findByUser(user);
    //valida que encuentre registros
    if (foundUsersHotels.isEmpty())
        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,true,"No se encontraron hoteles relacionados a este usuario"),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ApiResponse(foundUsersHotels,HttpStatus.OK),HttpStatus.OK);
    }


    @Transactional(rollbackFor = {SQLException.class})
    //la neta esto es chat
    public ResponseEntity<ApiResponse> getCities() {
        // Obtener todos los hoteles
        List<Hotel> hotels = hotelRepository.findAll();

        // Extraer las ciudades de los hoteles y eliminar duplicados utilizando Stream API
        List<String> cities = hotels.stream()
                .map(Hotel::getCity) // Obtener la ciudad de cada hotel
                .distinct() // Eliminar ciudades duplicadas
                .collect(Collectors.toList()); // Recolectar en una lista

        // Verificar si se encontraron ciudades
        if (!cities.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(cities, HttpStatus.OK), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "No se encontraron ciudades"), HttpStatus.NOT_FOUND);
        }
    }



}
