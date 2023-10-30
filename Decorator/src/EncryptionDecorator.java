public class EncryptionDecorator implements DataSource {
    private DataSource dataSource;

    public EncryptionDecorator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String read(String filePath) {
        String content = dataSource.read(filePath);
        String decryptedContent = decrypt(content);
        System.out.println("Decrypted Content:");
        System.out.println(decryptedContent);
		return null;
    }

    @Override
    public void write(String filePath, String content) {
        String encryptedContent = encrypt(content);
        dataSource.write(filePath, encryptedContent);
    }

    private String encrypt(String content) {
       
        byte[] bytes = content.getBytes();
        StringBuilder encryptedContent = new StringBuilder();
        for (byte b : bytes) {
            encryptedContent.append(String.format("%02x", b));
        }
        return encryptedContent.toString();
    }

    private String decrypt(String content) {
        
        StringBuilder decryptedContent = new StringBuilder();
        for (int i = 0; i < content.length(); i += 2) {
            String byteStr = content.substring(i, i + 2);
            int byteValue = Integer.parseInt(byteStr, 16);
            decryptedContent.append((char) byteValue);
        }
        return decryptedContent.toString();
    }
}
