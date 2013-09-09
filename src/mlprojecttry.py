import sys
import os
import re
from sets import Set
import math
import pickle

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

#def Cosine_Similarity
#def Pearson_cofficient

def Euclidean_Similarity(i,j,dictaux):
    commonmovielist = set(dictaux[i].keys()).intersection(set(dictaux[j].keys()))
    ans = 0.0
    for k in commonmovielist:
        ans = ans + pow(int(dictaux[i][k])-int(dictaux[j][k]),2)

    if (ans==0.0):
        return 1000000000000
    return math.sqrt(ans)

def compute_similarity(userinfo,maindict):
    similarity = [[10000000 for x in xrange(len(userinfo))] for x in xrange(len(userinfo))] 
    for i in range(len(userinfo)-1):
        for j in range(i+1,len(userinfo)):
            temp = 1/(1+Euclidean_Similarity(userinfo[i][0],userinfo[j][0],maindict))
            similarity[i][j] = temp
            similarity[j][i] = temp
    return similarity            

def k_similar_users(i,data,k):
    similarusers = []
    for p in range(len(userinfo)):
        j = userinfo[p][0]
        if(j!=i):
            similarusers.append([j,similarity[int(i)-1][int(j)-1]])
    similarusers = sorted(similarusers,key=lambda a:a[1])
    return similarusers[0:k]


def k_watched_users(i,similarity,dictaux,movie,k):
    similarusers = []
    for j in range(len(userinfo)):
        if (j!=i):
            if (movie in dictaux[userinfo[j][0]]):
                similarusers.append([userinfo[j][0],float(dictaux[userinfo[j][0]][movie])])
    similarusers = sorted(similarusers,key=lambda a:a[1])
    if (len(similarusers)<k):
        return similarusers
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
    for i in range(len(userinfo)):
        helper=dict()
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



'''
print 'building_dictionary'
maindict = build_dict(u1base)
print 'building_similarity matrix'
similarity = compute_similarity(userinfo,maindict)
print 'done'

output = open('initialdata.pkl','wb')
pickle.dump(maindict,output)
pickle.dump(similarity,output,-1)
output.close()

'''
pkl_file = open('initialdata.pkl','rb')
maindict = pickle.load(pkl_file)
similarity = pickle.load(pkl_file)
pkl_file.close()



ans = 0

for i in range(len(u1test)):
    #print u1test[i]
    similaruseri = k_similar_users(u1test[i][0],u1base,20)
    #print similaruseri
    #print "---------\n"
    x = predict1(similaruseri,u1base,u1test[i][1],maindict)
    print "Prediction "+ str(x)
    ans = ans + pow( x - int(u1test[i][2]),2)
    #print ans

print ans

ans = 0

for i in range(len(u1test)):
    #print u1test[i]
    similaruseri = k_watched_users(u1test[i][0],similarity,maindict,u1test[i][1],20)
    #print similaruseri
    #print "---------\n"
    x = predict1(similaruseri,u1base,u1test[i][1],maindict)
    #print "Prediction "+ str(x)
    ans = ans + pow( x - int(u1test[i][2]),2)
    #print ans

print ans
