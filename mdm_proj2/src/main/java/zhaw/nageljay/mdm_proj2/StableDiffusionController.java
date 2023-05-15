package zhaw.nageljay.mdm_proj2;

import ai.djl.Device;
import ai.djl.modality.cv.Image;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/generate")
public class StableDiffusionController {

    private StableDiffusionModel model;

    public StableDiffusionController() throws Exception {
        // Initialize the model
        this.model = new StableDiffusionModel(Device.cpu()); // Changed this line
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
public byte[] generateImage(@RequestBody PromptDTO dto) throws Exception {
    // Generate the image
    Image image = model.generateImageFromText(dto.getPrompt(), 10); // 500 is the number of steps
    // Convert the image to a byte array (in this case, a PNG image)
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
        image.save(outputStream, "png");
        return outputStream.toByteArray();
    }
}

    

}
