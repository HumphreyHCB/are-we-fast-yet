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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The Havlak loop finding algorithm.
 *
 * This class encapsulates the complete finder algorithm
 *
 * @author rhundt
 */
final class HavlakLoopFinder {

  private final ControlFlowGraph   cfg;      // Control Flow Graph
  private final LoopStructureGraph lsg;      // Loop Structure Graph

  // Marker for uninitialized nodes.
  private static final int UNVISITED = Integer.MAX_VALUE;

  // Safeguard against pathological algorithm behavior.
  private static final int MAXNONBACKPREDS = (32 * 1024);

  private final List<Set<Integer>> nonBackPreds = new ArrayList<>();
  private final List<List<Integer>> backPreds = new ArrayList<>();
  private final Map<BasicBlock, Integer> number = new HashMap<>();
  private int                      maxSize = 0;
  private int[]                    header;
  private BasicBlockClass[]        type;
  private int[]                    last;
  private UnionFindNode[]          nodes;

  HavlakLoopFinder(final ControlFlowGraph cfg, final LoopStructureGraph lsg) {
    this.cfg = cfg;
    this.lsg = lsg;
  }

  /**
   * enum BasicBlockClass
   *
   * Basic Blocks and Loops are being classified as regular, irreducible,
   * and so on. This enum contains a symbolic name for all these classifications
   */
  private enum BasicBlockClass {
    BB_TOP,          // uninitialized
    BB_NONHEADER,    // a regular BB
    BB_REDUCIBLE,    // reducible loop
    BB_SELF,         // single BB loop
    BB_IRREDUCIBLE,  // irreducible loop
    BB_DEAD,         // a dead BB
    BB_LAST          // Sentinel
  }

  //
  // IsAncestor
  //
  // As described in the paper, determine whether a node 'w' is a
  // "true" ancestor for node 'v'.
  //
  // Dominance can be tested quickly using a pre-order trick
  // for depth-first spanning trees. This is why DFS is the first
  // thing we run below.
  //
  private boolean isAncestor(final int w, final int v) {
    return w <= v && v <= last[w];
  }

  //
  // DFS - Depth-First-Search
  //
  // DESCRIPTION:
  // Simple depth first traversal along out edges with node numbering.
  //
  private int doDFS(final BasicBlock currentNode, final int current) {
    nodes[current].initNode(currentNode, current);
    number.put(currentNode, current);

    int lastId = current;
    List<BasicBlock> outerBlocks = currentNode.getOutEdges();

    for (BasicBlock target : outerBlocks) {
      if (number.getOrDefault(target, UNVISITED) == UNVISITED) {
        lastId = doDFS(target, lastId + 1);
      }
    }

    last[current] = lastId;
    return lastId;
  }

  private void initAllNodes() {
    // Step a:
    //   - initialize all nodes as unvisited.
    //   - depth-first traversal and numbering.
    //   - unreached BB's are marked as dead.
    //
    for (BasicBlock bb : cfg.getBasicBlocks()) {
      number.put(bb, UNVISITED);
    }
    doDFS(cfg.getStartBasicBlock(), 0);
  }

  private void identifyEdges(final int size) {
    // Step b:
    //   - iterate over all nodes.
    //
    //   A backedge comes from a descendant in the DFS tree, and non-backedges
    //   from non-descendants (following Tarjan).
    //
    //   - check incoming edges 'v' and add them to either
    //     - the list of backedges (backPreds) or
    //     - the list of non-backedges (nonBackPreds)
    //
    for (int w = 0; w < size; w++) {
      header[w] = 0;
      type[w] = BasicBlockClass.BB_NONHEADER;

      BasicBlock nodeW = nodes[w].getBb();
      if (nodeW == null) {
        type[w] = BasicBlockClass.BB_DEAD;
      } else {
        processEdges(nodeW, w);
      }
    }
  }

  private void processEdges(final BasicBlock nodeW, final int w) {
    for (BasicBlock nodeV : nodeW.getInEdges()) {
      int v = number.getOrDefault(nodeV, UNVISITED);
      if (v != UNVISITED) {
        if (isAncestor(w, v)) {
          backPreds.get(w).add(v);
        } else {
          nonBackPreds.get(w).add(v);
        }
      }
    }
  }

