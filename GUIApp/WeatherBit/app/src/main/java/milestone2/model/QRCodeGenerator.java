package milestone2.model;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.BarcodeFormat;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.FileInputStream;
import java.util.Base64;


public class QRCodeGenerator {


    public String generateQRCode(String weatherReport) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        String contents = weatherReport;
        String fileName = "./src/main/resources/qrcode.png";
        int width = 200;
        int height = 200;

        FileInputStream ImageInput = null;
        Base64.Encoder encoder = Base64.getEncoder();



        BitMatrix bitMatrix = qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, width, height);

        Path filePath = Paths.get(fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filePath);

        ImageInput = new FileInputStream(fileName);

        int availableImage = ImageInput.available();
        byte[] bytes = new byte[availableImage];
        ImageInput.read(bytes);

        String base64Str = encoder.encodeToString(bytes);
        return base64Str;
    }
}
