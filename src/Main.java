import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static final BigInteger module = new BigInteger("8280064579810533644487080439294704795447245093482107594667610146544746141828885095648238300216162725911416598758685240103294167057595750714080913894168699");
    private static BigInteger password;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        System.out.println("Выберите требуемое действие:\n" +
                "   1 - сгенерировать ключи;\n" +
                "   2 - зашифровать файл;\n" +
                "   3 - расшифровать расшифровать.");
        int mode = scanner.nextInt();
        scanner.nextLine();
        switch (mode) {
            case 1:
                generateKeys();
                break;
            case 2:
                encrypt();
                break;
            case 3:
                decrypt();
                break;
            default:
                System.out.println("Необходимо ввести число от 1 до 3 в соответствии с требуемым действием.");
        }

    }

    private static void decrypt() throws Exception {
        System.out.println("Введите ключ.");
        password = new BigInteger(scanner.next());
       System.out.println("Введите формат файла.");
        String format = scanner.next();
        byte[] key = (password.mod(module).toByteArray());
        AES aes = new AES(Arrays.copyOfRange(key, 0, 16));
        aes.decrypt(format);


    }

    private static void encrypt() throws Exception {
        System.out.println("Введите ключ.");
        password = new BigInteger(scanner.next());
        System.out.println("Введите путь к файлу.");
        String path = scanner.next();
        File file = new File(path);
        byte[] key = (password.mod(module).toByteArray());
        AES aes = new AES(Arrays.copyOfRange(key, 0, 16));
        aes.encrypt(file);
    }

    private static String FileToString(File file) throws IOException {

        FileInputStream fis = null;
        String str = "";


        fis = new FileInputStream(file);
        int content;
        while ((content = fis.read()) != -1) {
            // convert to char and display it
            str += (char) content;
        }

        return str;
    }


    public static void generateKeys() {
        System.out.println("Введите количество ключей.");
        int n = scanner.nextInt();
        Random random = new Random();
        password = new BigInteger(15, random);
        ArrayList<BigInteger> result = new ArrayList<BigInteger>();
        BigInteger local = password;
        for (int i = 0; i < n; i++) {
            local = local.add(module);
            result.add(local);
            System.out.println(local);
        }

    }
}
