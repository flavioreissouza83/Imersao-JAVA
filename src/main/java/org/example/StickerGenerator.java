package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class StickerGenerator {

    public void creator(InputStream inputStream, String nameFile, String text, String stars) throws Exception {

        // leitura da imagem
        BufferedImage originalImage = ImageIO.read(inputStream);

        // criar novo diretório "output"
        File outputDir = new File("stickers");
        outputDir.mkdir();

        // criar nova imagem em memória com transparência e com tamanho novo
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        int newHeight = height + 200;
        BufferedImage newImage = new BufferedImage(width, newHeight, BufferedImage.TRANSLUCENT);

        // copiar a imagem original pra novo imagem (em memória)
        Graphics2D graphics2D = (Graphics2D) newImage.getGraphics();
        graphics2D.drawImage(originalImage, 0, 0, null);

        // configurar a fonte
        var font = new Font("IMPACT", Font.BOLD, 100);
        graphics2D.setFont(font);
        graphics2D.setColor(Color.BLACK);

        // centralizar texto
        FontMetrics fontMetrics = graphics2D.getFontMetrics(font);
        int textWidth = fontMetrics.stringWidth(text);
        int x = (width - textWidth) / 2;
        int y = newHeight - 100;

        // desenhar texto com outline
        graphics2D.setStroke(new BasicStroke(4));
        graphics2D.drawString(text, x, y);
        graphics2D.setColor(Color.YELLOW);
        graphics2D.drawString(text, x + 2, y + 2);

        // escrever a nova imagem em um arquivo
        ImageIO.write(newImage, "png", new File(outputDir, nameFile));
    }
}
