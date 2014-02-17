# HMapGen

hmapgen is a small Java library that allows you to generate and render heightmap data. Heightmaps are created using the diamond-square algorithm.

Generating data for a 256x256 heightmap:
```
   HeightMapGenerator g = new HeightMapGenerator();
   g.setSize(256,256);

   double[][] data = g.generate();   
```

Rendering heightmap data:
```
   HeightMapRenderer r = new HeightMapRenderer(data)
   r.show();
```

## Screenshots

Example raw output from hmapgen.

![alt text](http://turtlesort.com/imgs/hmapgen_demo1.png "Example 1") 

![alt text](http://turtlesort.com/imgs/hmapgen_demo2.png "Example 2")


A world map created using hmapgen (this functionality is not part of the core library, but the example class that created this map is included in the repository).

![alt text](http://turtlesort.com/imgs/thumb_hmapgen.png "Example 3")

# License


Copyright (c) 2012, Kevin Sacro

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice, this
  list of conditions and the following disclaimer in the documentation and/or
  other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
