package sharklaserz.weather.base;

import dagger.ObjectGraph;

public class Injector {

    private static ObjectGraph graph;

    public static void setGraph(ObjectGraph graph) {
        Injector.graph = graph;
    }

    public static ObjectGraph getGraph() {
        return graph;
    }

    public static <T> void inject(T target) {
        graph.inject(target);
    }
}