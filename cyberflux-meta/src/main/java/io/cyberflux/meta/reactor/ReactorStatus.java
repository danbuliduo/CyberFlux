package io.cyberflux.meta.reactor;

public enum ReactorStatus {
    /**
     * @brief Reactor Opening
     * @status Reactor::start() Under execution
     */
    OPENING(),
    /**
     * @brief Reactor Closing
     * @status Reactor::shutdown() Under execution
     */
    CLOSING(),
	/**
	 * @brief Reactor Invalid
	 */
    INVALID(),
	/**
	 * @brief Reactor Exeption
	 */
    EXCEPTION(),
	/**
	 * @brief Reactor Operation
	 */
    OPERATION()
}