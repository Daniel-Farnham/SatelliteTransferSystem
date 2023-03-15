package unsw.components.Files;

public interface FileCarrier {
    void addFile(File file);
    void removeFile(File file);
    int getFileCount();
}
