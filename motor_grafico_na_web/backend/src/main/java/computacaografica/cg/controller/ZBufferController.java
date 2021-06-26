package computacaografica.cg.controller;

import computacaografica.cg.negocio.Drawner;
import computacaografica.cg.negocio.beans.CameraVirtual;
import computacaografica.cg.negocio.beans.Iluminacao;
import computacaografica.cg.negocio.beans.Objeto;
import computacaografica.cg.negocio.exception.NegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/objetos")
public class ZBufferController {

    @GetMapping("/formas")
    public ResponseEntity<?> listarObjetos() {
        List<String> objetos = new ArrayList<>();
        File[] files = new File("Formas").listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                if (fileName.indexOf(".") > 0)
                    fileName = fileName.substring(0, fileName.lastIndexOf("."));
                objetos.add(fileName);
            }
        }
        return new ResponseEntity<>(objetos, HttpStatus.OK);
    }

    @GetMapping("/cameras")
    public ResponseEntity<?> listarCameras() {
        List<String> objetos = new ArrayList<>();
        File[] files = new File("CameraVirtual").listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                if (fileName.indexOf(".") > 0)
                    fileName = fileName.substring(0, fileName.lastIndexOf("."));
                objetos.add(fileName);
            }
        }
        return new ResponseEntity<>(objetos, HttpStatus.OK);
    }

    @GetMapping("/{objeto}/{camera}")
    public ResponseEntity<?> renderizar(@PathVariable("objeto") String fileObjeto,
                                        @PathVariable("camera") String fileCamera,
                                        @RequestParam int width,
                                        @RequestParam int height)
            throws IOException, NegocioException {
        boolean check = new File("Formas", fileObjeto+".byu").exists();
        if(check == true) {
            Objeto objeto = new Objeto(fileObjeto);
            CameraVirtual camera = new CameraVirtual(fileCamera);
            Iluminacao iluminacao = new Iluminacao("iluminacao");
            return new ResponseEntity<>(Drawner.renderizar(objeto, camera, iluminacao, width, height), HttpStatus.OK);
        }
        return new ResponseEntity<>("Objeto não encontrado", HttpStatus.NOT_FOUND);
    }

}


