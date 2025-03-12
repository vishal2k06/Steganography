package Steganography;

import Steganography.Encryption;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.ImageView;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SteganographyDecoding {
    public static String decode(String inputImagePath) {
        try {
            BufferedImage image = ImageIO.read(new File(inputImagePath));
            return extractMessage(image);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: Unable to decode the image.";
        }
    }

    private static String extractMessage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        StringBuilder binaryMessage = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int b = rgb & 0xFF;
                int lsb = b & 1;
                binaryMessage.append(lsb);
            }
        }

        return fromBinary(binaryMessage.toString());
    }

    private static String fromBinary(String binaryMessage) {
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < binaryMessage.length(); i += 8) {
            String byteString = binaryMessage.substring(i, i + 8);
            if (byteString.equals("00000000")) break;
            char c = (char) Integer.parseInt(byteString, 2);
            message.append(c);
        }
        return message.toString();
    }

    public static void GetFilePath(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an Image File");

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File inputFile = fileChooser.getSelectedFile();
            String imagePath = inputFile.getAbsolutePath();

            String password = JOptionPane.showInputDialog("Enter the password to decode the message:");

            if (password != null && !password.isEmpty()) {
                try {
                    String combinedMessage = decode(imagePath);
                    
                    String[] parts = combinedMessage.split("\\|", 2);

                    if (parts.length != 2) throw new Exception("Corrupted or invalid data.");

                    String storedHash = parts[0];
                    String originalMessage = parts[1];

                    String inputHash = Encryption.hash(password);
                    if (storedHash.equals(inputHash)) {
                        JOptionPane.showMessageDialog(null, "Decoded Message: " + originalMessage);
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect password. Access denied.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: Could not decode the message.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Password is required. Exiting...");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No file selected. Exiting...");
        }
    }
}
