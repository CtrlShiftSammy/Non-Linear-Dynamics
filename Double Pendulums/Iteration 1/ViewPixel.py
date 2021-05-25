import numpy as np
from PIL import Image
from array import *
rows, cols = (3,6000)
col = open ('D:\IIT Roorkee\Miscellaneous\Double Pendulums\Iteration 1\OutputColours.txt', 'r')
Matrix = [[None]*rows]*cols
i = 0
while i < 6000:
    j = 0
    while j < 3:
        Matrix[i][j] = int(float((col.readline()).strip()))
        j = j + 1
    print(Matrix[0][0], Matrix[0][1], Matrix[0][2])
    arr = np.zeros([100, 100, 3], dtype=np.uint8)
    arr[:,:] = [Matrix[0][0], Matrix[0][1], Matrix[0][2]]
    img = Image.fromarray(arr)
    name = "Frame"+str(i)
    filename = "D:\IIT Roorkee\Miscellaneous\Double Pendulums\Iteration 1\Colours1\%s.png" % name
    img.save(filename)
    i = i + 1
col.close()


