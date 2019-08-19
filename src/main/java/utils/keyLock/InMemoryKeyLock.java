package utils.keyLock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

public enum  InMemoryKeyLock implements KeyLock {
    INSTANCE;

    private final ConcurrentMap<String, ReentrantLock> lock = new ConcurrentHashMap<>();
    @Override
    public boolean lock(String key) {
        ReentrantLock reentrantLock = new ReentrantLock();
        ReentrantLock existingLock = lock.putIfAbsent(key, reentrantLock);
        if (existingLock != null) {
            return false;
        }
        reentrantLock.lock();
        return true;
    }

    @Override
    public boolean unlock(String key) {
        ReentrantLock existingLock = lock.get(key);
        if (existingLock != null && existingLock.isHeldByCurrentThread()){
            existingLock.unlock();
               lock.remove(key);
               return true;
        }

        return false;
    }

}
