def readFile (filename):
    data = []
    source = file (filename)
    for line in source:
        words = line.split ()
        for word in words:
            data.append (int (word))
    source.close ()
    return data

def parentIndex (index):
    return (index - 1) // 2

def leftChildIndex (index):
    return 2 * index + 1

def rightChildIndex (index):
    return 2 * index + 2

def upHeap (data, n):
    if n == 0:
        # the root has no parent, no upHeap possible
        return
    p = parentIndex (n)
    if data [p] >= data [n]:
        return #done
    else:
        data [p], data [n] = data [n], data [p] # swap
        upHeap (data, p)

def buildHeap (data):
    n = len (data)
    for i in range (n):
        upHeap (data, i)

def downHeap (data, n, stop):
    left = leftChildIndex (n)
    right = rightChildIndex (n)
    if left >= stop:
        # case 1: already a leave
        return
    elif left < stop and right >= stop:
        # case 2: only have a left child
        if data [n] < data [left]:
            data [n], data [left] = data [left], data [n] # swap
            downHeap (data, left, stop)
        else:
            return # done
    else:
        # case 3: has both left and right children
        if data [n] >= data [left] and data [n] >= data [right]:
            return # done
        else:
            largestChild = left
            if data [left] < data [right]:
                largestChild = right
            data [n], data [largestChild] = data [largestChild], data [n] # swap
            downHeap (data, largestChild, stop)

def sortHeap (data):
    n = len (data)
    for i in range (n-1, 0, -1):
        data [0], data [i] = data [i], data [0] # swap, and sorted element i
        downHeap (data, 0, i)

def inorder (data, n = 0, depth = 0):
    nn = len (data)
    if n >= nn:
        return []
    left = leftChildIndex (n)
    right = rightChildIndex (n)
    isRightChild = (n % 2) == 0 # only exception is for the root
    hasRightChild = (n * 2  + 2) < nn
    return inorder (data, left, depth+1) \
         + [(data [n], depth, isRightChild, hasRightChild)] \
         + inorder (data, right, depth+1)

def updateBars (bars, depth, isRightChild, hasRightChild):
    location1 = 5 * depth - 4 # location for level depth - 1
    location2 = location1 + 5 # location for level depth
    if len (bars) < location2:
        bars = bars + ' ' * (location2 - len (bars))
    if depth == 0:
        # this is the root, no bar to remove
        pass
    elif isRightChild:
        # remove the bar at level depth - 1
        bars = bars [:location1] + ' ' + bars [location1+1:]
    else:
        # add a bar at level depth - 1
        bars = bars [:location1] + '|' + bars [location1+1:]
    if not hasRightChild:
        # remove the bar at level depth, since no one else will remove it.
        bars = bars [:location2] + ' ' + bars [location2+1:]
    return bars.rstrip ()

def drawTree (data):
    bars = ''
    results = []
    items = inorder (data)
    for value, depth, isRightChild, hasRightChild in items:
        results.append (str (bars))
        valueStr = str (value).center (3)
        if not isRightChild:
            bars = updateBars (bars, depth, isRightChild, hasRightChild)
        if depth == 0:
            results.append (valueStr)
        else:
            barPrefix = (depth-1) * 5
            results.append ('%s +---%s' % (bars [:barPrefix], valueStr))
        if isRightChild:
            bars = updateBars (bars, depth, isRightChild, hasRightChild)
        results.append (str (bars))
    results.reverse ()
    return '\n'.join (results)

def sortData (data):
    buildHeap (data)
    result = drawTree (data)
    sortHeap (data)
    return result

def verifySorted (data):
    n = len (data)
    if n < 2:
        # list of length less than two is sorted by definition
        return True
    else:
        for i in range (1, n):
            if data [i-1] > data [i]:
                return False
        return True

if __name__ == '__main__':
    def processData (data):
        heap = sortData (data)
        if not verifySorted (data):
            print ('***** Warning: Data are not sorted. *****')
        print (heap)
        print (data)
        
    processData ([3,1,4,1,5,9,2,6,5,3,6])
    processData (readFile ('data.txt'))
    
