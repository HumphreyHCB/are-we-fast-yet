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



/**
 * A simple class simulating the concept of
 * a control flow graph.
 *
 * CFG maintains a list of nodes, plus a start node.
 * That's it.
 *
 * @author rhundt
 */
import java.util.ArrayList;
import java.util.List;
final class ControlFlowGraph {

  private final List<BasicBlock> basicBlockMap;
  private BasicBlock startNode;
  private final List<BasicBlockEdge> edgeList;

  ControlFlowGraph() {
    startNode = null;
    basicBlockMap = new ArrayList<>();
    edgeList = new ArrayList<>();
  }

  public BasicBlock createNode(final int name) {
    BasicBlock node;
    while (name >= basicBlockMap.size()) {
      basicBlockMap.add(null); // Extend list with nulls if index is out of bounds
    }
    if (basicBlockMap.get(name) != null) {
      node = basicBlockMap.get(name);
    } else {
      node = new BasicBlock(name);
      basicBlockMap.set(name, node);
    }

    if (getNumNodes() == 1) {
      startNode = node;
    }
    return node;
  }

  public void addEdge(final BasicBlockEdge edge) {
    edgeList.add(edge);
  }

  public int getNumNodes() {
    return basicBlockMap.size();
  }

  public BasicBlock getStartBasicBlock() {
    return startNode;
  }

  public List<BasicBlock> getBasicBlocks() {
    return basicBlockMap;
  }
}
