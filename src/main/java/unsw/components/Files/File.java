package unsw.components.Files;

import java.util.List;

public class File {
    private String filename;
    private String content;
    private int filesize;
    private boolean transferComplete; 
    private static List<File> fileList; 

    public File(String filename, String content) {
        this.filename = filename;
        this.content = content;
        this.filesize = content.length();
        this.transferComplete = true; 
    }

    public String getFilename() {
        return filename;
    }

    public String getContent() {
        return content;
    }

    public int getFilesize() {
        return filesize;
    }

    // make test
    public static String getContentfromFileName(String fileName) {
        String relevantContent = null; 

        for (int i = 0; i < fileList.size(); i++) {
            if (fileList.get(i).getFilename().equals(fileName)) {
                relevantContent = fileList.get(i).getContent();
                return relevantContent; 
            }
        }
        return relevantContent;
    }

    public boolean transferComplete() {
        return transferComplete;
    }

}
