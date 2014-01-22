package org.smartcampus.simulation.framework.simulator;

/**
 * Implementing this interface allows an object to be the target for the method transform.
 * The generics parameter :
 * - T correspond to the return of the associative Law's method 'evaluate'
 * - R correspond to the HTTP request value
 */
public interface SensorTransformation<T, R> {
    /**
     * 
     * Transform a T value into a R result.
     * 
     * @param val
     *            , the return type of the associative Law
     * @param lastVal
     *            , the last value return by the sensor
     * @return the HTTP request value
     */
    public R transform(T val, R lastVal);
}
