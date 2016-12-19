package sample.algoritmos;

import com.google.ortools.constraintsolver.*;
import sample.tipos.Coordenada;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jol on 19/12/16.
 */
public class ORTools {
    static { System.loadLibrary("jniortools"); }

    static class RandomManhattan extends NodeEvaluator2 {
        public RandomManhattan(List<Coordenada> lista) {
            this.xs = new int[lista.size()];
            this.ys = new int[lista.size()];

            for (int i = 0; i < lista.size(); ++i) {
                xs[i] = (int) (lista.get(i).getLon()*100);
                ys[i] = (int) (lista.get(i).getLat()*100);
            }
        }

        @Override
        public long run(int firstIndex, int secondIndex) {
            return Math.abs(xs[firstIndex] - xs[secondIndex]) +
                    Math.abs(ys[firstIndex] - ys[secondIndex]);
        }

        private int[] xs;
        private int[] ys;
    }

    static class ConstantCallback extends NodeEvaluator2 {
        @Override
        public long run(int firstIndex, int secondIndex) {
            return 1;
        }
    }

    public static List<Integer> resolver(List<Coordenada> lista) {
        try {
            RoutingModel routing = new RoutingModel(lista.size(), 1, 0);

            // Setting the cost function.
            // Put a permanent callback to the distance accessor here. The callback
            // has the following signature: ResultCallback2<int64, int64, int64>.
            // The two arguments are the from and to node inidices.
            RandomManhattan distances = new RandomManhattan(lista);
            routing.setCost(distances);

            // Add dummy dimension to test API.
            routing.addDimension(
                    new ConstantCallback(),
                    lista.size() + 1,
                    lista.size() + 1,
                    true,
                    "dummy");

            // Solve, returns a solution if any (owned by RoutingModel).
            RoutingSearchParameters search_parameters =
                    RoutingSearchParameters.newBuilder()
                            .mergeFrom(RoutingModel.defaultSearchParameters())
                            .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                            .build();

            ArrayList<Integer> resultado = new ArrayList<>();
            Assignment solution = routing.solveWithParameters(search_parameters);
            if (solution != null) {
                // Solution cost.
                System.out.println("Cost = " + solution.objectiveValue());
                // Inspect solution.
                // Only one route here; otherwise iterate from 0 to routing.vehicles() - 1
                int route_number = 0;
                for (long node = routing.start(route_number); !routing.isEnd(node); node = solution.value(routing.nextVar(node))) {
                    System.out.print("" + node + " -> ");
                    resultado.add((int) node);
                }
                System.out.println("0");
            }

            return resultado;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
