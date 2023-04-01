import java.util.Scanner;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class DES {
    private static final String ALGORITHM = "DES";
    private static final byte[] keyValue = new byte[8];

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Запрашиваем ключ у пользователя и сохраняем его в массиве keyValue
        System.out.print("Введите ключ (8 байт): ");
        String keyString = scanner.nextLine();
        byte[] keyBytes = keyString.getBytes();
        for (int i = 0; i < 8; i++) {
            if (i < keyBytes.length) {
                keyValue[i] = keyBytes[i];
            } else {
                keyValue[i] = 0;
            }
        }

        // Запрашиваем операцию у пользователя (шифрование или дешифрование)
        System.out.print("Выберите операцию (0 - зашифровать, 1 - дешифровать): ");
        int operation = scanner.nextInt();
        scanner.nextLine(); // Чтение символа новой строки после ввода числа

        // Запрашиваем текст у пользователя и сохраняем его в переменной inputText
        System.out.print("Введите текст: ");
        String inputText = scanner.nextLine();

        // Создаем экземпляр объекта Cipher для шифрования или дешифрования
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec key = new SecretKeySpec(keyValue, ALGORITHM);

        byte[] outputBytes = null;
        String outputText = null;

        if (operation == 0) {
            // Шифруем текст
            cipher.init(Cipher.ENCRYPT_MODE, key);
            outputBytes = cipher.doFinal(inputText.getBytes());
            outputText = bytesToHex(outputBytes);
        } else if (operation == 1) {
            // Дешифруем текст
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] inputBytes = hexToBytes(inputText);
            outputBytes = cipher.doFinal(inputBytes);
            outputText = new String(outputBytes);
        } else {
            System.out.println("Некорректный выбор операции!");
            return;
        }

        // Выводим результат операции
        System.out.println("Результат операции: " + outputText);
    }

    public static String bytesToHex(byte[] bytes) { //двоичный в текст
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
        public static byte[] hexToBytes(String hexString) { //текст в двоичный
            int len = hexString.length();
            byte[] result = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                result[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                        + Character.digit(hexString.charAt(i+1), 16));
            }
            return result;
        }
    }
