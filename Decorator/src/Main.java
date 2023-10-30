public class Main {
    public static void main(String[] args) {
        DataSource fileDataSource = new FileDataSource();
        DataSource encryptedFileDataSource = new EncryptionDecorator(fileDataSource);
        DataSource compressedEncryptedFileDataSource = new CompressionDecorator(encryptedFileDataSource);

        String content = "This is some sensitive data.";

        // Write 
        compressedEncryptedFileDataSource.write("encrypted_compressed_data.txt", content);

        // Read 
        compressedEncryptedFileDataSource.read("encrypted_compressed_data.txt");
    }
}
