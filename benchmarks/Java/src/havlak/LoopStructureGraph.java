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

import som.ForEachInterface;
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
  private final Vector<SimpleLoop> loops;
  private int                      loopCounter;

  LoopStructureGraph() {
    loopCounter = 0;
    loops = new Vector<>();
    root = new SimpleLoop(null, true);
    root.setNestingLevel(0);
    root.setCounter(loopCounter);
    loopCounter += 1;
    loops.append(root);
  }

  public SimpleLoop createNewLoop(final BasicBlock bb, final boolean isReducible) {
    SimpleLoop loop = new SimpleLoop(bb, isReducible);
    loop.setCounter(loopCounter);
    loopCounter += 1;
    loops.append(loop);
    return loop;
  }

  public void calculateNestingLevel() {
    // Link up all 1st level loops to the artificial root node.
    loops.forEach(new LinkFirstLevelLoopsTask(root));


    // recursively traverse the tree and assign levels.
    calculateNestingLevelRec(root, 0);
  }

  public void calculateNestingLevelRec(final SimpleLoop loop, final int depth) {
    loop.setDepthLevel(depth);
    loop.getChildren().forEach(new AssignNestingLevelsTask(this, loop, depth));
  }


  public int getNumLoops() {
    return loops.size();
  }
}

class LinkFirstLevelLoopsTask implements ForEachInterface<SimpleLoop> {
  private final SimpleLoop root;

  public LinkFirstLevelLoopsTask(SimpleLoop root) {
    this.root = root;
  }

  @Override
  public void apply(SimpleLoop liter) {
    if (!liter.isRoot()) {
      if (liter.getParent() == null) {
        liter.setParent(root);
      }
    }
  }
}

class AssignNestingLevelsTask implements ForEachInterface<SimpleLoop> {
  private final LoopStructureGraph graph;
  private final SimpleLoop parentLoop;
  private final int parentDepth;

  public AssignNestingLevelsTask(LoopStructureGraph graph, SimpleLoop parentLoop, int parentDepth) {
    this.graph = graph;
    this.parentLoop = parentLoop;
    this.parentDepth = parentDepth;
  }

  @Override
  public void apply(SimpleLoop liter) {
    graph.calculateNestingLevelRec(liter, parentDepth + 1);
    parentLoop.setNestingLevel(
        Math.max(parentLoop.getNestingLevel(), 1 + liter.getNestingLevel()));
  }
}


