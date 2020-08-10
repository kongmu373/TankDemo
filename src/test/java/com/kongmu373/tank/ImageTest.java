package com.kongmu373.tank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageTest {

    @Test
    public void testLoadImage() throws IOException {
        BufferedImage read = ImageIO.read(new File("src\\main\\resources\\images\\BadTank1.png"));
        Assertions.assertNotNull(read);

        BufferedImage read2 = ImageIO.read(ImageTest.class.getClassLoader()
                                               .getResourceAsStream("images/bulletD.gif"));
        Assertions.assertNotNull(read2);
    }

    @Test
    public void testRotateImage() throws IOException {
        ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));

    }
}
