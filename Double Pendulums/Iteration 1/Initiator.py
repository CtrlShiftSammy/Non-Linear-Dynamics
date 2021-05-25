from PIL import Image
from numpy import asarray
import numpy as np
# load the image
image = Image.open('BaseImage.jpg')
# convert image to numpy array
data = asarray(image)
print(data.shape)

image2 = Image.fromarray(data)
print(type(image2))

# summarize image details
print(image2.mode)
print(image2.size)

#print(data)
name = "test_file"
Image.fromarray(data).save("%s.jpeg" % name)

a_file = open("1.txt", "w")

print(str(data.shape))
a=0
while a < 128:
    b=0
    while b < 128:
        c=0
        while c < 3:
            a_file.write(str(data[a,b,c]))
            c = c + 1
            a_file.write("  ")
        b = b + 1
        a_file.write("\n")
    a = a + 1
a_file.close()
