// Copyright 2022 Kunshan Wang
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to
// deal in the Software without restriction, including without limitation the
// rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
// sell copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
// FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
// IN THE SOFTWARE.

class Node {
    int n;
    Node left;
    Node right;

    Node(int n, Node left, Node right) {
        this.n = n;
        this.left = left;
        this.right = right;
    }

    static Node makeTree(int depth) {
        if (depth == 0) {
            return null;
        } else {
            return new Node(depth, makeTree(depth - 1), makeTree(depth - 1));
        }
    }
}

public class TraceTest {
    public static void main(String[] args) {
        int depth = Integer.parseInt(args[0]);
        int iterations = Integer.parseInt(args[1]);
        int warmups = Integer.parseInt(args[2]);
        long[] gctimes = new long[iterations];

        Node tree = Node.makeTree(depth);

        for (int i = 0; i < warmups; i++) {
            System.gc();
        }

        for (int i = 0; i < iterations; i++) {
            long time1 = System.nanoTime();
            System.gc();
            long time2 = System.nanoTime();

            gctimes[i] = time2 - time1;
        }

        for (long gctime: gctimes) {
            System.out.println(gctime);
        }
    }
}
