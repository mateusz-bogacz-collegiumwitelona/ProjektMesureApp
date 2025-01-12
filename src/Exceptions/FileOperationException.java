package Exceptions;

public class FileOperationException extends MeasurementException {
    private final String filePath;
    private final OperationType operationType;

    public enum OperationType {
        READ,
        WRITE,
        CREATE,
        DELETE
    }

    public FileOperationException(String filePath, OperationType operationType, String message) {
        super(message);
        this.filePath = filePath;
        this.operationType = operationType;
    }

    public FileOperationException(String filePath, OperationType operationType,
                                  String message, Throwable cause) {
        super(message, cause);
        this.filePath = filePath;
        this.operationType = operationType;
    }

    public String getFilePath() { return filePath; }
    public OperationType getOperationType() { return operationType; }
}