  //
  // findLoops
  //
  // Find loops and build loop forest using Havlak's algorithm, which
  // is derived from Tarjan. Variable names and step numbering has
  // been chosen to be identical to the nomenclature in Havlak's
  // paper (which, in turn, is similar to the one used by Tarjan).
  //
  public void findLoops() {
    if (cfg.getStartBasicBlock() == null) {
      return;
    }

    int size = cfg.getNumNodes();

    nonBackPreds.clear();
    backPreds.clear();
    number.clear();

    if (size > maxSize) {
      header = new int[size];
      type = new BasicBlockClass[size];
      last = new int[size];
      nodes = new UnionFindNode[size];
      maxSize = size;
    }

    for (int i = 0; i < size; ++i) {
      nonBackPreds.add(new HashSet<>());
      backPreds.add(new ArrayList<>());
      nodes[i] = new UnionFindNode();
    }

    initAllNodes();
    identifyEdges(size);

    // Start node is root of all other loops.
    header[0] = 0;

    // Step c:
    //
    // The outer loop, unchanged from Tarjan. It does nothing except
    // for those nodes which are the destinations of backedges.
    // For a header node w, we chase backward from the sources of the
    // backedges adding nodes to the set P, representing the body of
    // the loop headed by w.
    //
    // By running through the nodes in reverse of the DFST preorder,
    // we ensure that inner loop headers will be processed before the
    // headers for surrounding loops.
    //
    for (int w = size - 1; w >= 0; w--) {
      // this is 'P' in Havlak's paper
      List<UnionFindNode> nodePool = new ArrayList<>();
      BasicBlock nodeW = nodes[w].getBb();
      if (nodeW != null) {
        stepD(w, nodePool);

        List<UnionFindNode> workList = new ArrayList<>(nodePool);
        // Copy nodePool to workList.
        //

        if (!nodePool.isEmpty()) {
          type[w] = BasicBlockClass.BB_REDUCIBLE;
        }

        // work the list...
        //
        while (!workList.isEmpty()) {
          UnionFindNode x = workList.remove(0);
          // Step e:
          //
          // Step e represents the main difference from Tarjan's method.
          // Chasing upwards from the sources of a node w's backedges. If
          // there is a node y' that is not a descendant of w, w is marked
          // the header of an irreducible loop, there is another entry
          // into this loop that avoids w.
          //

          // The algorithm has degenerated. Break and
          // return in this case.
          //
          int nonBackSize = nonBackPreds.get(x.getDfsNumber()).size();
          if (nonBackSize > MAXNONBACKPREDS) {
            return;
          }

          stepEProcessNonBackPreds(w, nodePool, workList, x);
        }

        // Collapse/Unionize nodes in a SCC to a single node
        // For every SCC found, create a loop descriptor and link it in.
        //
        if (!nodePool.isEmpty() || type[w] == BasicBlockClass.BB_SELF) {
          SimpleLoop loop = lsg.createNewLoop(nodeW, type[w] != BasicBlockClass.BB_IRREDUCIBLE);
          setLoopAttributes(w, nodePool, loop);
        }
      }
    }  // Step c
  }  // findLoops

  private void stepEProcessNonBackPreds(final int w, final List<UnionFindNode> nodePool,
      final List<UnionFindNode> workList, final UnionFindNode x) {
    for (Integer iter : nonBackPreds.get(x.getDfsNumber())) {
      UnionFindNode y = nodes[iter];
      UnionFindNode ydash = y.findSet();

      if (!isAncestor(w, ydash.getDfsNumber())) {
        type[w] = BasicBlockClass.BB_IRREDUCIBLE;
        nonBackPreds.get(w).add(ydash.getDfsNumber());
      } else {
        if (ydash.getDfsNumber() != w && !nodePool.contains(ydash)) {
          workList.add(ydash);
          nodePool.add(ydash);
        }
      }
    }
  }

  private void setLoopAttributes(final int w, final List<UnionFindNode> nodePool, final SimpleLoop loop) {
    // At this point, one can set attributes to the loop, such as:
    //
    // the bottom node:
    //    iter  = backPreds[w].begin();
    //    loop bottom is: nodes[iter].node);
    //
    // the number of backedges:
    //    backPreds[w].size()
    //
    // whether this loop is reducible:
    //    type[w] != BasicBlockClass.BB_IRREDUCIBLE
    //
    for (UnionFindNode node : nodePool) {
      
      header[node.getDfsNumber()] = w;
      node.union(nodes[w]);

      if (node.getLoop() != null) {
        node.getLoop().setParent(loop);
      } else {
        loop.addNode(node.getBb());
      }
    }
  }

  private void stepD(final int w, final List<UnionFindNode> nodePool) {
    for (Integer v : backPreds.get(w)) {
      if (v != w) {
        nodePool.add(nodes[v].findSet());
      } else {
        type[w] = BasicBlockClass.BB_SELF;
      }
    }
  }
}
