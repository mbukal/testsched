#!/usr/bin/env python
import numpy as np
import matplotlib.mlab as mlab
import matplotlib.pyplot as plt

import sys

x = np.array(ast.literal_eval(sys.argv[1]))

# the histogram of the data
n, bins, patches = plt.hist(x, 50, normed=1, alpha=0.75)

# add a 'best fit' line
l = plt.plot(bins, y, 'r--', linewidth=1)

plt.xlabel('Duration')
plt.ylabel('Normed frequency')
plt.title(r'$\mathrm{Histogram\ of\ IQ:}\ \mu=100,\ \sigma=15$')
plt.axis([40, 160, 0, 0.03])
plt.grid(True)

plt.show()