package utils.keyLock;

public interface KeyLock {

    public boolean lock(String key);

    public boolean unlock(String key);

}
