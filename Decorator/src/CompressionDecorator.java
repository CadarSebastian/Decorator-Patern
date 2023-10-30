public class CompressionDecorator implements DataSource {
    private DataSource dataSource;

    public CompressionDecorator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String read(String filePath) {
        String compressedContent = dataSource.read(filePath);
        String decompressedContent = decompress(compressedContent);
        System.out.println("Decompressed Content:");
        System.out.println(decompressedContent);
		return null;
    }

    @Override
    public void write(String filePath, String content) {
        String compressedContent = compress(content);
        dataSource.write(filePath, compressedContent);
    }

    private String compress(String content) {
        StringBuilder compressedContent = new StringBuilder();
        int length = content.length();
        int count = 1;

        for (int i = 1; i < length; i++) {
            if (content.charAt(i) == content.charAt(i - 1)) {
                count++;
            } else {
                compressedContent.append(content.charAt(i - 1));
                compressedContent.append(count);
                count = 1;
            }
        }
        compressedContent.append(content.charAt(length - 1));
        compressedContent.append(count);

        return compressedContent.toString();
    }

    private String decompress(String content) {
        StringBuilder decompressedContent = new StringBuilder();
        int length = content.length();
        int i = 0;

        while (i < length) {
            char c = content.charAt(i);
            i++;
            int count = Integer.parseInt(String.valueOf(content.charAt(i)));
            i++;

            for (int j = 0; j < count; j++) {
                decompressedContent.append(c);
            }
        }

        return decompressedContent.toString();
    }
}
