package edu.vuum.mocca;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

/**
 * @class SimpleSemaphore
 * 
 * @brief This class provides a simple counting semaphore implementation using
 *        Java a ReentrantLock and a ConditionObject. It must implement both
 *        "Fair" and "NonFair" semaphore semantics, just liked Java Semaphores.
 */
public class SimpleSemaphore {
    /**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here
	private final ReentrantLock mReentrantLock;

    /**
     * Define a ConditionObject to wait while the number of
     * permits is 0.
     */
    // TODO - you fill in here
	private final Condition mCondition;// = mReentrantLock.newCondition();

    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..
	private volatile int permits;

    public SimpleSemaphore(int permits, boolean fair) {
        // TODO - you fill in here to initialize the SimpleSemaphore,
        // making sure to allow both fair and non-fair Semaphore
        // semantics.
    	this.permits = permits;
    	mReentrantLock = new ReentrantLock(fair);
    	mCondition = mReentrantLock.newCondition();
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be
     * interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here.
    	mReentrantLock.lockInterruptibly();
    	try{
	    	while (permits <= 0){
	    		mReentrantLock.wait();
	    	}
	    	if (permits > 0){
	    		permits--;
	    	}
    	}
    	finally{
    		mReentrantLock.unlock();
    	}
    }

    /**
     * Acquire one permit from the semaphore in a manner that cannot be
     * interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here.
    	mReentrantLock.lock();
    	try{
	    	while (permits <= 0){
	    		mReentrantLock.wait();
	    	}
	    	if (permits > 0){
	    		permits--;
	    	}
    	} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally{
    		mReentrantLock.unlock();
    	}
    }

    /**
     * Return one permit to the semaphore.
     */
    void release() {
        // TODO - you fill in here.
    	mReentrantLock.lock();
    	try{
    		permits++;
    		mCondition.signalAll();
    	}
    	finally{
    		mReentrantLock.unlock();
    	}
    }

    /**
     * Return the number of permits available.
     */
    public int availablePermits() {
        // TODO - you fill in here by changing null to the appropriate
        // return value.
        return permits;
    }
}
