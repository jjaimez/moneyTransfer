package utils.keyLock;

import dependencies.DependencyFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class InMemoryKeyLockTest {

    KeyLock keyLock = DependencyFactory.getKeyLock();

    @Test
    void lockShouldLock() {
        assertTrue(keyLock.lock("test1"));
    }

    @Test
    void lockShouldBeLocked() {
        keyLock.lock("test");
        assertFalse(keyLock.lock("test"));
    }

    @Test
    void unlockShouldUnlock() {
        keyLock.lock("testUnlock");
        boolean unlock = keyLock.unlock("testUnlock");
        assertTrue(unlock);
    }

    @Test
    void unlockShouldNotUnlock() {
        assertFalse(keyLock.unlock("failUnlock"));
    }

    @Test
    void unlockShouldNotUnlockByOtherThread() {

        keyLock.lock("testUnlock");

        new Thread(() -> keyLock.unlock("testUnlock")).start();

        assertFalse(keyLock.lock("testUnlock"));

        boolean unlock = keyLock.unlock("testUnlock");
        assertTrue(unlock);
    }




}