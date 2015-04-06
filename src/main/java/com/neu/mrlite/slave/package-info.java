/**
 * This package mainly contains subpackages and classes for slave-node JVM process 
 * (nodes which does the actual job computations using data from master-node).
 * It will have all the required functionalities - 
 * 1) health-check module - periodically pings the master to show this slave-node is alive, 
 * piggybacks the status information about its memory and and current job completion 
 * meta-data information
 * 2) Mapper Executor Service
 * 3) Reducer Executor Service
 * 4) In-memory job-results store
 * @author Pramod Khare
 */
package com.neu.mrlite.slave;