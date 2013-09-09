import sys
import os
import re
from sets import Set
import math

os.system('./../dataset/mku.sh')
#print "DONE"

def split(string):
    stringenders = re.compile('[|\t\n]*')
    stringlist = stringenders.split(string)
    return stringlist[0:len(stringlist)-1]

def getdata(filename):
    f = open('./../dataset/'+filename)
    lines = f.readlines()
    f.close()
    dataarray = []
    for i in range(len(lines)):
        dataarray.append(split(lines[i]))
    return dataarray

def Euclidean_Similarity(i,j,data):
    listi = []
    listj = []
    commonmovielist = []
    
    for k in range(len(data)):
        if(data[k][0]==i):
            listi.append(data[k][1])
        if(data[k][0]==j):
            listj.append(data[k][1])

    commonmovielist = [item for item in listi if item in listj]
    ans = 0.0

    for k in range(len(commonmovielist)):
        for l in range(len(data)):
            if(data[l][1]==commonmovielist[k]):
                if(data[l][0]==i):
                    valuei = int(data[l][2])
                if(data[l][0]==j):
                    valuej = int(data[l][2])
        ans += pow(valuei-valuej,2)

    if (ans==0.0):
        return 1000000000000

    return math.sqrt(ans)

def k_similar_users(i,data,k):
    similarusers = []
    for p in range(len(userinfo)/10):           #60 factor to be removed here
        j = userinfo[p][0]
        if(j!=i):
            similarusers.append([j,1/Euclidean_Similarity(i,j,data)])
    similarusers = sorted(similarusers,key=lambda a:a[1])
    return similarusers[0:k]

def predict1(users,data,movie,dictaux):
    numerator = 0.0
    denominator = 0.0
    for j in range(len(users)):
        if (movie in dictaux[users[j][0]]):
            numerator = numerator + users[j][1]*float(dictaux[users[j][0]][movie])
            denominator = denominator + users[j][1]
    if (denominator == 0):
        return 0
    return numerator/denominator

def build_dict(data):
    main = dict()
    helper = dict()
    for i in range(len(userinfo)):
        helper.clear()
        for j in range(len(userratings)):
            if(userratings[j][0]==userinfo[i][0]):
                helper[userratings[j][1]] = userratings[j][2]
        main[userinfo[i][0]] = helper
    return main


userratings = getdata('u.data')
movies = getdata('u.item')
genres = getdata('u.genre')
userinfo = getdata('u.user')

u1base = getdata('u1.base')
u1test = getdata('u1.test')

maindict = build_dict(userratings)

ans = 0

for i in range(len(u1test)/2000):
    similaruseri = k_similar_users(u1test[i][0],u1base,20)
    x = predict1(similaruseri,u1base,u1test[i][1],maindict)
    print "Prediction "+ str(x)
    ans = ans + pow( x - int(u1test[i][2]),2)
    print ans

print ans


