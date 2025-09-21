#include <vector>
#include <queue>
#include <limits>
#include <tuple>
#include <algorithm> // For std::min

class Solution {
public:
    vector<int> shortestAlternatingPaths(int n, vector<vector<int>>& redEdges, vector<vector<int>>& blueEdges) {
        // Build the adjacency list. Each pair stores {destination node, edge color}.
        // 0:red edges, 1:blue edges 
        vector<vector<pair<int, int>>> adj(n);
        for (auto& edge : redEdges)  adj[edge[0]].push_back({edge[1], 0});
        for (auto& edge : blueEdges) adj[edge[0]].push_back({edge[1], 1});
        
        vector<int> dist(n, INT_MAX); // std::numeric_limits<int>::max()
        
        // This 2D array tracks visited states to prevent cycles.
        // visited[node][color] becomes true if 'node' is reached via a 'color' edge.
        vector<vector<bool>> visited(n, vector<bool>(2, false));

        // Use a queue for BFS: {current node, distance, color of previous edge}
        queue<tuple<int, int, int>> q;

        // Initialize BFS from source node 0.
        dist[0] = 0;
        
        // Start BFS with "dummy" edges of both colors from node 0, with a distance of 0.
        // The color doesn't matter for the first node, but we need to track it for its neighbors.
        q.push({0, 0, 0}); // Start with red path (color 0)
        q.push({0, 0, 1}); // Start with blue path (color 1)
        
        // Since we are starting BFS from node 0, we can mark it as visited for both colors
        // to prevent cycles back to the start.
        visited[0][0] = true;
        visited[0][1] = true;

        while (!q.empty()) {
            auto [currentNode, currentDistance, prevEdgeColor] = q.front(); q.pop();

            // Explore neighbors of the current node
            for (auto& [neighbor, edgeColor] : adj[currentNode]) {
                // The core logic for alternating colors
                if (edgeColor != prevEdgeColor) {
                    // Check if this node-color state has been visited.
                    // This prevents visiting the same node with the same color edge,
                    // which could lead to an infinite loop in a cyclic graph.
                    if (!visited[neighbor][edgeColor]) {
                        visited[neighbor][edgeColor] = true;
                        
                        // Update the minimum distance to this neighbor.
                        // Since this is BFS, the first time we reach a node, it's the shortest path.
                        dist[neighbor] = std::min(dist[neighbor], currentDistance + 1);
                        
                        // Add the neighbor to the queue for the next level of BFS.
                        q.push({neighbor, currentDistance + 1, edgeColor});
                    }
                }
            }
        }

        // Convert any unreachable nodes to -1 as per the problem requirements.
        for (int i = 0; i < n; ++i) {
            if (dist[i] == std::numeric_limits<int>::max()) {
                dist[i] = -1;
            }
        }
        
        return dist;
    }
};