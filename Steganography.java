
import Steganography.SteganographyEncoding;
import Steganography.SteganographyDecoding;
import Steganography.Encryption;

import javax.swing.*;

class Main {
    public static void main(String[] args) {
        String[] options = {"Encode", "Decode"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose an option:",
                "Steganography Tool",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            // Encoding
            SteganographyEncoding.GetFilePath();
        } else if (choice == 1) {
            SteganographyDecoding.GetFilePath();
        } else {
            JOptionPane.showMessageDialog(null, "Operation cancelled.");
        }
    }
}
