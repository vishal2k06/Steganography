package Steganography;

import Steganography.Encryption;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SteganographyEncoding {

    public static void encode(String inputImagePath, String outputImagePath, String combinedMessage) {
        try {
            BufferedImage image = ImageIO.read(new File(inputImagePath));
            BufferedImage encodedImage = hideMessage(image, combinedMessage);

            File outputFile = new File(outputImagePath);
            ImageIO.write(encodedImage, "png", outputFile);

            System.out.println("Message encoded and saved to: " + outputImagePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error: Could not encode the image.");
        }
    }

    private static BufferedImage hideMessage(BufferedImage image, String message) {
        int width = image.getWidth();
        int height = image.getHeight();
        String binaryMessage = toBinary(message) + "00000000";

        int messageIndex = 0;
        BufferedImage encodedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                if (messageIndex < binaryMessage.length()) {
                    b = (b & 0xFE) | (binaryMessage.charAt(messageIndex) - '0');
                    messageIndex++;
                }

                int newRgb = (r << 16) | (g << 8) | b;
                encodedImage.setRGB(x, y, newRgb);
            }
        }

        return encodedImage;
    }

    private static String toBinary(String message) {
        StringBuilder binary = new StringBuilder();
        for (char c : message.toCharArray()) {
            binary.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
        }
        return binary.toString();
    }

    public static void GetFilePath(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an Image File");

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File inputFile = fileChooser.getSelectedFile();
            String inputImagePath = inputFile.getAbsolutePath();
            String parentPath = inputFile.getParent();
            String outputImagePath = parentPath + File.separator + "output.png";

            String password = JOptionPane.showInputDialog("Enter a password to secure the message:");
            String message = JOptionPane.showInputDialog("Enter the secret message to hide:");

            if (password != null && !password.isEmpty() && message != null && !message.isEmpty()) {
                try {
                    String passwordHash = Encryption.hash(password);
                    String combinedMessage = passwordHash + "|" + message;

                    encode(inputImagePath, outputImagePath, combinedMessage);
                    JOptionPane.showMessageDialog(null, "Message encoded successfully! Saved as: " + outputImagePath);
                    BufferedImage img = javax.imageio.ImageIO.read(new File(outputImagePath));
                    JFrame frame = new JFrame("ImageViewer");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(img.getWidth(), img.getHeight());

                    JLabel label = new JLabel(new ImageIcon(img));
                    frame.add(label);
                    frame.pack();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: Could not process the password.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Password and message are required. Exiting...");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No file selected. Exiting...");
        }
    }
}

