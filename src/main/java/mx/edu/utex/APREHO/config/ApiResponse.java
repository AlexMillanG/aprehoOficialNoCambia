package mx.edu.utex.APREHO.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ApiResponse {
    private Object data;
    private HttpStatus status;
    private boolean error;
    private String mensaje;

    public ApiResponse(Object data, HttpStatus status) {
        this.data = data;
        this.status = status;
        this.mensaje = mensaje;
    }

    public ApiResponse(Object data, boolean error, String mensaje) {
        this.data = data;
        this.error = error;
        this.mensaje = mensaje;
    }
}