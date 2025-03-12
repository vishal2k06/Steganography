# Steganography Tool

## Overview
This project implements a simple steganography tool in Java, allowing users to hide and retrieve secret messages in image files. The tool secures messages using SHA-256 encryption and encodes them in the least significant bits of an image's pixel values.

## Features
- **Encoding:** Hides a secret message within an image.
- **Decoding:** Retrieves the hidden message from an encoded image.
- **Password Protection:** Ensures secure access to the hidden message using SHA-256 hashing.
- **Graphical User Interface (GUI):** Uses `JFileChooser` and `JOptionPane` for user interaction.

## Requirements
- Java Development Kit (JDK) 8 or later
- Java Swing for GUI components
- `javax.imageio.ImageIO` for image processing

## File Structure
```
Steganography/
│── Encryption.java             # Handles SHA-256 hashing for password protection
│── SteganographyEncoding.java  # Encodes messages into images
│── SteganographyDecoding.java  # Decodes messages from images
│── Main.java                   # Provides a user interface to choose encoding or decoding
│── README.md                   # Documentation file
```

## How It Works
### Encoding Process
1. The user selects an image file.
2. The user enters a secret message and a password.
3. The password is hashed using SHA-256 and stored with the message.
4. The message is converted into a binary format and embedded in the least significant bits of the image.
5. The modified image is saved as `output.png` in the same directory.

### Decoding Process
1. The user selects an encoded image file.
2. The user enters the password used during encoding.
3. The tool extracts the hidden binary message from the image.
4. It verifies the password hash before displaying the message.

## How to Run
1. Compile all Java files:
   ```sh
   javac Steganography/*.java
   ```
2. Run the main application:
   ```sh
   java Main
   ```

## Example Usage
- **Encoding:**
  - Select an image.
  - Enter a password.
  - Enter a message.
  - The tool creates `output.png` with the hidden message.
- **Decoding:**
  - Select the encoded image (`output.png`).
  - Enter the correct password.
  - The tool retrieves and displays the message.

## Notes
- Ensure the original image is large enough to store the message.
- If an incorrect password is entered, access is denied.
- The hidden message terminates with an 8-bit `00000000` sequence.
