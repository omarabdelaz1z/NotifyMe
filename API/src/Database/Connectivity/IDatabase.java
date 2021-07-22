package Database.Connectivity;

public interface IDatabase {
    void openConnection();
    Object getClientConnection();
}