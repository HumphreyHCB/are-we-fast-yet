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
import java.util.List;

/**
 * A simple class simulating the concept of Basic Blocks
 *
 * BasicBlock only maintains a list of in-edges and
 * a list of out-edges.
 *
 * @author rhundt
 */
final class BasicBlock {

  private final List<BasicBlock> inEdges;  // Replaced Vector with ArrayList
  private final List<BasicBlock> outEdges; // Replaced Vector with ArrayList
  private final int name;

  BasicBlock(final int name) {
    this.name = name;
    inEdges   = new ArrayList<>(2); // Initialize with a capacity of 2
    outEdges  = new ArrayList<>(2); // Initialize with a capacity of 2
  }

  public List<BasicBlock> getInEdges() {
    return inEdges;
  }

  public List<BasicBlock> getOutEdges() {
    return outEdges;
  }

  public int getNumPred() {
    return inEdges.size(); // No change needed
  }

  public void addOutEdge(final BasicBlock to) {
    outEdges.add(to); // Use add() instead of append()
  }

  public void addInEdge(final BasicBlock from) {
    inEdges.add(from); // Use add() instead of append()
  }

  public int getName() {
    return name;
  }

  @Override
  public int hashCode() {
    return name; // Custom hash remains the same
  }
}
