#!/usr/bin/env python
import numpy as np
import matplotlib.mlab as mlab
import matplotlib.pyplot as plt
import ast
import sys

x = np.array(ast.literal_eval(sys.argv[1]))
hist, bins = np.histogram(x, bins=50, normed=True)
width = 1 * (bins[1] - bins[0])
center = (bins[:-1] + bins[1:]) / 2
plt.bar(center, hist, align='center', width=width)
plt.xlabel('trajanje')
plt.ylabel('udio')
plt.show()