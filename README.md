# cell-simulator

Your simulation context is Wild Fire:
https://docs.google.com/document/d/10UD1AevWZ05GC5VDAUwBhbRIA2qT3xDRTaw1K6fMpCg/edit?usp=sharing


- View: Maddie
  - changing color of squares in grid
  - take in info from Controller if the color needs to change
  - handles user interface buttons

- Model: Sibel
  - create grid
  - reset grid
  - determines initial state of square (edge, empty, live tree, burning tree, burnt down tree)
  - returns specific state of specific square to Controller
  
- Controller: Sophie
  - handles user interface interaction (methods for how trees have burnded)
  - change/status of grid
  - talks to both model and view
