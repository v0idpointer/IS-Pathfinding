package net.v0idpointer.is.ai;

public enum PathfindingAlgorithm {

    DEPTH_FIRST_SEARCH {

        @Override
        public String toString() {
            return "Depth-first search";
        }

    },

    BREADTH_FIRST_SEARCH {

        @Override
        public String toString() {
            return "Breadth-first search";
        }

    },

    BEST_FIRST_SEARCH {

        @Override
        public String toString() {
            return "Best-first search";
        }

    },

}
