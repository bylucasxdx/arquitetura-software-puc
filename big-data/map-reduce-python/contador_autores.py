import mincemeat
import glob
import csv
import re

text_files = glob.glob("C:\\Users\\Lucas\\Desktop\\map-reduce-python\\autores\\*")

def file_contents(file_name):
    f = open(file_name)
    try:
        return f.read()
    finally:
        f.close()

source = dict((file_name, file_contents(file_name)) for file_name in text_files)

def mapfn(k, v):
    print 'map ' + k
    import re
    from stopwords import allStopWords

    for line in v.splitlines():    
        authors = line.split(":::")[1].split("::")
        words = line.split(":::")[2].split()

        for word in words:
            word = word.lower()
            word = re.sub(u'[^a-zA-Z]', '', word.decode('utf-8'))
            if (word not in allStopWords and word != ''):
                for author in authors:
                    yield author, word

def reducefn(k, v):
    print 'reduce ' + k
    words = {}

    for index, item in enumerate(v):
        item = item.encode('utf-8')

        if (item not in words):
            words[item] = 0
        
        words[item] = words[item] + 1

    return words

s = mincemeat.Server()
s.datasource = source
s.mapfn = mapfn
s.reducefn = reducefn

results = s.run_server(password="changeme")

w = csv.writer(open("C:\\Users\\Lucas\\Desktop\\map-reduce-python\\result.csv", "w"))
for k, v in results.items():
    w.writerow([k, v])

