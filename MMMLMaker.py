import random

def stateAdd(num):
    string="states:"
    for i in range(num):
        string+="q"+str(i)+","
    string=string[:-1]
    return string+"\n"
def initStateAdd(num):
    string="initial_states:"
    for i in range(num):
        string+="q"+str(i)+","
    string=string[:-1]
    return string+"\n"

def labelAdd(num):
    string = "lables:"
    for i in range(1, num + 1):
        string += chr(i+96)+ ","
    string = string[:-1]
    return string+"\n"

def lableFunction(states,labels):
    states=states[:-1]
    labels=labels[:-1]
    stateList=states.replace("states:","").split(",")
    labelsList=labels.replace("lables:","").split(",")
    string="lables_function:"
    for i in range(len(stateList)):
        l=labelsList[random.randint(0,len(labelsList)-1)]
        string+=stateList[i]+":"+l+","
    string=string[:-1]
    return string+"\n"
def transitionRelationAdd(states):
    string="transition_relation:"
    states = states[:-1]
    stateList = states.replace("states:", "").split(",")
    transDict={}
    for i in range(len(stateList)):
         degNum=random.randint(1,2)
         for j in range(degNum):
             index=random.randint(0,len(stateList)-1)
             while stateList[index]==stateList[i]:
                 index = random.randint(0, len(stateList)-1)
             key=stateList[i]+":"+stateList[index]
             key2=stateList[index]+":"+stateList[i]
             if key in transDict or key2 in transDict:
                 continue
             else:
                 transDict[key]=1
                 transDict[key2]=1
                 string += key + ","
    string=string[:-1]
    return string+"\n"
def create_kripke(filemodel,fileltl,numStates,numinitstates,numlabels):
    states=stateAdd(numStates)
    filemodel.write(states)
    initstate=initStateAdd(numinitstates)
    filemodel.write(initstate)
    labels=labelAdd(numlabels)
    filemodel.write(labels)
    filemodel.write(lableFunction(states,labels))
    filemodel.write(transitionRelationAdd(states))
    filemodel.write("END")
    ranNum=random.randint(0,1)
    eachOrEvery="EACH:{}" if ranNum==1 else "EVERY:{}"
    fileltl.write(eachOrEvery)

multimodel = open("multiModel.txt", "w")
multiltl = open("multiLTL.txt", "w")
numberOfKripke=int(input("Please enter number of kripke desirded: \n"))
numberOfstates=int(input("Please enter number of states desirded: \n"))
numberOfIstates=int(input("Please enter number of inital states desirded: \n"))
numberOfAtomic=int(input("Please enter number of atomic prop desirded: \n"))
for i in range(numberOfKripke-1):
    create_kripke(multimodel,multiltl,numberOfstates,numberOfIstates,numberOfAtomic)
    multimodel.write("\n")
    multiltl.write("\n")
create_kripke(multimodel,multiltl,numberOfstates,numberOfIstates,numberOfAtomic)
multimodel.close()
multiltl.close()