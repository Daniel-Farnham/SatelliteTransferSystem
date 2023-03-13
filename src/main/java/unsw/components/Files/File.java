package unsw.components.Files;

public class File {
    private String filename;
    private String content;
    private int filesize;
    private boolean transferComplete; 

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

    public boolean transferComplete() {
        return transferComplete;
    }

}
