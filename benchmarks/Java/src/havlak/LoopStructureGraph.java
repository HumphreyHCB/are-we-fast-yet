// Copyright 2011 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package havlak;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import som.Vector;

/**
 * LoopStructureGraph
 *
 * Maintain loop structure for a given CFG.
 *
 * Two values are maintained for this loop graph, depth, and nesting level.
 * For example:
 *
 * loop        nesting level    depth
 *----------------------------------------
 * loop-0      2                0
 *   loop-1    1                1
 *   loop-3    1                1
 *     loop-2  0                2
 *
 * @author rhundt
 */
final class LoopStructureGraph {

  private final SimpleLoop         root;
  private final List<SimpleLoop> loops;
  private int                      loopCounter;

  LoopStructureGraph() {
    loopCounter = 0;
    loops = new ArrayList<>();
    root = new SimpleLoop(null, true);
    root.setNestingLevel(0);
    root.setCounter(loopCounter);
    loopCounter += 1;
    loops.add(root);
  }

  public SimpleLoop createNewLoop(final BasicBlock bb, final boolean isReducible) {
    SimpleLoop loop = new SimpleLoop(bb, isReducible);
    loop.setCounter(loopCounter);
    loopCounter += 1;
    loops.add(loop);
    return loop;
  }

  public void calculateNestingLevel() {
    // link up all 1st level loops to artificial root node.
    for (SimpleLoop liter : loops) {
      if (!liter.isRoot() && liter.getParent() == null) {
        liter.setParent(root);
      }
    }

    // recursively traverse the tree and assign levels.
    calculateNestingLevelRec(root, 0);
  }

  private void calculateNestingLevelRec(final SimpleLoop loop, final int depth) {
    loop.setDepthLevel(depth);

    for (SimpleLoop childLoop : loop.getChildren()) {
      calculateNestingLevelRec(childLoop, depth + 1);

      loop.setNestingLevel(Math.max(
          loop.getNestingLevel(),
          1 + childLoop.getNestingLevel()));
    }
  }

  public int getNumLoops() {
    return loops.size();
  }
}
