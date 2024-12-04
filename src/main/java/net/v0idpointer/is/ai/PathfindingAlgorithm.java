/*

    This project was developed as part
    of the Intelligent Systems course
    at Računarski fakultet (RAF).

    Copyright (c) 2024 V0idPointer
    Licensed under the MIT License

*/

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
