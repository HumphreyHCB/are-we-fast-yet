/*
 * This benchmark is derived from Mario Wolczko's Java and Smalltalk version of
 * DeltaBlue.
 *
 * It is modified to use the SOM class library and Java 8 features.
 * License details:
 *   http://web.archive.org/web/20050825101121/http://www.sunlabs.com/people/mario/java_benchmarking/index.html
 */
package deltablue;

import java.util.Vector;

// A Plan is an ordered list of constraints to be executed in sequence
// to resatisfy all currently satisfiable constraints in the face of
// one or more changing inputs.
final class Plan {
    private final Vector<AbstractConstraint> constraints;

    Plan() {
        this.constraints = new Vector<>(15); // Maintain initial capacity of 15
    }

    // Maintain compatibility by accepting Object
    public void append(Object obj) {
      if (obj instanceof AbstractConstraint) {
          constraints.add((AbstractConstraint) obj);
      } else {
          throw new IllegalArgumentException("Only AbstractConstraint objects can be appended to Plan");
      }
  }

    public void execute() {
        for (AbstractConstraint c : constraints) {
            c.execute(); // Execute each constraint
        }
    }
}
