package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    final private List<Simulation> simulations;
    private final List<Thread> threads = new ArrayList<>();
    private final ExecutorService threadPool = Executors.newFixedThreadPool(4);

    SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
    }
    void runSync(){
        for (Simulation simulation : simulations) {
            simulation.run();
        }
    }

    void runAsync(){
        threads.clear();
        for (Simulation simulation : simulations) {
            Thread thread = new Thread(simulation);
            threads.add(thread);
            thread.start();
        }
    }
    public void awaitSimulationsEnd() throws InterruptedException{
        for (Thread thread : threads)
            thread.join();
        threads.clear();

        if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)){
            threadPool.shutdown();
        }
    }

    public void runAsyncInThreadPool() {
        for (Simulation simulation : simulations)
            threadPool.submit(simulation);
    }

}
